package com.ufcg.psoft.mercadofacil.service;


import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.stereotype.Service;

@Service
public class ProdutoImpVerificaCodBarras implements ProdutoVerificaCodBarras {

    @Override
    public boolean verificarCodBarras(Produto produto) {
        String codBarras = produto.getCodigoBarras();
        int somaImpares= 0;
        int somaPares = 0;

        for (int i = 0; i < 12; i+=1) {
            somaPares += codBarras.charAt(i);
            somaImpares += codBarras.charAt(i+1);
        }

        int aux = (somaImpares * 3) + somaPares;
        int verificador = 10 - aux%10;
        if (verificador == 10){
            verificador = 0;
        }

        if(verificador == codBarras.charAt(12)){
            return true;
        }
        return false;
    }
}
