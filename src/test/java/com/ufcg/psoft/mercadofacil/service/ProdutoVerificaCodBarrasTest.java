package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("Testes verificador de codBarras")
class ProdutoVerificaCodBarrasTest {
    @Autowired
    ProdutoVerificaCodBarras driver;

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
    @DisplayName("Testa codBarras Valido")
    void codBarrasValido(){
        assertTrue(driver.verificarCodBarras(produto));
    }

    @Test
    @DisplayName("Testa codBarras Invalido")
    void codBarrasInvalido(){
        produto.setCodigoBarras("4012345678902");
        assertFalse(driver.verificarCodBarras(produto));
    }

}
