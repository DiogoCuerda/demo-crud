package com.api.democrud.service;


import com.api.democrud.assembler.ProdutoAssembler;
import com.api.democrud.dto.request.ProdutoRequestDTO;
import com.api.democrud.dto.response.ProdutoResponseDTO;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.exception.ProdutoDuplicadoException;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.api.democrud.exception.CustomExceptionHandler.DESCRICAO_DUPLICADA;
import static com.api.democrud.exception.CustomExceptionHandler.PRODUTO_NENCONTRADO;


@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public Produto save(ProdutoRequestDTO produtoRequestDto) {
        if (produtoRepository.existsByNome(produtoRequestDto.getNome())) {

            throw new ProdutoDuplicadoException(String.format(DESCRICAO_DUPLICADA));
        }
       return produtoRepository.save(ProdutoAssembler.toEntity(produtoRequestDto));
    }

    public void update(UUID id, ProdutoRequestDTO produtoRequestDto) {
        Produto produto = ProdutoAssembler.toEntity(produtoRequestDto);
        Produto produtoExistente = produtoRepository.findById(id).orElseThrow(() -> new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO)));

        if (produtoRepository.existsByNome(produtoRequestDto.getNome()) && !produtoRequestDto.getNome().equals(produtoExistente.getNome())) {
            throw new ProdutoDuplicadoException(String.format(DESCRICAO_DUPLICADA));
        }

        produtoExistente.update(produto);
        produtoRepository.save(produtoExistente);
    }

    public ProdutoResponseDTO findById(UUID id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO)));
        return ProdutoAssembler.toResponseModel(produto);
    }

    public List<ProdutoResponseDTO> findAll() {
        List<Produto> produtos = produtoRepository.findAll();
        return ProdutoAssembler.toListResponseModel(produtos);
    }

    public void delete(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO)));
        produtoRepository.delete(produto);
    }

}
