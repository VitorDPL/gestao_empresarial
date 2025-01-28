package example.DAO;
import java.sql.SQLException;

public interface DaoGenerics<C, K> {

    public void inserir(C obj) throws ClassNotFoundException, SQLException;

}
