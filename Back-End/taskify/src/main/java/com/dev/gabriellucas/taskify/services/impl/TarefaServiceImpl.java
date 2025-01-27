package com.dev.gabriellucas.taskify.services.impl;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.entities.*;
import com.dev.gabriellucas.taskify.enums.StatusTarefa;
import com.dev.gabriellucas.taskify.exceptions.BusinessException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.mappers.AnexoMapper;
import com.dev.gabriellucas.taskify.mappers.ComentarioMapper;
import com.dev.gabriellucas.taskify.mappers.HistoricoMapper;
import com.dev.gabriellucas.taskify.mappers.TarefaMapper;
import com.dev.gabriellucas.taskify.repositories.*;
import com.dev.gabriellucas.taskify.services.TarefaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository repository;
    private final TarefaMapper mapper;
    private final ListaRepository listaRepository;
    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;
    private final AnexoRepository anexoRepository;
    private final AnexoMapper anexoMapper;
    private final HistoricoRepository historicoRepository;
    private final HistoricoMapper historicoMapper;

    public TarefaServiceImpl(TarefaRepository repository, TarefaMapper mapper, ListaRepository listaRepository,
    ComentarioRepository comentarioRepository, ComentarioMapper comentarioMapper, AnexoRepository anexoRepository,
                             AnexoMapper anexoMapper, HistoricoRepository historicoRepository, HistoricoMapper historicoMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.listaRepository = listaRepository;
        this.comentarioRepository = comentarioRepository;
        this.comentarioMapper = comentarioMapper;
        this.anexoRepository = anexoRepository;
        this.anexoMapper = anexoMapper;
        this.historicoRepository = historicoRepository;
        this.historicoMapper = historicoMapper;
    }

    @Override
    @Transactional
    public TarefaResponseDTO saveTarefa(TarefaRequestDTO request, Long idLista) {
        Lista lista = listaRepository.findById(idLista)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + idLista));
        checkQuantity(idLista);
        Tarefa entity = mapper.toEntity(request);
        entity.setLista(lista);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public TarefaResponseDTO findTarefaById(Long id) {
        Optional<Tarefa> obj = repository.findById(id);
        Tarefa entity = obj.orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id:" + id));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public TarefaResponseDTO updateTarefa(TarefaRequestDTO request, Long id) {
        Tarefa entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id:" + id));
        mapper.updateEntityFromDTO(request, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public TarefaResponseDTO updateTarefaParcial(TarefaRequestPatchDTO request, Long id) {
        Tarefa entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id:" + id));
        if (request.getTitulo() != null) entity.setTitulo(request.getTitulo());
        if (request.getDescricao() != null) entity.setDescricao(request.getDescricao());
        if (request.getDataVencimento() != null) entity.setDataVencimento(request.getDataVencimento());
        if (request.getStatus() != null) entity.setStatus(request.getStatus());
        if (request.getPrioridade() != null) entity.setPrioridade(request.getPrioridade());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public TarefaResponseDTO completeTarefa(Long id) {
        Tarefa entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id:" + id));
        entity.setStatus(StatusTarefa.CONCLUIDA);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioResponseDTO> findAllCommentsByTarefaId(Long id) {
        List<Comentario> comentarios = comentarioRepository.findAllByTarefaId(id);
        return comentarios.stream()
                .map(comentarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnexoResponseDTO> findAllAnexosByTarefaId(Long id) {
        List<Anexo> anexos = anexoRepository.findAllByTarefaId(id);
        return anexos.stream()
                .map(anexoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoricoResponseDTO> findAllHistoricosByTarefaId(Long id) {
        List<Historico> historicos = historicoRepository.findAllByTarefaId(id);
        return historicos.stream()
                .map(historicoMapper::toDto)
                .collect(Collectors.toList());
    }

    protected void checkQuantity(Long id){
        int quantidade = repository.countTarefasByListaId(id);
        if(quantidade >= 1000){
            throw new BusinessException("Limite de 1000 tarefas por lista atingido para a lista com ID: " + id);
        }
    }


}
