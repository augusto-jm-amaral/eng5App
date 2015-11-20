package org.eng5.populaBanco;

import org.eng5.dao.UsuarioDao;
import org.eng5.model.Usuario;

public class PopulaBanco {
	
	public static void main(String[] args) {
		
		Usuario usuario1 = new Usuario();
		usuario1.setNome("Joao");
		usuario1.setUsername("jao@email.com");
		usuario1.setPassword("123");
		usuario1.setAdmin(true);
		usuario1.setCpf("999999999-50");
		usuario1.setEndereco("Rua do jao, numero 123, Bairro do jao, Cidade Jão");
		
		UsuarioDao dao = new UsuarioDao();
		dao.inserirUsuario(usuario1);
	}
}
