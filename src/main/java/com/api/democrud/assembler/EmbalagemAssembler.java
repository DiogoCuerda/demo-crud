package com.api.democrud.assembler;

import com.api.democrud.dto.request.EmbalagemRequestDTO;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Embalagem;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.ProdutoRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.UUID;


@RequiredArgsConstructor
public class EmbalagemAssembler {


    private static void toEntity(EmbalagemRequestDTO dto){

       // return new Embalagem(dto.getNome(),)
    }

    private static void uuidToProduto(UUID id){
        ProdutoRepository produtoRepository;
       // return produtoRepository.findById().orElseThrow(()-> new ElementoNencontradoException("Produto não encontrado"));
    }
}
