package com.ufcg.psoft.mercadofacil.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPrecoDTO {
    @NotNull(message = "Preço obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    Double preco;
}
