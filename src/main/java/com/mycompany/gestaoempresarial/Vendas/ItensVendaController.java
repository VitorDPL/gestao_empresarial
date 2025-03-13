package com.mycompany.gestaoempresarial.Vendas;

import example.DAO.ItemVendaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ItensVendaController {
    @FXML
    private TableView<ItemVenda> tabelaItensVenda;
    @FXML
    private TableColumn<ItemVenda, Integer> colunaIdVenda;
    @FXML
    private TableColumn<ItemVenda, String> colunaNomeProduto;
    @FXML
    private TableColumn<ItemVenda, BigDecimal> colunaPrecoVendaProduto;

    private ObservableList<ItemVenda> listaItensVenda = FXCollections.observableArrayList();
    private Venda venda;

    @FXML
    public void initialize() {
        colunaIdVenda.setCellValueFactory(new PropertyValueFactory<>("vendaId"));
        colunaNomeProduto.setCellValueFactory(new PropertyValueFactory<>("produtoNome"));
        colunaPrecoVendaProduto.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
        carregarItensVenda();
    }

    private void carregarItensVenda() {
        if (venda != null) {
            ItemVendaDAO dao = new ItemVendaDAO();
            try {
                List<ItemVenda> itensVenda = dao.buscarPorVenda(venda.getId());
                listaItensVenda.clear();
                listaItensVenda.addAll(itensVenda);
                tabelaItensVenda.setItems(listaItensVenda);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}