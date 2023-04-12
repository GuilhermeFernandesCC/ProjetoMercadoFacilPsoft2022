package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ProdutoAlterarService;
import com.ufcg.psoft.mercadofacil.service.ProdutoListarService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping("/{id}")
    public Produto atualizarProduto(
            @PathVariable Long id,
            @RequestBody Produto produto) {
        return produtoAtualizarService.alterar(produto);
    }

    @GetMapping
    public List<Produto> listarProdutos(){
        return produtoListarService.listar(null);
    }
}
