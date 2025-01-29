package com.dev.gabriellucas.taskify.services.impl;

import com.dev.gabriellucas.taskify.DTO.EtiquetaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.EtiquetaRequestPatchDTO;
import com.dev.gabriellucas.taskify.DTO.EtiquetaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Etiqueta;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import com.dev.gabriellucas.taskify.exceptions.BusinessException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.mappers.EtiquetaMapper;
import com.dev.gabriellucas.taskify.repositories.EtiquetaRepository;
import com.dev.gabriellucas.taskify.services.EtiquetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EtiquetaServiceImpl implements EtiquetaService {

    private final EtiquetaMapper etiquetaMapper;
    private final EtiquetaRepository repository;

    @Override
    @Transactional
    public EtiquetaResponseDTO saveEtiqueta(EtiquetaRequestDTO request) {
        maxEtiquetas();
        Etiqueta etiqueta = etiquetaMapper.toEntity(request);
        return etiquetaMapper.toDto(repository.save(etiqueta));
    }

    @Override
    @Transactional(readOnly = true)
    public EtiquetaResponseDTO findEtiquetaById(Long id) {
        Optional<Etiqueta> obj = repository.findById(id);
        Etiqueta entity = obj.orElseThrow(() -> new ResourceNotFoundException("Etiqueta não encontrada, ID:" + id));
        return etiquetaMapper.toDto(entity);
    }

    @Override
    @Transactional
    public EtiquetaResponseDTO updateEtiqueta(Long id, EtiquetaRequestDTO request) {
        Etiqueta entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etiqueta não encontrada, ID:" + id));
        nameAlreadyExist(entity, request.getNome());
        etiquetaMapper.updateEntityFromDTO(request, entity);
        return etiquetaMapper.toDto(repository.save(entity));
    }

    @Override
    public void deleteEtiqueta(Long id) {
        findEtiquetaById(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
                throw new BusinessException("Não é permitido a exclusão de etiquetas em uso");
        }
    }

    @Override
    @Transactional
    public EtiquetaResponseDTO updateEtiquetaParcial(Long id, EtiquetaRequestPatchDTO request) {
        Etiqueta entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etiqueta não encontrada, ID:" + id));

        var nameChange = request.getNome() != null && !request.getNome().equals(entity.getNome());

        if (nameChange) {
            nameAlreadyExist(entity, request.getNome());
            entity.setNome(request.getNome());
        }
        
        if (request.getCor() != null) {
            entity.setCor(request.getCor());
        }

        return etiquetaMapper.toDto(repository.save(entity));
    }

    protected void nameAlreadyExist(Etiqueta entity, String nome){
        for (Tarefa tarefa : entity.getTarefas()) {
            boolean exists = repository.existsEtiquetaWithNomeInTarefa(nome, tarefa, entity.getId());
            if (exists) {
                throw new BusinessException("Não é possível ter mais de uma Etiqueta com o mesmo nome na tarefa.");
            }
        }
    }

    protected void maxEtiquetas(){
        int quantidade = repository.countEtiquetas();
        if (quantidade >= 50) {
            throw new BusinessException("Limite máximo de Etiquetas alcançados no sistema.");
        }
    }


}
