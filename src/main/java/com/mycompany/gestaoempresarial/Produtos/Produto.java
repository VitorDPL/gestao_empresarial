package com.mycompany.gestaoempresarial.Produtos;

import java.util.Date;

public class Produto {
    private int id;
    private String nome;
    private String codigo;
    private String descricao;
    private int categoria_id;
    private int preco_compra;
    private double preco_venda;
    private double lucro_produto;
    private double custo;
    private int estoque_atual;

    public Produto(int id, String nome, String codigo, String descricao, int categoria_id, int preco_compra, double preco_venda, double lucro_produto, double custo, int estoque_atual, Date date) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.descricao = descricao;
        this.categoria_id = categoria_id;
        this.preco_compra = preco_compra;
        this.preco_venda = preco_venda;
        this.lucro_produto = lucro_produto;
        this.custo = custo;
        this.estoque_atual = estoque_atual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public int getPreco_compra() {
        return preco_compra;
    }

    public void setPreco_compra(int preco_compra) {
        this.preco_compra = preco_compra;
    }

    public double getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(double preco_venda) {
        this.preco_venda = preco_venda;
    }

    public double getLucro_produto() {
        return lucro_produto;
    }

    public void setLucro_produto(double lucro_produto) {
        this.lucro_produto = lucro_produto;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public int getEstoque_atual() {
        return estoque_atual;
    }

    public void setEstoque_atual(int estoque_atual) {
        this.estoque_atual = estoque_atual;
    }
}
