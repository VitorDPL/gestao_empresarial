package com.mycompany.gestaoempresarial.Vendas;

import com.mycompany.gestaoempresarial.Produtos.Produto;
import example.DAO.VendasDAO;
import example.DAO.ProdutosDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class GerenciarVendasController implements Initializable {

    @FXML
    private TextField campoPesquisa;

    @FXML
    private RadioButton radioNomeCliente;

    @FXML
    private RadioButton radioDataVenda;

    @FXML
    private Button botaoFiltrar;

    @FXML
    private TableView<Venda> tabelaVendas;

    @FXML
    private TableColumn<Venda, Integer> colunaIdVenda;

    @FXML
    private TableColumn<Venda, String> colunaNomeCliente;

    @FXML
    private TableColumn<Venda, String> colunaDataVenda;

    @FXML
    private TableColumn<Venda, Double> colunaTotalVenda;

    @FXML
    private TextField campoNomeProduto;

    @FXML
    private Button botaoAdicionarItem;

    @FXML
    private Button botaoRemoverItem;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, Integer> colunaIdProduto;

    @FXML
    private TableColumn<Produto, String> colunaNomeProduto;

    private ObservableList<Venda> listaVendas = FXCollections.observableArrayList();
    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuração inicial dos botões de pesquisa
        ToggleGroup toggleGroup = new ToggleGroup();
        radioNomeCliente.setToggleGroup(toggleGroup);
        radioDataVenda.setToggleGroup(toggleGroup);
        radioNomeCliente.setSelected(true);

        colunaIdVenda.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNomeCliente.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        colunaDataVenda.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaTotalVenda.setCellValueFactory(new PropertyValueFactory<>("total"));

        tabelaVendas.setItems(listaVendas);

        colunaIdProduto.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));

        tabelaProdutos.setItems(listaProdutos);

        // Ação do botão de pesquisa
        botaoFiltrar.setOnAction(event -> pesquisarVenda());

        // Ação do botão adicionar item
        botaoAdicionarItem.setOnAction(event -> adicionarItem());

        // Ação do botão remover item
        botaoRemoverItem.setOnAction(event -> removerItem());

        carregarTodasVendas();
    }

    private void pesquisarVenda() {
        String valorPesquisa = campoPesquisa.getText().trim();

        if (valorPesquisa.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Atenção", "Digite um nome ou data para pesquisar.");
            return;
        }

        try {
            ObservableList<Venda> vendasEncontradas = FXCollections.observableArrayList();

            VendasDAO vendasDAO = new VendasDAO();
            if (radioNomeCliente.isSelected()) {
                vendasEncontradas.addAll(vendasDAO.buscarPorNomeCliente(valorPesquisa));
            } else if (radioDataVenda.isSelected()) {
                vendasEncontradas.addAll(vendasDAO.buscarPorDataVenda(valorPesquisa));
            }

            if (!vendasEncontradas.isEmpty()) {
                listaVendas.setAll(vendasEncontradas);
            } else {
                listaVendas.clear();
                mostrarAlerta(AlertType.INFORMATION, "Venda não encontrada", "Nenhuma venda encontrada.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", "Erro ao buscar venda.");
        }
    }

    private void adicionarItem() {
        String nomeProduto = campoNomeProduto.getText().trim();

        if (nomeProduto.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Atenção", "Digite o nome do produto para adicionar.");
            return;
        }

        try {
            ProdutosDAO produtosDAO = new ProdutosDAO();
            Produto produto = produtosDAO.buscarPorNome(nomeProduto).get(0); // Assuming the first match is the desired product
            listaProdutos.add(produto);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", "Erro ao adicionar produto.");
        }
    }

    private void removerItem() {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            mostrarAlerta(AlertType.WARNING, "Atenção", "Selecione um produto para remover.");
            return;
        }

        listaProdutos.remove(produtoSelecionado);
    }

    private void carregarTodasVendas() {
        try {
            VendasDAO vendasDAO = new VendasDAO();
            listaVendas.setAll(vendasDAO.buscarTodos());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", "Não foi possível carregar as vendas.");
        }
    }

    private void mostrarAlerta(AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}