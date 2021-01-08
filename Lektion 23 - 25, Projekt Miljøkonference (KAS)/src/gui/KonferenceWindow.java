package gui;

import java.time.LocalDate;

import application.model.Hotel;
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

public class KonferenceWindow extends Stage {
    private Konference konference;

    public KonferenceWindow(String title, Konference konference) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.konference = konference;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    public KonferenceWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfNavn, txfStartDato, txfSlutDato, txfBy, txfAdresse, txfDagsPris;
    private CheckBox chbHotel;
    private ComboBox<Hotel> cbbHotel;
    private CheckBox chbUdflugt;
    private ComboBox<Udflugt> cbbUdflugt;
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

        Label lblStartDato = new Label("StartDato (ÅÅÅÅ-MM-DD)");
        pane.add(lblStartDato, 0, 2);

        txfStartDato = new TextField();
        pane.add(txfStartDato, 0, 3);
        txfStartDato.setPrefWidth(200);

        Label lblSlutDato = new Label("SlutDato (ÅÅÅÅ-MM-DD)");
        pane.add(lblSlutDato, 0, 4);

        txfSlutDato = new TextField();
        pane.add(txfSlutDato, 0, 5);
        txfSlutDato.setPrefWidth(200);

        Label lblBy = new Label("By");
        pane.add(lblBy, 0, 6);

        txfBy = new TextField();
        pane.add(txfBy, 0, 7);
        txfBy.setPrefWidth(200);

        Label lblAdresse = new Label("Adresse");
        pane.add(lblAdresse, 0, 8);

        txfAdresse = new TextField();
        pane.add(txfAdresse, 0, 9);
        txfAdresse.setPrefWidth(200);

        Label lblDagsPris = new Label("Pris pr. dag");
        pane.add(lblDagsPris, 0, 10);

        txfDagsPris = new TextField();
        pane.add(txfDagsPris, 0, 11);
        txfDagsPris.setPrefWidth(200);

        chbHotel = new CheckBox("Hotel");
        pane.add(chbHotel, 0, 12);
        ChangeListener<Boolean> listener = (ov, oldHotel, newHotel) -> selectedHotelChanged(newHotel);
        chbHotel.selectedProperty().addListener(listener);

        cbbHotel = new ComboBox<>();
        pane.add(cbbHotel, 0, 13);
        cbbHotel.getItems().addAll(Service.getHoteller());

        chbUdflugt = new CheckBox("Udflugt");
        pane.add(chbUdflugt, 0, 14);
        ChangeListener<Boolean> listener1 = (ov, oldUdflugt, newUdflugt) -> selectedUdflugtChanged(newUdflugt);
        chbUdflugt.selectedProperty().addListener(listener1);

        cbbUdflugt = new ComboBox<>();
        pane.add(cbbUdflugt, 0, 15);
        cbbUdflugt.getItems().addAll(Service.getUdflugter());

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 16);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 16);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        lblFejl = new Label();
        pane.add(lblFejl, 0, 17);
        lblFejl.setStyle("-fx-text-fill: red");

        cbbHotel.setDisable(true);
        cbbUdflugt.setDisable(true);
        initControls();
    }

    private void initControls() {
        chbHotel.selectedProperty().addListener(event -> {
            cbbHotel.setDisable(!chbHotel.isSelected());
        });
        chbUdflugt.selectedProperty().addListener(event -> {
            cbbUdflugt.setDisable(!chbUdflugt.isSelected());
        });
        if (konference != null) {
            txfNavn.setText(konference.getNavn());
            txfStartDato.setText("" + konference.getStartDato());
            txfSlutDato.setText("" + konference.getSlutDato());
            txfBy.setText("" + konference.getBy());
            txfAdresse.setText("" + konference.getAdresse());
            txfDagsPris.setText("" + konference.getDagsPris());
            if (konference.getHoteller() != null) {
                chbHotel.setSelected(true);
                cbbHotel.getSelectionModel().select(konference.getHotel());
            } else {
                cbbHotel.getSelectionModel().select(0);
            }
            if (konference.getUdflugter() != null) {
                chbUdflugt.setSelected(true);
                cbbUdflugt.getSelectionModel().select(konference.getUdflugt());
            } else {
                cbbUdflugt.getSelectionModel().select(0);
            }
        } else {
            txfNavn.clear();
            txfStartDato.clear();
            txfSlutDato.clear();
            txfBy.clear();
            txfAdresse.clear();
            txfDagsPris.clear();
            cbbHotel.getSelectionModel().select(0);
            cbbUdflugt.getSelectionModel().select(0);
        }
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        String navn = txfNavn.getText().trim();
        LocalDate startDato = LocalDate.parse(txfStartDato.getText().trim());
        LocalDate slutDato = LocalDate.parse(txfSlutDato.getText().trim());
        String by = txfBy.getText().trim();
        String adresse = txfAdresse.getText().trim();

        if (navn.length() == 0) {
            lblFejl.setText("Navngiv venligst konferencen.");
            return;
        }

        double dagsPris = 0;
        try {
            dagsPris = Double.parseDouble(txfDagsPris.getText().trim());
        } catch (NumberFormatException ex) {
            // do nothing
        }
        if (dagsPris < 0) {
            lblFejl.setText("Prisen er ikke et positivt tal.");
            return;
        }

        boolean hotelIsSelected = chbHotel.isSelected();
        Hotel hotel = cbbHotel.getSelectionModel().getSelectedItem();

        boolean udflugtIsSelected = chbUdflugt.isSelected();
        Udflugt udflugt = cbbUdflugt.getSelectionModel().getSelectedItem();

        // Call service methods
        if (konference != null) {
            Service.updateKonference(konference, navn, startDato, slutDato, by, adresse, dagsPris);
        } else {
            konference = Service.createKonference(navn, startDato, slutDato, by, adresse, dagsPris);
        }
        if (hotelIsSelected == true) {
            Service.addHotelTilKonference(hotel, konference);
        }
        if (udflugtIsSelected == true) {
            Service.addUdflugtTilKonference(udflugt, konference);
        }
        hide();
    }

    // ------------------------------------------------------------------------------------------------

    private void selectedHotelChanged(boolean checked) {
        chbHotel.setDisable(!checked);
    }

    private void selectedUdflugtChanged(boolean checked) {
        chbUdflugt.setDisable(!checked);
    }
}
