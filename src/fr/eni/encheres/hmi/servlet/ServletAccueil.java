package fr.eni.encheres.hmi.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.hmi.Cookies;
import fr.eni.encheres.hmi.HMIException;
import fr.eni.encheres.hmi.bean.UserBean;

/**
 * Servlet implementation class ServletInitApplication
 */

@WebServlet("/Accueil")

public class ServletAccueil extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recherche d'un cookie d'utilisateur existant et mise en session du bean user
		try {
			UserBean user = Cookies.getCookieObjectByName(request,"cookie_user", UserBean.class);
			if(user!=null) {
				request.getSession().setAttribute("user", user);
			}
		} catch (HMIException e) {
			e.printStackTrace();
		}

		// Chargement liste Categories en session
		if(request.getSession().getAttribute("categories")==null) {
			request.getSession().setAttribute("categories",initCategories());
		}
		
		// Redirection vers ServletListeEncheres
		response.sendRedirect(request.getContextPath()+"/ListeEncheres");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	private HashMap<String,String> initCategories() {
		HashMap<String,String> r = new HashMap<String,String>();
		try {
			List<Categorie> categories = CategorieManager.getInstance().selectAllCategories();
			for(Categorie c : categories) {
				r.put(String.valueOf(c.getNoCategorie()), c.getLibelle());
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
		return r;
	}
	
}
