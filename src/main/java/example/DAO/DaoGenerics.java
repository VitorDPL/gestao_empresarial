package example.DAO;
import com.mycompany.gestaoempresarial.Usuarios.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DaoGenerics<C, K> {

    public void inserir(C obj) throws ClassNotFoundException, SQLException;

    public ArrayList<C> buscarTodos() throws ClassNotFoundException, SQLException;

    public C editar(C obj, String cpf_cnpj) throws ClassNotFoundException, SQLException;
}
