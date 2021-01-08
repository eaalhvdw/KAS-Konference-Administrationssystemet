package gui;

import java.time.LocalDate;
import application.model.Hotel;
import application.model.Tilmelding;
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

public class TilmeldingWindow extends Stage {
    private Tilmelding tilmelding;

    public TilmeldingWindow(String title, Tilmelding tilmelding) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.tilmelding = tilmelding;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    public TilmeldingWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfNavn, txfAnkomstDato, txfAfrejseDato, txfKonference, txfSamletPris;
    private CheckBox chbForedragsholder;
    private CheckBox chbHotel;
    private ComboBox<Hotel> cbbHotel;
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

        Label lblAnkomstDato = new Label("Ankomst Dato");
        pane.add(lblAnkomstDato, 0, 2);

        txfAnkomstDato = new TextField();
        pane.add(txfAnkomstDato, 0, 3);
        txfAnkomstDato.setPrefWidth(200);

        Label lblAfrejseDato = new Label("Afrejse Dato");
        pane.add(lblAfrejseDato, 0, 4);

        txfAfrejseDato = new TextField();
        pane.add(txfAfrejseDato, 0, 5);
        txfAfrejseDato.setPrefWidth(200);

        Label lblKon = new Label("Konference");
        pane.add(lblKon, 0, 6);

        txfKonference = new TextField();
        pane.add(txfKonference, 0, 7);
        txfKonference.setPrefWidth(200);

        Label lblSamletPris = new Label("Total Pris");
        pane.add(lblSamletPris, 0, 8);

        txfSamletPris = new TextField();
        pane.add(txfSamletPris, 0, 9);
        txfSamletPris.setPrefWidth(200);

        chbForedragsholder = new CheckBox("Foredragsholder");
        pane.add(chbForedragsholder, 0, 10);
        ChangeListener<Boolean> listener = (ov, oldForedragsholder,
                newForedragsholder) -> selectedForedragsholderChanged(newForedragsholder);
        chbForedragsholder.selectedProperty().addListener(listener);

        chbHotel = new CheckBox("Hotel");
        pane.add(chbHotel, 0, 11);
        ChangeListener<Boolean> listener2 = (ov, oldHotel, newHotel) -> selectedHotelChanged(newHotel);
        chbHotel.selectedProperty().addListener(listener2);

        cbbHotel = new ComboBox<>();
        pane.add(cbbHotel, 0, 12);
        cbbHotel.getItems().addAll(Service.getHoteller());
        cbbHotel.setDisable(true);

        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 13);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 13);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        lblFejl = new Label();
        pane.add(lblFejl, 0, 14);
        lblFejl.setStyle("-fx-text-fill: red");

        initControls();
    }

    private void initControls() {
        if (tilmelding != null) {
            txfNavn.setText(tilmelding.getDeltager().getNavn());
            txfAnkomstDato.setText("" + tilmelding.getAnkomstDato());
            txfAfrejseDato.setText("" + tilmelding.getAfrejseDato());
            if (tilmelding.getForedragsholder() == true) {
                chbForedragsholder.setSelected(true);
            }
            if (tilmelding.getHotel() != null) {
                chbHotel.setSelected(true);
                cbbHotel.getSelectionModel().select(tilmelding.getHotel());
            } else {
                cbbHotel.getSelectionModel().select(0);
            }
        } else {
            txfNavn.clear();
            txfAnkomstDato.clear();
            txfAfrejseDato.clear();
            cbbHotel.getSelectionModel().select(0);
        }
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        String navn = txfNavn.getText().trim();
        LocalDate ankomstDato = LocalDate.parse(txfAnkomstDato.getText().trim());
        LocalDate afrejseDato = LocalDate.parse(txfAfrejseDato.getText().trim());
        String konference = txfKonference.getText().trim();

        if (navn.length() == 0) {
            lblFejl.setText("Skriv et navn");
            return;
        }

        double samletPris = 0;
        try {
            samletPris = Double.parseDouble(txfSamletPris.getText().trim());
        } catch (NumberFormatException ex) {
            // do nothing
        }
        if (samletPris < 0) {
            lblFejl.setText("Prisen er ikke et positivt tal");
            return;
        }

        boolean ForedragsholderIsSelected = chbForedragsholder.isSelected();
        boolean HotelIsSelected = chbHotel.isSelected();
        Hotel hotel = cbbHotel.getSelectionModel().getSelectedItem();

        // Call service methods
        // if (tilmelding != null) {
        // Service.updateTilmelding(tilmelding, konference, navn, foredragsholder,
        // påHotel, ankomstDato, afrejseDato);
        // } else {
        // Service.createTilmelding(konference, navn, foredragsholder, påHotel,
        // ankomstDato, afrejseDato);
        // }
        //
        // hide();
    }

    // ---------------------------------------------------------------------------------------------

    private void selectedForedragsholderChanged(boolean checked) {
        chbForedragsholder.setDisable(!checked);
    }

    private void selectedHotelChanged(boolean checked) {
        chbHotel.setDisable(!checked);
    }
}
