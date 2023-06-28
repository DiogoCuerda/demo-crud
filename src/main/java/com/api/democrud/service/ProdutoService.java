package com.api.democrud.service;


import com.api.democrud.dto.request.ProdutoRequestDTO;
import com.api.democrud.dto.response.ProdutoResponseDTO;
import com.api.democrud.enums.CategoriaProdutoEnum;
import com.api.democrud.exception.ProdutoDuplicadoException;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.api.democrud.exception.CustomExceptionHandler.DESCRICAO_DUPLICADA;
import static com.api.democrud.exception.CustomExceptionHandler.PRODUTO_NENCONTRADO;


@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(ProdutoRequestDTO produtoRequestDto) {
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoRequestDto, produto);
        produto.setCategoria(CategoriaProdutoEnum.REVENDA);
        if (produtoRepository.existsByNome(produtoRequestDto.getNome())) {

            throw new ProdutoDuplicadoException(String.format(DESCRICAO_DUPLICADA));
        }

        return produtoRepository.save(produto);
    }

    public Produto editar(UUID id, ProdutoRequestDTO produtoRequestDto) {

        try {
            var produtoEdit = buscaporId(id).get();
            //Validações
            if (produtoRepository.existsByNome(produtoRequestDto.getNome())
                    && !produtoRequestDto.getNome().equals(produtoEdit.getNome())) {
                throw new ProdutoDuplicadoException(String.format(DESCRICAO_DUPLICADA));
            }

            produtoEdit.setNome(produtoRequestDto.getNome());
            produtoEdit.setAtivo(produtoRequestDto.getAtivo());
            produtoEdit.setCategoria(produtoRequestDto.getCategoria());

            if (produtoRequestDto.getPreco() != null) {
                produtoEdit.setPreco(produtoRequestDto.getPreco());
            }

            return produtoRepository.save(produtoEdit);
        }
        catch (NoSuchElementException e) {
            throw new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO));
        }
    }

    public Produto consultaUm(UUID id) {
        try {
            return buscaporId(id).get();
        } catch (NoSuchElementException e) {
            throw new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO));
        }
    }

    public List<ProdutoResponseDTO> consultaTodos() {
        List<Produto> produtos = produtoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        List<ProdutoResponseDTO> produtoResponseDTOS = new ArrayList<ProdutoResponseDTO>();

        for(int i = 0;i < produtos.size();i++){
           ProdutoResponseDTO produtoResponseDTO = new ProdutoResponseDTO();
           Produto produto = produtos.get(i);

           produtoResponseDTO.setId(produto.getId());
           produtoResponseDTO.setEstoque(produto.getEstoque());
           produtoResponseDTO.setNome(produto.getNome());
           produtoResponseDTO.setAtivo(produto.getAtivo());
           produtoResponseDTO.setPreco(produto.getPreco());
           produtoResponseDTO.setCategoria(produto.getCategoria());
           produtoResponseDTO.setEmbalagem(produto.getUuids());
           produtoResponseDTOS.add(produtoResponseDTO);
        }

        return produtoResponseDTOS;
    }

    public List<Produto> consultaFiltro(String nome, Boolean ativo){

           return produtoRepository.findAllByNomeContainingAndAtivo(nome,ativo);
    }
    public Optional<Produto> buscaporId(UUID id) {
        return produtoRepository.findById(id);
    }

    public String deleteporId(UUID id) {
        if (!produtoRepository.existsById(id)) {
            throw new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO));
        }
        produtoRepository.deleteById(id);
        return ("Produto deletado:" + id);
    }


}
