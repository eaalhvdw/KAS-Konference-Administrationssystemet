package gui;

import java.util.ArrayList;
import java.util.Optional;
import application.model.Ledsager;
import application.model.Udflugt;
import application.service.Service;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class UdflugtPane extends GridPane {
    private TextField txfNavn, txfDato, txfBy, txfAdresse, txfPris, txfKonference, txfFrokost;
    private TextArea txaLed;
    private ListView<Udflugt> lvwUdflugter;

    public UdflugtPane() {
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        Label lblUd = new Label("Udflugter");
        this.add(lblUd, 0, 0);

        lvwUdflugter = new ListView<>();
        this.add(lvwUdflugter, 0, 1, 3, 7);
        lvwUdflugter.setPrefWidth(200);
        lvwUdflugter.setPrefHeight(200);
        lvwUdflugter.getItems().setAll(Service.getUdflugter());

        ChangeListener<Udflugt> listener = (ov, oldUdflugt, newUdflugt) -> selectedUdflugtChanged();
        lvwUdflugter.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblNavn = new Label("Navn:");
        this.add(lblNavn, 3, 1);

        txfNavn = new TextField();
        this.add(txfNavn, 4, 1);
        txfNavn.setEditable(false);

        Label lblDato = new Label("Dato:");
        this.add(lblDato, 3, 2);

        txfDato = new TextField();
        this.add(txfDato, 4, 2);
        txfDato.setEditable(false);

        Label lblBy = new Label("By:");
        this.add(lblBy, 3, 4);

        txfBy = new TextField();
        this.add(txfBy, 4, 4);
        txfBy.setEditable(false);

        Label lblAdresse = new Label("Adresse:");
        this.add(lblAdresse, 3, 5);

        txfAdresse = new TextField();
        this.add(txfAdresse, 4, 5);
        txfAdresse.setEditable(false);

        Label lblDagsPris = new Label("Pris:");
        this.add(lblDagsPris, 3, 6);

        txfPris = new TextField();
        this.add(txfPris, 4, 6);
        txfPris.setEditable(false);

        Label lblFrokost = new Label("Frokost: ");
        this.add(lblFrokost, 3, 7);

        txfFrokost = new TextField();
        this.add(txfFrokost, 4, 7);

        Label lblKonference = new Label("Konference:");
        this.add(lblKonference, 3, 8);

        txfKonference = new TextField();
        this.add(txfKonference, 4, 8);
        txfKonference.setEditable(false);

        Label lblLed = new Label("Tilmeldte:");
        this.add(lblLed, 3, 9);
        GridPane.setValignment(lblLed, VPos.BASELINE);
        lblLed.setPadding(new Insets(6, 0, 6, 0));

        txaLed = new TextArea();
        this.add(txaLed, 4, 9);
        txaLed.setPrefWidth(200);
        txaLed.setPrefHeight(100);
        txaLed.setEditable(false);

        HBox hbxButtons = new HBox(40);
        this.add(hbxButtons, 0, 8, 3, 1);
        hbxButtons.setPadding(new Insets(10, 0, 0, 0));
        hbxButtons.setAlignment(Pos.BASELINE_CENTER);

        Button btnCreate = new Button("Create");
        hbxButtons.getChildren().add(btnCreate);
        btnCreate.setOnAction(event -> createAction());

        Button btnUpdate = new Button("Update");
        hbxButtons.getChildren().add(btnUpdate);
        btnUpdate.setOnAction(event -> updateAction());

        Button btnDelete = new Button("Delete");
        hbxButtons.getChildren().add(btnDelete);
        btnDelete.setOnAction(event -> deleteAction());

        if (lvwUdflugter.getItems().size() > 0) {
            lvwUdflugter.getSelectionModel().select(0);
        }
    }

    private ArrayList<Udflugt> initAllUdflList() {
        ArrayList<Udflugt> list = new ArrayList<>();
        for (Udflugt ud : Service.getUdflugter()) {
            list.add(ud);
        }
        return list;
    }

    // -------------------------------------------------------------------------

    private void createAction() {
        UdflugtWindow dia = new UdflugtWindow("Opret Udflugt");
        dia.showAndWait();
        // Wait for the modal dialog to close

        lvwUdflugter.getItems().setAll(initAllUdflList());
        updateControls();
    }

    private void updateAction() {
        Udflugt udflugt = lvwUdflugter.getSelectionModel().getSelectedItem();
        if (udflugt == null) {
            return;
        }

        UdflugtWindow dia = new UdflugtWindow("Opdater Udflugt", udflugt);
        dia.showAndWait();
        // Wait for the modal dialog to close

        int selectIndex = lvwUdflugter.getSelectionModel().getSelectedIndex();
        lvwUdflugter.getItems().setAll(Service.getUdflugter());
        lvwUdflugter.getSelectionModel().select(selectIndex);
    }

    private void deleteAction() {
        Udflugt udflugt = lvwUdflugter.getSelectionModel().getSelectedItem();
        if (udflugt == null) {
            return;
        }

        if (udflugt.getLedsagere() == null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Slet Udflugt");
            alert.setHeaderText("Er du sikker?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                Service.deleteUdflugt(udflugt);
                lvwUdflugter.getItems().setAll(Service.getUdflugter());
                updateControls();
            }

        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Slet Udflugt");
            alert.setHeaderText("Det er ikke muligt at slette en udflugt med tilmeldinger.");
            // wait for the modal dialog to close
            alert.show();
        }
    }

    // -------------------------------------------------------------------------

    private void selectedUdflugtChanged() {
        updateControls();
    }

    public void updateControls() {
        Udflugt udflugt = lvwUdflugter.getSelectionModel().getSelectedItem();
        if (udflugt != null) {
            txfNavn.setText(udflugt.getNavn());
            txfDato.setText("" + udflugt.getDato());
            txfBy.setText("" + udflugt.getBy());
            txfAdresse.setText("" + udflugt.getAdresse());
            txfPris.setText("" + udflugt.getPris());
            if (udflugt.getFrokost() == true) {
                txfFrokost.setText("Inkluderet i prisen");
            } else {
                txfFrokost.setText("Ekskluderet i prisen");
            }
            StringBuilder sb = new StringBuilder();
            for (Ledsager l : udflugt.getLedsagere()) {
                sb.append(l + "\n");
            }
            txaLed.setText(sb.toString());
            if (udflugt.getKonference() != null) {
                txfKonference.setText("" + udflugt.getKonference());
            } else {
                txfNavn.clear();
                txfDato.clear();
                txfBy.clear();
                txfAdresse.clear();
                txfPris.clear();
                txfFrokost.clear();
                txfKonference.clear();
                txaLed.clear();
            }
        }
    }
}