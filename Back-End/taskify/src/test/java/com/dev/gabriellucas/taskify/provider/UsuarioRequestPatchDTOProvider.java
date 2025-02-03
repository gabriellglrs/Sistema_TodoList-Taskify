package com.dev.gabriellucas.taskify.provider;

import com.dev.gabriellucas.taskify.DTO.UsuarioRequestPatchDTO;

import java.time.LocalDateTime;

public class UsuarioRequestPatchDTOProvider {
     private static final Long ID = 1L;
     private static final String NOME = "Gabriel";
     private static final String EMAIL = "gabriel@gmail.com";
     private static final String SENHA = "123456";
     private static final LocalDateTime DATA_CADASTRO = LocalDateTime.now();

     public UsuarioRequestPatchDTO create() {
          UsuarioRequestPatchDTO usuario = new UsuarioRequestPatchDTO();
          usuario.setId(ID);
          usuario.setNome(NOME);
          usuario.setEmail(EMAIL);
          usuario.setSenha(SENHA);
          usuario.setDataCadastro(DATA_CADASTRO);
          return usuario;
     }
}
