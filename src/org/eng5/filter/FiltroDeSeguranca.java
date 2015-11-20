package org.eng5.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eng5.controller.UsuarioController;
import org.eng5.model.Usuario;

@WebFilter(filterName = "filtroDeSeguranca")
public class FiltroDeSeguranca implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		String method = httpReq.getMethod();
		String requestURI = httpReq.getRequestURI();

		System.out.println(method + ":::" + requestURI);

		Usuario usuario = (Usuario) ((HttpServletRequest) httpReq).getSession()
				.getAttribute(UsuarioController.USUARIO_LOGADO);

		if (method.equals("GET") && !requestURI.contains("partialsAdmin")) {
			filterChain.doFilter(req, resp);
		} else {
			if (requestURI.contains("carrinho") || requestURI.contains("login")) {
				filterChain.doFilter(req, resp);
			} else {
				if (usuario != null) {
					if (usuario.isAdmin()) {
						filterChain.doFilter(req, resp);
					} else {
						httpResp.setStatus(401);
					}
				} else {
					httpResp.setStatus(401);
				}

			}
		}
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
