package com.api.democrud.services;


import com.api.democrud.dtos.ProdutoDto;
import com.api.democrud.exception.ProdutoDuplicadoException;
import com.api.democrud.models.Produto;
import com.api.democrud.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.api.democrud.exception.CustomExceptionHandler.DESCRICAO_DUPLICADA;


@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public Produto save(ProdutoDto produtoDto) {
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDto, produto);

        if (produtoRepository.existsByDescricao(produtoDto.getDescricao())) {
            throw new ProdutoDuplicadoException(String.format(DESCRICAO_DUPLICADA));
        }

        return produtoRepository.save(produto);
    }


    public boolean existsByDescricao(String descricao) {
        return produtoRepository.existsByDescricao(descricao);

    }

    public List<Produto> getAll() {
        return produtoRepository.findAll(Sort.by(Sort.Direction.ASC, "descricao"));
    }

    public Optional<Produto> findbyId(UUID id) {
        return produtoRepository.findById(id);
    }

    public void deletebyId(UUID id) {
        produtoRepository.deleteById(id);
    }


}
