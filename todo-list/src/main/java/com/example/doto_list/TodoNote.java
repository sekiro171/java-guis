package com.example.doto_list;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;


public class TodoNote extends Application {
    static ListView<HBox> taskList = new ListView<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField nameOfTask = new TextField();
        Button add_btn = new Button("Add");
        Button clearAll = new Button("Clear all");
        clearAll.setOnAction(e -> clearAll());
        Button clearButton = new Button("Clear done tasks");
        clearButton.setOnAction(e -> clearTask());
        Label label1 = new Label("Date of task  ");
        DatePicker dateOfTask = new DatePicker();

        dateOfTask.setValue(LocalDate.now());
        dateOfTask.valueProperty().addListener((observable, oldValue, newValue) -> {
           dateOfTask.setValue(newValue);
        });
        add_btn.setOnAction(e -> addTask(nameOfTask, dateOfTask.getValue().toString()));

        HBox selectDate = new HBox(label1, dateOfTask);
        HBox inputButton = new HBox(10, add_btn, nameOfTask, clearAll);
        VBox layout = new VBox(10, selectDate, inputButton, taskList, clearButton);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Todo List");
        primaryStage.show();

    }



    private void addTask(TextField task, String dateTime) {


        if (task.getText() == null || task.getText().trim().isEmpty())
            return;
        CheckBox check = new CheckBox(task.getText());
        Label date = new Label(dateTime);
        date.setAlignment(Pos.BOTTOM_RIGHT);
        HBox item  = new HBox();
        item.setSpacing(30);
        item.getChildren().addAll(check, date);

        taskList.getItems().add(item);

        task.setText("");
    }

    private void clearTask() {
        taskList.getItems().removeIf(item -> {
            CheckBox clear = (CheckBox) item.getChildren().get(0);
            return clear.isSelected();
        });
    }

    private void clearAll() {
        taskList.getItems().clear();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
