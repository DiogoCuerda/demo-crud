package com.api.democrud.service;


import com.api.democrud.dto.ProdutoDto;
import com.api.democrud.exception.ProdutoDuplicadoException;
import com.api.democrud.exception.ProdutoNencontradoException;
import com.api.democrud.model.FichaMateriaPrima;
import com.api.democrud.model.Produto;
import com.api.democrud.repositorie.FichaMateriaPrimaRepository;
import com.api.democrud.repositorie.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static com.api.democrud.exception.CustomExceptionHandler.DESCRICAO_DUPLICADA;
import static com.api.democrud.exception.CustomExceptionHandler.PRODUTO_NENCONTRADO;


@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(ProdutoDto produtoDto) {
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDto, produto);

        if (produtoRepository.existsByDescricao(produtoDto.getDescricao())) {

            throw new ProdutoDuplicadoException(String.format(DESCRICAO_DUPLICADA));
        }

        return produtoRepository.save(produto);
    }

    public Produto editar(UUID id, ProdutoDto produtoDto) {

        try {
            var produtoEdit = buscaporId(id).get();
            //Validações
            if (produtoRepository.existsByDescricao(produtoDto.getDescricao())
                    && !produtoDto.getDescricao().equals(produtoEdit.getDescricao())) {
                throw new ProdutoDuplicadoException(String.format(DESCRICAO_DUPLICADA));
            }

            produtoEdit.setDescricao(produtoDto.getDescricao());
            produtoEdit.setAtivo(produtoDto.getAtivo());

            if (produtoDto.getPreco() != 0) {
                produtoEdit.setPreco(produtoDto.getPreco());
            }

            return produtoRepository.save(produtoEdit);
        }
        catch (NoSuchElementException e) {
            throw new ProdutoNencontradoException(String.format(PRODUTO_NENCONTRADO));
        }
    }

    public Produto consultaUm(UUID id) {
        try {
            return buscaporId(id).get();
        } catch (NoSuchElementException e) {
            throw new ProdutoNencontradoException(String.format(PRODUTO_NENCONTRADO));
        }
    }

    public List<Produto> consultaTodos() {
        return produtoRepository.findAll(Sort.by(Sort.Direction.ASC, "descricao"));
    }

    public Optional<Produto> buscaporId(UUID id) {
        return produtoRepository.findById(id);
    }

    public String deleteporId(UUID id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNencontradoException(String.format(PRODUTO_NENCONTRADO));
        }
        produtoRepository.deleteById(id);
        return ("Produto deletado:" + id);
    }


}
