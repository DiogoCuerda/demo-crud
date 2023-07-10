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

    public LojaResponseDTO findById(UUID id) {
        return LojaAssembler.toResponseModel(lojaRepository.findById(id).orElseThrow(() -> new ElementoNencontradoException("Loja não encontrada")));
    }

    public void update(UUID id, LojaRequestDTO lojaRequestDTO) {

       Loja lojaExistente = lojaRepository.findById(id).orElseThrow(()-> new ElementoNencontradoException("Loja não encontrada"));
       List<Produto> produtos = lojaRequestDTO.getProdutos().stream()
                .map(uuidProduto -> produtoRepository.findById(uuidProduto)
                       .orElseThrow(() -> new ElementoNencontradoException("Produto não encontrado"))).toList();
       Loja loja = new Loja();
       loja.setNome(lojaRequestDTO.getNome());
       loja.setCredito(lojaRequestDTO.getCredito());
//       produtos = produtos.stream()
//                .map(produto -> {produto.getLoja().add(loja);return produto;}).toList();
       loja.setProduto(produtos);


       lojaExistente.update(loja);
       lojaRepository.save(lojaExistente);

        //        Loja lojaExistente = lojaRepository.findById(id).orElseThrow(() -> new ElementoNencontradoException("Loja não encontrada"));
//        List<Produto> produtosExistente = lojaExistente.getProduto().stream()
//                .map(produtoExistenteMap -> produtoRepository.findById(produtoExistenteMap.getId())
//                        .orElseThrow(() -> new ElementoNencontradoException("Produto não encontrado"))).toList();
//
//        List<Produto> produtos = lojaRequestDTO.getProdutos().stream()
//                .map(uuidProduto -> produtoRepository.findById(uuidProduto)
//                        .orElseThrow(() -> new ElementoNencontradoException("Produto não encontrado"))).toList();
//
//        lojaExistente.update(lojaRequestDTO.getNome(),lojaRequestDTO.getCredito(), produtos);
//        lojaRepository.save(lojaExistente);
    }
}
