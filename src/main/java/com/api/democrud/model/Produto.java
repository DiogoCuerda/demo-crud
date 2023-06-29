package com.api.democrud.model;


import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.enums.CategoriaProdutoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@Table(name = "tb_produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private BigDecimal preco;
    private Integer estoque;
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private CategoriaProdutoEnum categoria;

    @OneToMany(mappedBy = "produto",fetch = FetchType.LAZY)
    private List<Embalagem> embalagem;

    @Column(name = "data_registro")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataRegistro;

    @PrePersist
    public void prePersist(){
        this.dataRegistro = LocalDateTime.now();
    }

//    @JsonIgnore
//    public List<UUID> getUuids(){
//        List<UUID> listUuid = new ArrayList<UUID>();
//        if (embalagem != null) {
//            for (int i = 0; i < embalagem.size(); i++) {
//                listUuid.add(embalagem.get(i).getId());
//            }
//        }
//        return listUuid;
//    }
    @JsonIgnore
    public List<EmbalagemResponseDTO> getEmbalagems(){
        List<EmbalagemResponseDTO> listEmbalagem = new ArrayList<EmbalagemResponseDTO>();
        if (embalagem != null){
            for(int i = 0; i < embalagem.size(); i++){
               EmbalagemResponseDTO embalagemResponseDTO = new EmbalagemResponseDTO();
               embalagemResponseDTO.setProdutoId(embalagem.get(i).getProduto().getId());
               embalagemResponseDTO.setNome(embalagem.get(i).getNome());
               listEmbalagem.add(embalagemResponseDTO);
            }

        }
       return  listEmbalagem;
    }


}
