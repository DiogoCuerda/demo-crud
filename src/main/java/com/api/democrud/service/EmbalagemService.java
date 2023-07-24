package com.api.democrud.service;


import com.api.democrud.assembler.EmbalagemAssembler;
import com.api.democrud.dto.request.EmbalagemRequestDTO;
import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Embalagem;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.EmbalagemRepository;
import com.api.democrud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmbalagemService {

    private final EmbalagemRepository embalagemRepository;
    private final ProdutoRepository produtoRepository;

    public void save(EmbalagemRequestDTO embalagemRequestDTO) {
        Produto produto = produtoRepository.findById(embalagemRequestDTO.getProdutoId())
                .orElseThrow(()-> new ElementoNencontradoException("Produto não encontrado"));
         EmbalagemAssembler.toResponseModel(embalagemRepository.save(EmbalagemAssembler.toEntity(embalagemRequestDTO, produto)));
    }

    public void delete(UUID id) {
        Embalagem embalagem = embalagemRepository.findById(id).orElseThrow(()-> new ElementoNencontradoException("Embalagem não encontrada"));
        embalagemRepository.delete(embalagem);
    }

    public void update(UUID id,EmbalagemRequestDTO embalagemRequestDTO) {
        Embalagem embalagemExistente = embalagemRepository.findById(id).orElseThrow(()-> new ElementoNencontradoException("Embalagem não encontrada"));
        Produto produto = produtoRepository.findById(embalagemRequestDTO.getProdutoId()).orElseThrow(()-> new ElementoNencontradoException("Produto referenciado não encontrado"));
        Embalagem embalagem = EmbalagemAssembler.toEntity(embalagemRequestDTO,produto);
        embalagemExistente.update(embalagem);
        embalagemRepository.save(embalagemExistente);
    }

    public EmbalagemResponseDTO findById(UUID id) {
        return EmbalagemAssembler.toResponseModel(embalagemRepository.findById(id).orElseThrow(()-> new ElementoNencontradoException("Embalagem não encontrada")));
    }

    public List<EmbalagemResponseDTO> findAll() {
        return EmbalagemAssembler.toListResponseModel(embalagemRepository.findAll());
    }
}
