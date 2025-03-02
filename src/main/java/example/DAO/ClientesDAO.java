package example.DAO;

import com.mycompany.gestaoempresarial.clientes.Cliente;
import com.mycompany.gestaoempresarial.clientes.Segmento;
import com.mysql.cj.protocol.Resultset;
import example.banco.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO implements DaoGenerics<Cliente, String> {

    @Override
    public void inserir(Cliente obj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        // Usando PreparedStatement para evitar SQL Injection
        String sql = "INSERT INTO Clientes (nome, cpf_cnpj, endereco, telefone, email, segmento, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, obj.getNome());
        stmt.setString(2, obj.getCpf_cnpj());
        stmt.setString(3, obj.getEndereco());
        stmt.setString(4, obj.getTelefone());
        stmt.setString(5, obj.getEmail());
        stmt.setString(6, obj.getSegmento().toString());
        stmt.setDate(7, new java.sql.Date(obj.getDataCadastro().getTime()));


        stmt.executeUpdate();
        stmt.close();
        c.close();
    }

    @Override
    public ArrayList<Cliente> buscarTodos() throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "Select * from clientes";

        PreparedStatement pst = c.prepareStatement(sql);

        Resultset rs = (Resultset) pst.executeQuery();

        ArrayList<Cliente> clientes = new ArrayList<>();

        while (((java.sql.ResultSet) rs).next()) {
            Cliente cliente = new Cliente(((java.sql.ResultSet) rs).getString("nome"), ((java.sql.ResultSet) rs).getString("cpf_cnpj"), ((java.sql.ResultSet) rs).getString("endereco"), ((java.sql.ResultSet) rs).getString("telefone"), ((java.sql.ResultSet) rs).getString("email"), Segmento.valueOf(((java.sql.ResultSet) rs).getString("segmento")));
            clientes.add(cliente);
        }

        return clientes;

    }

    public Cliente editar(Cliente obj, String cpf_cnpj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        // Criando a consulta de atualização inicial
        StringBuilder sql = new StringBuilder("UPDATE clientes SET ");
        List<Object> parametros = new ArrayList<>();

        // Verifica e adiciona cada campo à consulta apenas se não estiver em branco
        if (obj.getNome() != null && !obj.getNome().isEmpty()) {
            sql.append("nome = ?, ");
            parametros.add(obj.getNome());
        }
        if (obj.getEndereco() != null && !obj.getEndereco().isEmpty()) {
            sql.append("endereco = ?, ");
            parametros.add(obj.getEndereco());
        }
        if (obj.getTelefone() != null && !obj.getTelefone().isEmpty()) {
            sql.append("telefone = ?, ");
            parametros.add(obj.getTelefone());
        }
        if (obj.getEmail() != null && !obj.getEmail().isEmpty()) {
            sql.append("email = ?, ");
            parametros.add(obj.getEmail());
        }
        if (obj.getSegmento() != null) {
            sql.append("segmento = ?, ");
            parametros.add(obj.getSegmento().toString());
        }

        // Remover a última vírgula e espaço da consulta
        sql.setLength(sql.length() - 2);

        // Adiciona a condição WHERE para o CPF/CNPJ
        sql.append(" WHERE cpf_cnpj = ?");

        // Adiciona o CPF/CNPJ como o último parâmetro
        parametros.add(cpf_cnpj);

        // Preparando a consulta
        PreparedStatement stmt = c.prepareStatement(sql.toString());

        // Definindo os parâmetros na consulta
        for (int i = 0; i < parametros.size(); i++) {
            stmt.setObject(i + 1, parametros.get(i));
        }

        // Executa a atualização
        stmt.executeUpdate();
        stmt.close();
        c.close();

        return obj;
    }


    public Cliente buscarPorCpfCnpj(String cpf_cnpj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "Select * from clientes where cpf_cnpj = ?";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, cpf_cnpj);

        Resultset rs = (Resultset) pst.executeQuery();

        Cliente cliente = null;

        if (((java.sql.ResultSet) rs).next()) {
            cliente = new Cliente(((java.sql.ResultSet) rs).getString("nome"), ((java.sql.ResultSet) rs).getString("cpf_cnpj"), ((java.sql.ResultSet) rs).getString("endereco"), ((java.sql.ResultSet) rs).getString("telefone"), ((java.sql.ResultSet) rs).getString("email"), Segmento.valueOf(((java.sql.ResultSet) rs).getString("segmento")));
        }

        return cliente;
    }

    public Cliente buscarPorNome(String nome) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "Select * from clientes where nome = ?";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, nome);

        Resultset rs = (Resultset) pst.executeQuery();

        Cliente cliente = null;

        if (((java.sql.ResultSet) rs).next()) {
            cliente = new Cliente(((java.sql.ResultSet) rs).getString("nome"), ((java.sql.ResultSet) rs).getString("cpf_cnpj"), ((java.sql.ResultSet) rs).getString("endereco"), ((java.sql.ResultSet) rs).getString("telefone"), ((java.sql.ResultSet) rs).getString("email"), Segmento.valueOf(((java.sql.ResultSet) rs).getString("segmento")));
        }

        return cliente;
    }

    public Cliente deletar(Cliente obj, String cpf_cnpj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "DELETE FROM clientes WHERE cpf_cnpj = ?";

        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, cpf_cnpj);

        stmt.executeUpdate();
        stmt.close();
        c.close();

        return obj;
    }

    public static void main(String[] args) {
        try {
            ClientesDAO dao = new ClientesDAO();
            Cliente cliente = dao.buscarPorCpfCnpj("cxz");
            dao.deletar(cliente, "cxz");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
