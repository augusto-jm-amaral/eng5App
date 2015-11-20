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
	
	public void AtualizarUsuario(Usuario usuario) {
		Usuario usuarioUpdate = entityManager.find(Usuario.class, usuario);
		abrirTransacao();
		usuarioUpdate.setAdmin(usuario.isAdmin());
		usuarioUpdate.setCpf(usuario.getCpf());
		usuarioUpdate.setEndereco(usuario.getEndereco());
		usuarioUpdate.setNome(usuario.getNome());
		usuarioUpdate.setPassword(usuario.getPassword());
		usuarioUpdate.setUsername(usuario.getUsername());
		commitar();
	}

	public Usuario buscarUsuarioPorUserName(String userName) {

		System.out.println(userName);

		Usuario usuario = null;

		Query query = entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.username=:user",
						Usuario.class).setParameter("user", userName);
		try {
			usuario = (Usuario) query.getSingleResult();
		} catch (Exception e) {
			usuario = null;
			System.out.println("UsuarioDao:::Usuario Não encontrado");
		}

		return usuario;
	}

	public void inserirUsuario(Usuario usuario) {
		abrirTransacao();
		entityManager.merge(usuario);
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
