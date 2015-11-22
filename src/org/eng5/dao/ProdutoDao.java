package org.eng5.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.eng5.jpa.JPAUtil;
import org.eng5.model.Produto;

//Classe responsavel por manipular os usuarios no banco de dados
public class ProdutoDao {

	// Classe responsavel por criar conexões com o banco
	private JPAUtil jpaUtil = null;
	// Classe que representa a conexão com o banco
	private EntityManager entityManager = null;

	// o contrutor inicia as duas classes
	public ProdutoDao() {
		this.jpaUtil = new JPAUtil();
		this.entityManager = jpaUtil.createEntityManager();
	}

	// devolve uma lista de produtos
	public List<Produto> listarProduto() {

		List<Produto> resultList = entityManager.createQuery(
				"SELECT p FROM Produto p", Produto.class).getResultList();

		return resultList;
	}

	// devolve um produto pesquisado pelo id
	public Produto buscaProduto(int id) {

		Produto find = entityManager.find(Produto.class, id);

		return find;
	}

	// salva um produto no banco
	public void salvaProduto(Produto produto) {
		abrirTransacao();
		entityManager.merge(produto);
		commitar();
	}

	// Remove um produto no banco
	public void removeProduto(Produto produto) {
		abrirTransacao();
		entityManager.remove(produto);
		entityManager.flush();
		commitar();
	}

	// commita a transação no banco
	private void commitar() {
		entityManager.getTransaction().commit();
	}

	// abri uma transação no banco
	private void abrirTransacao() {
		entityManager.getTransaction().begin();
	}

	// fecha a conexão com o banco
	public void close() {
		if (entityManager != null)
			jpaUtil.closeEntityManager(entityManager);

		jpaUtil.close();
	}

}
