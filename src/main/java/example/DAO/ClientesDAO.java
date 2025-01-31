package example.DAO;

import com.mycompany.gestaoempresarial.Usuarios.Cliente;
import com.mycompany.gestaoempresarial.Usuarios.Segmento;
import com.mysql.cj.protocol.Resultset;
import example.banco.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientesDAO implements DaoGenerics<Cliente, Integer> {

    @Override
    public void inserir(Cliente obj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        // Usando PreparedStatement para evitar SQL Injection
        String sql = "INSERT INTO Clientes (nome, cpf_cnpj, endereco, telefone, email, segmento) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, obj.getNome());
        stmt.setString(2, obj.getCpf_cnpj());
        stmt.setString(3, obj.getEndereco());
        stmt.setString(4, obj.getTelefone());
        stmt.setString(5, obj.getEmail());
        stmt.setString(6, obj.getSegmento().toString());

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

    public Cliente atualizar(Cliente obj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "UPDATE clientes SET nome = ?, endereco = ?, telefone = ?, email = ?, segmento = ? WHERE cpf_cnpj = ?";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getEndereco());
        pst.setString(3, obj.getTelefone());
        pst.setString(4, obj.getEmail());
        pst.setString(5, obj.getSegmento().toString());
        pst.setString(6, obj.getCpf_cnpj());

        pst.executeUpdate();

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

    public static void main(String[] args) {

        try{
            // buscando por cpf
            ClientesDAO dao = new ClientesDAO();
            Cliente cliente = dao.buscarPorCpfCnpj("1049185654");
            System.out.println(cliente.getNome());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
