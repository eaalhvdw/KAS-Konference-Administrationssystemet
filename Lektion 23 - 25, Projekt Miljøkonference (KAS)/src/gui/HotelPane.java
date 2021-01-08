package gui;

import java.util.ArrayList;
import java.util.Optional;
import application.model.Hotel;
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

public class HotelPane extends GridPane {
    private TextField txfNavn, txfAdresse, txfBy, txfKonference, txfEnkeltPris, txfDobbeltPris;
    private TextArea txaTilk, txaTilm;
    private ListView<Hotel> lvwHoteller;

    public HotelPane() {
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        Label lblHot = new Label("Hoteller");
        this.add(lblHot, 0, 0);

        lvwHoteller = new ListView<>();
        this.add(lvwHoteller, 0, 1, 3, 7);
        lvwHoteller.setPrefWidth(200);
        lvwHoteller.setPrefHeight(200);
        lvwHoteller.getItems().setAll(initAllHotList());

        ChangeListener<Hotel> listener = (ov, oldHotel, newHotel) -> selectedHotelChanged();
        lvwHoteller.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblNavn = new Label("Navn:");
        this.add(lblNavn, 3, 1);

        txfNavn = new TextField();
        this.add(txfNavn, 4, 1);
        txfNavn.setPrefWidth(200);
        txfNavn.setEditable(false);

        Label lblAdresse = new Label("Adresse:");
        this.add(lblAdresse, 3, 2);

        txfAdresse = new TextField();
        this.add(txfAdresse, 4, 2);
        txfAdresse.setPrefWidth(200);
        txfAdresse.setEditable(false);

        Label lblBy = new Label("By:");
        this.add(lblBy, 3, 3);

        txfBy = new TextField();
        this.add(txfBy, 4, 3);
        txfBy.setPrefWidth(200);
        txfBy.setEditable(false);

        Label lblEnkeltPris = new Label("Pris Enkeltværelse:");
        this.add(lblEnkeltPris, 3, 4);

        txfEnkeltPris = new TextField();
        this.add(txfEnkeltPris, 4, 4);
        txfEnkeltPris.setPrefWidth(200);
        txfEnkeltPris.setEditable(false);

        Label lblDobbeltPris = new Label("Pris Dobbeltværelse:");
        this.add(lblDobbeltPris, 3, 5);

        txfDobbeltPris = new TextField();
        this.add(txfDobbeltPris, 4, 5);
        txfDobbeltPris.setPrefWidth(200);
        txfDobbeltPris.setEditable(false);

        Label lblKonference = new Label("Konference:");
        this.add(lblKonference, 3, 6);

        txfKonference = new TextField();
        this.add(txfKonference, 4, 6);
        txfKonference.setPrefWidth(200);
        txfKonference.setEditable(false);

        Label lblTilk = new Label("Services:");
        this.add(lblTilk, 3, 7);
        GridPane.setValignment(lblTilk, VPos.BASELINE);
        lblTilk.setPadding(new Insets(6, 0, 6, 0));

        txaTilk = new TextArea();
        this.add(txaTilk, 4, 7);
        txaTilk.setPrefWidth(200);
        txaTilk.setPrefHeight(100);
        txaTilk.setEditable(false);

        Label lblTilm = new Label("Gæster:");
        this.add(lblTilm, 3, 8);
        GridPane.setValignment(lblTilm, VPos.BASELINE);
        lblTilm.setPadding(new Insets(6, 0, 6, 0));

        txaTilm = new TextArea();
        this.add(txaTilm, 4, 8);
        txaTilm.setPrefWidth(200);
        txaTilm.setPrefHeight(100);
        txaTilm.setEditable(false);

        HBox hbxButtons = new HBox(40);
        this.add(hbxButtons, 0, 9, 3, 1);
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

        if (lvwHoteller.getItems().size() > 0) {
            lvwHoteller.getSelectionModel().select(0);
        }
    }

    private ArrayList<Hotel> initAllHotList() {
        ArrayList<Hotel> list = new ArrayList<>();
        for (Hotel hotel : Service.getHoteller()) {
            list.add(hotel);
        }
        return list;
    }

    // -------------------------------------------------------------------------

    private void createAction() {
        HotelWindow dia = new HotelWindow("Opret Hotel");
        dia.showAndWait();
        // Wait for the modal dialog to close

        lvwHoteller.getItems().setAll(initAllHotList());
        updateControls();
    }

    private void updateAction() {
        Hotel hotel = lvwHoteller.getSelectionModel().getSelectedItem();
        if (hotel == null) {
            return;
        }

        HotelWindow dia = new HotelWindow("Opdater Hotel", hotel);
        dia.showAndWait();
        // Wait for the modal dialog to close

        int selectIndex = lvwHoteller.getSelectionModel().getSelectedIndex();
        lvwHoteller.getItems().setAll(initAllHotList());
        lvwHoteller.getSelectionModel().select(selectIndex);
    }

    private void deleteAction() {
        Hotel hotel = lvwHoteller.getSelectionModel().getSelectedItem();
        if (hotel == null) {
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Slet Hotel");
        alert.setHeaderText("Er du sikker?");
        Optional<ButtonType> result = alert.showAndWait();
        // Wait for the modal dialog to close

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            Service.deleteHotel(hotel);
            lvwHoteller.getItems().setAll(initAllHotList());
            updateControls();
        }

    }

    // -------------------------------------------------------------------------

    private void selectedHotelChanged() {
        updateControls();
    }

    public void updateControls() {
        Hotel hotel = lvwHoteller.getSelectionModel().getSelectedItem();
        if (hotel != null) {
            txfNavn.setText(hotel.getNavn());
            txfAdresse.setText(hotel.getAdresse());
            txfBy.setText(hotel.getBy());
            txfEnkeltPris.setText(hotel.getEnkeltPris() + " kr");
            txfDobbeltPris.setText(hotel.getDobbeltPris() + " kr");
            txfKonference.setText("" + hotel.getKonference());
            StringBuilder s = new StringBuilder();
            for (Tilkøb tk : hotel.getTilkøb()) {
                s.append(tk + "\n");
            }
            StringBuilder b = new StringBuilder();
            for (Tilmelding tl : hotel.getTilmeldinger()) {
                b.append(tl + "\n");
            }
            txaTilk.setText(s.toString());
            txaTilm.setText(b.toString());

            if (hotel.getKonference() != null) {
                txfKonference.setText(hotel.getKonference() + "");
            } else {
                txfKonference.clear();
            }
        } else {
            txfNavn.clear();
            txfAdresse.clear();
            txfBy.clear();
            txfEnkeltPris.clear();
            txfDobbeltPris.clear();
            txfKonference.clear();
            txaTilk.clear();
            txaTilm.clear();
        }
    }

}
