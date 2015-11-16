package org.eng5.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.eng5.jpa.JPAUtil;
import org.eng5.model.Produto;

public class ProdutoDao {

	private JPAUtil jpaUtil = null;
	private EntityManager entityManager = null;

	public ProdutoDao() {
		this.jpaUtil = new JPAUtil();
		this.entityManager = jpaUtil.createEntityManager();
	}

	public List<Produto> listarProduto() {

		List<Produto> resultList = entityManager.createQuery(
				"SELECT p FROM Produto p", Produto.class).getResultList();

		return resultList;
	}

	public Produto buscaProduto(int id) {

		Produto find = entityManager.find(Produto.class, id);

		return find;
	}

	public void salvaProduto(Produto produto) {
		abrirTransacao();
		entityManager.merge(produto);
		commitar();
	}

	public void removeProduto(Produto produto) {
		abrirTransacao();
		entityManager.remove(produto);
		entityManager.flush();
		commitar();
	}

	private void commitar() {
		entityManager.getTransaction().commit();
	}

	private void abrirTransacao() {
		entityManager.getTransaction().begin();
	}

	public void close() {
		if (entityManager != null)
			jpaUtil.closeEntityManager(entityManager);

		jpaUtil.close();
	}

}
