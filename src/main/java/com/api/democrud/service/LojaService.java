package com.api.democrud.service;

import com.api.democrud.dto.request.LojaRequestDTO;
import com.api.democrud.dto.request.ProdutoLojaRequestDTO;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.Loja;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.LojaRepository;
import com.api.democrud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final LojaRepository lojaRepository;
    private final ProdutoRepository produtoRepository;
    public void save(LojaRequestDTO lojaRequestDTO) {
        List<Produto> produtos = new ArrayList<>();
        Loja loja = new Loja();
        List<Loja> lojas = new ArrayList<>();
        lojas.add(loja);
        produtos = lojaRequestDTO.getProdutos().stream()
                        .map(id ->{
                              Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ElementoNencontradoException("Produto não encontrado"));
                              produto.setLoja(lojas);
                              return produto;
                        }).toList();


        loja.setCredito(lojaRequestDTO.getCredito());
        loja.setNome(lojaRequestDTO.getNome());
        loja.setProduto(produtos);


        lojaRepository.save(loja);
    }
}
