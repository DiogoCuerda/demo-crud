package com.api.democrud.service;

import com.api.democrud.assembler.LojaAssembler;
import com.api.democrud.dto.request.LojaRequestDTO;
import com.api.democrud.dto.response.LojaResponseDTO;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.LojaRepository;
import com.api.democrud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final LojaRepository lojaRepository;
    private final ProdutoRepository produtoRepository;

    public LojaResponseDTO save(LojaRequestDTO lojaRequestDTO) {
        List<Produto> produtos = lojaRequestDTO.getProdutos().stream()
                .map(id -> {
                    Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ElementoNencontradoException("Produto não encontrado"));
                    return produto;
                }).toList();
        return LojaAssembler.toResponseModel(lojaRepository.save(LojaAssembler.toEntity(lojaRequestDTO, produtos)));
    }

    public List<LojaResponseDTO> findAll() {
        return LojaAssembler.toListResponseModel(lojaRepository.findAll());
    }
}
