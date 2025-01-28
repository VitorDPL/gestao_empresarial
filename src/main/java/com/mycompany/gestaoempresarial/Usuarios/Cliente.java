package com.mycompany.gestaoempresarial.Usuarios;

public class Cliente {

    private int id;
    private String nome;
    private String cpf_cnpj;
    private String endereco;
    private String telefone;
    private String email;
    private Segmento segmento;

    public Cliente(int id, String nome, String cpf_cnpj, String endereco, String telefone, String email, Segmento segmento) {
        this.id = id;
        this.nome = nome;
        this.cpf_cnpj = cpf_cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.segmento = segmento;
    }

    public Cliente(String nome, String cpf_cnpj, String endereco, String telefone, String email, Segmento segmento) {
        this.nome = nome;
        this.cpf_cnpj = cpf_cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.segmento = segmento;
    }

    public Cliente() {

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
}
