package com.CompraDeProdutos.Service;

import com.CompraDeProdutos.Component.ItemCarrinho;
import com.CompraDeProdutos.Entity.ProdutosDeTecnologia;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarrinhoService {
    private final List<ItemCarrinho> carrinho = new ArrayList<>();

    public void adicionarAoCarrinho(ProdutosDeTecnologia produto, int quantidade) {
        ItemCarrinho item = new ItemCarrinho(
                produto.getId(),
                produto.getNome(),
                quantidade,
                produto.getValor()
        );
        carrinho.add(item);
    }

    public List<ItemCarrinho> verCarrinho() {
        return carrinho;
    }

    public double calcularTotal() {
        return carrinho.stream().mapToDouble(ItemCarrinho::getPrecoTotal).sum();
    }

    public List<ItemCarrinho> limparCarrinho() {
        List<ItemCarrinho> itens = new ArrayList<>(carrinho);
        carrinho.clear();
        return itens;
    }

    public boolean isCarrinhoVazio() {
        return carrinho.isEmpty();
    }
}

