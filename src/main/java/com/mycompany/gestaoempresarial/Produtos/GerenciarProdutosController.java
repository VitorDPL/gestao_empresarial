package com.mycompany.gestaoempresarial.Produtos;

import example.DAO.CategoriasDAO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class GerenciarProdutosController implements Initializable {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField codigoField;

    @FXML
    private TextField descricaoField;

    @FXML
    private TextField precoCompraField;

    @FXML
    private TextField precoVendaField;

    @FXML
    private ComboBox<String> categoriaComboBox;

    @FXML
    private TextField quantidadeField;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private RadioButton radioNome;

    @FXML
    private RadioButton radioCod;

    @FXML
    private TextField campoPesquisa;

    @FXML
    private Button botaoPesquisar;

    @FXML
    private Button botaoSalvar;

    @FXML
    private Button botaoDeletar;

    @FXML
    private Button botaoCadastrarProduto;

    @FXML
    private TableColumn<Produto, Integer> colunaId;

    @FXML
    private TableColumn<Produto, String> colunaNome;

    @FXML
    private TableColumn<Produto, String> colunaCodigo;

    @FXML
    private TableColumn<Produto, Double> colunaPrecoCompra;

    @FXML
    private TableColumn<Produto, Double> colunaPrecoVenda;

    @FXML
    private TableColumn<Produto, Double> colunaLucro;

    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuração inicial dos botões de pesquisa
        ToggleGroup toggleGroup = new ToggleGroup();
        radioNome.setToggleGroup(toggleGroup);
        radioCod.setToggleGroup(toggleGroup);
        radioNome.setSelected(true);

        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaPrecoCompra.setCellValueFactory(new PropertyValueFactory<>("preco_compra"));
        colunaPrecoVenda.setCellValueFactory(new PropertyValueFactory<>("preco_venda"));
        colunaLucro.setCellValueFactory(new PropertyValueFactory<>("lucro_produto"));

        tabelaProdutos.setItems(listaProdutos);

        // Ação do botão de pesquisa
        botaoPesquisar.setOnAction(event -> pesquisarProduto());

        // Ação do botão salvar
        botaoSalvar.setOnAction(event -> salvarProduto());

        // Ação do botão deletar
        botaoDeletar.setOnAction(event -> deletarProduto());

        // Ação do botão cadastrar
        botaoCadastrarProduto.setOnAction(event -> {
            try {
                cadastrarProduto();
            } catch (SQLException ex) {
                Logger.getLogger(GerenciarProdutosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        carregarTodosProdutos();
        carregarCategorias();
    }

    private void carregarCategorias() {
        try {
            CategoriasDAO categoriasDAO = new CategoriasDAO();
            ArrayList<Categoria> categorias = categoriasDAO.buscarTodos();
            for (Categoria categoria : categorias) {
                categoriaComboBox.getItems().add(categoria.getNome());
            }
        } catch (SQLException e) {
            mostrarAlerta(AlertType.ERROR, "Erro", "Erro ao carregar categorias: " + e.getMessage());
        }
    }

    private void pesquisarProduto() {
        String valorPesquisa = campoPesquisa.getText().trim();

        if (valorPesquisa.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Atenção", "Digite um nome ou código para pesquisar.");
            return;
        }

        try {
            ObservableList<Produto> produtosEncontrados = FXCollections.observableArrayList();

            ProdutosDAO produtosDAO = new ProdutosDAO();
            if (radioNome.isSelected()) {
                produtosEncontrados.addAll(produtosDAO.buscarPorNome(valorPesquisa));
            } else if (radioCod.isSelected()) {
                produtosEncontrados.addAll(produtosDAO.buscarPorCodigo(valorPesquisa));
            }

            if (!produtosEncontrados.isEmpty()) {
                listaProdutos.setAll(produtosEncontrados);
            } else {
                listaProdutos.clear();
                mostrarAlerta(AlertType.INFORMATION, "Produto não encontrado", "Nenhum produto encontrado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", "Erro ao buscar produto.");
        }
    }

    private void salvarProduto() {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            mostrarAlerta(AlertType.WARNING, "Atenção", "Selecione um produto para editar.");
            return;
        }

        try {
            String nome = nomeField.getText().trim();
            String codigo = codigoField.getText().trim();
            String descricao = descricaoField.getText().trim();
            String precoCompraStr = precoCompraField.getText().trim();
            String precoVendaStr = precoVendaField.getText().trim();
            String quantidadeStr = quantidadeField.getText().trim();

            if (!nome.isEmpty()) {
                produtoSelecionado.setNome(nome);
            }
            if (!codigo.isEmpty()) {
                produtoSelecionado.setCodigo(codigo);
            }
            if (!descricao.isEmpty()) {
                produtoSelecionado.setDescricao(descricao);
            }
            if (!precoCompraStr.isEmpty()) {
                double precoCompra = Double.parseDouble(precoCompraStr);
                produtoSelecionado.setPreco_compra((int) precoCompra);
            }
            if (!precoVendaStr.isEmpty()) {
                double precoVenda = Double.parseDouble(precoVendaStr);
                produtoSelecionado.setPreco_venda(precoVenda);
            }
            if (!quantidadeStr.isEmpty()) {
                int quantidade = Integer.parseInt(quantidadeStr);
                produtoSelecionado.setEstoque_atual(quantidade);
            }

            // Recalculate lucro_produto if preco_compra or preco_venda is updated
            if (!precoCompraStr.isEmpty() || !precoVendaStr.isEmpty()) {
                double precoCompra = produtoSelecionado.getPreco_compra();
                double precoVenda = produtoSelecionado.getPreco_venda();
                double lucroProduto = precoVenda - precoCompra;
                produtoSelecionado.setLucro_produto(lucroProduto);
            }

            ProdutosDAO produtosDAO = new ProdutosDAO();
            produtosDAO.editar(produtoSelecionado, String.valueOf(produtoSelecionado.getId()));
            mostrarAlerta(AlertType.INFORMATION, "Sucesso", "Produto editado com sucesso.");
            carregarTodosProdutos();
        } catch (NumberFormatException e) {
            mostrarAlerta(AlertType.ERROR, "Erro", "Formato de número inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", "Erro ao editar o produto: " + e.getMessage());
        }
    }

    private void deletarProduto() {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            mostrarAlerta(AlertType.WARNING, "Atenção", "Selecione um produto para deletar.");
            return;
        }

        try {
            ProdutosDAO produtosDAO = new ProdutosDAO();
            produtosDAO.deletar(produtoSelecionado, String.valueOf(produtoSelecionado.getId()));
            mostrarAlerta(AlertType.INFORMATION, "Sucesso", "Produto deletado com sucesso.");
            carregarTodosProdutos();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", "Erro ao deletar o produto.");
        }
    }

    private void carregarTodosProdutos() {
        try {
            ProdutosDAO produtosDAO = new ProdutosDAO();
            listaProdutos.setAll(produtosDAO.buscarTodos());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", "Não foi possível carregar os produtos.");
        }
    }

    private void mostrarAlerta(AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    private void cadastrarProduto() throws SQLException {
        try {
            String nome = nomeField.getText();
            String codigo = codigoField.getText();
            String descricao = descricaoField.getText();
            double precoCompra = Double.parseDouble(precoCompraField.getText());
            double precoVenda = Double.parseDouble(precoVendaField.getText());
            int categoriaId = categoriaComboBox.getSelectionModel().getSelectedIndex() + 1;
            int quantidade = Integer.parseInt(quantidadeField.getText());

            double lucroProduto = precoVenda - precoCompra;
            double custo = precoCompra * quantidade;

            Produto produto = new Produto(0, nome, codigo, descricao, categoriaId, (int)precoCompra, precoVenda, lucroProduto, custo, quantidade, new java.util.Date());

            ProdutosDAO produtosDAO = new ProdutosDAO();
            produtosDAO.inserir(produto);
            mostrarAlerta(AlertType.INFORMATION, "Sucesso", "Produto cadastrado com sucesso.");
            carregarTodosProdutos();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", "Erro ao cadastrar produto: " + e.getMessage());
        }
    }
}