package example.DAO;

import com.mycompany.gestaoempresarial.Produtos.Produto;
import example.banco.ConnectionFactory;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO implements DaoGenerics<Produto, String> {
    @Override
    public void inserir(Produto obj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        // Usando PreparedStatement para evitar SQL Injection
        String sql = "INSERT INTO Produtos (nome, codigo, descricao, categoria_id, preco_compra, preco_venda, lucro_produto, custo, estoque_atual) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, obj.getNome());
        stmt.setString(2, obj.getCodigo());
        stmt.setString(3, obj.getDescricao());
        stmt.setInt(4, obj.getCategoria_id());
        stmt.setDouble(5, obj.getPreco_compra());
        stmt.setDouble(6, obj.getPreco_venda());
        stmt.setDouble(7, obj.getLucro_produto());
        stmt.setDouble(8, obj.getCusto());
        stmt.setInt(9, obj.getEstoque_atual());

        stmt.executeUpdate();
        stmt.close();
        c.close();
    }

    @Override
    public ArrayList<Produto> buscarTodos() throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "Select * from produtos";

        PreparedStatement pst = c.prepareStatement(sql);

        java.sql.ResultSet rs = pst.executeQuery();

        ArrayList<Produto> produtos = new ArrayList<>();

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
                    rs.getInt("estoque_atual")
            );

            produtos.add(produto);
        }

        return produtos;
    }

    @Override
    public Produto editar(Produto obj, String id) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        // Criando a consulta de atualização inicial
        StringBuilder sql = new StringBuilder("UPDATE produtos SET ");
        List<Object> parametros = new ArrayList<>();

        // Verifica e adiciona cada campo à consulta apenas se não estiver em branco/nulo
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
        if (obj.getEstoque_atual() >= 0) { // Estoque pode ser zero, então >= 0
            sql.append("estoque_atual = ?, ");
            parametros.add(obj.getEstoque_atual());
        }

        // Se não houver atualizações, retorna sem fazer nada
        if (parametros.isEmpty()) {
            c.close();
            return obj;
        }

        // Remover a última vírgula e espaço da consulta
        sql.setLength(sql.length() - 2);

        // Adiciona a condição WHERE para identificar o produto pelo ID
        sql.append(" WHERE id = ?");
        parametros.add(id);

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

    @Override
    public Produto deletar(Produto obj, String codigo) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "DELETE FROM produtos WHERE id = ?";

        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, codigo);

        stmt.executeUpdate();
        stmt.close();
        c.close();

        return obj;
    }

    public static void main(String[] args) {
        ArrayList<Produto> produtos = new ArrayList<>();
        ProdutosDAO produtosDAO = new ProdutosDAO();

        try {
            produtos = produtosDAO.buscarTodos();

            for(Produto produto : produtos) {
                System.out.println(produto.getNome());
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}