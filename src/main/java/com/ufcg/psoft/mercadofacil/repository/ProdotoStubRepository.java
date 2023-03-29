package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Produto;

import java.util.List;

public class ProdotoStubRepository implements ProdutoRepository<Produto,Long> {
    @Override
    public Produto save(Produto produto) {
        return null;
    }

    @Override
    public Produto find(Long aLong) {
        return null;
    }

    @Override
    public List<Produto> findAll() {
        return null;
    }

    @Override
    public Produto update(Produto produto) {
        return null;
    }

    @Override
    public void delete(Produto produto) {

    }

    @Override
    public void deleteAll() {

    }
}
