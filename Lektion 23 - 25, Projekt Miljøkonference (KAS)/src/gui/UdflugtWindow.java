package gui;

import java.time.LocalDate;

import application.model.Konference;
import application.model.Udflugt;
import application.service.Service;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UdflugtWindow extends Stage {
    private Udflugt udflugt;

    public UdflugtWindow(String title, Udflugt udflugt) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.udflugt = udflugt;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    public UdflugtWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfNavn, txfDato, txfBy, txfAdresse, txfPris;
    private CheckBox chbFrokost;
    private CheckBox chbKonference;
    private ComboBox<Konference> cbbKonference;
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

        Label lblStartDato = new Label("Dato");
        pane.add(lblStartDato, 0, 2);

        txfDato = new TextField();
        pane.add(txfDato, 0, 3);
        txfDato.setPrefWidth(200);

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

        Label lblPris = new Label("Pris");
        pane.add(lblPris, 0, 8);

        txfPris = new TextField();
        pane.add(txfPris, 0, 9);
        txfPris.setPrefWidth(200);

        chbFrokost = new CheckBox("Frokost");
        pane.add(chbFrokost, 0, 10);
        ChangeListener<Boolean> listener = (ov, oldFrokost, newFrokost) -> selectedFrokostChanged(newFrokost);
        chbFrokost.selectedProperty().addListener(listener);

        chbKonference = new CheckBox("Konference");
        pane.add(chbKonference, 0, 11);
        ChangeListener<Boolean> listener1 = (ov, oldKonference,
                newKonference) -> selectedKonferenceChanged(newKonference);
        chbKonference.selectedProperty().addListener(listener1);

        cbbKonference = new ComboBox<>();
        pane.add(cbbKonference, 0, 12);
        cbbKonference.getItems().addAll(Service.getKonferencer());

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 13);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 13);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        lblFejl = new Label();
        pane.add(lblFejl, 0, 14);
        lblFejl.setStyle("-fx-text-fill: red");

        initControls();
    }

    private void initControls() {
        if (udflugt != null) {
            txfNavn.setText(udflugt.getNavn());
            txfDato.setText("" + udflugt.getDato());
            txfBy.setText(udflugt.getBy());
            txfAdresse.setText(udflugt.getAdresse());
            txfPris.setText("" + udflugt.getPris());
            if (udflugt.getFrokost() == true) {
                chbFrokost.setSelected(true);
            }
            if (udflugt.getKonference() != null) {
                chbKonference.setSelected(true);
                cbbKonference.getSelectionModel().select(udflugt.getKonference());
            } else {
                cbbKonference.getSelectionModel().select(0);
            }
        } else {
            txfNavn.clear();
            txfDato.clear();
            txfBy.clear();
            txfAdresse.clear();
            txfPris.clear();
            cbbKonference.getSelectionModel().select(0);
        }
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        String navn = txfNavn.getText().trim();
        LocalDate dato = LocalDate.parse(txfDato.getText().trim());
        String by = txfBy.getText().trim();
        String adresse = txfAdresse.getText().trim();
        double pris = Double.parseDouble(txfPris.getText().trim());
        boolean KonferenceIsSelected = chbKonference.isSelected();
        Konference newKonference = cbbKonference.getSelectionModel().getSelectedItem();
        boolean frokost = chbFrokost.isSelected();
        if (navn.length() == 0) {
            lblFejl.setText("Navngiv venligst udflugten.");
            return;
        }

        try {
            pris = Double.parseDouble(txfPris.getText().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Skriv venligst et tal.");
        }
        if (pris < 0) {
            lblFejl.setText("Prisen skal være et positivt tal.");
            return;
        }

        // Call service methods

        if (udflugt != null) {
            Service.updateUdflugt(udflugt, navn, dato, adresse, by, pris, frokost, newKonference);
        } else {
            udflugt = Service.createUdflugt(navn, dato, by, adresse, pris, frokost, newKonference);
        }
        if (frokost) {
            udflugt.setFrokost(frokost);
        }
        if (KonferenceIsSelected == true) {
            Service.addUdflugtTilKonference(udflugt, newKonference);
        }

        if (udflugt != null) {
            Service.updateUdflugt(udflugt, navn, dato, by, adresse, pris, frokost, newKonference);
            if (KonferenceIsSelected) {
                Service.addUdflugtTilKonference(udflugt, newKonference);
            } else {
                Service.removeUdflugtFraKonference(udflugt, udflugt.getKonference());
            }
        } else {
            if (KonferenceIsSelected) {
                Service.createUdflugt(navn, dato, by, adresse, pris, frokost, newKonference);
            } else {
                Service.createUdflugt(navn, dato, adresse, by, pris, frokost, newKonference);
            }
        }

        hide();
    }
    // -------------------------------------------------------------------------------------------------

    private void selectedFrokostChanged(boolean checked) {
        chbFrokost.setDisable(!checked);
    }

    private void selectedKonferenceChanged(boolean checked) {
        chbKonference.setDisable(!checked);
    }
}
