package com.dev.gabriellucas.taskify.services.impl;

import com.dev.gabriellucas.taskify.entities.Anexo;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import com.dev.gabriellucas.taskify.exceptions.BusinessException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.repositories.AnexoRepository;
import com.dev.gabriellucas.taskify.repositories.TarefaRepository;
import com.dev.gabriellucas.taskify.services.AnexoService;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AnexoServiceImpl implements AnexoService {

    private final AnexoRepository repository;
    private final TarefaRepository tarefaRepository;

    @Override
    @Transactional(readOnly = true)
    public Anexo getAnexoById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anexo não encontrado, ID: " + id));
    }

    @Override
    @Transactional
    public Anexo saveAnexo(MultipartFile file, Long idTarefa) throws IOException  {

        if(file.isEmpty()){
            throw new BusinessException("O arquivo não pode ser vazio");
        }

        Tarefa tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada. ID:" + idTarefa));

        maxAnexos(idTarefa);
        String mime = checkingTypeMime(file);

        Anexo anexo = new Anexo();
        anexo.setNome(StringUtils.cleanPath(file.getOriginalFilename()));
        anexo.setTipo(mime);
        anexo.setTamanho(file.getSize());
        anexo.setDados(file.getBytes());
        anexo.setTarefa(tarefa);

        repository.save(anexo);

        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/anexos/")
                .path(anexo.getId().toString())
                .toUriString();

        anexo.setUrl(downloadUrl);

        return repository.save(anexo);
    }

    @Override
    public void deleteAnexo(Long id) {
        Anexo anexo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anexo não encontrado, ID:" + id));
        repository.deleteById(anexo.getId());
    }

    protected void maxAnexos(Long idTarefa){
        int quantidade = repository.countAnexosByTarefaId(idTarefa);
        if (quantidade >= 10) {
            throw new BusinessException("Limite máximo de Anexos alcançados na tarefa, ID:" + idTarefa);
        }
    }

    protected String checkingTypeMime(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        String mime = tika.detect(file.getInputStream());
        if (mime.equals("application/octet-stream")) {
            throw new BusinessException("Não é permitido esse tipo de arquivo.");
        }
        return mime;
    }

}
