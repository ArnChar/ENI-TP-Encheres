package fr.eni.encheres.hmi.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.hmi.Cookies;

/**
 * Servlet implementation class ServletDeconnexion
 */

@WebServlet("/Deconnexion")

public class ServletDeconnexion extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// Suppression des attributs en session
		Enumeration<String> names = session.getAttributeNames();
		while(names.hasMoreElements()) {
			session.removeAttribute(names.nextElement());
		}
		
		// Suppression du cookie de user s'il existe
		Cookie cookie = Cookies.getCookieByName(request, "cookie_user");
		if(cookie!=null) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		
		// redirection vers la racine du site
		response.sendRedirect(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
