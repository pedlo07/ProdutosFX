package br.com.senac.produtosfx.repository;

import br.com.senac.produtosfx.model.Produto;
import br.com.senac.produtosfx.service.ProdutosCSVService;

import java.util.List;

public interface Produtos {
    public void salvarProduto(Produto produto);

    public void atualizarProduto(Produto produto);

    public void apagarProduto(int id);

    public List<Produto> buscarTodasAsContas();

    public Produto buscarUmaConta(int id);

    public static Produtos getNewInstance() {
        return new ProdutosCSVService();
    }
}

