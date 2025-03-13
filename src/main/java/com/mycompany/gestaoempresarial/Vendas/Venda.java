package com.mycompany.gestaoempresarial.Vendas;

import java.util.Date;

public class Venda {
    private int id;
    private Date data;
    private int clienteId;
    private FormaPagamento formaPagamento;
    private double total;
    private String clienteNome;

    public Venda(int id, Date data, int clienteId, FormaPagamento formaPagamento, double total) {
        this.id = id;
        this.data = data;
        this.clienteId = clienteId;
        this.formaPagamento = formaPagamento;
        this.total = total;
    }

    public Venda(int id, String clienteNome, Date data, double total){
        this.id = id;
        this.clienteNome = clienteNome;
        this.data = data;
        this.total = total;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
}