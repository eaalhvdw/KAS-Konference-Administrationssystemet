package gui;

import application.model.Deltager;
import application.service.Service;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DeltagerWindow extends Stage {
    private Deltager deltager;

    public DeltagerWindow(String title, Deltager deltager) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.deltager = deltager;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    public DeltagerWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfNavn, txfLand, txfBy, txfAdresse, txfTelefonNr, txfLedsager, txfFirma;
    private Label lblFejl;
    private CheckBox checkLedsager;
    private CheckBox checkFirma;

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

        Label lblLand = new Label("Land");
        pane.add(lblLand, 0, 2);

        txfLand = new TextField();
        pane.add(txfLand, 0, 3);
        txfLand.setPrefWidth(200);

        Label lblBy = new Label("By");
        pane.add(lblBy, 0, 4);

        txfBy = new TextField();
        pane.add(txfBy, 0, 5);
        txfBy.setPrefWidth(200);

        Label lblAdresse = new Label("Adresse");
        pane.add(lblAdresse, 0, 6);

        txfAdresse = new TextField();
        pane.add(txfAdresse, 0, 7);
        txfAdresse.setPrefWidth(200);

        Label lblTlf = new Label("Telefonnummer");
        pane.add(lblTlf, 0, 8);

        txfTelefonNr = new TextField();
        pane.add(txfTelefonNr, 0, 9);
        txfTelefonNr.setPrefWidth(200);

        checkLedsager = new CheckBox("Ledsager");
        pane.add(checkLedsager, 0, 10);
        ChangeListener<Boolean> listener = (ov, oldLedsager, newLedsager) -> selectedLedsagerChanged(newLedsager);
        checkLedsager.selectedProperty().addListener(listener);

        txfLedsager = new TextField();
        pane.add(txfLedsager, 0, 11);
        txfLedsager.setPrefWidth(200);
        txfLedsager.setDisable(true);

        checkFirma = new CheckBox("Firma");
        pane.add(checkFirma, 0, 12);
        ChangeListener<Boolean> listener2 = (ov, oldFirma, newFirma) -> selectedFirmaChanged(newFirma);
        checkFirma.selectedProperty().addListener(listener2);

        txfFirma = new TextField();
        pane.add(txfFirma, 0, 13);
        txfFirma.setPrefWidth(200);
        txfFirma.setDisable(true);

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 14);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 14);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        lblFejl = new Label();
        pane.add(lblFejl, 0, 15);
        lblFejl.setStyle("-fx-text-fill: red");

        initControls();
    }

    private void initControls() {
        if (deltager != null) {
            txfNavn.setText(deltager.getNavn());
            txfLand.setText(deltager.getLand());
            txfBy.setText(deltager.getBy());
            txfAdresse.setText(deltager.getAdresse());
            txfTelefonNr.setText("" + deltager.getTelefonNr());
        } else {
            txfNavn.clear();
            txfLand.clear();
            txfBy.clear();
            txfAdresse.clear();
            txfTelefonNr.clear();
        }
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        String navn = txfNavn.getText().trim();
        String land = txfLand.getText().trim();
        String by = txfBy.getText().trim();
        String adresse = txfAdresse.getText().trim();

        if (navn.length() == 0) {
            lblFejl.setText("Skriv venligst dit navn.");
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
        if (deltager != null) {
            Service.updateDeltager(deltager, navn, land, by, adresse, telefonNr);
        } else {
            Service.createDeltager(navn, land, by, adresse, telefonNr);
        }

        hide();
    }

    // --------------------------------------------------------------------------------------------

    private void selectedLedsagerChanged(boolean checked) {
        checkLedsager.setDisable(!checked);
        if (checkLedsager.isSelected()) {
            txfLedsager.setDisable(false);
        }
    }

    private void selectedFirmaChanged(boolean checked) {
        checkFirma.setDisable(!checked);
        if (checkFirma.isSelected()) {
            txfFirma.setDisable(false);
        }
    }
}
