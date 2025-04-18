package example.DAO;

import com.mycompany.gestaoempresarial.Vendas.ItemVenda;
import example.banco.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemVendaDAO implements DaoGenerics<ItemVenda, Integer> {

    @Override
    public void inserir(ItemVenda itemVenda) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "INSERT INTO itens_venda (venda_id, produto_id) VALUES (?, ?)";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setInt(1, itemVenda.getVendaId());
        stmt.setInt(2, itemVenda.getProdutoId());

        stmt.executeUpdate();
        stmt.close();
        c.close();
    }

    @Override
    public ArrayList<ItemVenda> buscarTodos() throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "SELECT * FROM itens_venda";
        PreparedStatement pst = c.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();
        ArrayList<ItemVenda> itensVenda = new ArrayList<>();

        while (rs.next()) {
            ItemVenda itemVenda = new ItemVenda(
                    rs.getInt("id"),
                    rs.getInt("venda_id"),
                    rs.getInt("produto_id")
            );
            itensVenda.add(itemVenda);
        }

        rs.close();
        pst.close();
        c.close();

        return itensVenda;
    }

    @Override
    public ItemVenda editar(ItemVenda itemVenda, String id) throws ClassNotFoundException, SQLException {
        // Implement edit logic if needed
        return null;
    }

    @Override
    public ItemVenda deletar(ItemVenda itemVenda, String id) throws ClassNotFoundException, SQLException {
        // Implement delete logic if needed
        return null;
    }

    public List<ItemVenda> buscarPorVenda(int vendaId) throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "SELECT iv.venda_id, p.nome, p.preco_venda " +
                "FROM itens_venda iv " +
                "INNER JOIN produtos p ON iv.produto_id = p.id " +
                "WHERE iv.venda_id = ?";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, vendaId);

        ResultSet rs = pst.executeQuery();
        List<ItemVenda> itensVenda = new ArrayList<>();

        while (rs.next()) {
            ItemVenda itemVenda = new ItemVenda(
                    rs.getInt("venda_id"),
                    rs.getString("nome"),
                    rs.getBigDecimal("preco_venda")
            );
            itensVenda.add(itemVenda);
        }

        rs.close();
        pst.close();
        c.close();

        return itensVenda;
    }

}