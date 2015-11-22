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

//	Responsavel por fazer o controle de acesso dos verbos http
// 	ele filtra todas as requisições da aplicação
@WebFilter(filterName = "filtroDeSeguranca")
public class FiltroDeSeguranca implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		// converte o ServletResquest em HttpServletRequest e servletResponse em
		// HttpServletResponse
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		// pega o metodo que esta sendo chamado
		String method = httpReq.getMethod();
		// pega o endereço requisitado dentro do servidor
		String requestURI = httpReq.getRequestURI();
		// exibe o endereço e o metodo HTTP com a finalidade de debugar a
		// aplicação
		System.out.println(method + ":::" + requestURI);
		// Pega o objeto usuario caso ele estja logado
		Usuario usuario = (Usuario) ((HttpServletRequest) httpReq).getSession()
				.getAttribute(UsuarioController.USUARIO_LOGADO);
		// Caso o metodo seja get e não seja apara arquivos da pasta do Admin
		// ele processa a requisição
		if (method.equals("GET") && !requestURI.contains("partialsAdmin")) {
			filterChain.doFilter(req, resp);
			// caso contrario
		} else {
			// a requisição seja pro carrinho ou pro login, seja ela post put ou
			// delete ele processa a requisição
			if (requestURI.contains("carrinho") || requestURI.contains("login")) {
				filterChain.doFilter(req, resp);
				// tudo que for diferente disso o usuario devera estar logado
				// como administrador
			} else {
				// Verifica se o usuario esta logado
				if (usuario != null) {
					// verifica se ele é admin
					if (usuario.isAdmin()) {
						// se for processa a requisição
						filterChain.doFilter(req, resp);
					} else {
						// caso contrario responde com status 401 =
						// "Não Autorizado"
						httpResp.setStatus(401);
					}
				} else {
					// se o usuario não for nulo responde como não autorizado
					// também
					httpResp.setStatus(401);
				}

			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
