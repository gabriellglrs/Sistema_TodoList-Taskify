package com.dev.gabriellucas.taskify.services;

import com.dev.gabriellucas.taskify.DTO.AnexoResponseDTO;
import com.dev.gabriellucas.taskify.entities.Anexo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AnexoService {

    Anexo getAnexoById(Long id);
    Anexo saveAnexo(MultipartFile file, Long idTarefa) throws IOException;
    void deleteAnexo(Long id);
}
