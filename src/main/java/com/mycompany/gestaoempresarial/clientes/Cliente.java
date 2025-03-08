package com.mycompany.gestaoempresarial.clientes;

import java.util.Date;

public class Cliente {

    private int id;
    private String nome;
    private String cpf_cnpj;
    private String endereco;
    private String telefone;
    private String email;
    private Segmento segmento;
    private Date data_cadastro;

    // Construtor completo
    public Cliente(int id, String nome, String cpf_cnpj, String endereco, String telefone, String email, Segmento segmento, Date data_cadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf_cnpj = cpf_cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.segmento = segmento;
        this.data_cadastro = data_cadastro;
    }

    // Construtor sem ID e data_cadastro (data será setada como a atual)
    public Cliente(String nome, String cpf_cnpj, String endereco, String telefone, String email, Segmento segmento) {
        this.nome = nome;
        this.cpf_cnpj = cpf_cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.segmento = segmento;
        this.data_cadastro = new Date(); // Define a data atual
    }

    // Construtor vazio
    public Cliente() {
        this.data_cadastro = new Date(); // Define a data atual
    }

    // Getters e Setters
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

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public Date getDataCadastro() {
        return this.data_cadastro;
    }

    public void setDataCadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }
}