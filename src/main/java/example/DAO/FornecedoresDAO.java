package example.DAO;

import com.mycompany.gestaoempresarial.Fornecedores.Fornecedor;
import example.banco.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FornecedoresDAO implements DaoGenerics<Fornecedor, String> {

    @Override
    public void inserir(Fornecedor obj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "INSERT INTO Fornecedores (nome, cnpj, telefone, email, endereco) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, obj.getNome());
        stmt.setString(2, obj.getCnpj());
        stmt.setString(3, obj.getTelefone());
        stmt.setString(4, obj.getEmail());
        stmt.setString(5, obj.getEndereco());

        stmt.executeUpdate();
        stmt.close();
        c.close();
    }

    @Override
    public ArrayList<Fornecedor> buscarTodos() throws SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM fornecedores";
        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        ArrayList<Fornecedor> fornecedores = new ArrayList<>();
        while (rs.next()) {
            Fornecedor fornecedor = new Fornecedor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cnpj"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("endereco")
            );
            fornecedores.add(fornecedor);
        }
        rs.close();
        pst.close();
        c.close();
        return fornecedores;
    }

    public Fornecedor editar(Fornecedor obj, String cnpj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        StringBuilder sql = new StringBuilder("UPDATE fornecedores SET ");
        List<Object> parametros = new ArrayList<>();

        if (obj.getNome() != null && !obj.getNome().isEmpty()) {
            sql.append("nome = ?, ");
            parametros.add(obj.getNome());
        }
        if (obj.getTelefone() != null && !obj.getTelefone().isEmpty()) {
            sql.append("telefone = ?, ");
            parametros.add(obj.getTelefone());
        }
        if (obj.getEmail() != null && !obj.getEmail().isEmpty()) {
            sql.append("email = ?, ");
            parametros.add(obj.getEmail());
        }
        if (obj.getEndereco() != null && !obj.getEndereco().isEmpty()) {
            sql.append("endereco = ?, ");
            parametros.add(obj.getEndereco());
        }

        // Remove the trailing comma and space
        if (sql.toString().endsWith(", ")) {
            sql.setLength(sql.length() - 2);
        }

        sql.append(" WHERE cnpj = ?");
        parametros.add(cnpj);

        PreparedStatement stmt = c.prepareStatement(sql.toString());

        for (int i = 0; i < parametros.size(); i++) {
            stmt.setObject(i + 1, parametros.get(i));
        }

        int rowsUpdated = stmt.executeUpdate();
        stmt.close();
        c.close();

        if (rowsUpdated > 0) {
            return obj;
        } else {
            throw new SQLException("No rows updated, check if the CNPJ exists.");
        }
    }

    public Fornecedor buscarPorNome(String nome) throws SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM fornecedores WHERE nome = ?";
        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, nome);
        ResultSet rs = pst.executeQuery();

        Fornecedor fornecedor = null;
        if (rs.next()) {
            fornecedor = new Fornecedor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cnpj"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("endereco")
            );
        }
        rs.close();
        pst.close();
        c.close();
        return fornecedor;
    }

    public Fornecedor buscarPorCnpj(String cnpj) throws SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM fornecedores WHERE cnpj = ?";
        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, cnpj);
        ResultSet rs = pst.executeQuery();

        Fornecedor fornecedor = null;
        if (rs.next()) {
            fornecedor = new Fornecedor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cnpj"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("endereco")
            );
        }
        rs.close();
        pst.close();
        c.close();
        return fornecedor;
    }

    public Fornecedor deletar(Fornecedor obj, String cnpj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "DELETE FROM fornecedores WHERE cnpj = ?";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, cnpj);

        stmt.executeUpdate();
        stmt.close();
        c.close();

        return obj;
    }

    public static void main(String[] args) {
        try {
            FornecedoresDAO dao = new FornecedoresDAO();
            Fornecedor fornecedor = dao.buscarPorCnpj("12345678901234");
            dao.deletar(fornecedor, "12345678901234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}