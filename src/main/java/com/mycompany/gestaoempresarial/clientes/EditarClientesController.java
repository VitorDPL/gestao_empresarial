package com.mycompany.gestaoempresarial;

import com.mycompany.gestaoempresarial.clientes.Cliente;
import com.mycompany.gestaoempresarial.clientes.Segmento;
import example.DAO.ClientesDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditarClientesController implements Initializable {

    @FXML private RadioButton radioNome;
    @FXML private RadioButton radioCPF;
    @FXML private TextField campoPesquisa;
    @FXML private Button botaoPesquisar;
    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colCPF;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colEndereco;
    @FXML private TableColumn<Cliente, String> colSegmento;
    
    @FXML private TextField nomeField;
    @FXML private TextField enderecoField;
    @FXML private TextField telefoneField;
    @FXML private TextField emailField;
    @FXML private Button botaoSalvar;
    @FXML private Button botaoDeletar;
    @FXML private Button botaoCadastrarCliente;

    @FXML
    private TextField addCliNomeCompletoField;
    @FXML
    private TextField addCliCpfCnpjField;
    @FXML
    private TextField addCliEnderecoField;
    @FXML
    private TextField addCliTelefoneField;
    @FXML
    private TextField addCliEmailField;
    
    private ClientesDAO clienteDAO = new ClientesDAO();
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuração inicial dos botões de pesquisa
        ToggleGroup toggleGroup = new ToggleGroup();
        radioNome.setToggleGroup(toggleGroup);
        radioCPF.setToggleGroup(toggleGroup);
        radioNome.setSelected(true);

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpf_cnpj"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));       
        colSegmento.setCellValueFactory(new PropertyValueFactory<>("segmento"));

        tabelaClientes.setItems(listaClientes);
        
        // Ação do botão de pesquisa
        botaoPesquisar.setOnAction(event -> pesquisarCliente());

        // Ação do botão salvar
        botaoSalvar.setOnAction(event -> salvarCliente());

        // Ação do botão deletar
        botaoDeletar.setOnAction(event -> deletarCliente());

        botaoCadastrarCliente.setOnAction(event -> {
            try {
                cadastrarCliente();
            } catch (SQLException ex) {
                Logger.getLogger(EditarClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        carregarTodosClientes();
    }

    // Método para pesquisar o cliente com base no nome ou CPF/CNPJ
    private void pesquisarCliente() {
        String valorPesquisa = campoPesquisa.getText().trim();

        if (valorPesquisa.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Digite um nome ou CPF/CNPJ para pesquisar.");
            return;
        }

        try {
            ObservableList<Cliente> clientesEncontrados = FXCollections.observableArrayList();

            if (radioNome.isSelected()) {
                clientesEncontrados.addAll(clienteDAO.buscarPorNome(valorPesquisa));
            } else if (radioCPF.isSelected()) {
                clientesEncontrados.addAll(clienteDAO.buscarPorCpfCnpj(valorPesquisa));
            }

            if (!clientesEncontrados.isEmpty()) {
                listaClientes.setAll(clientesEncontrados);
            } else {
                listaClientes.clear();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente não encontrado", "Nenhum cliente encontrado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao buscar cliente.");
        }
    }

    // Método para carregar todos os clientes na tabela
    private void carregarTodosClientes() {
        try {
            listaClientes.setAll(clienteDAO.buscarTodos());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os clientes.");
        }
    }

    private void salvarCliente() {
        // Obtém o cliente selecionado na tabela
        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um cliente para editar.");
            return;
        }

            // Pergunta ao usuário se ele tem certeza de que deseja salvar
            Optional<ButtonType> resultado = mostrarConfirmacao("Tem certeza de que deseja salvar as alterações neste cliente?");

            // Se o usuário clicar em "Sim", continua o processo de salvar
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                // Atualiza os dados do cliente com os campos da interface
                clienteSelecionado.setNome(nomeField.getText());
                clienteSelecionado.setEndereco(enderecoField.getText());
                clienteSelecionado.setTelefone(telefoneField.getText());
                clienteSelecionado.setEmail(emailField.getText());

                try {
                    // Chama o método editar passando o cliente e o CPF/CNPJ
                    clienteDAO.editar(clienteSelecionado, clienteSelecionado.getCpf_cnpj());

                    // Exibe mensagem de sucesso
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Cliente atualizado com sucesso.");

                    // Atualiza a tabela de clientes
                    carregarTodosClientes();
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar as alterações.");
                }
            } else {
                // Se o usuário cancelar, não faz nada
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelado", "A edição foi cancelada.");
            }
        }

        private void deletarCliente() {
            // Obtém o cliente selecionado na tabela
            Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();

            if (clienteSelecionado == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um cliente para deletar.");
                return;
            }

            // Pergunta ao usuário se ele tem certeza de que deseja deletar
            Optional<ButtonType> resultado = mostrarConfirmacao("Tem certeza de que deseja deletar este cliente?");

            // Se o usuário clicar em "Sim", continua o processo de deletar
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                try {
                    // Chama o método deletar passando o cliente e o CPF/CNPJ
                    clienteDAO.deletar(clienteSelecionado, clienteSelecionado.getCpf_cnpj());

                    // Exibe mensagem de sucesso
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Cliente deletado com sucesso.");

                    // Atualiza a tabela de clientes
                    carregarTodosClientes();
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao deletar o cliente.");
                }
            } else {
                // Se o usuário cancelar, não faz nada
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelado", "A exclusão foi cancelada.");
            }
        }

        // Método para mostrar a confirmação
        private Optional<ButtonType> mostrarConfirmacao(String mensagem) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText(null);
            alert.setContentText(mensagem);

            // Exibe a caixa de diálogo e retorna a escolha do usuário
            return alert.showAndWait();
        }

   @FXML
   private void cadastrarCliente() throws SQLException {
       String nome = addCliNomeCompletoField.getText();
       String cpfCnpj = addCliCpfCnpjField.getText();
       String endereco = addCliEnderecoField.getText();
       String telefone = addCliTelefoneField.getText();
       String email = addCliEmailField.getText();
       Segmento segmento = Segmento.Regular;

       Cliente cliente = new Cliente(nome, cpfCnpj, endereco, telefone, email, segmento);
       ClientesDAO dao = new ClientesDAO();

       try {
           dao.inserir(cliente);  // Insere o cliente no banco de dados
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Sucesso");
           alert.setHeaderText("Cliente cadastrado com sucesso!");
           alert.showAndWait();

           // Atualiza a tabela após o cadastro
           carregarTodosClientes();

       } catch (ClassNotFoundException | SQLException e) {
           Alert alert = new Alert(AlertType.ERROR);
           alert.setTitle("Erro");
           alert.setHeaderText("Erro ao cadastrar cliente!" + e);
           alert.setContentText(e.getMessage());
           alert.showAndWait();
           System.out.println(e);
       }

       // Limpar campos após cadastro
       addCliNomeCompletoField.clear();
       addCliCpfCnpjField.clear();
       addCliEnderecoField.clear();
       addCliTelefoneField.clear();
       addCliEmailField.clear();
   }


       

    // Método para exibir alertas de erro, sucesso, etc.
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
