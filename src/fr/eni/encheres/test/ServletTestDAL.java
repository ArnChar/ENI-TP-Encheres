package fr.eni.encheres.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.dao.UtilisateurDAO;

/**
 * Servlet implementation class ServletTestDAL
 */
@WebServlet("/test/ServletTestDAL")
public class ServletTestDAL extends HttpServlet {
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
			Utilisateur u1 = new Utilisateur(null,"pseudo 1","nom 1","prenom 1","email 1","tel 1","rue 1","cp1","ville 1","mdp 1",501,true);
			Utilisateur u2 = new Utilisateur(null,"pseudo 2","nom 2","prenom 2","email 2","tel 2","rue 2","cp2","ville 2","mdp 2",502,false);
			Utilisateur u3 = new Utilisateur(null,"pseudo 3","nom 3","prenom 3","email 3","tel 3","rue 3","cp3","ville 3","mdp 3",503,true);
			out.println("Insertion de 3 utilisateurs");
			utilisateurDAO.insert(u1);
			utilisateurDAO.insert(u2);
			utilisateurDAO.insert(u3);

			out.println();
			out.println("Utilisateurs existants");
			List<Utilisateur> listeUtilisateurs = utilisateurDAO.selectAll();
			for(Utilisateur u : listeUtilisateurs) {
				out.println(u.toString());
			}
			
			out.println();
			out.println("Modification utilisateur 2");
			u2.setNom("nom 2 modifié");
			u2.setRue("rue 2 modifié");
			utilisateurDAO.update(u2);

			Utilisateur u2modif = utilisateurDAO.selectBy(u2.getNoUtilisateur());
			out.println();
			out.println("Utilisateur 2 modifié");
			out.println(u2modif.toString());
			
			out.println();
			out.println("Suppresion des 3 utilisateurs");
			utilisateurDAO.delete(u1);
			utilisateurDAO.delete(u2);
			utilisateurDAO.delete(u3);

			listeUtilisateurs = utilisateurDAO.selectAll();
			out.println();
			out.println("Utilisateurs restants = "+listeUtilisateurs.size());

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
