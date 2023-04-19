package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.ProdutoPrecoDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;

@FunctionalInterface
public interface ProdutoAlterarPrecoService {
    Produto atualizarPreco(Long id, ProdutoPrecoDTO produtoPrecoDTO);
}
