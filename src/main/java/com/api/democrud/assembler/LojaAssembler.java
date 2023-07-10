package com.api.democrud.assembler;

import com.api.democrud.dto.request.LojaRequestDTO;
import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.dto.response.LojaResponseDTO;
import com.api.democrud.dto.response.ProdutoResponseDTO;
import com.api.democrud.model.Embalagem;
import com.api.democrud.model.Loja;
import com.api.democrud.model.Produto;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class LojaAssembler {
    public static LojaResponseDTO toResponseModel(Loja loja) {
        return new LojaResponseDTO(loja.getNome(), loja.getCredito(), setListofProdutoResponse(loja.getProduto()));
    }

    public static List<LojaResponseDTO> toListResponseModel(List<Loja> loja) {
        return loja.stream()
                .map(LojaAssembler::toResponseModel).toList();
    }

    public static Loja toEntity(LojaRequestDTO lojaRequestDTO, List<Produto> produtos) {
        Loja loja = new Loja(lojaRequestDTO.getNome(), lojaRequestDTO.getCredito());
        loja.setProduto(produtos);
        return loja;
    }

    public static List<ProdutoResponseDTO> setListofProdutoResponse(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> {
                    ProdutoResponseDTO produtoResponseDTO = new ProdutoResponseDTO();
                    produtoResponseDTO.setNome(produto.getNome());
                    produtoResponseDTO.setCategoria(produto.getCategoria());
                    produtoResponseDTO.setPreco(produto.getPreco());
                    produtoResponseDTO.setAtivo(produto.getAtivo());
                    produtoResponseDTO.setEstoque(produto.getEstoque());
                    produtoResponseDTO.setEmbalagem(setListofEmbalagemResponse(produto.getEmbalagem()));
                    return produtoResponseDTO;
                }).toList();
    }

    public static List<EmbalagemResponseDTO> setListofEmbalagemResponse(List<Embalagem> embalagems) {
        return embalagems.stream()
                .map(embalagem -> {
                    EmbalagemResponseDTO embalagemResponseDTO = new EmbalagemResponseDTO();
                    embalagemResponseDTO.setNome(embalagem.getNome());
                    return embalagemResponseDTO;
                }).toList();
    }
}
