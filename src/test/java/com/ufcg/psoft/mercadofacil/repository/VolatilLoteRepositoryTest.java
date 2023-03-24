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
    @DisplayName("Achar um Lote pelo seu Id com apenas um Lote")
    void findLoteByIdUmLote(){ //apenas um lote no repositorio
        resultado = driver.save(lote);
        Long id1 = 1L;
        Long id2 = 2L;

        assertNull(driver.find(id2));
        assertEquals(driver.find(id1),resultado);

    }

    @Test
    @DisplayName("Achar um Lote pelo seu Id com multiplos Lotes")
    void findLoteById() { //dois ou mais lotes no repositorio
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

        Long id1 = 1L;
        Long id2 = 2L;
        Long id3 = 3L;
        resultado = driver.save(lote);

        Lote resultado2 = driver.save(loteExtra);

        assertEquals(driver.find(id1),resultado);
        assertEquals(driver.find(id2),resultado2);
        assertNull(driver.find(id3));
    }

    @Test
    @DisplayName("Achar um Lote pelo seu Id sem nenhum lote")
    void findLoteByIdVazio(){ // nenhum lote no repositorio
        Long id1 = 1L;
        assertNull(driver.find(id1));
    }


    @Test
    @DisplayName("Recuperar todos os lotes Repositorio Vazio")
    void findAllVazio() {
        assertTrue(driver.findAll().isEmpty());

    }

    @Test
    @DisplayName("Recuperar todos os lotes")
    void findAll(){

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

        resultado = driver.save(lote);//adiciona o lote no repositorio

        assertTrue(driver.findAll().contains(resultado));// testa se o lote esta na lista
        assertFalse(driver.findAll().contains(loteExtra));// testa se o loteExtra não está na lista

        Lote resultado2 = driver.save(loteExtra);//adiciona o loteExtra no repositorio

        assertTrue(driver.findAll().contains(resultado));// testa se o lote está na lista
        assertTrue(driver.findAll().contains(resultado2));// testa se o loteExtra está na lista
    }


    @Test
    void update() {
        Produto produtoExtra = Produto.builder()
                .id(1L)
                .nome("Produto Extra")
                .codigoBarras("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        assertEquals(driver.find(1L),lote);
        resultado = driver.update(loteExtra);
        assertNotEquals(driver.find(1L),lote);
        assertEquals(driver.find(1L),resultado);
    }

    @Test
    void delete() {
        resultado = driver.save(lote);
        assertEquals(driver.find(lote.getId()),resultado);
        driver.delete(lote);
        assertNull(driver.find(lote.getId()));
    }

    @Test
    void deleteAll() {
        resultado = driver.save(lote);
        driver.deleteAll();
        assertTrue(driver.findAll().isEmpty());
    }
}
