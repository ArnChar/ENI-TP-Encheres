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
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.hmi.ViewModel;
import fr.eni.encheres.hmi.bean.CarteArticleBean;
import fr.eni.encheres.hmi.bean.UserBean;
import fr.eni.encheres.tool.Messages;

/**
 * Servlet implementation class ServletListeEncheres
 */

@WebServlet("/ListeEncheres")

public class ServletListeEncheres extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> alerts = new ArrayList<String>();
		
		// Récupération des enchères correspondant aux critères 
		String filtreNom = request.getParameter("in_nom");
		Categorie categorie = null;
		String noCategorie = request.getParameter("sl_categorie");
		if(noCategorie!=null && !noCategorie.equals("-1") ) {
			categorie = new Categorie(Integer.parseInt(noCategorie));
		}
		// Récupère les ventes en cours qui correspondent au filtre nom, catégorie et :
		// - si déconnecté, uniquement en vente
		// - si connecté, tous
		UserBean user = (UserBean)request.getSession().getAttribute("user");
		LocalDateTime now = user==null ? LocalDateTime.now() : null;
		List<CarteArticleBean> cartes = buildListeCarteArticle(filtreNom, categorie, now, alerts);
		request.setAttribute("cartes", cartes);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/listeEncheres.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recupération des champs du formulaire dans le modèle de vue
		ViewModel viewModel = new ViewModel();
		viewModel.getFromRequest(request, 
			new String[] {"in_nom","sl_categorie"});
		
		viewModel.putIntoRequest(request);
		doGet(request, response);
	}

	
	// Récupère les ventes en cours qui correspondent au filtre nom, catégorie et :
	// - si now spécifié, uniquement en vente
	// - si now null, tous
	// En cas d'erreur, enrichit la liste d'alertes à afficher
	private List<CarteArticleBean> buildListeCarteArticle(String filtreNom, Categorie categorie, LocalDateTime now, List<String> alerts) {
		List<CarteArticleBean> r = null; 
		try {
			// Récupération des articles :
			List<ArticleVendu> articles = ArticleManager.getInstance().selectArticlesByFilter(filtreNom, categorie, now);
			// Conversion en liste de cartes à afficher
			r = new ArrayList<CarteArticleBean>();
			CarteArticleBean c;
			for(ArticleVendu a : articles) {
				c = new CarteArticleBean();
				c.setCheminPhoto(a.getCheminPhoto()!=null ? a.getCheminPhoto().trim() : "");
				c.setNomArticle(a.getNomArticle());
				c.setCategorie(Messages.get(a.getCategorieArticle().getLibelle()));
				c.setPrix( String.valueOf(a.getMiseAPrix()));
				c.setDateFin( a.getDateFinEncheres().format(
					DateTimeFormatter.ofPattern(Messages.get(Messages.LABEL_LISTEENCHERES_CARD_DATETIME_FORMAT))));
				c.setNomVendeur(a.getVenduPar().getPrenom()+" "+a.getVenduPar().getNom());
				c.setIdArticle(a.getNoArticle());
				c.setIdVendeur(a.getVenduPar().getNoUtilisateur());
				
				r.add(c);
			}
		} catch (BLLException e) {
			alerts.add(e.getUserMessage());
		}
		return r;
	}
}
