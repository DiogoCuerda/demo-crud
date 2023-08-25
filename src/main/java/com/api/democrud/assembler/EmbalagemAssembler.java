package com.api.democrud.assembler;

import com.api.democrud.dto.request.EmbalagemRequestDTO;
import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Embalagem;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.ProdutoRepository;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class EmbalagemAssembler {

    public static Embalagem toEntity(EmbalagemRequestDTO dto, Produto produto) {
        return new Embalagem(dto.getNome(),produto);
    }

    public static EmbalagemResponseDTO toResponseModel(Embalagem embalagem) {
        return new EmbalagemResponseDTO(embalagem.getId(),embalagem.getNome(),embalagem.getProduto().getId());
    }

    public static List<EmbalagemResponseDTO> toListResponseModel(List<Embalagem> embalagems) {
        return embalagems.stream().map(EmbalagemAssembler::toResponseModel).toList();
    }
}
