package org.eng5.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eng5.jpa.JPAUtil;
import org.eng5.model.Usuario;

public class UsuarioDao {

	private JPAUtil jpaUtil = null;
	private EntityManager entityManager = null;

	public UsuarioDao() {
		jpaUtil = new JPAUtil();
		entityManager = jpaUtil.createEntityManager();
	}

	public Usuario buscarUsuarioPorUserName(String userName) {
		Query query = entityManager.createQuery(
				"SELECT u FROM Usuario u WHERE u.username=" + userName,
				Usuario.class);

		Usuario singleResult = (Usuario) query.getSingleResult();

		return singleResult;
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
