package gui;

import application.model.Hotel;
import application.model.Konference;
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

public class HotelWindow extends Stage {
    private Hotel hotel;

    public HotelWindow(String title, Hotel hotel) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.hotel = hotel;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    public HotelWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfNavn, txfAdresse, txfBy, txfEnkeltPris, txfDobbeltPris;
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

        Label lblAdresse = new Label("Adresse");
        pane.add(lblAdresse, 0, 2);

        txfAdresse = new TextField();
        pane.add(txfAdresse, 0, 3);
        txfAdresse.setPrefWidth(200);

        Label lblBy = new Label("By");
        pane.add(lblBy, 0, 4);

        txfBy = new TextField();
        pane.add(txfBy, 0, 5);
        txfBy.setPrefWidth(200);

        Label lblEnkeltPris = new Label("Pris Enkeltværelse");
        pane.add(lblEnkeltPris, 0, 6);

        txfEnkeltPris = new TextField();
        pane.add(txfEnkeltPris, 0, 7);
        txfEnkeltPris.setPrefWidth(200);

        Label lblDobbeltPris = new Label("Pris Dobbeltværelse");
        pane.add(lblDobbeltPris, 0, 8);

        txfDobbeltPris = new TextField();
        pane.add(txfDobbeltPris, 0, 9);
        txfDobbeltPris.setPrefWidth(200);

        chbKonference = new CheckBox("Konference");
        pane.add(chbKonference, 0, 10);
        ChangeListener<Boolean> listener = (ov, oldKonference,
                newKonference) -> selectedKonferenceChanged(newKonference);
        chbKonference.selectedProperty().addListener(listener);

        cbbKonference = new ComboBox<>();
        pane.add(cbbKonference, 0, 11);
        cbbKonference.getItems().addAll(Service.getKonferencer());

        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 12);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 1, 12);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        lblFejl = new Label();
        pane.add(lblFejl, 0, 13);
        lblFejl.setStyle("-fx-text-fill: red");

        initControls();
    }

    private void initControls() {
        if (hotel != null) {
            txfNavn.setText(hotel.getNavn());
            txfAdresse.setText(hotel.getAdresse());
            txfBy.setText(hotel.getBy());
            txfEnkeltPris.setText("" + hotel.getEnkeltPris());
            txfDobbeltPris.setText("" + hotel.getDobbeltPris());
            if (hotel.getKonference() != null) {
                chbKonference.setSelected(true);
                cbbKonference.getSelectionModel().select(hotel.getKonference());
            } else {
                cbbKonference.getSelectionModel().select(0);
            }
        } else {
            txfNavn.clear();
            txfAdresse.clear();
            txfBy.clear();
            txfEnkeltPris.clear();
            txfDobbeltPris.clear();
            cbbKonference.getSelectionModel().select(0);
        }
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        String navn = txfNavn.getText().trim();
        String adresse = txfAdresse.getText().trim();
        String by = txfBy.getText().trim();
        double dobbeltPris = Double.parseDouble(txfDobbeltPris.getText().trim());
        double enkeltPris = Double.parseDouble(txfEnkeltPris.getText().trim());
        boolean KonferenceIsSelected = chbKonference.isSelected();
        Konference konference = cbbKonference.getSelectionModel().getSelectedItem();

        if (navn.length() == 0) {
            lblFejl.setText("Skriv venligst hotellets navn.");
            return;
        }

        try {
            enkeltPris = Double.parseDouble(txfEnkeltPris.getText().trim());
        } catch (NumberFormatException ex) {
            // do nothing
        }
        if (enkeltPris < 0) {
            lblFejl.setText("Prisen er ikke et positivt tal.");
            return;
        }

        try {
            dobbeltPris = Double.parseDouble(txfDobbeltPris.getText().trim());
        } catch (NumberFormatException ex) {
            // do nothing
        }
        if (dobbeltPris < 0) {
            lblFejl.setText("Prisen er ikke et positivt tal.");
            return;
        }

        // Call Service methods
        if (hotel != null) {
            Service.updateHotel(hotel, navn, adresse, by, enkeltPris, dobbeltPris, konference);
        } else {
            hotel = Service.createHotel(navn, adresse, by, enkeltPris, dobbeltPris, konference);
        }
        if (KonferenceIsSelected == true) {
            Service.addHotelTilKonference(hotel, konference);
        }
        hide();
    }

    // -------------------------------------------------------------------------

    private void selectedKonferenceChanged(boolean checked) {
        chbKonference.setDisable(!checked);
    }
}
