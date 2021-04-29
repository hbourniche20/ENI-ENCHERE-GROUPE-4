package fr.eni.enchere.ihm;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class Filter
 */
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST, 
		DispatcherType.FORWARD, 
		DispatcherType.INCLUDE, 
		DispatcherType.ERROR
	}, 
	urlPatterns = { "/*" }
)
public class FiltreSession implements javax.servlet.Filter {

	/**
	 * @see FiltreSession#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see FiltreSession#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpRequest.setCharacterEncoding("UTF-8");

		String url = httpRequest.getServletPath().toLowerCase();
		if ((url.contains("/articles/") || url.contains("/utilisateur/") || url.contains("/seDeconnecter")) && httpRequest.getSession().getAttribute("user") == null) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		} else if ((url.contains("/creerCompte") || url.contains("/seConnecter")) && httpRequest.getSession().getAttribute("user") != null) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see FiltreSession#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
