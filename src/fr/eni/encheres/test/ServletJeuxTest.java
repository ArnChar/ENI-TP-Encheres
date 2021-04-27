package fr.eni.encheres.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.dao.ArticleVenduDAO;
import fr.eni.encheres.dal.dao.CategorieDAO;
import fr.eni.encheres.dal.dao.RetraitDAO;
import fr.eni.encheres.dal.dao.UtilisateurDAO;

/**
 * Servlet implementation class ServletTestDAL
 */
@WebServlet("/test/ServletJeuxTest")
public class ServletJeuxTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter(); 
		out.println("Served at: "+request.getContextPath());
		out.println();
		try {
			// Insertions Utilisateurs
			UtilisateurDAO utilisateurDAO = DAOFactory.getInstance().getUtilisateurDAO();
			List<Utilisateur> listeUtilisateurs = utilisateurDAO.selectAll();
			for(Utilisateur utilisateur : listeUtilisateurs) {
				utilisateurDAO.delete(utilisateur);
			}
			Utilisateur u1 = new Utilisateur(null,"Thor","TILLE","Thor","tt@mail.fr","0610102020","1 rue de l'impasse","44000","NANTES","toto",501,true);
			Utilisateur u2 = new Utilisateur(null,"Jean","NEYMAR","Jean","jn@mail.fr","0630304040","2 boulevard Ansort","35000","RENNES","tutu",10,false);
			utilisateurDAO.insert(u1);
			utilisateurDAO.insert(u2);
			listeUtilisateurs = utilisateurDAO.selectAll();
			out.println();
			out.println("Utilisateurs = "+listeUtilisateurs.size());

			// Insertions Categories
			CategorieDAO categorieDAO = DAOFactory.getInstance().getCategorieDAO();
			List<Categorie> listeCategorie = categorieDAO.selectAll();
			for(Categorie categorie : listeCategorie) {
				categorieDAO.delete(categorie);
			}
			Categorie c1 = new Categorie(null,"label.categorie.it");
			Categorie c2 = new Categorie(null,"label.categorie.furnishing");
			Categorie c3 = new Categorie(null,"label.categorie.clothing");
			Categorie c4 = new Categorie(null,"label.categorie.sports");
			categorieDAO.insert(c1);
			categorieDAO.insert(c2);
			categorieDAO.insert(c3);
			categorieDAO.insert(c4);
			listeCategorie = categorieDAO.selectAll();
			out.println();
			out.println("Categories = "+listeCategorie.size());

			// Insertions ArticleVendu
			ArticleVenduDAO articleVenduDAO = DAOFactory.getInstance().getArticleVenduDAO();
			List<ArticleVendu> listeArticleVendu = articleVenduDAO.selectAll();
			for(ArticleVendu article : listeArticleVendu) {
				articleVenduDAO.delete(article);
			}
			// sport, commencé avant, fini avant
			ArticleVendu a1 = new ArticleVendu(null,"Marteau","Marteau de menuisier",LocalDateTime.of(2020,11,05,20,0,0),LocalDateTime.of(2020,11,10,10,0,0),150,0,"https://www.cdiscount.com/pdt2/9/8/6/1/700x700/auc3438050152986/rw/marteau-de-menuisier-25-mm.jpg",u1,u2,c4,null);
			// info, commencé avant, fini après
			ArticleVendu a2 = new ArticleVendu(null,"Pelle","Pelle de chantier",LocalDateTime.of(2020,11,10,9,30,0),LocalDateTime.of(2020,11,29,19,15,0),50,0,"https://www.cdiscount.com/pdt2/6/3/9/1/300x300/sil5024763029639/rw/pelle-ronde-poignee-en-t-1080mm.jpg",u2,u1,c1,null);
			// sport, commencé après, fini après
			ArticleVendu a3 = new ArticleVendu(null,"Cisaille","Cisaille de jardin",LocalDateTime.of(2020,12,10,9,30,0),LocalDateTime.of(2020,12,20,19,15,0),50,0,"https://www.cdiscount.com/pdt2/9/5/3/1/700x700/auc2009801670953/rw/taille-haie-manuel-ciseau-cisaille-a-haie-jardinag.jpg",u2,u1,c4,null);
			// sport, commencé avant, fini après
			ArticleVendu a4 = new ArticleVendu(null,"Pioche","Pioche à bitume",LocalDateTime.of(2020,11,8,20,0,0),LocalDateTime.of(2020,12,05,10,0,0),150,0,"https://www.cdiscount.com/pdt2/5/1/2/1/700x700/leb3157330581512/rw/pioche-cantonnier.jpg",u1,u2,c4,null);
			// ameublement, commencé avant, fini après
			ArticleVendu a5 = new ArticleVendu(null,"Lampadaire","Luminaire cuivre",LocalDateTime.of(2020,11,5,10,0,0),LocalDateTime.of(2020,11,25,16,0,0),125,0,"https://www.cdiscount.com/pdt2/9/8/9/1/700x700/glo9007371308989/rw/lampadaire-luminaire-cuivre-arque-reglable-en-haut.jpg",u1,u2,c2,null);
			// vetements, commencé après, fini après
			ArticleVendu a6 = new ArticleVendu(null,"Robe","Robe soirée avec strass",LocalDateTime.of(2020,12,1,10,0,0),LocalDateTime.of(2020,12,30,16,0,0),125,0,"https://www.cdiscount.com/pdt2/5/9/0/1/700x700/mp02998590/rw/robe-sexy-longue-robe-soiree-avec-strass-fait-main.jpg",u1,u2,c3,null);
			articleVenduDAO.insert(a1);
			articleVenduDAO.insert(a2);
			articleVenduDAO.insert(a3);
			articleVenduDAO.insert(a4);
			articleVenduDAO.insert(a5);
			articleVenduDAO.insert(a6);
			listeArticleVendu = articleVenduDAO.selectAll();
			out.println();
			out.println("ArticleVendus = "+listeArticleVendu.size());

			// Insertions Retraits
			RetraitDAO retraitDAO = DAOFactory.getInstance().getRetraitDAO();
			List<Retrait> listeRetrait = retraitDAO.selectAll();
			for(Retrait retrait : listeRetrait) {
				retraitDAO.delete(retrait);
			}
			Retrait r1 = new Retrait(a1,"4 rue de l'Impaire","44700","SAINT HERBLAIN");
			Retrait r2 = new Retrait(a2,"3 boulevard Tichaud","35100","RENNES");
			Retrait r3 = new Retrait(a3,"7 rue de l'Impaire","44700","SAINT HERBLAIN");
			Retrait r4 = new Retrait(a4,"24 boulevard Tichaud","35100","RENNES");
			Retrait r5 = new Retrait(a5,"12 boulevard Tichaud","35100","RENNES");
			Retrait r6 = new Retrait(a6,"10 rue de l'Impaire","44700","SAINT HERBLAIN");
			retraitDAO.insert(r1);
			retraitDAO.insert(r2);
			retraitDAO.insert(r3);
			retraitDAO.insert(r4);
			retraitDAO.insert(r5);
			retraitDAO.insert(r6);
			listeRetrait = retraitDAO.selectAll();
			out.println();
			out.println("Retraits = "+listeRetrait.size());

		} catch (Exception e) {
			e.printStackTrace(out);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
