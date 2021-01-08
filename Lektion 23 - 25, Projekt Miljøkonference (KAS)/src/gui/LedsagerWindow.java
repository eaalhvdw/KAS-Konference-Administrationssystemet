package gui;

import application.model.Ledsager;
import application.service.Service;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LedsagerWindow extends Stage {
    private Ledsager ledsager;

    public LedsagerWindow(String title, Ledsager ledsager) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.ledsager = ledsager;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    public LedsagerWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfNavn, txfDeltager;
    private Label lblFejl;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblNavn = new Label("Navn");
        pane.add(lblNavn, 0, 0);

        txfNavn = new TextField();
        pane.add(txfNavn, 0, 1);
        txfNavn.setPrefWidth(200);

        Label lblDeltager = new Label("Deltager");
        pane.add(lblDeltager, 0, 2);

        txfDeltager = new TextField();
        pane.add(txfDeltager, 0, 3);
        txfDeltager.setPrefWidth(200);

        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 10);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 1, 10);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        lblFejl = new Label();
        pane.add(lblFejl, 0, 11);
        lblFejl.setStyle("-fx-text-fill: red");

        initControls();
    }

    private void initControls() {
        if (ledsager != null) {
            txfNavn.setText(ledsager.getNavn());
            txfDeltager.setText("" + ledsager.getDeltager());
        } else {
            txfNavn.clear();
            txfDeltager.clear();
        }
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        String navn = txfNavn.getText().trim();
        String deltager = txfDeltager.getText().trim();

        if (navn.length() == 0) {
            lblFejl.setText("Skriv venligst ledsagers navn.");
            return;
        }
        if (deltager == null) {
            lblFejl.setText("Vælg en deltager");
            return;
        }

        // Call Service methods
        if (ledsager != null) {
            Service.updateLedsager(ledsager, navn, ledsager.getDeltager());
        } else {
            Service.createLedsager(navn, ledsager.getDeltager());
        }
        hide();
    }
}
