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
        } catch (NoSuchElementException e) {
            throw new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO));
        }
    }

    public ProdutoResponseDTO findById(UUID id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ElementoNencontradoException(String.format(PRODUTO_NENCONTRADO)));

        return ProdutoResponseDTO.builder()
                .estoque(produto.getEstoque())
                .nome(produto.getNome())
                .ativo(produto.getAtivo())
                .preco(produto.getPreco())
                .categoria(produto.getCategoria())
                .embalagem(getEmbalagems(produto.getEmbalagem()))
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
                    dto.setEmbalagem(getEmbalagems(produto.getEmbalagem()));
                    return dto;
                }).toList();

        //        List<Produto> produtos = produtoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
//        List<ProdutoResponseDTO> produtoResponseDTOS = new ArrayList<ProdutoResponseDTO>();
//
//        for(int i = 0;i < produtos.size();i++){
//
//            Produto produto = produtos.get(i);
//
//           ProdutoResponseDTO produtoResponseDTO = ProdutoResponseDTO.builder()
//                   .id(produto.getId())
//                   .estoque(produto.getEstoque())
//                   .nome(produto.getNome())
//                   .ativo(produto.getAtivo())
//                   .preco(produto.getPreco())
//                   .categoria(produto.getCategoria())
//                   .embalagem(produto.getEmbalagems())
//                   .build();
//            produtoResponseDTOS.add(produtoResponseDTO);
//
//        }
//        return produtoResponseDTOS;
    }

    public List<Produto> consultaFiltro(String nome, Boolean ativo) {

        return produtoRepository.findAllByNomeContainingAndAtivo(nome, ativo);
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


    public List<EmbalagemResponseDTO> getEmbalagems(List<Embalagem> embalagem) {
        List<EmbalagemResponseDTO> listEmbalagem = new ArrayList<>();
        if (embalagem != null) {
            for (int i = 0; i < embalagem.size(); i++) {
                EmbalagemResponseDTO embalagemResponseDTO = EmbalagemResponseDTO.builder()
                        // .id(embalagem.get(i).getId())
                        .nome(embalagem.get(i).getNome())
                        .build();

                listEmbalagem.add(embalagemResponseDTO);
            }

        }
        return listEmbalagem;
    }

}
