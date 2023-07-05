package com.api.democrud.service;


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

        EmbalagemResponseDTO embalagemResponseDTO = new EmbalagemResponseDTO();

        Produto produto = produtoRepository.findById(embalagemRequestDTO.getProdutoId())
                .orElseThrow(() -> new ElementoNencontradoException("Produto não encontrado."));

        Embalagem embalagem = embalagemRepository.save(Embalagem.builder()
                .nome(embalagemRequestDTO.getNome())
                .produto(produto).build());

        embalagemResponseDTO.setNome(embalagem.getNome());
        return embalagemResponseDTO;
    }

    public void delete(UUID id) {
        Embalagem embalagem = embalagemRepository.getById(id);
        embalagemRepository.delete(embalagem);
    }
}
