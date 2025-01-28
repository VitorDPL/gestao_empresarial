package example.DAO;

import com.mycompany.gestaoempresarial.Usuarios.Cliente;
import com.mycompany.gestaoempresarial.Usuarios.Segmento;
import example.banco.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public static void main(String[] args) {
        try {
            // Teste de inserção
            ClientesDAO dao = new ClientesDAO();
            Cliente cliente = new Cliente("Vitor", "43058739425", "Rua 1", "123456789", "hfg", Segmento.vip);
            dao.inserir(cliente);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
