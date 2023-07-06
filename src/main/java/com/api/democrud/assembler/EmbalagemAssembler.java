package com.api.democrud.assembler;

import com.api.democrud.dto.request.EmbalagemRequestDTO;
import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Embalagem;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.UUID;


@NoArgsConstructor
public class EmbalagemAssembler {

    public static Embalagem toEntity(EmbalagemRequestDTO dto, ProdutoRepository produtoRepository) {
        return new Embalagem(dto.getNome(), produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new ElementoNencontradoException("Produto não encontrado.")));
    }

    public static EmbalagemResponseDTO toResponseModel(Embalagem embalagem) {
        return new EmbalagemResponseDTO(embalagem.getNome());
    }

}
