package com.dev.gabriellucas.taskify.services.impl;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.entities.*;
import com.dev.gabriellucas.taskify.enums.StatusTarefa;
import com.dev.gabriellucas.taskify.exceptions.BusinessException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.mappers.*;
import com.dev.gabriellucas.taskify.repositories.*;
import com.dev.gabriellucas.taskify.services.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository repository;
    private final TarefaMapper tarefaMapper;
    private final ListaRepository listaRepository;
    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;
    private final AnexoRepository anexoRepository;
    private final AnexoMapper anexoMapper;
    private final HistoricoRepository historicoRepository;
    private final HistoricoMapper historicoMapper;
    private final EtiquetaRepository etiquetaRepository;
    private final EtiquetaMapper etiquetaMapper;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public TarefaServiceImpl(TarefaRepository repository, TarefaMapper tarefaMapper, ListaRepository listaRepository, ComentarioRepository comentarioRepository, ComentarioMapper comentarioMapper, AnexoRepository anexoRepository, AnexoMapper anexoMapper, HistoricoRepository historicoRepository, HistoricoMapper historicoMapper, EtiquetaRepository etiquetaRepository, CategoriaRepository categoriaRepository, EtiquetaMapper etiquetaMapper) {
        this.repository = repository;
        this.tarefaMapper = tarefaMapper;
        this.listaRepository = listaRepository;
        this.comentarioRepository = comentarioRepository;
        this.comentarioMapper = comentarioMapper;
        this.anexoRepository = anexoRepository;
        this.anexoMapper = anexoMapper;
        this.historicoRepository = historicoRepository;
        this.historicoMapper = historicoMapper;
        this.etiquetaRepository = etiquetaRepository;
        this.etiquetaMapper = etiquetaMapper;
        this.categoriaRepository = categoriaRepository;

    }

    @Override
    @Transactional
    @CachePut(value = "tarefas", key = "#idLista")
    @CacheEvict(value = "listaTarefas", key = "#idLista")
    public TarefaResponseDTO saveTarefa(TarefaRequestDTO request, Long idLista) {
        Lista lista = listaRepository.findById(idLista)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + idLista));
        checkQuantity(idLista);
        Tarefa entity = tarefaMapper.toEntity(request);
        entity.setLista(lista);
        return tarefaMapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "tarefas", key = "#id")
    public TarefaResponseDTO findTarefaById(Long id) {
        Optional<Tarefa> obj = repository.findById(id);
        Tarefa entity = obj.orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id:" + id));
        return tarefaMapper.toDto(entity);
    }

    @Override
    @Transactional
    @CachePut(value = "tarefas", key = "#id")
    public TarefaResponseDTO updateTarefa(TarefaRequestDTO request, Long id) {
        Tarefa entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id:" + id));
        tarefaMapper.updateEntityFromDTO(request, entity);
        return tarefaMapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @CachePut(value = "tarefas", key = "#id")
    public TarefaResponseDTO updateTarefaParcial(TarefaRequestPatchDTO request, Long id) {
        Tarefa entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id:" + id));
        if (request.getTitulo() != null) entity.setTitulo(request.getTitulo());
        if (request.getDescricao() != null) entity.setDescricao(request.getDescricao());
        if (request.getDataVencimento() != null) entity.setDataVencimento(request.getDataVencimento());
        if (request.getStatus() != null) entity.setStatus(request.getStatus());
        if (request.getPrioridade() != null) entity.setPrioridade(request.getPrioridade());
        return tarefaMapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @CachePut(value = "tarefas", key = "#id")
    public TarefaResponseDTO completeTarefa(Long id) {
        Tarefa entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id:" + id));
        entity.setStatus(StatusTarefa.CONCLUIDA);
        return tarefaMapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "comentarios", key = "#id")
    public List<ComentarioResponseDTO> findAllCommentsByTarefaId(Long id) {
        List<Comentario> comentarios = comentarioRepository.findAllByTarefaId(id);
        return comentarios.stream()
                .map(comentarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "anexos", key = "#id")
    public List<AnexoResponseDTO> findAllAnexosByTarefaId(Long id) {
        List<Anexo> anexos = anexoRepository.findAllByTarefaId(id);
        return anexos.stream()
                .map(anexoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "historicos", key = "#id")
    public List<HistoricoResponseDTO> findAllHistoricosByTarefaId(Long id) {
        List<Historico> historicos = historicoRepository.findAllByTarefaId(id);
        return historicos.stream()
                .map(historicoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut(value = "tarefas", key = "#idTarefa")
    public void addEtiquetaToTarefa(Long idTarefa, List<EtiquetaInsertRequestDTO> request) {
        Tarefa entity = repository.findById(idTarefa)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id:" + idTarefa));

        if (entity.getEtiquetas().size() + request.size() > 10) {
            throw new BusinessException("Limite máximo de 10 etiquetas por tarefa alcançado");
        }

        List<Long> idsEtiquetas = request.stream()
                .map(EtiquetaInsertRequestDTO::getIdEtiqueta)
                .toList();

        List<Etiqueta> etiquetas = etiquetaRepository.findAllById(idsEtiquetas);

        if (etiquetas.size() != idsEtiquetas.size()) {
            throw new ResourceNotFoundException("Uma ou mais etiquetas não foram encontradas.");
        }

        for (Etiqueta etiqueta : etiquetas) {
            boolean existsSameName = entity.getEtiquetas().stream()
                    .anyMatch(e -> e.getNome().equalsIgnoreCase(etiqueta.getNome()));
            if (existsSameName) {
                throw new BusinessException("Etiqueta com o nome '" + etiqueta.getNome() + "' já está associada à tarefa");
            }
        }

        entity.getEtiquetas().addAll(etiquetas);
        repository.save(entity);
    }

    @Override
    @Transactional
    @CachePut(value = "tarefas", key = "#idTarefa") // Atualiza o cache da tarefa
    @CacheEvict(value = "etiquetas", key = "#idTarefa") // Limpa o cache das etiquetas
    public void removeEtiquetaFromTarefa(Long idTarefa, Long idEtiqueta) {
        Tarefa entity = repository.findById(idTarefa)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id: " + idTarefa));

        boolean etiquetaExists = entity.getEtiquetas().stream()
                .anyMatch(etiqueta -> etiqueta.getId().equals(idEtiqueta));

        if (!etiquetaExists) {
            throw new BusinessException("Etiqueta com ID " + idEtiqueta + " não está associada à tarefa");
        }

        entity.getEtiquetas().removeIf(etiqueta -> etiqueta.getId().equals(idEtiqueta));

        repository.save(entity);
    }

    @Override
    @Cacheable(value = "etiquetas", key = "#id") // Adicione esta linha
    public List<EtiquetaResponseDTO> findAllEtiquetasByTarefaId(Long id) {
        List<Etiqueta> etiquetas = etiquetaRepository.findAllByTarefasId(id);
        return etiquetas.stream()
                .map(etiquetaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "tarefas", key = "#tarefaId")
    public TarefaResponseDTO addCategoriaToTarefa(Long tarefaId, Long categoriaId) {
        Tarefa tarefa = repository.findById(tarefaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id: " + tarefaId));

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada, id: " + categoriaId));

        if (tarefa.getCategorias() == null) {
            tarefa.setCategorias(new HashSet<>()); // Inicializa caso esteja nulo
        }

        tarefa.getCategorias().add(categoria);


        return tarefaMapper.toDto(repository.save(tarefa));
    }

    @Override
    @Transactional
    @CachePut(value = "tarefas", key = "#tarefaId")
    @CacheEvict(value = "categoria", key = "#categoriaId")
    public TarefaResponseDTO removeCategoriaFromTarefa(Long tarefaId, Long categoriaId) {
        Tarefa tarefa = repository.findById(tarefaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada, id: " + tarefaId));

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada, id: " + categoriaId));

        // Verifica se a tarefa possui categorias associadas
        if (tarefa.getCategorias() != null && tarefa.getCategorias().contains(categoria)) {
            tarefa.getCategorias().remove(categoria); // Remove a categoria da tarefa
        } else {
            throw new ResourceNotFoundException("A tarefa não contém essa categoria.");
        }

        return tarefaMapper.toDto(repository.save(tarefa));
    }

    protected void checkQuantity(Long id){
        int quantidade = repository.countTarefasByListaId(id);
        if(quantidade >= 1000){
            throw new BusinessException("Limite de 1000 tarefas por lista atingido para a lista com ID: " + id);
        }
    }

}
