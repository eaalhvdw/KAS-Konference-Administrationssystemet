package gui;

import java.util.ArrayList;
import java.util.Optional;

import application.model.Hotel;
import application.model.Konference;
import application.model.Tilmelding;
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

public class KonferencePane extends GridPane {
    private TextField txfNavn, txfStartDato, txfSlutDato, txfBy, txfAdresse, txfDagsPris;
    private TextArea txaHot, txaUdfl, txaTilm;
    private ListView<Konference> lvwKonferencer;

    public KonferencePane() {
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        Label lblKon = new Label("Konferencer");
        this.add(lblKon, 0, 0);

        lvwKonferencer = new ListView<>();
        this.add(lvwKonferencer, 0, 1, 3, 7);
        lvwKonferencer.setPrefWidth(200);
        lvwKonferencer.setPrefHeight(200);
        lvwKonferencer.getItems().setAll(Service.getKonferencer());

        ChangeListener<Konference> listener = (ov, oldKonference, newKonference) -> selectedKonferenceChanged();
        lvwKonferencer.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblNavn = new Label("Navn:");
        this.add(lblNavn, 3, 1);

        txfNavn = new TextField();
        this.add(txfNavn, 4, 1);
        txfNavn.setEditable(false);

        Label lblStartDato = new Label("Start dato:");
        this.add(lblStartDato, 3, 2);

        txfStartDato = new TextField();
        this.add(txfStartDato, 4, 2);
        txfStartDato.setEditable(false);

        Label lblSlutDato = new Label("Slut dato:");
        this.add(lblSlutDato, 3, 3);

        txfSlutDato = new TextField();
        this.add(txfSlutDato, 4, 3);
        txfSlutDato.setEditable(false);

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

        Label lblDagsPris = new Label("Pris pr. dag:");
        this.add(lblDagsPris, 3, 6);

        txfDagsPris = new TextField();
        this.add(txfDagsPris, 4, 6);
        txfDagsPris.setEditable(false);

        Label lblHot = new Label("Hoteller:");
        this.add(lblHot, 3, 7);
        GridPane.setValignment(lblHot, VPos.BASELINE);
        lblHot.setPadding(new Insets(6, 0, 6, 0));

        txaHot = new TextArea();
        this.add(txaHot, 4, 7);
        txaHot.setPrefWidth(200);
        txaHot.setPrefHeight(100);
        txaHot.setEditable(false);

        Label lblUdflugt = new Label("Udflugter:");
        this.add(lblUdflugt, 3, 8);
        GridPane.setValignment(lblUdflugt, VPos.BASELINE);
        lblUdflugt.setPadding(new Insets(6, 0, 6, 0));

        txaUdfl = new TextArea();
        this.add(txaUdfl, 4, 8);
        txaUdfl.setPrefWidth(200);
        txaUdfl.setPrefHeight(100);
        txaUdfl.setEditable(false);

        Label lblTilm = new Label("Deltagere:");
        this.add(lblTilm, 3, 9);
        GridPane.setValignment(lblTilm, VPos.BASELINE);
        lblTilm.setPadding(new Insets(6, 0, 6, 0));

        txaTilm = new TextArea();
        this.add(txaTilm, 4, 9);
        txaTilm.setPrefWidth(200);
        txaTilm.setPrefHeight(100);
        txaTilm.setEditable(false);

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

        if (lvwKonferencer.getItems().size() > 0) {
            lvwKonferencer.getSelectionModel().select(0);
        }
    }

    private ArrayList<Konference> initAllKonsList() {
        ArrayList<Konference> list = new ArrayList<>();
        for (Konference kons : Service.getKonferencer()) {
            list.add(kons);
        }
        return list;
    }

    // -------------------------------------------------------------------------

    private void createAction() {
        KonferenceWindow dia = new KonferenceWindow("Opret Konference");
        dia.showAndWait();
        // Wait for the modal dialog to close

        lvwKonferencer.getItems().setAll(initAllKonsList());
        int index = lvwKonferencer.getItems().size() - 1;
        lvwKonferencer.getSelectionModel().select(index);
        updateControls();
    }

    private void updateAction() {
        Konference konference = lvwKonferencer.getSelectionModel().getSelectedItem();
        if (konference == null) {
            return;
        }

        KonferenceWindow dia = new KonferenceWindow("Opdater Konference", konference);
        dia.showAndWait();
        // Wait for the modal dialog to close

        int selectIndex = lvwKonferencer.getSelectionModel().getSelectedIndex();
        lvwKonferencer.getItems().setAll(Service.getKonferencer());
        lvwKonferencer.getSelectionModel().select(selectIndex);
    }

    private void deleteAction() {
        Konference konference = lvwKonferencer.getSelectionModel().getSelectedItem();
        if (konference == null) {
            return;
        }
        if (konference.getTilmeldinger() == null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Slet Konference");
            alert.setHeaderText("Er du sikker?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                Service.deleteKonference(konference);
                lvwKonferencer.getItems().setAll(Service.getKonferencer());
                updateControls();
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Slet Konference");
            alert.setHeaderText("Det er ikke muligt at slette en konference med tilmeldinger.");
            // wait for the modal dialog to close
            alert.show();
        }
    }

    // -------------------------------------------------------------------------

    private void selectedKonferenceChanged() {
        updateControls();
    }

    public void updateControls() {
        Konference konference = lvwKonferencer.getSelectionModel().getSelectedItem();
        if (konference != null) {
            txfNavn.setText(konference.getNavn());
            txfStartDato.setText("" + konference.getStartDato());
            txfSlutDato.setText("" + konference.getSlutDato());
            txfBy.setText("" + konference.getBy());
            txfAdresse.setText("" + konference.getAdresse());
            txfDagsPris.setText("" + konference.getDagsPris());
            StringBuilder sbh = new StringBuilder();
            for (Hotel h : konference.getHoteller()) {
                sbh.append(h + "\n");
            }
            StringBuilder sbu = new StringBuilder();
            for (Udflugt u : konference.getUdflugter()) {
                sbu.append(u + "\n");
            }
            StringBuilder sbt = new StringBuilder();
            for (Tilmelding t : konference.getTilmeldinger()) {
                sbt.append(t + "\n");
            }
            txaHot.setText(sbh.toString());
            txaUdfl.setText(sbu.toString());
            txaTilm.setText(sbt.toString());
        } else {
            txfNavn.clear();
            txfStartDato.clear();
            txfSlutDato.clear();
            txfBy.clear();
            txfAdresse.clear();
            txfDagsPris.clear();
            txaHot.clear();
            txaUdfl.clear();
            txaTilm.clear();
        }
    }

}
