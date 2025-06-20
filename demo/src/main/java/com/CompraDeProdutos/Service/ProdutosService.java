package com.CompraDeProdutos.Service;

import com.CompraDeProdutos.Component.ItemCarrinho;
import com.CompraDeProdutos.Entity.ProdutosDeTecnologia;
import com.CompraDeProdutos.Repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutosService {

    private final ProdutosRepository repository;
    private final CarrinhoService carrinhoService;

    @Autowired
    public ProdutosService(ProdutosRepository repository, CarrinhoService carrinhoService) {
        this.repository = repository;
        this.carrinhoService = carrinhoService;

    }

    public List<ProdutosDeTecnologia> produtos() {
        return repository.findAll();
    }

    public ResponseEntity<?> pesquisaPorNome(String nome) {
        ProdutosDeTecnologia produtosDeTecnologia = repository.findByNome(nome).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        return ResponseEntity.ok(produtosDeTecnologia);
    }


    public ResponseEntity<?> pesquisarPorCategoria(String categoria) {
        List<ProdutosDeTecnologia> produtosDeTecnologia = repository.findAllByCategoria(categoria);
        if (produtosDeTecnologia.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Produto não encontrado");
        }
        return ResponseEntity.ok(produtosDeTecnologia);
    }


    public ResponseEntity<?> adicionarAoCarrinho(Long produtoId, int quantidade) {
        ProdutosDeTecnologia produto = repository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        if (quantidade <= 0) {
            return ResponseEntity.badRequest().body("Quantidade inválida");
        }

        if (produto.getQuantidade() < quantidade) {
            return ResponseEntity.badRequest().body("Estoque insuficiente");
        }

        carrinhoService.adicionarAoCarrinho(produto, quantidade);
        return ResponseEntity.ok("Produto adicionado ao carrinho");
    }


    public ResponseEntity<?> verCarrinho() {
        return ResponseEntity.ok(carrinhoService.verCarrinho());
    }
    public ResponseEntity<?> finalizarCompra() {
        if (carrinhoService.isCarrinhoVazio()) {
            return ResponseEntity.badRequest().body("Carrinho vazio");
        }

        List<ItemCarrinho> itens = carrinhoService.limparCarrinho();

        for (ItemCarrinho item : itens) {
            ProdutosDeTecnologia produto = repository.findById(item.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

            if (produto.getQuantidade() < item.getQuantidade()) {
                return ResponseEntity.badRequest().body("Estoque insuficiente para " + produto.getNome());
            }

            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            repository.save(produto);
        }

        double total = itens.stream().mapToDouble(ItemCarrinho::getPrecoTotal).sum();
        return ResponseEntity.ok("Compra realizada com sucesso! Total: R$" + total);
    }


}
