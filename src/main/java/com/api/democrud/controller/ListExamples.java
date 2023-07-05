package com.api.democrud.controller;

import com.api.democrud.dto.response.ProdutoResponseDTO;
import com.api.democrud.model.Produto;
import com.api.democrud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/list")
@RequiredArgsConstructor
public class ListExamples {

    private final ProdutoRepository produtoRepository;
    @GetMapping("/1")
    public String ListTest() {
        //String output = " ";
        return Stream.iterate(1, i -> i + 1)
                .takeWhile(n -> n <= 10)
                .map(x -> x * x).toList().toString();
               // .forEach(output = output + " :");

    }

    @GetMapping("/2")
    public String ListTest2(){
        List<String> list = new ArrayList<>();
        list.add("Brasil");
        list.add("Brasil");
        list.add("Argentina");
        list.add("Brasil");
        list.add("Alemanha");
        list.add("Alemanha");

        return Integer.toString((int) list.stream().filter(e-> e.equals("Brasil")).count());

    }

    @GetMapping("/3")
    public String ListTest3(){

        return produtoRepository.findAll().stream()
                .map(produto -> {
                    ProdutoResponseDTO dto = new ProdutoResponseDTO();
                    dto.setNome(produto.getNome());
                    dto.setEstoque(produto.getEstoque());
                    dto.setCategoria(produto.getCategoria());
                    dto.setPreco(produto.getPreco());
                    dto.setAtivo(produto.getAtivo());
                    dto.setEmbalagem(null);
                    return dto;
                }).mapToDouble(produto-> produto.getEstoque()).average().toString();//filter(e ->  !e.getNome().equals("agua")).toList().toString();

    }


    public void ListTest4(){
        Stream<Integer> evenNumStream = Stream.iterate(2, i -> i * 2);

    }

}
