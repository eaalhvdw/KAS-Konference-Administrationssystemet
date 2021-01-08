package gui;

import java.util.ArrayList;
import java.util.Optional;
import application.model.Deltager;
import application.model.Tilmelding;
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

public class DeltagerPane extends GridPane {
    private TextField txfNavn, txfLand, txfBy, txfAdresse, txfTelefonNr, txfLedsager, txfFirma;
    private TextArea txaTilm;
    private ListView<Deltager> lvwDeltagere;

    public DeltagerPane() {
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        Label lblDel = new Label("Deltager");
        this.add(lblDel, 0, 0);

        lvwDeltagere = new ListView<>();
        this.add(lvwDeltagere, 0, 1, 3, 7);
        lvwDeltagere.setPrefWidth(200);
        lvwDeltagere.setPrefHeight(200);
        lvwDeltagere.getItems().setAll(Service.getDeltagere());

        ChangeListener<Deltager> listener = (ov, oldDeltager, newDeltager) -> selectedDeltagerChanged();
        lvwDeltagere.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblNavn = new Label("Navn:");
        this.add(lblNavn, 3, 1);

        txfNavn = new TextField();
        this.add(txfNavn, 4, 1);
        txfNavn.setEditable(false);

        Label lblLand = new Label("Land:");
        this.add(lblLand, 3, 2);

        txfLand = new TextField();
        this.add(txfLand, 4, 2);
        txfLand.setEditable(false);

        Label lblBy = new Label("By:");
        this.add(lblBy, 3, 3);

        txfBy = new TextField();
        this.add(txfBy, 4, 3);
        txfBy.setEditable(false);

        Label lblAdresse = new Label("Adresse:");
        this.add(lblAdresse, 3, 4);

        txfAdresse = new TextField();
        this.add(txfAdresse, 4, 4);
        txfAdresse.setEditable(false);

        Label lblTelefonNr = new Label("Telefonnummer:");
        this.add(lblTelefonNr, 3, 5);

        txfTelefonNr = new TextField();
        this.add(txfTelefonNr, 4, 5);
        txfTelefonNr.setEditable(false);

        Label lblLedsager = new Label("Ledsager:");
        this.add(lblLedsager, 3, 6);

        txfLedsager = new TextField();
        this.add(txfLedsager, 4, 6);
        txfLedsager.setEditable(false);

        Label lblFirma = new Label("Firma:");
        this.add(lblFirma, 3, 7);

        txfFirma = new TextField();
        this.add(txfFirma, 4, 7);
        txfFirma.setEditable(false);

        Label lblTilm = new Label("Tilmeldinger:");
        this.add(lblTilm, 3, 8);
        GridPane.setValignment(lblTilm, VPos.BASELINE);
        lblTilm.setPadding(new Insets(6, 0, 6, 0));

        txaTilm = new TextArea();
        this.add(txaTilm, 4, 8);
        txaTilm.setPrefWidth(200);
        txaTilm.setPrefHeight(100);
        txaTilm.setEditable(false);

        HBox hbxButtons = new HBox(40);
        this.add(hbxButtons, 0, 10, 3, 1);
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

        if (lvwDeltagere.getItems().size() > 0) {
            lvwDeltagere.getSelectionModel().select(0);
        }
    }

    private ArrayList<Deltager> initAllDelList() {
        ArrayList<Deltager> list = new ArrayList<>();
        for (Deltager d : Service.getDeltagere()) {
            list.add(d);
        }
        return list;
    }

    // -------------------------------------------------------------------------

    private void createAction() {
        DeltagerWindow dia = new DeltagerWindow("Opret Deltager");
        dia.showAndWait();
        // Wait for the modal dialog to close

        lvwDeltagere.getItems().setAll(initAllDelList());
        updateControls();
    }

    private void updateAction() {
        Deltager deltager = lvwDeltagere.getSelectionModel().getSelectedItem();
        if (deltager == null) {
            return;
        }

        DeltagerWindow dia = new DeltagerWindow("Opdater Deltager", deltager);
        dia.showAndWait();
        // Wait for the modal dialog to close

        int selectIndex = lvwDeltagere.getSelectionModel().getSelectedIndex();
        lvwDeltagere.getItems().setAll(Service.getDeltagere());
        lvwDeltagere.getSelectionModel().select(selectIndex);
    }

    private void deleteAction() {
        Deltager deltager = lvwDeltagere.getSelectionModel().getSelectedItem();
        if (deltager == null) {
            return;
        }

        if (deltager.getTilmeldinger() == null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Slet Deltager");
            alert.setHeaderText("Er du sikker?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                Service.deleteDeltager(deltager);
                lvwDeltagere.getItems().setAll(Service.getDeltagere());
                updateControls();
            }

        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Slet Deltager");
            alert.setHeaderText(
                    "Det er ikke muligt at slette en deltager, som er tilmeldt en eller flere konferencer.");
            // wait for the modal dialog to close
            alert.show();
        }
    }

    // -------------------------------------------------------------------------

    private void selectedDeltagerChanged() {
        updateControls();
    }

    public void updateControls() {
        Deltager deltager = lvwDeltagere.getSelectionModel().getSelectedItem();
        if (deltager != null) {
            txfNavn.setText(deltager.getNavn());
            txfLand.setText("" + deltager.getLand());
            txfBy.setText("" + deltager.getBy());
            txfAdresse.setText("" + deltager.getAdresse());
            txfAdresse.setText("" + deltager.getTelefonNr());
            txfLedsager.setText("" + deltager.getLedsager());
            txfFirma.setText("" + deltager.getFirma());
            StringBuilder sb = new StringBuilder();
            for (Tilmelding t : deltager.getTilmeldinger()) {
                sb.append(t + "\n");
            }
            txaTilm.setText(sb.toString());
        } else {
            txfNavn.clear();
            txfLand.clear();
            txfBy.clear();
            txfAdresse.clear();
            txfTelefonNr.clear();
            txfLedsager.clear();
            txfFirma.clear();
            txaTilm.clear();
        }
    }

}