package br.com.senac.produtosfx.controller;

import br.com.senac.produtosfx.model.Produto;
import br.com.senac.produtosfx.repository.Produtos;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class ProdutosController implements Initializable {
    @FXML
    private TextField txtProduto;
    @FXML
    private TextField txtTipo;
    @FXML
    private TextField vlrEntrada;
    @FXML
    private TextField vlrSaida;
    @FXML
    private TableView<Produto> tblprodutos;
    @FXML
    private TableColumn<Produto, String> clProd;
    @FXML
    private TableColumn<Produto, String> clTipo;
    @FXML
    private TableColumn<Produto, String> clEntra;
    @FXML
    private TableColumn<Produto, String> clSaida;
    @FXML
    private Button btnApagar;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnLimpar;

    private Produtos produtos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        produtos = Produtos.getNewInstance();
        configuraColunas();
        configuraTela();
        atualizaDados();
    }

    private void configuraTela() {
        BooleanBinding camposPreenchidos = txtProduto.textProperty()
                .isEmpty().or(txtTipo.textProperty().isEmpty())
                .or(vlrEntrada.textProperty().isEmpty()).or(vlrSaida.textProperty().isEmpty());

        BooleanBinding produtoSelecionado = tblprodutos.getSelectionModel()
                .selectedItemProperty().isNull();


        btnApagar.disableProperty().bind(produtoSelecionado);
        btnAtualizar.disableProperty().bind(produtoSelecionado);
        btnLimpar.disableProperty().bind(produtoSelecionado);

        btnSalvar.disableProperty().bind(produtoSelecionado.not()
                .or(camposPreenchidos));
        tblprodutos.getSelectionModel().selectedItemProperty().addListener(
                (b, o, n) -> {
                    if(n != null){
                        txtProduto.setText(n.getProduto());
                        txtTipo.setText(n.getTipo());
                        vlrEntrada.setText(n.getEntrada());
                        vlrSaida.setText(n.getSaida());
                    }
                }
        );
    }
    private void configuraColunas() {
        clProd.setCellValueFactory(new PropertyValueFactory<>("Produto"));
        clTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
        clEntra.setCellValueFactory(new PropertyValueFactory<>("Entrada"));
        clSaida.setCellValueFactory(new PropertyValueFactory<>("Saida"));
    }
    public void salvar() {
        Produto produto = new Produto();
        pegaValores(produto);
        produtos.salvarProduto(produto);
        atualizaDados();
    }

    private void atualizaDados() {
        tblprodutos.getItems().setAll(produtos.buscarTodasAsContas());
        limpar();
    }

    public void limpar() {
        tblprodutos.getSelectionModel().select(null);
        txtProduto.setText("");
        txtTipo.setText("");
        vlrEntrada.setText("");
        vlrSaida.setText("");
    }

    private void pegaValores(Produto produto) {
        produto.setProduto(txtProduto.getText());
        produto.setTipo(txtTipo.getText());
        produto.setEntrada(vlrEntrada.getText());
        produto.setSaida(vlrSaida.getText());
    }
    public void atualizar() {
        Produto p = tblprodutos.getSelectionModel().getSelectedItem();
        pegaValores(p);
        produtos.atualizarProduto(p);
        atualizaDados();
    }
    public void apagar(){
        Produto p = tblprodutos.getSelectionModel().getSelectedItem();
        produtos.apagarProduto(p.getId());
        atualizaDados();
    }
}
