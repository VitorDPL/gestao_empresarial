package example.DAO;

import com.mycompany.gestaoempresarial.Produtos.Produto;
import com.mycompany.gestaoempresarial.Vendas.FormaPagamento;
import com.mycompany.gestaoempresarial.Vendas.ItemVenda;
import com.mycompany.gestaoempresarial.Vendas.Venda;
import example.banco.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class VendasDAO implements DaoGenerics<Venda, String> {

    @Override
    public void inserir(Venda obj) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "INSERT INTO Vendas (data, clienteId, formaPagamento, total) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(obj.getData().getTime()));
        stmt.setInt(2, obj.getClienteId());
        stmt.setString(3, obj.getFormaPagamento().name());
        stmt.setDouble(4, obj.getTotal());

        stmt.executeUpdate();
        stmt.close();
        c.close();
    }

    @Override
    public ArrayList<Venda> buscarTodos() throws SQLException {
        ArrayList<Venda> vendas = new ArrayList<>();
        String sql = "SELECT v.id, c.nome AS cliente_nome, v.data, v.total " +
                "FROM vendas v " +
                "JOIN clientes c ON v.clienteId = c.id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venda venda = new Venda(
                        rs.getInt("id"),
                        rs.getString("cliente_nome"),
                        rs.getDate("data"),
                        rs.getDouble("total")
                );
                vendas.add(venda);
            }
        }
        return vendas;
    }

    @Override
    public Venda editar(Venda obj, String id) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        StringBuilder sql = new StringBuilder("UPDATE vendas SET ");
        List<Object> parametros = new ArrayList<>();

        if (obj.getData() != null) {
            sql.append("data = ?, ");
            parametros.add(new java.sql.Date(obj.getData().getTime()));
        }
        if (obj.getClienteId() > 0) {
            sql.append("clienteId = ?, ");
            parametros.add(obj.getClienteId());
        }
        if (obj.getFormaPagamento() != null) {
            sql.append("formaPagamento = ?, ");
            parametros.add(obj.getFormaPagamento().name());
        }
        if (obj.getTotal() > 0) {
            sql.append("total = ?, ");
            parametros.add(obj.getTotal());
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");
        parametros.add(Integer.parseInt(id));

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
    public Venda deletar(Venda obj, String id) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "DELETE FROM vendas WHERE id = ?";

        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(id));

        stmt.executeUpdate();
        stmt.close();
        c.close();

        return obj;
    }

    public Venda buscarPorId(String id) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "SELECT * FROM vendas WHERE id = ?";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, Integer.parseInt(id));

        ResultSet rs = pst.executeQuery();

        Venda venda = null;

        if (rs.next()) {
            venda = new Venda(
                    rs.getInt("id"),
                    rs.getDate("data"),
                    rs.getInt("clienteId"),
                    FormaPagamento.valueOf(rs.getString("formaPagamento")),
                    rs.getDouble("total")
            );
        }

        return venda;
    }

    public ArrayList<Venda> buscarPorNomeCliente(String nomeCliente) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "SELECT * FROM vendas v JOIN clientes c ON v.clienteId = c.id WHERE c.nome LIKE ?";
        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, "%" + nomeCliente + "%");

        ResultSet rs = pst.executeQuery();

        ArrayList<Venda> vendas = new ArrayList<>();

        while (rs.next()) {
            Venda venda = new Venda(
                    rs.getInt("v.id"),
                    rs.getDate("v.data"),
                    rs.getInt("v.clienteId"),
                    FormaPagamento.valueOf(rs.getString("v.formaPagamento")),
                    rs.getDouble("v.total")
            );
            vendas.add(venda);
        }

        rs.close();
        pst.close();
        c.close();

        return vendas;
    }

    public ArrayList<Venda> buscarPorDataVenda(String dataVenda) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "SELECT * FROM vendas WHERE data = ?";
        PreparedStatement pst = c.prepareStatement(sql);
        pst.setDate(1, java.sql.Date.valueOf(dataVenda));

        ResultSet rs = pst.executeQuery();

        ArrayList<Venda> vendas = new ArrayList<>();

        while (rs.next()) {
            Venda venda = new Venda(
                    rs.getInt("id"),
                    rs.getDate("data"),
                    rs.getInt("clienteId"),
                    FormaPagamento.valueOf(rs.getString("formaPagamento")),
                    rs.getDouble("total")
            );
            vendas.add(venda);
        }

        rs.close();
        pst.close();
        c.close();

        return vendas;
    }

    public int buscarUltimaVendaId() throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "SELECT MAX(id) AS id FROM vendas";
        PreparedStatement pst = c.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();
        int vendaId = 0;
        if (rs.next()) {
            vendaId = rs.getInt("id");
        }

        rs.close();
        pst.close();
        c.close();

        return vendaId;
    }

    public List<ItemVenda> buscarItensPorVendaId(int vendaId) throws SQLException {
        List<ItemVenda> itensVenda = new ArrayList<>();
        String sql = "SELECT iv.id, iv.venda_id, iv.produto_id " +
                "FROM itens_venda iv " +
                "WHERE iv.venda_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vendaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int produtoId = rs.getInt("produto_id");

                ItemVenda itemVenda = new ItemVenda(id, vendaId, produtoId);
                itensVenda.add(itemVenda);
            }
        }
        return itensVenda;
    }

    public Produto buscarProdutoPorId(int produtoId) throws SQLException {
        String sql = "SELECT id, nome, preco_compra, preco_venda " +
                "FROM produtos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double precoCompra = rs.getDouble("preco_compra");
                double precoVenda = rs.getDouble("preco_venda");

                return new Produto(id, nome, (int)precoCompra, precoVenda);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            VendasDAO dao = new VendasDAO();

            // Create a new Venda object
            Venda novaVenda = new Venda(0, new java.util.Date(), 29, FormaPagamento.CartãoCrédito, 150.75);

            // Insert the new Venda object into the database
            dao.inserir(novaVenda);

            System.out.println("Venda inserida com sucesso!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}