package example.DAO;

import com.mycompany.gestaoempresarial.Produtos.Categoria;
import example.banco.ConnectionFactory;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriasDAO implements DaoGenerics<Categoria, String> {
    @Override
    public void inserir(Categoria obj) throws SQLException {
        Connection c = ConnectionFactory.getConnection();

        // Usando PreparedStatement para evitar SQL Injection
        String sql = "INSERT INTO Categorias (nome, descricao) VALUES (?, ?)";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, obj.getNome());
        stmt.setString(2, obj.getDescricao());

        stmt.executeUpdate();
        stmt.close();
        c.close();
    }

    @Override
    public ArrayList<Categoria> buscarTodos() throws SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "SELECT * FROM Categorias";

        PreparedStatement pst = c.prepareStatement(sql);

        java.sql.ResultSet rs = pst.executeQuery();

        ArrayList<Categoria> categorias = new ArrayList<>();

        while (rs.next()) {
            Categoria categoria = new Categoria(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao")
            );
            categorias.add(categoria);
        }

        pst.close();
        c.close();
        return categorias;
    }


    @Override
    public Categoria editar(Categoria obj, String nome) throws SQLException {
        Connection c = ConnectionFactory.getConnection();

        // Usando PreparedStatement para evitar SQL Injection
        String sql = "UPDATE Categorias SET nome = ?, descricao = ? WHERE nome = ?";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, obj.getNome());
        stmt.setString(2, obj.getDescricao());
        stmt.setString(3, nome);

        stmt.executeUpdate();
        stmt.close();
        c.close();
        return obj;
    }

    @Override
    public Categoria deletar(Categoria obj, String nome) throws SQLException {
        Connection c = ConnectionFactory.getConnection();

        // Usando PreparedStatement para evitar SQL Injection
        String sql = "DELETE FROM Categorias WHERE nome = ?";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, nome);

        stmt.executeUpdate();
        stmt.close();
        c.close();
        return obj;
    }

    public Categoria buscarPorNome(String nome) throws SQLException {
        Connection c = ConnectionFactory.getConnection();

        // Usando PreparedStatement para evitar SQL Injection
        String sql = "SELECT * FROM Categorias WHERE nome = ?";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, nome);

        java.sql.ResultSet rs = stmt.executeQuery();
        Categoria categoria = null;

        while (rs.next()) {
            categoria = new Categoria(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao")
            );
        }

        stmt.close();
        c.close();
        return categoria;
    }

    public static void main(String[] args) {
        CategoriasDAO categoriasDAO = new CategoriasDAO();
        Categoria categoria = new Categoria(2, "Categoria 2", "Descrição da categoria 2");
        try {
            categoriasDAO.inserir(categoria);
            System.out.println("Categoria inserida com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
