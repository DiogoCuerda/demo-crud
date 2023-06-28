package com.api.democrud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmbalagemResponseDTO {

    private String nome;
    private UUID produtoId;
}
