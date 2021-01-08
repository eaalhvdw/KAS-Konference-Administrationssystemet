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

public class LedsagerPane extends GridPane {
    private TextField txfNavn, txfDeltager;
    private TextArea txaUdfl;
    private ListView<Ledsager> lvwLedsagere;

    public LedsagerPane() {
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        Label lblLed = new Label("Ledsager");
        this.add(lblLed, 0, 0);

        lvwLedsagere = new ListView<>();
        this.add(lvwLedsagere, 0, 1, 3, 7);
        lvwLedsagere.setPrefWidth(200);
        lvwLedsagere.setPrefHeight(200);
        lvwLedsagere.getItems().setAll(Service.getLedsagere());

        ChangeListener<Ledsager> listener = (ov, oldLedsager, newLedsager) -> selectedLedsagerChanged();
        lvwLedsagere.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblNavn = new Label("Navn:");
        this.add(lblNavn, 3, 1);

        txfNavn = new TextField();
        this.add(txfNavn, 4, 1);
        txfNavn.setEditable(false);

        Label lblDel = new Label("Deltager:");
        this.add(lblDel, 3, 2);

        txfDeltager = new TextField();
        this.add(txfDeltager, 4, 2);
        txfDeltager.setEditable(false);

        Label lblUdflugt = new Label("Udflugter:");
        this.add(lblUdflugt, 3, 3);
        GridPane.setValignment(lblUdflugt, VPos.BASELINE);
        lblUdflugt.setPadding(new Insets(6, 0, 6, 0));

        txaUdfl = new TextArea();
        this.add(txaUdfl, 4, 3);
        txaUdfl.setPrefWidth(200);
        txaUdfl.setPrefHeight(100);
        txaUdfl.setEditable(false);

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

        if (lvwLedsagere.getItems().size() > 0) {
            lvwLedsagere.getSelectionModel().select(0);
        }
    }

    private ArrayList<Ledsager> initAllLedsagereList() {
        ArrayList<Ledsager> list = new ArrayList<>();
        for (Ledsager ledsager : Service.getLedsagere()) {
            list.add(ledsager);
        }
        return list;
    }

    // -------------------------------------------------------------------------

    private void createAction() {
        LedsagerWindow dia = new LedsagerWindow("Opret Ledsager");
        dia.showAndWait();
        // Wait for the modal dialog to close

        lvwLedsagere.getItems().setAll(initAllLedsagereList());
        updateControls();
    }

    private void updateAction() {
        Ledsager ledsager = lvwLedsagere.getSelectionModel().getSelectedItem();
        if (ledsager == null) {
            return;
        }

        LedsagerWindow dia = new LedsagerWindow("Opdater Ledsager", ledsager);
        dia.showAndWait();
        // Wait for the modal dialog to close

        int selectIndex = lvwLedsagere.getSelectionModel().getSelectedIndex();
        lvwLedsagere.getItems().setAll(Service.getLedsagere());
        lvwLedsagere.getSelectionModel().select(selectIndex);
    }

    private void deleteAction() {
        Ledsager ledsager = lvwLedsagere.getSelectionModel().getSelectedItem();
        if (ledsager == null) {
            return;
        }
        if (ledsager.getUdflugter() == null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Slet Ledsager");
            alert.setHeaderText("Er du sikker?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                Service.deleteLedsager(ledsager);
                lvwLedsagere.getItems().setAll(Service.getLedsagere());
                updateControls();
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Slet Ledsager");
            alert.setHeaderText("Det er ikke muligt at slette en ledsager, som er tilmeldt en eller flere udflugter.");
            // wait for the modal dialog to close
            alert.show();
        }
    }

    // -------------------------------------------------------------------------

    private void selectedLedsagerChanged() {
        updateControls();
    }

    public void updateControls() {
        Ledsager ledsager = lvwLedsagere.getSelectionModel().getSelectedItem();
        if (ledsager != null) {
            txfNavn.setText(ledsager.getNavn());
            txfDeltager.setText("" + ledsager.getDeltager());
            StringBuilder sb = new StringBuilder();
            for (Udflugt u : ledsager.getUdflugter()) {
                sb.append(u + "\n");
            }
            txaUdfl.setText(sb.toString());
        } else {
            txfNavn.clear();
            txfDeltager.clear();
            txaUdfl.clear();
        }
    }

}
