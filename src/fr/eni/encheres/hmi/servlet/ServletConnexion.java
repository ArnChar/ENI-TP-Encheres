package fr.eni.encheres.hmi.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.hmi.Cookies;
import fr.eni.encheres.hmi.HMIException;
import fr.eni.encheres.hmi.ViewModel;
import fr.eni.encheres.hmi.bean.UserBean;
import fr.eni.encheres.tool.Messages;

/**
 * Servlet implementation class ServletConnexion
 */

@WebServlet("/Connexion")

public class ServletConnexion extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recupération des champs du formulaire dans le modèle de vue
		ViewModel viewModel = new ViewModel();
		viewModel.getFromRequest(request, new String[] {"in_username","in_password", "ck_remember"});

		// recherche de l'utilisateur en base
		UserBean user = new UserBean();
		user.setUsername(viewModel.get("in_username"));
		user.setPassword(viewModel.get("in_password"));
		try {
			UtilisateurManager.getInstance().authenticateUser(user);
		} catch (BLLException e) {
			e.printStackTrace();
		}

		// utilisateur non authentifié, on réalimente la page avec les valeurs du modèle de vue 
		if(user.getNoUtilisateur()==null) {
			viewModel.put("alert",Messages.ALERT_CONNEXION_DENIED);
			viewModel.putIntoRequest(request);
			doGet(request, response);

		// utilisateur authentifié
		} else {
			// mise en session du user authentifié
			request.getSession().setAttribute("user", user);
			
			// si l'utilisateur veut mémoriser son authentification, on stocke le bean user en cookie
			if(viewModel.get("ck_remember")!=null) {
				try {
					Cookie cookie = Cookies.createFromObject("cookie_user", user);
					cookie.setMaxAge(604800);  // 7 jours
					response.addCookie(cookie);
				} catch (HMIException e) {
					e.printStackTrace();
				}
			}
			// redirection vers page Accueil en étant authentifié
			response.sendRedirect(request.getContextPath()+"/ListeEncheres");
		}
	}

}
