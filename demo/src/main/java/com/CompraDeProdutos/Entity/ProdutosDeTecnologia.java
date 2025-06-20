package com.CompraDeProdutos.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class ProdutosDeTecnologia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double valor;
    private int quantidade;
    private String descricao;
    private String categoria;


    public ProdutosDeTecnologia() {

    }


    public ProdutosDeTecnologia(Long id, String nome, double valor, int quantidade, String descricao) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProdutosDeTecnologia that)) return false;
        return Double.compare(getValor(), that.getValor()) == 0 && getQuantidade() == that.getQuantidade() && Objects.equals(getId(), that.getId()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getDescricao(), that.getDescricao()) && Objects.equals(getCategoria(), that.getCategoria());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getValor(), getQuantidade(), getDescricao(), getCategoria());
    }
}
