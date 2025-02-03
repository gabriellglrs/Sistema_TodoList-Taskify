package com.dev.gabriellucas.taskify.provider;

import com.dev.gabriellucas.taskify.DTO.UsuarioResponseDTO;

import java.time.LocalDateTime;

public class UsuarioResponseDTOProvider {
     private static final Long ID = 1L;
     private static final String NOME = "Gabriel";
     private static final String EMAIL = "gabriel@gmail.com";
     private static final LocalDateTime DATA_CADASTRO = LocalDateTime.now();

     public UsuarioResponseDTO create() {
          UsuarioResponseDTO usuario = new UsuarioResponseDTO();
          usuario.setId(ID);
          usuario.setNome(NOME);
          usuario.setEmail(EMAIL);
          usuario.setDataCadastro(DATA_CADASTRO);
          return usuario;
     }
}
