package example.DAO;

import com.mycompany.gestaoempresarial.Vendas.FormaPagamento;
import com.mycompany.gestaoempresarial.Vendas.Venda;
import com.mycompany.gestaoempresarial.clientes.Cliente;
import com.mycompany.gestaoempresarial.clientes.Segmento;
import com.mysql.cj.protocol.Resultset;
import example.banco.ConnectionFactory;
import com.mycompany.gestaoempresarial.Produtos.Produto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public ArrayList<Cliente> buscarTodos() throws SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM clientes";
        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        ArrayList<Cliente> clientes = new ArrayList<>();
        while (rs.next()) {
            Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf_cnpj"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    Segmento.valueOf(rs.getString("segmento")),
                    rs.getDate("data_cadastro")
            );
            clientes.add(cliente);
        }
        rs.close();
        pst.close();
        c.close();
        return clientes;
    }

    public Cliente editar(Cliente obj, String cpf_cnpj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        StringBuilder sql = new StringBuilder("UPDATE clientes SET ");
        List<Object> parametros = new ArrayList<>();

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

        sql.setLength(sql.length() - 2);

        sql.append(" WHERE cpf_cnpj = ?");

        parametros.add(cpf_cnpj);

        PreparedStatement stmt = c.prepareStatement(sql.toString());

        for (int i = 0; i < parametros.size(); i++) {
            stmt.setObject(i + 1, parametros.get(i));
        }

        stmt.executeUpdate();
        stmt.close();
        c.close();

        return obj;
    }


    public Cliente buscarPorNome(String nome) throws SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?";
        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, "%" + nome + "%");
        ResultSet rs = pst.executeQuery();

        Cliente cliente = null;
        if (rs.next()) {
            cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf_cnpj"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    Segmento.valueOf(rs.getString("segmento")),
                    rs.getDate("data_cadastro")
            );
        }
        rs.close();
        pst.close();
        c.close();
        return cliente;
    }

    public Cliente buscarPorCpfCnpj(String cpfCnpj) throws SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM clientes WHERE cpf_cnpj = ?";
        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, cpfCnpj);
        ResultSet rs = pst.executeQuery();

        Cliente cliente = null;
        if (rs.next()) {
            cliente = new Cliente(
                    rs.getInt("id"), // Ensure the ID is set
                    rs.getString("nome"),
                    rs.getString("cpf_cnpj"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    Segmento.valueOf(rs.getString("segmento")),
                    rs.getDate("data_cadastro")
            );
        }
        rs.close();
        pst.close();
        c.close();
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

    public List<Venda> buscarPorVendasCliente(int clienteId) {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM vendas WHERE clienteId = ?";

        System.out.println("Buscando vendas do cliente: " + clienteId);

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Venda venda = new Venda(
                            rs.getInt("id"),
                            rs.getDate("data"),
                            rs.getInt("clienteId"),
                            FormaPagamento.valueOf(rs.getString("formaPagamento")),
                            rs.getDouble("total")
                    );
                    vendas.add(venda);
                    System.out.println("Vendas encontradas: " + vendas.size());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vendas;
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
