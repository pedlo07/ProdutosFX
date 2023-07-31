module br.com.senac.contasfx{
    requires javafx.controls;
    requires javafx.fxml;

    opens br.com.senac.produtosfx.view to javafx.fxml;
    exports br.com.senac.produtosfx.view;

    opens br.com.senac.produtosfx.controller to javafx.fxml;
    exports br.com.senac.produtosfx.controller;

    opens br.com.senac.produtosfx.model to javafx.fxml;
    exports br.com.senac.produtosfx.model;
}