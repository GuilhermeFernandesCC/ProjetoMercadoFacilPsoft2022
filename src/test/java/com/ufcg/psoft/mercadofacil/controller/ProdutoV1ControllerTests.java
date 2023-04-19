package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.dto.ProdutoPrecoDTO;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Produtos")
public class ProdutoV1ControllerTests {
    @Autowired
    MockMvc driver;

    final String URI_PRODUTOS = "/v1/produtos";
    @Autowired
    ProdutoRepository produtoRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Produto produto;

    @BeforeEach
    void setup() {
        produto = produtoRepository.save(Produto.builder()
                .codigoDeBarras("1234567890123")
                .fabricante("Fabricante Dez")
                .nome("Produto Dez")
                .preco(100.00)
                .build());
    }

    @AfterEach
    void tearDown() {
        produtoRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de campos obrigatórios")
    class ProdutoValidacaoCamposObrigatorios {

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados válidos")
        void quandoAlteramosNomeDoProdutoValido() throws Exception {
            // Arrange
            produto.setNome("Produto Dez Alterado");

            // Act
            String responseJsonString = driver.perform(put(URI_PRODUTOS+"/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals(resultado.getNome(), "Produto Dez Alterado");
        }

    }

    @Nested
    @DisplayName("Conjunto de casos de verificação da regra sobre o preço")
    class ProdutoValidacaoRegrasDoPreco {
        // Implementar os testes aqui
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação da validação do código de barras")
    class ProdutoValidacaoCodigoDeBarras {
        // Implementar os testes aqui
    }

    @Nested
    @DisplayName("Conjunto de casos de teste REST API (caminhos basicos)")
    class ProdutoValidacaoRestApiBasico {
        //getAll()
        Produto produto1;
        @BeforeEach
        void setup(){
            produto1 = produtoRepository.save(Produto.builder()
                    .codigoDeBarras("9876543210123")
                    .fabricante("Fabricante Vinte")
                    .nome("Produto Vinte")
                    .preco(200.00)
                    .build());
        }
        @Test
        @DisplayName("Teste do GetAll()")
        void quandoUsuarioAPIGetAllProdutos() throws Exception {
            //arrange
            produtoRepository.save(produto);
            produtoRepository.save(produto1);

            //act
            String responseJsonString = driver.perform(get(URI_PRODUTOS)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Produto> resultados = objectMapper
                    .readerForListOf(Produto.class)
                    .readValue(responseJsonString);

            //assert
            assertEquals(2,resultados.size());
        }
        //get(id)
        //post() -> body
        //put(id) -> body
        //delete(id)
        //deleteAll

        @Test
        @DisplayName("Teste a API ao alterar o preço de um produto")
        void quandoUsuarioAPIAtualizaPrecoDeUmProduto() throws Exception{
            //Arrange
            ProdutoPrecoDTO produtoPrecoDTO = ProdutoPrecoDTO.builder()
                    .preco(220.00)
                    .build();

            // Act
            String responseJsonString = driver.perform(patch(URI_PRODUTOS+"/"+produto1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoPrecoDTO.getPreco())))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
            Produto resultado = objectMapper.readValue(responseJsonString,Produto.class);

            //Assert
            assertEquals(220.0,resultado.getPreco());
        }

    }

    @Nested
    @DisplayName("Conjunto de casos de teste exceções na REST API (caminhos inesperados)")
    class ProdutoValidacaRestApiExcecoes{
        @Test
        @DisplayName("Quando informamos valor de produto é menor ou igual a zero")
        void quandoalterarPrecoProdutoMenorIgualAZero() throws Exception {
            //Arrange
            ProdutoPrecoDTO produtoPrecoDTO = ProdutoPrecoDTO.builder()
                    .preco(0.0)
                    .build();
            //Act
            String responseJsonString = driver.perform(patch(URI_PRODUTOS+"/"+produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoPrecoDTO.getPreco())))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
            CustomErrorType resultado = objectMapper.readValue(responseJsonString,CustomErrorType.class);
            //Assert
            assertEquals("error.validation.method.argument.not.valid",resultado.getErrorCode());
            assertEquals("Erros de validacao encontrados",resultado.getMessage());
            assertArrayEquals(new String[]{"O valor deve ser maior que zero"},resultado.getErrors().toArray());
        }
    }


}
