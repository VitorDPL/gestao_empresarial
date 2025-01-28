package example.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {

        String user = System.getenv("USER");
        String password = System.getenv("PASSWORD");

        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gestao_empresarial", user, password);
    }

    public static void main(String[] args) {

        Connection c = null;
        try {
            c = ConnectionFactory.getConnection();
            System.out.println("Sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao se conectar no banco! " + e);
        }
    }
}
