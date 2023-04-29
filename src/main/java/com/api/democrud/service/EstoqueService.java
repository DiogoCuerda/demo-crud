package com.api.democrud.service;

import com.api.democrud.dto.EstoqueDto;
import com.api.democrud.exception.ProdutoNencontradoException;
import com.api.democrud.model.Produto;
import com.api.democrud.repositorie.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

import static com.api.democrud.exception.CustomExceptionHandler.ESTOQUE_NEGATIVO;
import static com.api.democrud.exception.CustomExceptionHandler.PRODUTO_NENCONTRADO;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;

    public Produto entradaEstoque(UUID id, EstoqueDto estoqueDto) {
        Optional<Produto> produto = produtoService.buscaporId(id);
        if (!produto.isPresent()) {
            throw new ProdutoNencontradoException(String.format(PRODUTO_NENCONTRADO));
        }

        produto.get().setEstoque(produto.get().getEstoque() + estoqueDto.getEstoque());

        return produtoRepository.save(produto.get());

    }
    public Produto saidaEstoque(UUID id, EstoqueDto estoqueDto) {
        Optional<Produto> produto = produtoService.buscaporId(id);
        if (!produto.isPresent()) {
            throw new ProdutoNencontradoException(String.format(PRODUTO_NENCONTRADO));
        }
         if (produto.get().getEstoque() - estoqueDto.getEstoque() >= 0) {
           produto.get().setEstoque(produto.get().getEstoque() - estoqueDto.getEstoque());
            return produtoRepository.save(produto.get());
        }
        throw new ProdutoNencontradoException(String.format(ESTOQUE_NEGATIVO));
    }
}
