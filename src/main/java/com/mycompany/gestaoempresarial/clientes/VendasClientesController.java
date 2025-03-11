package com.mycompany.gestaoempresarial.clientes;

import com.mycompany.gestaoempresarial.Vendas.Venda;
import example.DAO.ClientesDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class VendasClientesController {
    private Cliente cliente;

    @FXML
    private TableView<Venda> tabelaVendas;
    @FXML
    private TableColumn<Venda, String> colunaNomeCliente; // Nova coluna para o nome do cliente
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
        colunaNomeCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cliente.getNome()));
        colunaIdVenda.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaDataVenda.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
        colunaTotalVenda.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}
