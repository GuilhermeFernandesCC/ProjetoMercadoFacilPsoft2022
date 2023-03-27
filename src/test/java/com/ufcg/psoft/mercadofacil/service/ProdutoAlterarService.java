package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc//faz o mock ser usado em outras classes
@DisplayName("Testes para alteração do Produto")
class ProdutoAlterarServiceTest {
    @Autowired
    ProdutoAlterarService driver;

    @MockBean
    ProdutoRepository<Produto,Long> produtoRepository; //Ainda não implementado
    Produto produto;

    @BeforeEach
    void setup(){
        Mockito.when(produtoRepository.find(10L))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarras("7899137500704")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );
        produto = produtoRepository.find(10L);

        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarras("7899137500704")
                        .nome("Nome Produto Alterado")
                        .fabricante("Nome Fabricante Alterado")
                        .preco(500.00)
                        .build()
                );


    }

    @Test
    @DisplayName("Quando alterar o nome do produto com um nome válido")
    void alterarNomeDoProduto(){
        //Arrange
        produto.setNome("Nome Produto Alterado");
        //Act
        Produto newProduto = driver.alterar(produto);
        //Assert
        assertEquals("Nome Produto Alterado",newProduto.getNome() );
    }

    @Test
    @DisplayName("Quando preco é menor ou igual a zero")
    void precomenorIgualZero(){
        //Arrange
        produto.setPreco(0.0);
        //arrange
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                ()-> driver.alterar(produto)
        );
        //Assert
        assertEquals("Preco invalido",thrown.getMessage());


    }

}
