package org.eng5.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eng5.dao.ProdutoDao;
import org.eng5.model.Produto;

import com.google.gson.Gson;

@WebServlet("/produto/*")
public class ProdutoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProdutoDao dao = null;

	@Override
	public void init() throws ServletException {
		super.init();
		this.dao = new ProdutoDao();

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
		super.service(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pathInfo = req.getPathInfo();
		Gson gson = new Gson();
		PrintWriter writer = resp.getWriter();
		String returnData = "";

		if (pathInfo != null) {
			int id = Integer.parseInt(pathInfo.substring(1, pathInfo.length()));
			Produto produto = dao.buscaProduto(id);
			returnData = gson.toJson(produto);

		} else {
			List<Produto> listarCategorias = dao.listarProduto();
			returnData = gson.toJson(listarCategorias);
		}

		resp.setStatus(200);
		writer.print(returnData);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			Gson gson = new Gson();
			StringBuilder sb = new StringBuilder();
			String s;

			while ((s = req.getReader().readLine()) != null) {
				sb.append(s);
			}

			Produto produto = gson.fromJson(sb.toString(), Produto.class);
			this.dao.salvaProduto(produto);
		} catch (Exception exception) {
			exception.printStackTrace();
			resp.setStatus(500);
		}

		resp.setStatus(201);

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pathInfo = req.getPathInfo();

		if (pathInfo != null) {
			int id = Integer.parseInt(pathInfo.substring(1, pathInfo.length()));
			Produto produto = dao.buscaProduto(id);
			dao.removeProduto(produto);
		}

		resp.setStatus(202);

	}

	@Override
	public void destroy() {
		super.destroy();
		dao.close();
	}

}
