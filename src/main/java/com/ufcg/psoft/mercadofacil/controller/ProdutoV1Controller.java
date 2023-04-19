package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.ProdutoPrecoDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ProdutoAlterarPadraoService;
import com.ufcg.psoft.mercadofacil.service.ProdutoAlterarPrecoService;
import com.ufcg.psoft.mercadofacil.service.ProdutoAlterarService;
import com.ufcg.psoft.mercadofacil.service.ProdutoListarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/v1/produtos",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProdutoV1Controller {

    @Autowired
    ProdutoAlterarService produtoAtualizarService;

    @Autowired
    ProdutoListarService produtoListarService;

    @Autowired
    ProdutoAlterarPrecoService produtoAlterarPrecoService;
    @PutMapping("/{id}")
    public Produto atualizarProduto(
            @PathVariable Long id,
            @RequestBody Produto produto) {
        return produtoAtualizarService.alterar(produto);
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoListarService.listar(null);
    }

    @PatchMapping("/{id}")
    public Produto atualizarPrecoProduto(@PathVariable Long id,
                                         @RequestBody @Valid ProdutoPrecoDTO produtoPreco){

        return produtoAlterarPrecoService.atualizarPreco(id,produtoPreco);
    }

    /*
    @DeleteMapping("/{id}")
    public Produto deletarProduto(@PathVariable Long id) {return produtoExcluirService(id)}
   */
}