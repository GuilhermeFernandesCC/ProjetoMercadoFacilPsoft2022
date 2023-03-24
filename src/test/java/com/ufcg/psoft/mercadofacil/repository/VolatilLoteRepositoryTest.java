package com.ufcg.psoft.mercadofacil.repository;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
class VolatilLoteRepositoryTest {
    @Autowired
    LoteRepository<Lote,Long> driver = new VolatilLoteRepository();
    Lote lote;
    Lote resultado;
    Produto produto;
    @BeforeEach
    void setup(){
        produto = Produto.builder()
                .id(1L)
                .nome("Produto Base")
                .preco(125.36)
                .fabricante("Fabricante Base")
                .codigoBarras("12345678")
                .build();
        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produto)
                .build();
    }
    @AfterEach
    void tearDown(){
        produto = null;
        driver.deleteAll();
    }

    @Test
    @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
    void salvarPrimeiroLote(){
        resultado = driver.save(lote);

        assertEquals(driver.findAll().size(),1);
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
    }

    @Test
    @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados")
    void salvarSegundoLoteOuPosterior() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarras("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);

        resultado = driver.save(loteExtra);

        assertEquals(driver.findAll().size(),2);
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);

    }
    @Test
    @DisplayName("Achar um Lote pelo seu Id")
    void findLoteById(){
        resultado = driver.save(lote);
        assertEquals(driver.find(1L).getId(),lote.getId());
        assertEquals(resultado.getProduto(), produto);
    }

}
