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

/**
 * Classe responsavel por fornecer e receber os dados aos dos produtos
 */
@WebServlet("/produto/*")
public class ProdutoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// Produto dão vai manipular os dados no banco
	private ProdutoDao dao = null;

	// Inicia a servlet
	@Override
	public void init() throws ServletException {
		super.init();
		// Inicia a classe DAO
		this.dao = new ProdutoDao();

	}

	// Chamado antes de cada verbo HTTP
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Seta o CharacterEncoding para utf-8 para que não tenha problemas em
		// manipular letras acentuadas
		resp.setCharacterEncoding("UTF-8");
		super.service(req, resp);
	}

	// Requisições do tipo GET
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Pega o url de caminho utilizada chamada pelo cliente
		String pathInfo = req.getPathInfo();
		// Classe responsavel por manipular o json
		Gson gson = new Gson();
		// Classe responsavel por enviar dados ao cliente
		PrintWriter writer = resp.getWriter();
		// A string que sera retornada para o cliente
		String returnData = "";
		// Verifica se existir algum caracter depois do barra
		if (pathInfo != null) {
			// Filtra essa string para retirar a barra do inicio e pegar o
			// numero que vem depois
			int id = Integer.parseInt(pathInfo.substring(1, pathInfo.length()));
			// Adiciona o numero ao id de um produto
			Produto produto = dao.buscaProduto(id);
			// Retorna o produto em formato JSON
			returnData = gson.toJson(produto);
			// caso não tenha nenhum caracter apos o endereço /produto/ ele
			// retornara a lista
		} else {
			List<Produto> listarCategorias = dao.listarProduto();
			returnData = gson.toJson(listarCategorias);
		}
		// Seta o status da requisição (200) = Sucesso
		resp.setStatus(200);
		// Envia os dados
		writer.print(returnData);

	}

	// O verbo post é utilizado para salvar o produto
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			// Classe responsavel por manipular o json
			Gson gson = new Gson();
			// Strings para receber os dados
			StringBuilder sb = new StringBuilder();
			String s;
			// Lendo os dados recebidos, que no caso é um JSON
			while ((s = req.getReader().readLine()) != null) {
				sb.append(s);
			}
			// Transforma os dados JSON em um objeto Produto
			Produto produto = gson.fromJson(sb.toString(), Produto.class);
			// Salva no banco
			this.dao.salvaProduto(produto);
		} catch (Exception exception) {
			// Caso der erro exibe o erro no console do servidor e devolve um
			// status 500 = ERRO INTERNO NO SERVIDOR
			exception.printStackTrace();
			resp.setStatus(500);
		}
		// Se tudo der certo retorna um 201 = CRIADO
		resp.setStatus(201);

	}

	// O verbo delete foi utilizado para excluir um produto
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// pega o endereço requisitado para deletar
		String pathInfo = req.getPathInfo();
		// retira do endereço o id do produto a ser deletado
		if (pathInfo != null) {
			int id = Integer.parseInt(pathInfo.substring(1, pathInfo.length()));
			// busca o produto no banco
			Produto produto = dao.buscaProduto(id);
			// remove o produto
			dao.removeProduto(produto);
		}
		// retorna ao cliente um status 200 de aceito
		resp.setStatus(202);

	}

	// chamado quando a servlet for destruida
	@Override
	public void destroy() {
		super.destroy();
		// fecha a conexão com o banco
		dao.close();
	}

}
