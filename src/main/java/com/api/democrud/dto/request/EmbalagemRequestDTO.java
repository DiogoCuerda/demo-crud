package com.api.democrud.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmbalagemRequestDTO {

    private String nome;
    private UUID produtoId;
}
