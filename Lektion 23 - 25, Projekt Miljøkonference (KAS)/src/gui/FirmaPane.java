package gui;

import java.util.ArrayList;
import java.util.Optional;
import application.model.Deltager;
import application.model.Firma;
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

public class FirmaPane extends GridPane {
    private TextField txfNavn, txfTelefonNr;
    private TextArea txaDel;
    private ListView<Firma> lvwFirmaer;

    public FirmaPane() {
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        Label lblFirma = new Label("Firmaer");
        this.add(lblFirma, 0, 0);

        lvwFirmaer = new ListView<>();
        this.add(lvwFirmaer, 0, 1, 3, 7);
        lvwFirmaer.setPrefWidth(200);
        lvwFirmaer.setPrefHeight(200);
        lvwFirmaer.getItems().setAll(Service.getFirmaer());

        ChangeListener<Firma> listener = (ov, oldFirma, newFirma) -> selectedFirmaChanged();
        lvwFirmaer.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblNavn = new Label("Navn:");
        this.add(lblNavn, 3, 1);

        txfNavn = new TextField();
        this.add(txfNavn, 4, 1);
        txfNavn.setEditable(false);

        Label lblTelefonNr = new Label("Telefonnummer:");
        this.add(lblTelefonNr, 3, 2);

        txfTelefonNr = new TextField();
        this.add(txfTelefonNr, 4, 2);
        txfTelefonNr.setEditable(false);

        Label lblDel = new Label("Deltagere:");
        this.add(lblDel, 3, 3);
        GridPane.setValignment(lblDel, VPos.BASELINE);
        lblDel.setPadding(new Insets(6, 0, 6, 0));

        txaDel = new TextArea();
        this.add(txaDel, 4, 3);
        txaDel.setPrefWidth(200);
        txaDel.setPrefHeight(100);
        txaDel.setEditable(false);

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

        if (lvwFirmaer.getItems().size() > 0) {
            lvwFirmaer.getSelectionModel().select(0);
        }
    }

    private ArrayList<Firma> initAllFirmaList() {
        ArrayList<Firma> list = new ArrayList<>();
        for (Firma firma : Service.getFirmaer()) {
            list.add(firma);
        }
        return list;
    }

    // -------------------------------------------------------------------------

    private void createAction() {
        FirmaWindow dia = new FirmaWindow("Opret Firma");
        dia.showAndWait();
        // Wait for the modal dialog to close

        lvwFirmaer.getItems().setAll(initAllFirmaList());
        updateControls();
    }

    private void updateAction() {
        Firma firma = lvwFirmaer.getSelectionModel().getSelectedItem();
        if (firma == null) {
            return;
        }

        FirmaWindow dia = new FirmaWindow("Opdater Firma", firma);
        dia.showAndWait();
        // Wait for the modal dialog to close

        int selectIndex = lvwFirmaer.getSelectionModel().getSelectedIndex();
        lvwFirmaer.getItems().setAll(Service.getFirmaer());
        lvwFirmaer.getSelectionModel().select(selectIndex);
    }

    private void deleteAction() {
        Firma firma = lvwFirmaer.getSelectionModel().getSelectedItem();
        if (firma == null) {
            return;
        }

        if (firma.getDeltagere() == null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Slet Firma");
            alert.setHeaderText("Er du sikker?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                Service.deleteFirma(firma);
                lvwFirmaer.getItems().setAll(Service.getFirmaer());
                updateControls();
            }

        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Slet Firma");
            alert.setHeaderText("Det er ikke muligt at slette firmaet, når der er deltagere tilmeldt igennem firmaet.");
            // wait for the modal dialog to close
            alert.show();
        }
    }

    // -------------------------------------------------------------------------

    private void selectedFirmaChanged() {
        updateControls();
    }

    public void updateControls() {
        Firma firma = lvwFirmaer.getSelectionModel().getSelectedItem();
        if (firma != null) {
            txfNavn.setText(firma.getNavn());
            txfTelefonNr.setText("" + firma.getTelefonNr());
            StringBuilder sb = new StringBuilder();
            for (Deltager d : firma.getDeltagere()) {
                sb.append(d + "\n");
            }
            txaDel.setText(sb.toString());
        } else {
            txfNavn.clear();
            txfTelefonNr.clear();
            txaDel.clear();
        }
    }

}
