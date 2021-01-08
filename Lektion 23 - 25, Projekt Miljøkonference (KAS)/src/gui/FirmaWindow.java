package gui;

import application.model.Firma;
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

public class FirmaWindow extends Stage {
    private Firma firma;

    public FirmaWindow(String title, Firma firma) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.firma = firma;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    public FirmaWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfNavn, txfTelefonNr;
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

        Label lblTelefonNr = new Label("Telefonnummer");
        pane.add(lblTelefonNr, 0, 2);

        txfTelefonNr = new TextField();
        pane.add(txfTelefonNr, 0, 3);
        txfTelefonNr.setPrefWidth(200);

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 4);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 4);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        lblFejl = new Label();
        pane.add(lblFejl, 0, 5);
        lblFejl.setStyle("-fx-text-fill: red");

        initControls();
    }

    private void initControls() {
        if (firma != null) {
            txfNavn.setText(firma.getNavn());
            txfTelefonNr.setText("" + firma.getTelefonNr());
        } else {
            txfNavn.clear();
            txfTelefonNr.clear();
        }
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        String navn = txfNavn.getText().trim();
        if (navn.length() == 0) {
            lblFejl.setText("Skriv venligst firmaets navn.");
            return;
        }

        int telefonNr = 0;
        try {
            telefonNr = Integer.parseInt(txfTelefonNr.getText().trim());
        } catch (NumberFormatException ex) {
            // do nothing
        }
        if (telefonNr < 0) {
            lblFejl.setText("Telefonnummeret er ikke gyldigt.");
            return;
        }

        // Call service methods
        if (firma != null) {
            Service.updateFirma(firma, telefonNr);
        } else {
            Service.createFirma(navn, telefonNr);
        }

        hide();
    }

}