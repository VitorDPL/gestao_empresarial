package com.mycompany.gestaoempresarial;

import com.mycompany.gestaoempresarial.Usuarios.Cliente;
import example.DAO.ClientesDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;

public class ExibirClientesController implements Initializable {

    @FXML private RadioButton radioNome;
    @FXML private RadioButton radioCPF;
    @FXML private TextField campoPesquisa;
    @FXML private Button botaoPesquisar;
    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colCPF;
    @FXML private TableColumn<Cliente, String> colEndereco;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colSegmento;

    private ClientesDAO clienteDAO = new ClientesDAO();
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup toggleGroup = new ToggleGroup();
        radioNome.setToggleGroup(toggleGroup);
        radioCPF.setToggleGroup(toggleGroup);
        radioNome.setSelected(true);

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpf_cnpj"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSegmento.setCellValueFactory(new PropertyValueFactory<>("segmento"));

        tabelaClientes.setItems(listaClientes);

        botaoPesquisar.setOnAction(event -> pesquisarCliente());

        carregarTodosClientes();
    }

    private void pesquisarCliente() {
        String valorPesquisa = campoPesquisa.getText().trim();

        if (valorPesquisa.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Digite um nome ou CPF/CNPJ para pesquisar.");
            return;
        }

        Cliente cliente = null;

        try {
            if (radioNome.isSelected()) {
                cliente = clienteDAO.buscarPorNome(valorPesquisa);
            } else if (radioCPF.isSelected()) {
                cliente = clienteDAO.buscarPorCpfCnpj(valorPesquisa);
            }

            if (cliente != null) {
                listaClientes.setAll(cliente);
            } else {
                listaClientes.clear();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente não encontrado", "Nenhum cliente encontrado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao buscar cliente.");
        }
    }

    private void carregarTodosClientes() {
        try {
            listaClientes.setAll(clienteDAO.buscarTodos());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os clientes.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
