package com.mycompany.gestaoempresarial.Vendas;

public class ItemVenda {
    private int id;
    private int vendaId;
    private int produtoId;

    public ItemVenda(int id, int vendaId, int produtoId) {
        this.id = id;
        this.vendaId = vendaId;
        this.produtoId = produtoId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getVendaId() { return vendaId; }
    public void setVendaId(int vendaId) { this.vendaId = vendaId; }
    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }
}