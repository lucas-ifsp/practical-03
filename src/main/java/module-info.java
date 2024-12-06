module br.ifsp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens br.ifsp.view;
    opens br.ifsp.controller;

    exports br.ifsp.view;
    exports br.ifsp.controller;
}