package gui;

import application.service.Service;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        Service.initStorage();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Konference Administrations System");
        BorderPane pane = new BorderPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    // -------------------------------------------------------------------------

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Tab tabKonference = new Tab("Konference");
        tabPane.getTabs().add(tabKonference);

        KonferencePane konferencePane = new KonferencePane();
        tabKonference.setContent(konferencePane);
        tabKonference.setOnSelectionChanged(event -> konferencePane.updateControls());

        Tab tabHotel = new Tab("Hotel");
        tabPane.getTabs().add(tabHotel);

        HotelPane hotelPane = new HotelPane();
        tabHotel.setContent(hotelPane);
        tabHotel.setOnSelectionChanged(event -> hotelPane.updateControls());

        Tab tabUdflugt = new Tab("Udflugt");
        tabPane.getTabs().add(tabUdflugt);

        UdflugtPane udflugtPane = new UdflugtPane();
        tabUdflugt.setContent(udflugtPane);
        tabUdflugt.setOnSelectionChanged(event -> udflugtPane.updateControls());

        Tab tabTilmelding = new Tab("Tilmelding");
        tabPane.getTabs().add(tabTilmelding);

        TilmeldingPane tilmeldingPane = new TilmeldingPane();
        tabTilmelding.setContent(tilmeldingPane);
        tabTilmelding.setOnSelectionChanged(event -> tilmeldingPane.updateControls());

        Tab tabDeltager = new Tab("Deltager");
        tabPane.getTabs().add(tabDeltager);

        DeltagerPane deltagerPane = new DeltagerPane();
        tabDeltager.setContent(deltagerPane);
        tabDeltager.setOnSelectionChanged(event -> deltagerPane.updateControls());

        Tab tabFirma = new Tab("Firma");
        tabPane.getTabs().add(tabFirma);

        FirmaPane firmaPane = new FirmaPane();
        tabFirma.setContent(firmaPane);
        tabFirma.setOnSelectionChanged(event -> firmaPane.updateControls());

        Tab tabLedsager = new Tab("Ledsager");
        tabPane.getTabs().add(tabLedsager);

        LedsagerPane ledsagerPane = new LedsagerPane();
        tabLedsager.setContent(ledsagerPane);
        tabLedsager.setOnSelectionChanged(event -> ledsagerPane.updateControls());
    }

}
