package com.mycompany.gestaoempresarial.Vendas;

import com.mycompany.gestaoempresarial.Produtos.Produto;
import com.mycompany.gestaoempresarial.clientes.Cliente;
import example.DAO.ItemVendaDAO;
import example.DAO.VendasDAO;
import example.DAO.ProdutosDAO;
import example.DAO.ClientesDAO;
import java.net.URL;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GerenciarVendasController implements Initializable {

    @FXML
    private TextField campoPesquisa;

    @FXML
    private RadioButton radioNomeCliente;

    @FXML
    private RadioButton radioDataVenda;

    @FXML
    private RadioButton radioCodigoProduto;

    @FXML
    private RadioButton radioNomeProduto;

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

    @FXML
    private TableColumn<Produto, Double> colunaPrecoVenda;

    @FXML
    private Label labelTotalLiquido;

    @FXML
    private Label labelTotalBruto;

    @FXML
    private TextField campoCpfCliente;

    @FXML
    private Button botaoAdicionarVenda;

    @FXML
    private Button botaoEncontrarClientes;

    @FXML
    private Button botaoEncontrarProdutos;

    private ObservableList<Venda> listaVendas = FXCollections.observableArrayList();
    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuração inicial dos botões de pesquisa
        ToggleGroup toggleGroupVenda = new ToggleGroup();
        radioNomeCliente.setToggleGroup(toggleGroupVenda);
        radioDataVenda.setToggleGroup(toggleGroupVenda);
        radioNomeCliente.setSelected(true);

        ToggleGroup toggleGroupProduto = new ToggleGroup();
        radioCodigoProduto.setToggleGroup(toggleGroupProduto);
        radioNomeProduto.setToggleGroup(toggleGroupProduto);
        radioCodigoProduto.setSelected(true);

        colunaIdVenda.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNomeCliente.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        colunaDataVenda.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaTotalVenda.setCellValueFactory(new PropertyValueFactory<>("total"));

        tabelaVendas.setItems(listaVendas);

        colunaIdProduto.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPrecoVenda.setCellValueFactory(new PropertyValueFactory<>("preco_venda"));

        tabelaProdutos.setItems(listaProdutos);

        // Ação do botão de pesquisa
        botaoFiltrar.setOnAction(event -> pesquisarVenda());

        // Ação do botão adicionar item
        botaoAdicionarItem.setOnAction(event -> adicionarItem());

        // Ação do botão remover item
        botaoRemoverItem.setOnAction(event -> removerItem());

        // Ação do botão adicionar venda
        botaoAdicionarVenda.setOnAction(event -> adicionarVenda());

        // Ação dos botões encontrar clientes e produtos
        botaoEncontrarClientes.setOnAction(event -> abrirGerenciarClientesView());
        botaoEncontrarProdutos.setOnAction(event -> abrirGerenciarProdutosView());

        carregarTodasVendas();
        atualizarTotais();
    }

    private void pesquisarVenda() {
        String valorPesquisa = campoPesquisa.getText().trim();

        if (valorPesquisa.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Digite um nome ou data para pesquisar.");
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
                mostrarAlerta(Alert.AlertType.INFORMATION, "Venda não encontrada", "Nenhuma venda encontrada.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao buscar venda.");
        }
    }

    private void adicionarItem() {
        String valorPesquisa = campoNomeProduto.getText().trim();

        if (valorPesquisa.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Digite o nome ou código do produto para adicionar.");
            return;
        }

        try {
            ProdutosDAO produtosDAO = new ProdutosDAO();
            Produto produto;
            if (radioNomeProduto.isSelected()) {
                produto = produtosDAO.buscarPorNome(valorPesquisa).get(0);
            } else {
                produto = produtosDAO.buscarPorCodigo(valorPesquisa);
            }
            listaProdutos.add(produto);
            atualizarTotais();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao adicionar produto.");
        }
    }

    private void removerItem() {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um produto para remover.");
            return;
        }

        listaProdutos.remove(produtoSelecionado);
        atualizarTotais();
    }

    private void carregarTodasVendas() {
        try {
            VendasDAO vendasDAO = new VendasDAO();
            listaVendas.setAll(vendasDAO.buscarTodos());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar as vendas.");
        }
    }

    private void atualizarTotais() {
        double totalLiquido = listaProdutos.stream().mapToDouble(Produto::getLucro_produto).sum();
        double totalBruto = listaProdutos.stream().mapToDouble(Produto::getPreco_venda).sum();
        labelTotalLiquido.setText(String.format("Total Líquido: %.2f", totalLiquido));
        labelTotalBruto.setText(String.format("Total Bruto: %.2f", totalBruto));
    }
    private void adicionarVenda() {
        String cpfCliente = campoCpfCliente.getText().trim();
        if (cpfCliente.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Digite o CPF do cliente.");
            return;
        }

        try {
            ClientesDAO clientesDAO = new ClientesDAO();
            Cliente cliente = clientesDAO.buscarPorCpfCnpj(cpfCliente);
            if (cliente == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Cliente não encontrado.");
                return;
            }

            int clienteId = cliente.getId();
            VendasDAO vendasDAO = new VendasDAO();
            Venda novaVenda = new Venda(0, new java.util.Date(), clienteId, FormaPagamento.CartãoCrédito, calcularTotalVenda());
            vendasDAO.inserir(novaVenda);

            // Obter o ID da venda recém-inserida
            int vendaId = vendasDAO.buscarUltimaVendaId();

            // Inserir itens da venda
            ItemVendaDAO itemVendaDAO = new ItemVendaDAO();
            for (Produto produto : listaProdutos) {
                ItemVenda itemVenda = new ItemVenda(0, vendaId, produto.getId());
                itemVendaDAO.inserir(itemVenda);
            }

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Venda adicionada com sucesso.");
            listaProdutos.clear();
            atualizarTotais();
            carregarTodasVendas();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao adicionar venda.");
        }
    }

    private double calcularTotalVenda() {
        double total = 0.0;
        for (Produto produto : listaProdutos) {
            total += produto.getPreco_compra();
        }
        return total;
    }

    private void abrirGerenciarClientesView() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/gestaoempresarial/gerenciarClientesView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Gerenciar Clientes");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao abrir a tela de gerenciamento de clientes.");
        }
    }

    private void abrirGerenciarProdutosView() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/gestaoempresarial/gerenciarProdutosView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Gerenciar Produtos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao abrir a tela de gerenciamento de produtos.");
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