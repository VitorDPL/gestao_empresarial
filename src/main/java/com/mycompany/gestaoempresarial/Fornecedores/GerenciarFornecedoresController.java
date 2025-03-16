package com.mycompany.gestaoempresarial.Fornecedores;

import com.mycompany.gestaoempresarial.Fornecedores.Fornecedor;
import example.DAO.FornecedoresDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GerenciarFornecedoresController implements Initializable {

    @FXML
    private TextField campoPesquisaNome;

    @FXML
    private Button botaoPesquisar;

    @FXML
    private TableView<Fornecedor> tabelaFornecedores;

    @FXML
    private TableColumn<Fornecedor, Integer> colunaIdFornecedor;

    @FXML
    private TableColumn<Fornecedor, String> colunaNomeFornecedor;

    @FXML
    private TableColumn<Fornecedor, String> colunaCnpjFornecedor;

    @FXML
    private TableColumn<Fornecedor, String> colunaTelefoneFornecedor;

    @FXML
    private TableColumn<Fornecedor, String> colunaEmailFornecedor;

    @FXML
    private TableColumn<Fornecedor, String> colunaEnderecoFornecedor;

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoCnpj;

    @FXML
    private TextField campoTelefone;

    @FXML
    private TextField campoEmail;

    @FXML
    private TextField campoEndereco;

    @FXML
    private Button botaoAdicionarFornecedor;

    @FXML
    private Button botaoEditarFornecedor;

    @FXML
    private Button botaoDeletarFornecedor;

    private ObservableList<Fornecedor> listaFornecedores = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaIdFornecedor.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCnpjFornecedor.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colunaTelefoneFornecedor.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaEmailFornecedor.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaEnderecoFornecedor.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        tabelaFornecedores.setItems(listaFornecedores);

        botaoPesquisar.setOnAction(event -> {
            try {
                pesquisarFornecedor();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao buscar fornecedor.");
            }
        });

        botaoAdicionarFornecedor.setOnAction(event -> adicionarFornecedor());
        botaoEditarFornecedor.setOnAction(event -> editarFornecedor());
        botaoDeletarFornecedor.setOnAction(event -> deletarFornecedor());

        carregarTodosFornecedores();
    }

    private void pesquisarFornecedor() throws SQLException, ClassNotFoundException {
        String nomePesquisa = campoPesquisaNome.getText().trim();

        FornecedoresDAO fornecedoresDAO = new FornecedoresDAO();
        ObservableList<Fornecedor> fornecedoresEncontrados = FXCollections.observableArrayList();

        if (nomePesquisa.isEmpty()) {
            fornecedoresEncontrados.addAll(fornecedoresDAO.buscarTodos());
        } else {
            fornecedoresEncontrados.addAll(fornecedoresDAO.buscarPorNome(nomePesquisa));
        }

        if (!fornecedoresEncontrados.isEmpty()) {
            listaFornecedores.setAll(fornecedoresEncontrados);
        } else {
            listaFornecedores.clear();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Fornecedor não encontrado", "Nenhum fornecedor encontrado.");
        }
    }

    private void adicionarFornecedor() {
        try {
            Fornecedor fornecedor = new Fornecedor(0, campoNome.getText(), campoCnpj.getText(), campoTelefone.getText(), campoEmail.getText(), campoEndereco.getText());
            FornecedoresDAO fornecedoresDAO = new FornecedoresDAO();
            fornecedoresDAO.inserir(fornecedor);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Fornecedor adicionado com sucesso.");
            carregarTodosFornecedores();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao adicionar fornecedor.");
        }
    }

    private void editarFornecedor() {
        Fornecedor fornecedorSelecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        if (fornecedorSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um fornecedor para editar.");
            return;
        }

        try {
            String nome = campoNome.getText().trim();
            String cnpj = campoCnpj.getText().trim();
            String telefone = campoTelefone.getText().trim();
            String email = campoEmail.getText().trim();
            String endereco = campoEndereco.getText().trim();

            if (!nome.isEmpty()) {
                fornecedorSelecionado.setNome(nome);
            }
            if (!cnpj.isEmpty()) {
                fornecedorSelecionado.setCnpj(cnpj);
            }
            if (!telefone.isEmpty()) {
                fornecedorSelecionado.setTelefone(telefone);
            }
            if (!email.isEmpty()) {
                fornecedorSelecionado.setEmail(email);
            }
            if (!endereco.isEmpty()) {
                fornecedorSelecionado.setEndereco(endereco);
            }

            FornecedoresDAO fornecedoresDAO = new FornecedoresDAO();
            fornecedoresDAO.editar(fornecedorSelecionado, fornecedorSelecionado.getCnpj());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Fornecedor editado com sucesso.");
            carregarTodosFornecedores();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao editar o fornecedor: " + e.getMessage());
        }
    }

    private void deletarFornecedor() {
        Fornecedor fornecedorSelecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        if (fornecedorSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um fornecedor para deletar.");
            return;
        }

        try {
            FornecedoresDAO fornecedoresDAO = new FornecedoresDAO();
            fornecedoresDAO.deletar(fornecedorSelecionado, fornecedorSelecionado.getCnpj());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Fornecedor deletado com sucesso.");
            carregarTodosFornecedores();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao deletar fornecedor.");
        }
    }

    private void carregarTodosFornecedores() {
        try {
            FornecedoresDAO fornecedoresDAO = new FornecedoresDAO();
            listaFornecedores.setAll(fornecedoresDAO.buscarTodos());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os fornecedores.");
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