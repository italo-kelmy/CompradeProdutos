package com.CompraDeProdutos.Repository;

import com.CompraDeProdutos.Entity.ProdutosDeTecnologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutosRepository extends JpaRepository<ProdutosDeTecnologia, Long> {
    Optional<ProdutosDeTecnologia> findByNome(String nome);
     List<ProdutosDeTecnologia> findAllByCategoria(String categoria);


}
