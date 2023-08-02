package com.api.democrud.service;

import com.api.democrud.assembler.LojaAssembler;
import com.api.democrud.dto.request.LojaRequestDTO;
import com.api.democrud.dto.response.LojaResponseDTO;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Loja;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.LojaRepository;
import com.api.democrud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final LojaRepository lojaRepository;
    private final ProdutoRepository produtoRepository;

    public Loja save(LojaRequestDTO lojaRequestDTO) {
        List<Produto> produtos = lojaRequestDTO.getProdutos().stream()
                .map(id -> produtoRepository.findById(id).orElseThrow(() -> new ElementoNencontradoException("Produto não encontrado"))).toList();
        Loja loja = LojaAssembler.toEntity(lojaRequestDTO, produtos);

        return lojaRepository.save(loja);
    }

    public List<LojaResponseDTO> findAll() {
        return LojaAssembler.toListResponseModel(lojaRepository.findAll());
    }

    public LojaResponseDTO findById(UUID id) {
        return LojaAssembler.toResponseModel(lojaRepository.findById(id).orElseThrow(() -> new ElementoNencontradoException("Loja não encontrada")));
    }

    public void update(UUID id, LojaRequestDTO lojaRequestDTO) {

        Loja lojaExistente = lojaRepository.findById(id).orElseThrow(()-> new ElementoNencontradoException("Loja não encontrada"));
        List<UUID> idsProduto = lojaRequestDTO.getProdutos();
        List<Produto> produtos = produtoRepository.findAllById(idsProduto);

        if (produtos.size() != idsProduto.size()){
            throw new ElementoNencontradoException("Produto não encontrado");
        }

        Loja loja = LojaAssembler.toEntity(lojaRequestDTO,produtos);
        lojaExistente.update(loja);
        lojaRepository.save(lojaExistente);
    }
    public void delete(UUID id) {
        Loja loja = lojaRepository.findById(id).orElseThrow(()-> new ElementoNencontradoException("Loja não encontrada"));
        lojaRepository.delete(loja);
    }

    public List<LojaResponseDTO> findMaisXProdutos(int numeroProdutos) {
        return LojaAssembler.toListResponseModel(lojaRepository.findMaisXProdutos(numeroProdutos));
    }
}
