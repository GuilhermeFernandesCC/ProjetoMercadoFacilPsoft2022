package com.ufcg.psoft.mercadofacil.repository;

import java.util.List;

public interface ProdutoRepository<P,ID> {
    P save(P produto);
    P find(ID id);
    List<P> findAll();
    P update(P produto);
    void delete(P produto);
    void deleteAll();
}
