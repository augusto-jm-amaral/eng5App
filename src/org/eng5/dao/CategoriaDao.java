package org.eng5.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.eng5.jpa.JPAUtil;
import org.eng5.model.Categoria;

public class CategoriaDao {

	private JPAUtil jpaUtil = null;
	private EntityManager entityManager = null;

	public CategoriaDao() {
		jpaUtil = new JPAUtil();
		entityManager = jpaUtil.createEntityManager();
	}

	public List<Categoria> listarCategorias() {
		List<Categoria> resultList = entityManager.createQuery(
				"SELECT p FROM Categoria p", Categoria.class).getResultList();
		return resultList;
	}

	public Categoria buscaCategoria(int id) {

		Categoria find = entityManager.find(Categoria.class, id);

		return find;
	}

	public void salvaCategoria(Categoria categoria) {
		abrirTransacao();
		entityManager.merge(categoria);
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

	public void removeCategoria(Categoria categoria) {
		abrirTransacao();
		entityManager.remove(categoria);
		entityManager.flush();
		commitar();
	}

}
