package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Teste do controlador de produto")
public class ProdutoV1Controller {

    @Autowired
    MockMvc mockMvc ;

    ObjectMapper objectMapper;
    Produto produto;

    @BeforeEach
    void setup(){
        produto = Produto.builder()
                .id(10L)
                .codigoBarras("4012345678901")
                .fabricante("Empresa 1")
                .nome("Produto 1")
                .preco(200.00)
                .build();
    }

    @Test
    @DisplayName("Quando altera o produto com nome válido")
    void alteraProdutoNomeValido() throws Exception {
        //Arrange
        produto.setNome("chiclete");
        //Act
        String produtoModificadoJSON = mockMvc.perform(put("/produtos/"+10)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))

                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        //Assert
        Produto produtoModificado = objectMapper.readValue(produtoModificadoJSON,Produto.class);
        assertEquals("Chiclete",produtoModificado.getNome());
    }
}

