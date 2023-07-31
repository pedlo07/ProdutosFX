package br.com.senac.produtosfx.service;

import br.com.senac.produtosfx.model.Produto;
import br.com.senac.produtosfx.repository.Produtos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutosCSVService implements Produtos {

    private static final String SEPARADOR = ";";

    private static final Path ARQUIVO_SAIDA = Paths.get("./tabelaprodutos.csv");

    private List<Produto> produtos = new ArrayList<>();

    public ProdutosCSVService() {
        carregaDados();
    }


    @Override
    public void salvarProduto(Produto produto) {
        produto.setId(ultimoId() + 1);
        produtos.add(produto);
        salvaDados();

    }

    @Override
    public void atualizarProduto(Produto produto) {
        Produto produtoAntigo = buscarPorId(produto.getId());
        produtoAntigo.setProduto(produto.getProduto());
        produtoAntigo.setTipo(produto.getTipo());
        produtoAntigo.setEntrada(produto.getEntrada());
        produtoAntigo.setSaida(produto.getSaida());
        salvaDados();

    }


    @Override
    public void apagarProduto(int id) {
        Produto produto = buscarPorId(id);
        produtos.remove(produto);
        salvaDados();

    }

    @Override
    public List<Produto> buscarTodasAsContas() {
        for (Produto produto: produtos) {
            System.out.println(produto.getProduto());
        }
        return produtos;
    }

    @Override
    public Produto buscarUmaConta(int id) {
        return buscarPorId(id);
    }

    private int ultimoId() {
        if (produtos == null) {
            return 0;
        } else {
            return produtos.stream().mapToInt(Produto::getId).max().orElse(0);
        }
    }

    private void salvaDados() {
        StringBuffer sb = new StringBuffer();
        for (Produto c : produtos) {
            String linha = criaLinha(c);
            sb.append(linha);
            sb.append(System.getProperty("line.separator"));
        }
        try {
            Files.delete(ARQUIVO_SAIDA);
            Files.write(ARQUIVO_SAIDA, sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }


    private String criaLinha(Produto produto) {
        String idStr = String.valueOf(produto.getId());

        //FINALIZA CRIAÇÃO DA LINHA
        String linha = String.join(SEPARADOR, idStr, produto.getProduto(), produto.getTipo(), produto.getEntrada(), produto.getSaida());

        return linha;
    }

    private Produto buscarPorId(int id) {
        return produtos.stream().filter(p -> p.getId() == id).findFirst()
                .orElseThrow(() -> new Error("Conta não encontrada"));
    }

    private void carregaDados() {
        try {
            if (!Files.exists(ARQUIVO_SAIDA)) {
                Files.createFile(ARQUIVO_SAIDA);
            }

            produtos = Files.lines(ARQUIVO_SAIDA).map(this::leLinha)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    private Produto leLinha(String linha) {
        String colunas[] = linha.split(SEPARADOR);
        int id = Integer.parseInt(colunas[0]);

        Produto produto = new Produto();
        produto.setId(id);
        produto.setProduto(colunas[1]);
        produto.setTipo(colunas[2]);
        produto.setEntrada(colunas[3]);
        produto.setSaida(colunas[4]);

        return produto;

    }
}
