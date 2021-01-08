package gui;

import java.util.ArrayList;
import java.util.Optional;
import application.model.Tilkøb;
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

public class TilmeldingPane extends GridPane {
    private TextField txfNavn, txfAnkomstDato, txfAfrejseDato, txfKonference, txfHotel, txfSamletPris;
    private TextArea txaTkb;
    private ListView<Tilmelding> lvwTilmeldinger;

    public TilmeldingPane() {
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        Label lblTil = new Label("Tilmeldinger");
        this.add(lblTil, 0, 0);

        lvwTilmeldinger = new ListView<>();
        this.add(lvwTilmeldinger, 0, 1, 3, 7);
        lvwTilmeldinger.setPrefWidth(200);
        lvwTilmeldinger.setPrefHeight(200);
        lvwTilmeldinger.getItems().setAll(Service.getTilmeldinger());

        ChangeListener<Tilmelding> listener = (ov, oldTilmelding, newTilmelding) -> selectedTilmeldingChanged();
        lvwTilmeldinger.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblNavn = new Label("Navn:");
        this.add(lblNavn, 3, 1);

        txfNavn = new TextField();
        this.add(txfNavn, 4, 1);
        txfNavn.setEditable(false);

        Label lblAnkomstDato = new Label("Ankomst dato:");
        this.add(lblAnkomstDato, 3, 2);

        txfAnkomstDato = new TextField();
        this.add(txfAnkomstDato, 4, 2);
        txfAnkomstDato.setEditable(false);

        Label lblAfrejseDato = new Label("Afrejse dato:");
        this.add(lblAfrejseDato, 3, 3);

        txfAfrejseDato = new TextField();
        this.add(txfAfrejseDato, 4, 3);
        txfAfrejseDato.setEditable(false);

        Label lblKonf = new Label("Konference:");
        this.add(lblKonf, 3, 4);

        txfKonference = new TextField();
        this.add(txfKonference, 4, 4);
        txfKonference.setEditable(false);

        Label lblHot = new Label("Hotel:");
        this.add(lblHot, 3, 5);

        txfHotel = new TextField();
        this.add(txfHotel, 4, 5);
        txfHotel.setEditable(false);

        Label lblTkb = new Label("Tilkøb:");
        this.add(lblTkb, 3, 6);
        GridPane.setValignment(lblTkb, VPos.BASELINE);
        lblTkb.setPadding(new Insets(6, 0, 6, 0));

        txaTkb = new TextArea();
        this.add(txaTkb, 4, 6);
        txaTkb.setPrefWidth(200);
        txaTkb.setPrefHeight(100);
        txaTkb.setEditable(false);

        Label lblSamletPris = new Label("Total pris:");
        this.add(lblSamletPris, 3, 7);

        txfSamletPris = new TextField();
        this.add(txfSamletPris, 4, 7);
        txfSamletPris.setEditable(false);

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

        if (lvwTilmeldinger.getItems().size() > 0) {
            lvwTilmeldinger.getSelectionModel().select(0);
        }
    }

    private ArrayList<Tilmelding> initAllTilmList() {
        ArrayList<Tilmelding> list = new ArrayList<>();
        for (Tilmelding t : Service.getTilmeldinger()) {
            list.add(t);
        }
        return list;
    }

    // -------------------------------------------------------------------------

    private void createAction() {
        TilmeldingWindow dia = new TilmeldingWindow("Opret Tilmelding");
        dia.showAndWait();
        // Wait for the modal dialog to close

        lvwTilmeldinger.getItems().setAll(initAllTilmList());
        updateControls();
    }

    private void updateAction() {
        Tilmelding tilmelding = lvwTilmeldinger.getSelectionModel().getSelectedItem();
        if (tilmelding == null) {
            return;
        }

        TilmeldingWindow dia = new TilmeldingWindow("Opdater Tilmelding", tilmelding);
        dia.showAndWait();
        // Wait for the modal dialog to close

        int selectIndex = lvwTilmeldinger.getSelectionModel().getSelectedIndex();
        lvwTilmeldinger.getItems().setAll(Service.getTilmeldinger());
        lvwTilmeldinger.getSelectionModel().select(selectIndex);
    }

    private void deleteAction() {
        Tilmelding tilmelding = lvwTilmeldinger.getSelectionModel().getSelectedItem();
        if (tilmelding == null) {
            return;
        }
        if (tilmelding.getDeltager() == null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Slet Tilmelding");
            alert.setHeaderText("Er du sikker?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                Service.deleteTilmelding(tilmelding);
                lvwTilmeldinger.getItems().setAll(Service.getTilmeldinger());
                updateControls();
                // wait for the modal dialog to close
                alert.show();
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Slet Tilmelding");
            alert.setHeaderText("Det er ikke muligt at slette en tilmelding, som har en deltager tilknyttet.");
            // wait for the modal dialog to close
            alert.show();
        }
    }

    // -------------------------------------------------------------------------

    private void selectedTilmeldingChanged() {
        updateControls();
    }

    public void updateControls() {
        Tilmelding tilmelding = lvwTilmeldinger.getSelectionModel().getSelectedItem();
        if (tilmelding != null) {
            txfNavn.setText(tilmelding.getDeltager().toString());
            txfAnkomstDato.setText("" + tilmelding.getAnkomstDato());
            txfAfrejseDato.setText("" + tilmelding.getAfrejseDato());
            txfKonference.setText("" + tilmelding.getKonference());
            txfHotel.setText("" + tilmelding.getHotel());
            txfSamletPris.setText("" + tilmelding.samletPris());
            StringBuilder sb = new StringBuilder();
            for (Tilkøb t : tilmelding.getTilkøb()) {
                sb.append(t + "\n");
            }
            txaTkb.setText(sb.toString());
        } else {
            txfNavn.clear();
            txfAnkomstDato.clear();
            txfAfrejseDato.clear();
            txfKonference.clear();
            txfHotel.clear();
            txfSamletPris.clear();
            txaTkb.clear();
        }
    }

}
