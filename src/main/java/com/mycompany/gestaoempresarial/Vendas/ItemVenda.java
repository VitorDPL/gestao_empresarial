package com.mycompany.gestaoempresarial.Vendas;

import java.math.BigDecimal;

public class ItemVenda {
    private int id;
    private int vendaId;
    private int produtoId;
    private String produtoNome;
    private BigDecimal precoVenda;

    // Existing constructor
    public ItemVenda(int id, int vendaId, int produtoId) {
        this.id = id;
        this.vendaId = vendaId;
        this.produtoId = produtoId;
    }

    // New constructor
    public ItemVenda(int vendaId, String produtoNome, BigDecimal precoVenda) {
        this.vendaId = vendaId;
        this.produtoNome = produtoNome;
        this.precoVenda = precoVenda;
    }

    // Getters and setters for the new fields
    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    // Existing getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVendaId() {
        return vendaId;
    }

    public void setVendaId(int vendaId) {
        this.vendaId = vendaId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }
}