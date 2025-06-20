package com.CompraDeProdutos.Controller;


import com.CompraDeProdutos.Component.CompraRequest;
import com.CompraDeProdutos.Entity.ProdutosDeTecnologia;
import com.CompraDeProdutos.Service.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProdutoController {

    private final ProdutosService service;



    @Autowired
    public ProdutoController(ProdutosService service) {
        this.service = service;
    }


    @GetMapping("/produtos")
    public List<ProdutosDeTecnologia> tecnologias() {
        return service.produtos();
    }


    @GetMapping("/produtos/{nome}")
    public ResponseEntity<?> filterPorNome(@PathVariable String nome) {
        return service.pesquisaPorNome(nome);
    }

    @GetMapping("/produtos/categoria/{categoria}")
    public ResponseEntity<?> filterPorCategoria(@PathVariable String categoria) {
        return service.pesquisarPorCategoria(categoria);
    }


    @PostMapping("/carrinho/adicionar")
    public ResponseEntity<?> adicionarAoCarrinho(@RequestBody CompraRequest request) {
        return service.adicionarAoCarrinho(request.getProdutoId(), request.getQuantidade());
    }

    @GetMapping("/carrinho")
    public ResponseEntity<?> verCarrinho() {
        return service.verCarrinho();
    }

    @PostMapping("/carrinho/finalizar")
    public ResponseEntity<?> finalizarCompra() {
        return service.finalizarCompra();
    }




}
