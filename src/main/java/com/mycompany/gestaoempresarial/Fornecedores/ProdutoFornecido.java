package com.mycompany.gestaoempresarial.Fornecedores;

public class ProdutoFornecido {
    private int id;
    private Integer fornecedorId;
    private Integer produtoId;

    public ProdutoFornecido(int id, Integer fornecedorId, Integer produtoId) {
        this.id = id;
        this.fornecedorId = fornecedorId;
        this.produtoId = produtoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Integer fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }
}