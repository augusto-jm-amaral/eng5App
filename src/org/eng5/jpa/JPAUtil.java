package org.eng5.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private EntityManagerFactory entityManagerFactory = null;

	public JPAUtil() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("eng5");
	}

	public EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	public void closeEntityManager(EntityManager entityManager) {
		entityManager.close();
	}

	public void close() {
		if (this.entityManagerFactory != null) {
			this.entityManagerFactory.close();
		}
	}

}
