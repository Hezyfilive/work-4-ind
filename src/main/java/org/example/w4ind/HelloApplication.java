package org.example.w4ind;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private final ObservableList<MetroStation> stationList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Lab Data Processor");

        BorderPane root = new BorderPane();

        // Menu
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem saveMenuItem = new MenuItem("Save");
        menuFile.getItems().addAll(newMenuItem, loadMenuItem, saveMenuItem);

        Menu menuHelp = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        menuHelp.getItems().add(aboutMenuItem);

        menuBar.getMenus().addAll(menuFile, menuHelp);
        root.setTop(menuBar);

        // Input fields and buttons
        VBox inputArea = new VBox();
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        TextField passengerField = new TextField();
        passengerField.setPromptText("Passenger Count");
        TextField commentField = new TextField();
        commentField.setPromptText("Comment");
        Button addButton = new Button("Add");

        Button searchButton = new Button("Search");
        TextField searchField = new TextField();
        searchField.setPromptText("Search");

        inputArea.getChildren().addAll(nameField, yearField, passengerField, commentField, addButton, searchField, searchButton);

        TableColumn<MetroStation, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<MetroStation, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());

        TableColumn<MetroStation, List<Hour>> hoursColumn = getMetroStationListTableColumn();

        TableView<MetroStation> mainTableView = new TableView<>();
        mainTableView.getColumns().addAll(nameColumn, yearColumn, hoursColumn);


        mainTableView.setItems(stationList);

        root.setLeft(inputArea);
        root.setCenter(mainTableView);

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            int year = Integer.parseInt(yearField.getText());
            int passengerCount = Integer.parseInt(passengerField.getText());
            String comment = commentField.getText();
            if(stationList.contains(new MetroStation(name, year))){
                int index = stationList.indexOf(new MetroStation(name, year));
                stationList.get(index).addHour(new Hour(passengerCount, comment));
                mainTableView.refresh();
            }else{
                List<Hour> hours = new ArrayList<>();
                Hour hour = new Hour(passengerCount, comment);
                hours.add(hour);

                MetroStation station = new MetroStation(name, year, hours);
                stationList.add(station);
                mainTableView.refresh();
            }
        });

        newMenuItem.setOnAction(e -> stationList.clear());

        loadMenuItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                List<MetroStation> loadedStations = SerializationUtils.deserializeFromXML(file);
                stationList.setAll(loadedStations);
            }
        });

        saveMenuItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                SerializationUtils.serializeToXML(new ArrayList<>(List.of(stationList.toArray())), file);
            }
        });

        aboutMenuItem.setOnAction(e -> {
            Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
            aboutAlert.setTitle("About");
            aboutAlert.setHeaderText("Lab Data Processor");
            aboutAlert.setContentText("Author: \nCourse: Java Programming");
            aboutAlert.showAndWait();
        });

        searchButton.setOnAction(e -> {
            String regex = searchField.getText();
            List<MetroStation> results = DataProcessor.searchStations(new ArrayList<>(stationList), regex);
            stationList.setAll(results);
        });


        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static TableColumn<MetroStation, List<Hour>> getMetroStationListTableColumn() {
        TableColumn<MetroStation, List<Hour>> hoursColumn = new TableColumn<>("Hours");
        hoursColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getHours()));

        hoursColumn.setCellFactory(param -> new TableCell<>() {
            private final TableView<Hour> nestedTableView = new TableView<>();

            {
                TableColumn<Hour, Integer> passCountColumn = new TableColumn<>("Passenger Count");
                passCountColumn.setCellValueFactory(cellData -> cellData.getValue().passengerCountProperty().asObject());

                TableColumn<Hour, String> commentColumn = new TableColumn<>("Comment");
                commentColumn.setCellValueFactory(cellData -> cellData.getValue().commentProperty());

                nestedTableView.getColumns().addAll(passCountColumn, commentColumn);
                nestedTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                nestedTableView.setPrefHeight(150);
            }

            @Override
            protected void updateItem(List<Hour> hours, boolean empty) {
                super.updateItem(hours, empty);

                if (empty || hours == null || hours.isEmpty()) {
                    setGraphic(null);
                } else {
                    nestedTableView.setItems(FXCollections.observableArrayList(hours));
                    setGraphic(nestedTableView);
                }
            }
        });
        return hoursColumn;
    }

    public static void main(String[] args) {
        launch();
    }
}
