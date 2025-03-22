package example.DAO;

import com.mycompany.gestaoempresarial.Fornecedores.Fornecedor;
import com.mycompany.gestaoempresarial.Produtos.Produto;
import example.banco.ConnectionFactory;
import com.mycompany.gestaoempresarial.Fornecedores.ProdutoFornecido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosFornecidosDAO implements DaoGenerics<ProdutoFornecido, String> {

    @Override
    public void inserir(ProdutoFornecido obj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "INSERT INTO produtos_fornecidos (fornecedor_id, produto_id) VALUES (?, ?)";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setInt(1, obj.getFornecedorId());
        stmt.setInt(2, obj.getProdutoId());
        stmt.executeUpdate();
        stmt.close();
        c.close();
    }

    @Override
    public ArrayList<ProdutoFornecido> buscarTodos() throws SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM produtos_fornecidos";
        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        ArrayList<ProdutoFornecido> produtosFornecidos = new ArrayList<>();
        while (rs.next()) {
            ProdutoFornecido produtoFornecido = new ProdutoFornecido(
                    rs.getInt("id"),
                    rs.getInt("fornecedor_id"),
                    rs.getInt("produto_id")
            );
            produtosFornecidos.add(produtoFornecido);
        }
        rs.close();
        pst.close();
        c.close();
        return produtosFornecidos;
    }

    @Override
    public ProdutoFornecido editar(ProdutoFornecido obj, String id) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        StringBuilder sql = new StringBuilder("UPDATE produtos_fornecidos SET ");
        List<Object> parametros = new ArrayList<>();

        if (obj.getFornecedorId() != null) {
            sql.append("fornecedor_id = ?, ");
            parametros.add(obj.getFornecedorId());
        }
        if (obj.getProdutoId() != null) {
            sql.append("produto_id = ?, ");
            parametros.add(obj.getProdutoId());
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");
        parametros.add(id);

        PreparedStatement stmt = c.prepareStatement(sql.toString());

        for (int i = 0; i < parametros.size(); i++) {
            stmt.setObject(i + 1, parametros.get(i));
        }

        stmt.executeUpdate();
        stmt.close();
        c.close();

        return obj;
    }

    @Override
    public ProdutoFornecido deletar(ProdutoFornecido obj, String id) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "DELETE FROM produtos_fornecidos WHERE id = ?";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.executeUpdate();
        stmt.close();
        c.close();
        return obj;
    }

    public ProdutoFornecido buscarPorId(Integer id) throws SQLException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM produtos_fornecidos WHERE id = ?";
        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        ProdutoFornecido produtoFornecido = null;
        if (rs.next()) {
            produtoFornecido = new ProdutoFornecido(
                    rs.getInt("id"),
                    rs.getInt("fornecedor_id"),
                    rs.getInt("produto_id")
            );
        }
        rs.close();
        pst.close();
        c.close();
        return produtoFornecido;
    }

    public ProdutoFornecido buscarPorProdutoId(int produtoId) throws SQLException {
        String sql = "SELECT * FROM produtos_fornecidos WHERE produto_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ProdutoFornecido(
                        rs.getInt("id"),
                        rs.getInt("fornecedor_id"),
                        rs.getInt("produto_id")
                );
            }
        }
        return null;
    }


    public List<Produto> buscarPorFornecedorId(int fornecedorId) throws SQLException {
        List<Produto> produtosFornecidos = new ArrayList<>();
        String sql = "SELECT p.* FROM produtos p " +
                "JOIN produtos_fornecidos pf ON p.id = pf.produto_id " +
                "WHERE pf.fornecedor_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fornecedorId);
            ResultSet rs = stmt.executeQuery();
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
                        rs.getInt("estoque_atual")
                );
                produtosFornecidos.add(produto);
            }
        }
        return produtosFornecidos;
    }

}
