package com.api.democrud.model;


import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.enums.CategoriaProdutoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.springframework.data.util.QTypeContributor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private Integer estoque;
    private Boolean ativo;

    @NotNull
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private CategoriaProdutoEnum categoria;

    @OneToMany(mappedBy = "produto",fetch = FetchType.LAZY)
    private List<Embalagem> embalagem;

    @ManyToMany(cascade = {
        CascadeType.ALL
    })
    @JoinTable(
            name = "tb_produtoloja",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "loja_id"))
    private List<Loja> loja;

    @Column(name = "data_registro")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataRegistro;

    public Produto(String nome, Integer estoque, Boolean ativo, BigDecimal preco, CategoriaProdutoEnum categoria) {
        this.nome = nome;
        this.estoque = estoque;
        this.ativo = ativo;
        this.preco = preco;
        this.categoria = categoria;
    }

    public Produto(UUID id, String nome, Integer estoque, Boolean ativo, BigDecimal preco, CategoriaProdutoEnum categoria) {
        this.id = id;
        this.nome = nome;
        this.estoque = estoque;
        this.ativo = ativo;
        this.preco = preco;
        this.categoria = categoria;
    }

    @PrePersist
    public void prePersist(){
        this.dataRegistro = LocalDateTime.now();
    }

//    @JsonIgnore
//    public List<EmbalagemResponseDTO> getEmbalagems(){
//        List<EmbalagemResponseDTO> listEmbalagem = new ArrayList<EmbalagemResponseDTO>();
//        if (embalagem != null){
//            for(int i = 0; i < embalagem.size(); i++){
//               EmbalagemResponseDTO embalagemResponseDTO = EmbalagemResponseDTO.builder()
//                       .id(embalagem.get(i).getId())
//                       .nome(embalagem.get(i).getNome())
//                       .build();
//
//               listEmbalagem.add(embalagemResponseDTO);
//            }
//
//        }
//       return  listEmbalagem;
//    }


}
