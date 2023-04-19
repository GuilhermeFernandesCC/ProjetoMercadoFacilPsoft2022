package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.ProdutoPrecoDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PordutoAlterarPrecoPadraoService  implements ProdutoAlterarPrecoService{
    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public Produto atualizarPreco(Long id, ProdutoPrecoDTO produtoPrecoDTO) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        produto.setPreco(produtoPrecoDTO.getPreco());
        return produtoRepository.save(produto);
    }
}
