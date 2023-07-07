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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmbalagemService {

    private final EmbalagemRepository embalagemRepository;
    private final ProdutoRepository produtoRepository;

    public EmbalagemResponseDTO save(EmbalagemRequestDTO embalagemRequestDTO) {
        Produto produto = produtoRepository.findById(embalagemRequestDTO.getProdutoId())
                .orElseThrow(()-> new ElementoNencontradoException("Produto não encontrado"));
        return EmbalagemAssembler.toResponseModel(embalagemRepository.save(EmbalagemAssembler.toEntity(embalagemRequestDTO, produto)));
    }

    public void delete(UUID id) {
        Embalagem embalagem = embalagemRepository.findById(id).orElseThrow(()-> new ElementoNencontradoException("Embalagem não encontrada"));
        embalagemRepository.delete(embalagem);
    }
}
