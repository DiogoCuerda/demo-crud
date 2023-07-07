package com.api.democrud.assembler;

import com.api.democrud.dto.request.ProdutoRequestDTO;
import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.dto.response.ProdutoResponseDTO;
import com.api.democrud.model.Embalagem;
import com.api.democrud.model.Produto;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class ProdutoAssembler {

    public static Produto toEntity(ProdutoRequestDTO dto){
        return new Produto(dto.getNome(),dto.getEstoque(),dto.getAtivo(),dto.getPreco(),dto.getCategoria());
    }

    public static ProdutoResponseDTO toResponseModel(Produto produto){
        return new ProdutoResponseDTO(produto.getNome(),produto.getPreco(),produto.getEstoque(),produto.getAtivo(),produto.getCategoria(),setListOf(produto.getEmbalagem()));
    }

    public static List<ProdutoResponseDTO> toListResponseModel(List<Produto> produtos){
        return produtos.stream()
                .map(ProdutoAssembler::toResponseModel).toList();
    }

    private static List<EmbalagemResponseDTO> setListOf(List<Embalagem> embalagems){
        return embalagems.stream()
                .map(embalagem -> {
                    EmbalagemResponseDTO embalagemResponseDTO = new EmbalagemResponseDTO();
                    embalagemResponseDTO.setNome(embalagem.getNome());
                    return embalagemResponseDTO;
                }).toList();
    }

}
