package example.DAO;

import com.mycompany.gestaoempresarial.Vendas.FormaPagamento;
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
    public ArrayList<Venda> buscarTodos() throws ClassNotFoundException, SQLException {
        Connection c = ConnectionFactory.getConnection();

        String sql = "SELECT * FROM vendas";

        PreparedStatement pst = c.prepareStatement(sql);

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

        String sql = "SELECT * FROM vendas v JOIN clientes c ON v.cliente_id = c.id WHERE c.nome LIKE ?";
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