package fr.eni.encheres.hmi.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.hmi.ViewModel;
import fr.eni.encheres.hmi.bean.UserBean;
import fr.eni.encheres.tool.Messages;

/**
 * Servlet implementation class ServletVente
 */

@WebServlet({"/Vente","/VenteDelete"})

public class ServletVente extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(Messages.get(Messages.LABEL_DATETIME_VALUE_PATTERN));
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Requete /VenteDelete : suppression de l'article et retour à la liste d'enchère
		if(request.getServletPath().equals("/VenteDelete")) {
			processVenteDelete(request, response);
		} else {
			processVente(request, response);
		}
	}
	
	// Requete /Vente : traitement de l'article
	private void processVente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Verif session
		UserBean user = checkUser(request, response);
		
		//Prise en compte du paramètre "action" déterminant le comportement de la page vente.jsp
		//create : création d'un article et sa mise aux enchères
		//modify : affichage et modification d'un article défini par le paramètre "no_article"
		//view   : affichage d'un article défini par le paramètre "no_article"
		if(request.getAttribute("action")==null) {
			request.setAttribute("action", request.getParameter("action"));
			request.setAttribute("no_article", request.getParameter("no_article"));
			request.setAttribute("title", request.getParameter("title"));
		}
		
		//En mode modify et view, Alimentation des champs de la page
		ViewModel viewModel;
		List<String> alerts = new ArrayList<String>();

		// Hors création, la consultation/surenchère ou la modification d'une vente dépend du vendeur et du jour en cours
		if( !request.getAttribute("action").equals("create") ) {
			viewModel = loadModelFromArticle(Integer.parseInt((String) request.getAttribute("no_article")), alerts);
			String action = overrideAction(viewModel, user, LocalDateTime.now(), alerts);
			request.setAttribute("action", action);

		// En création, on initialise le modèle de vue
		} else {
			viewModel = initModelFromUser(user, alerts);
		}
		viewModel.putIntoRequest(request);
		if(alerts.size()>0) {
			request.setAttribute("alerts", alerts);
		}

		// Redirection vers la page vente.jsp
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/vente.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Verif session
		UserBean user = checkUser(request, response);

		// recupération des champs du formulaire dans le modèle de vue
		ViewModel viewModel = new ViewModel();
		viewModel.getFromRequest(request, 
			new String[] {"action","no_article","title","in_item","ta_description","sl_category","in_picture","in_price","dt_begin","dt_end","in_street","in_zip",
				"in_city"});
		// Vérif erreurs de saisie
		List<String> alerts = new ArrayList<String>();
		checkModel(viewModel, alerts);

		// Si pas d'erreurs de saisie, on tente la sauvegarde en base 
		if(alerts.size()==0) {
			ArticleVendu article = getArticleFromModel(viewModel, user);
			Retrait retrait = getRetraitFromModel(viewModel, article);
			try {
				// Insertion ou maj utilisateur
				ArticleManager.getInstance().saveArticle(article, retrait);

			} catch(BLLException e) {
				// si violations de contraintes, on les ajoute aux alertes utilisateur
				alerts.add(e.getUserMessage());
			}
		}

		// Si présence d'erreurs, on réalimente la page avec le modèle de vue et les alertes
		if(alerts.size()>0) {
			viewModel.putIntoRequest(request);
			request.setAttribute("alerts", alerts);
			// Rafraîchissement de la page
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/vente.jsp");
			rd.forward(request, response);
		
		} else {
			// redirection vers page Accueil
			response.sendRedirect(request.getContextPath()+"/ListeEncheres");
		}
	}

	private UserBean checkUser(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		UserBean r = (UserBean)request.getSession().getAttribute("user");
		if(r==null) {
			response.sendRedirect(request.getContextPath()+"/Deconnexion");
		}
		return r;
	}
	
	private String overrideAction(ViewModel model, UserBean user, LocalDateTime now, List<String> alerts) {
		String r="view";
		try {
			ArticleVendu article =  ArticleManager.getInstance().readArticle(model.getInteger("no_article"));
			// Si le user est le vendeur et que la vente n'est pas commencée
			if( user.getNoUtilisateur().equals(article.getVenduPar().getNoUtilisateur())
				&& now.isBefore(article.getDateDebutEncheres()) ) {
				r = "update";
			}
			
		} catch (BLLException e) {
			alerts.add(e.getUserMessage());
		} 
		return r;
	}
	
	// Charge un modèle de vue avec un article existant
	// En cas d'erreur, enrichit la liste d'alertes à afficher
	private ViewModel loadModelFromArticle(Integer noArticle, List<String> alerts) {
		ViewModel r = new ViewModel();
		r.put("no_article",String.valueOf(noArticle));
		try {
			ArticleVendu article = ArticleManager.getInstance().readArticle(noArticle);
			r.put("in_item",article.getNomArticle());
			r.put("ta_description",article.getDescription());
			r.put("sl_category",String.valueOf(article.getCategorieArticle().getNoCategorie()));
			r.put("in_picture",article.getCheminPhoto()!=null ? article.getCheminPhoto().trim() : "");
			r.put("in_price",String.valueOf(article.getMiseAPrix()));
			r.put("dt_begin",article.getDateDebutEncheres().format(DATETIME_FORMATTER));
			r.put("dt_end",article.getDateFinEncheres().format(DATETIME_FORMATTER));
			r.put("in_street",article.getRetrait().getRue());
			r.put("in_zip",article.getRetrait().getCodePostal());
			r.put("in_city",article.getRetrait().getVille());
			
		} catch (BLLException e) {
			alerts.add(e.getUserMessage());
		}
		return r;
	}

	// Initialise un modèle de vue pour une création de vente
	private ViewModel initModelFromUser(UserBean user, List<String> alerts) {
		ViewModel r = new ViewModel();
		r.put("in_item",null);
		r.put("ta_description",null);
		r.put("sl_category","-1");
		r.put("in_picture","");
		r.put("in_price","0");
		// Initialise debut de la vente au lendemain à 00:00 
		LocalDateTime datetime = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		r.put("dt_begin",datetime.format(DATETIME_FORMATTER));
		// initialise fin de la vente à une semaine plus tard à 23:59 
		datetime = LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(0).withNano(0);
		r.put("dt_end", datetime.format(DATETIME_FORMATTER));
		try {
			// initalise l'adresse de retrait avec celle de l'utilisateur connecté
			Utilisateur utilisateur = UtilisateurManager.getInstance().readUtilisateur(user.getNoUtilisateur());
			r.put("in_street",utilisateur.getRue());
			r.put("in_zip",utilisateur.getCodePostal());
			r.put("in_city",utilisateur.getVille());
		} catch (BLLException e) {
			alerts.add(e.getUserMessage());
		}
		return r;
	}

	// Vérif erreurs de saisie et alimente une liste d'alertes
	private void checkModel(ViewModel viewModel, List<String> alerts) {
		// Mot de passe vide
		String idCategorie = viewModel.getString("sl_category");
		if(idCategorie==null || idCategorie.equals("-1")) {
			alerts.add(Messages.get(Messages.ALERT_VENTE_CATEGORIE));
			viewModel.put("sl_category","-1");
		}
		// Dates incorrectes
		LocalDateTime dateDebut = LocalDateTime.parse(viewModel.getString("dt_begin"),DATETIME_FORMATTER);
		LocalDateTime dateFin = LocalDateTime.parse(viewModel.getString("dt_end"),DATETIME_FORMATTER);
		if( dateDebut.compareTo(dateFin)>0 ) {
			alerts.add(Messages.get(Messages.ALERT_VENTE_DATES));
		}
	}

	// Instanciation d'un article à partir du modèle de vue
	private ArticleVendu getArticleFromModel(ViewModel viewModel, UserBean user) {
		ArticleVendu r = new ArticleVendu();
		r.setNoArticle(viewModel.getInteger("no_article"));
		r.setNomArticle(viewModel.getString("in_item"));
		r.setDescription(viewModel.getString("ta_description"));
		r.setCheminPhoto(viewModel.getString("in_picture"));
		r.setMiseAPrix(viewModel.getInteger("in_price"));
		r.setPrixVente(0);
		r.setDateDebutEncheres(LocalDateTime.parse(viewModel.getString("dt_begin"),DATETIME_FORMATTER));
		r.setDateFinEncheres(LocalDateTime.parse(viewModel.getString("dt_end"),DATETIME_FORMATTER));
		r.setAchetePar(null);
		r.setVenduPar(new Utilisateur(user.getNoUtilisateur()));
		r.setCategorieArticle(new Categorie(viewModel.getInteger("sl_category")));
		return r;
	}
		
	// Instanciation d'un article à partir du modèle de vue
	private Retrait getRetraitFromModel(ViewModel viewModel, ArticleVendu article) {
		Retrait r = new Retrait();
		r.setRue(viewModel.getString("in_street"));
		r.setCodePostal(viewModel.getString("in_zip"));
		r.setVille(viewModel.getString("in_city"));
		r.setArticleVendu(article);
		return r;
	}

	// Requete /VenteDelete : suppression de l'article et retour à la liste d'enchère
	private void processVenteDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Récupère l'article dans la request
		String noArticle = request.getParameter("no_article");
		if(noArticle!=null) {
			try {
				// Supprime l'article de la base
				ArticleManager.getInstance().deleteArticle(Integer.parseInt(noArticle));
				
			} catch (BLLException e) {
				e.printStackTrace();
			}
		}		
		// Déconnexion
		response.sendRedirect(request.getContextPath()+"/ListeEncheres");
	}

}
