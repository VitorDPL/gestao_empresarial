package example.DAO;

import com.mycompany.gestaoempresarial.Produtos.Produto;
import example.banco.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO implements DaoGenerics<Produto, String> {
    @Override
    public void inserir(Produto obj) throws ClassNotFoundException, SQLException {
        String checkSql = "SELECT * FROM produtos WHERE codigo = ?";
        String insertSql = "INSERT INTO Produtos (nome, codigo, descricao, categoria_id, preco_compra, preco_venda, lucro_produto, custo, estoque_atual) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE produtos SET estoque_atual = estoque_atual + ? WHERE codigo = ?";

        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement checkStmt = c.prepareStatement(checkSql)) {

            checkStmt.setString(1, obj.getCodigo());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Produto já existe, realiza a atualização
                try (PreparedStatement updateStmt = c.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, obj.getEstoque_atual());
                    updateStmt.setString(2, obj.getCodigo());
                    updateStmt.executeUpdate();
                }
            } else {
                // Produto não existe, realiza a inserção
                try (PreparedStatement insertStmt = c.prepareStatement(insertSql)) {
                    insertStmt.setString(1, obj.getNome());
                    insertStmt.setString(2, obj.getCodigo());
                    insertStmt.setString(3, obj.getDescricao());
                    insertStmt.setInt(4, obj.getCategoria_id());
                    insertStmt.setDouble(5, obj.getPreco_compra());
                    insertStmt.setDouble(6, obj.getPreco_venda());
                    insertStmt.setDouble(7, obj.getLucro_produto());
                    insertStmt.setDouble(8, obj.getCusto());
                    insertStmt.setInt(9, obj.getEstoque_atual());
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir ou atualizar o produto", e);
        }
    }

    @Override
    public ArrayList<Produto> buscarTodos() throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM produtos";
        ArrayList<Produto> produtos = new ArrayList<>();

        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement pst = c.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("codigo"),
                        rs.getString("descricao"),
                        rs.getInt("categoria_id"),
                        (int) rs.getDouble("preco_compra"),
                        rs.getDouble("preco_venda"),
                        rs.getDouble("lucro_produto"),
                        rs.getDouble("custo"),
                        rs.getInt("estoque_atual"),
                        new java.util.Date());
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar todos os produtos", e);
        }

        return produtos;
    }

    @Override
    public Produto editar(Produto obj, String id) throws ClassNotFoundException, SQLException {
        StringBuilder sql = new StringBuilder("UPDATE produtos SET ");
        List<Object> parametros = new ArrayList<>();

        if (obj.getNome() != null && !obj.getNome().isEmpty()) {
            sql.append("nome = ?, ");
            parametros.add(obj.getNome());
        }
        if (obj.getCodigo() != null && !obj.getCodigo().isEmpty()) {
            sql.append("codigo = ?, ");
            parametros.add(obj.getCodigo());
        }
        if (obj.getDescricao() != null && !obj.getDescricao().isEmpty()) {
            sql.append("descricao = ?, ");
            parametros.add(obj.getDescricao());
        }
        if (obj.getCategoria_id() > 0) {
            sql.append("categoria_id = ?, ");
            parametros.add(obj.getCategoria_id());
        }
        if (obj.getPreco_compra() > 0) {
            sql.append("preco_compra = ?, ");
            parametros.add(obj.getPreco_compra());
        }
        if (obj.getPreco_venda() > 0) {
            sql.append("preco_venda = ?, ");
            parametros.add(obj.getPreco_venda());
        }
        if (obj.getLucro_produto() > 0) {
            sql.append("lucro_produto = ?, ");
            parametros.add(obj.getLucro_produto());
        }
        if (obj.getCusto() > 0) {
            sql.append("custo = ?, ");
            parametros.add(obj.getCusto());
        }
        if (obj.getEstoque_atual() >= 0) {
            sql.append("estoque_atual = ?, ");
            parametros.add(obj.getEstoque_atual());
        }

        if (parametros.isEmpty()) {
            return obj;
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");
        parametros.add(id);

        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao editar o produto", e);
        }

        return obj;
    }

    public List<Produto> buscarPorNome(String nome) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM produtos WHERE nome LIKE ?";
        List<Produto> produtos = new ArrayList<>();

        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement pst = c.prepareStatement(sql)) {

            pst.setString(1, "%" + nome + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("codigo"),
                        rs.getString("descricao"),
                        rs.getInt("categoria_id"),
                        rs.getInt("preco_compra"),
                        rs.getDouble("preco_venda"),
                        rs.getDouble("lucro_produto"),
                        rs.getDouble("custo"),
                        rs.getInt("estoque_atual"),
                        new java.util.Date());
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar produtos por nome", e);
        }

        return produtos;
    }

    @Override
    public Produto deletar(Produto obj, String codigo) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao deletar o produto", e);
        }

        return obj;
    }

    public Produto buscarPorCodigo(String codigo) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM produtos WHERE codigo = ?";
        Produto produto = null;

        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement pst = c.prepareStatement(sql)) {

            pst.setString(1, codigo);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("codigo"),
                        rs.getString("descricao"),
                        rs.getInt("categoria_id"),
                        rs.getInt("preco_compra"),
                        rs.getDouble("preco_venda"),
                        rs.getDouble("lucro_produto"),
                        rs.getDouble("custo"),
                        rs.getInt("estoque_atual"),
                        new java.util.Date());
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar produto por código", e);
        }

        return produto;
    }

    public static void main(String[] args) {
        ProdutosDAO produtosDAO = new ProdutosDAO();
        Produto produto = new Produto(1, "Produto 1", "123456", "Descrição do produto 1", 1, 10, 20.0, 10.0, 10.0, 10, new java.util.Date());
        try {
            produtosDAO.inserir(produto);
            System.out.println("Produto inserido com sucesso!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
