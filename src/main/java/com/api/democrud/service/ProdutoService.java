package com.api.democrud.service;


import com.api.democrud.dto.request.ProdutoRequestDTO;
import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.dto.response.ProdutoResponseDTO;
import com.api.democrud.enums.CategoriaProdutoEnum;
import com.api.democrud.exception.ProdutoDuplicadoException;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Embalagem;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.api.democrud.exception.CustomExceptionHandler.DESCRICAO_DUPLICADA;
import static com.api.democrud.exception.CustomExceptionHandler.PRODUTO_NENCONTRADO;


@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public void salvar(ProdutoRequestDTO produtoRequestDto) {
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoRequestDto, produto);
        produto.setCategoria(CategoriaProdutoEnum.REVENDA);
        if (produtoRepository.existsByNome(produtoRequestDto.getNome())) {

            throw new ProdutoDuplicadoException(String.format(DESCRICAO_DUPLICADA));
        }
        produtoRepository.save(produto);
    }

    public void update(UUID id, ProdutoRequestDTO produtoRequestDto) {

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO)));

        if (produtoRepository.existsByNome(produtoRequestDto.getNome()) && !produtoRequestDto.getNome().equals(produto.getNome())) {
            throw new ProdutoDuplicadoException(String.format(DESCRICAO_DUPLICADA));
        }

        produto.builder()
                .ativo(produtoRequestDto.getAtivo())
                .nome(produtoRequestDto.getNome())
                .categoria(produtoRequestDto.getCategoria())
                .preco(produtoRequestDto.getPreco())
                .build();
        produtoRepository.save(produto);
    }

    public ProdutoResponseDTO findById(UUID id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO)));

        return ProdutoResponseDTO.builder()
                .estoque(produto.getEstoque())
                .nome(produto.getNome())
                .ativo(produto.getAtivo())
                .preco(produto.getPreco())
                .categoria(produto.getCategoria())
                .embalagem(getEmbalagemAsDTO(produto.getEmbalagem()))
                .build();
    }

    public List<ProdutoResponseDTO> findAll() {

        return produtoRepository.findAll().stream()
                .map(produto -> {
                    ProdutoResponseDTO dto = new ProdutoResponseDTO();
                    dto.setNome(produto.getNome());
                    dto.setEstoque(produto.getEstoque());
                    dto.setCategoria(produto.getCategoria());
                    dto.setPreco(produto.getPreco());
                    dto.setAtivo(produto.getAtivo());
                    dto.setEmbalagem(getEmbalagemAsDTO(produto.getEmbalagem()));
                    return dto;
                }).toList();
    }

    public List<Produto> consultaFiltro(String nome, Boolean ativo) {

        return produtoRepository.findAllByNomeContainingAndAtivo(nome, ativo);
    }

    public String deleteporId(UUID id) {
        if (!produtoRepository.existsById(id)) {
            throw new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO));
        }
        produtoRepository.deleteById(id);
        return ("Produto deletado:" + id);
    }

    public List<EmbalagemResponseDTO> getEmbalagemAsDTO(List<Embalagem> embalagem) {
        List<EmbalagemResponseDTO> listEmbalagem = new ArrayList<>();
        if (embalagem != null) {
            for (int i = 0; i < embalagem.size(); i++) {
                EmbalagemResponseDTO embalagemResponseDTO = EmbalagemResponseDTO.builder()
                        .nome(embalagem.get(i).getNome())
                        .build();

                listEmbalagem.add(embalagemResponseDTO);
            }
        }
        return listEmbalagem;
    }
}
