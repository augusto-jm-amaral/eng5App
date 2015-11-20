package org.eng5.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eng5.dao.UsuarioDao;
import org.eng5.model.Usuario;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@WebServlet("/login")
public class UsuarioController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String USUARIO_LOGADO = "org.eng5.controller.usuariocontroller.usuario_logado";
	private UsuarioDao dao = null;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		super.service(req, resp);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		dao = new UsuarioDao();
	}

	@Override
	public void destroy() {
		super.destroy();
		dao.close();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Usuario usuario = (Usuario) req.getSession().getAttribute(
				USUARIO_LOGADO);

		if (usuario != null) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("userName", usuario.getNome());
			PrintWriter writer = resp.getWriter();
			writer.println(jsonObject.toString());
			resp.setStatus(200);
		} else {
			resp.setStatus(404);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		PrintWriter writer = resp.getWriter();
		Gson gson = new Gson();

		try {
			StringBuilder sb = new StringBuilder();
			String s;

			while ((s = req.getReader().readLine()) != null) {
				sb.append(s);
			}

			JsonObject fromJson = gson
					.fromJson(sb.toString(), JsonObject.class);

			JsonElement username = fromJson.get("nome");
			JsonElement senha = fromJson.get("senha");

			Usuario usuario = dao.buscarUsuarioPorUserName(username.toString()
					.replaceAll("\"", ""));

			if (usuario != null) {

				if (usuario.getPassword().equals(
						senha.toString().replaceAll("\"", ""))) {

					req.getSession().setAttribute(USUARIO_LOGADO, usuario);
					resp.setStatus(200);

					JsonObject jsonObject = new JsonObject();
					if (usuario.isAdmin()) {
						jsonObject.addProperty("path", "/admin");
					} else {
						jsonObject.addProperty("path", "/");
					}
					String retorno = jsonObject.toString();

					writer.println(retorno);

				} else {
					resp.setStatus(400);
					JsonObject jsonObject = new JsonObject();
					jsonObject.addProperty("texto",
							"Usuario ou senha incorretos!");
					String retorno = jsonObject.toString();

					writer.println(retorno);
				}

			} else {
				resp.setStatus(400);
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("texto", "Usuario ou senha incorretos!");
				String retorno = jsonObject.toString();

				writer.println(retorno);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("texto",
					"Erro ao processar a operação, tente novamente.");
			String retorno = jsonObject.toString();

			writer.println(retorno);
			resp.setStatus(500);
		}

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Usuario novoUsuario = null;
		Gson gson = new Gson();
		Usuario usuario = (Usuario) req.getSession().getAttribute(
				USUARIO_LOGADO);
		try {
			StringBuilder sb = new StringBuilder();
			String s;

			while ((s = req.getReader().readLine()) != null) {
				sb.append(s);
			}

			novoUsuario = (Usuario) gson.fromJson(sb.toString(), Usuario.class);
		} catch (Exception exception) {
			exception.printStackTrace();
			resp.setStatus(500);
		}

		if (usuario != null) {
			novoUsuario.setId(usuario.getId());
			dao.AtualizarUsuario(novoUsuario);
		} else {
			dao.inserirUsuario(novoUsuario);
		}

	}

}
