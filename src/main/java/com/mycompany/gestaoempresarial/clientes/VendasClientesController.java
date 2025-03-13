package com.mycompany.gestaoempresarial.clientes;

import com.mycompany.gestaoempresarial.Vendas.ItensVendaController;
import com.mycompany.gestaoempresarial.Vendas.Venda;
import example.DAO.ClientesDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class VendasClientesController {
    private Cliente cliente;

    @FXML
    private TableView<Venda> tabelaVendas;
    @FXML
    private TableColumn<Venda, String> colunaNomeCliente;
    @FXML
    private TableColumn<Venda, Integer> colunaIdVenda;
    @FXML
    private TableColumn<Venda, String> colunaDataVenda;
    @FXML
    private TableColumn<Venda, String> colunaFormaPagamento;
    @FXML
    private TableColumn<Venda, Double> colunaTotalVenda;

    private ObservableList<Venda> listaVendas = FXCollections.observableArrayList();

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        System.out.println("Cliente recebido ID: " + cliente.getId());
        carregarVendasCliente();
    }

    private void carregarVendasCliente() {
        if (cliente != null && cliente.getId() > 0) {
            ClientesDAO dao = new ClientesDAO();
            List<Venda> vendas = dao.buscarPorVendasCliente(cliente.getId());
            listaVendas.clear();
            listaVendas.addAll(vendas);
            tabelaVendas.setItems(listaVendas);
        } else {
            System.out.println("Cliente ID inválido: " + (cliente != null ? cliente.getId() : "null"));
        }
    }

    @FXML
    public void initialize() {
        colunaNomeCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cliente != null ? cliente.getNome() : "Desconhecido"));
        colunaIdVenda.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaDataVenda.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
        colunaTotalVenda.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Adiciona o evento de clique duplo na tabela
        tabelaVendas.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                abrirItensVendaCliente();
            }
        });
    }

    private void abrirItensVendaCliente() {
        Venda vendaSelecionada = tabelaVendas.getSelectionModel().getSelectedItem();

        if (vendaSelecionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma venda para ver os itens.");
            return;
        }

        if (vendaSelecionada.getId() == 0) {
            System.out.println("Venda selecionada com ID 0. Verifique a origem dos dados.");
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Venda selecionada com ID inválido.");
            return;
        }

        System.out.println("Venda selecionada ID: " + vendaSelecionada.getId());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/gestaoempresarial/itensVendaView.fxml"));
            Parent root = loader.load();

            // Passar a venda selecionada para o controller da nova view
            ItensVendaController controller = loader.getController();
            controller.setVenda(vendaSelecionada);

            System.out.println("Venda passada para ItensVendaController ID: TO AQUI " + vendaSelecionada.getId());

            Stage stage = new Stage();
            stage.setTitle("Itens da Venda");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao abrir a tela de itens da venda.");
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