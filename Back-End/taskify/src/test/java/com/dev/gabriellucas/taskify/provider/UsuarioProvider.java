package com.dev.gabriellucas.taskify.provider;

import com.dev.gabriellucas.taskify.entities.Usuario;

import java.time.LocalDateTime;

public class UsuarioProvider {
     private static final Long ID = 1L;
     private static final String NOME = "Gabriel";
     private static final String EMAIL = "gabriel@gmail.com";
     private static final String SENHA = "123456";
     private static final LocalDateTime DATA_CADASTRO = LocalDateTime.now();

     public Usuario create() {
          Usuario usuario = new Usuario();
          usuario.setId(ID);
          usuario.setNome(NOME);
          usuario.setEmail(EMAIL);
          usuario.setSenha(SENHA);
          usuario.setDataCadastro(DATA_CADASTRO);
          return usuario;
     }
}
