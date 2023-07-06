package com.api.democrud.service;

import com.api.democrud.assembler.LojaAssembler;
import com.api.democrud.dto.request.LojaRequestDTO;
import com.api.democrud.dto.response.LojaResponseDTO;
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
        return LojaAssembler.toResponseModel(lojaRepository.save(LojaAssembler.toEntity(lojaRequestDTO,produtoRepository)));
    }

    public List<LojaResponseDTO> findAll() {
        return LojaAssembler.toListResponseModel(lojaRepository.findAll());
    }
}
