package com.gmail.Flameaxio;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.DoubleStringConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller
{
    private SolutionCascade sc;
    private ObservableList<Point> fx;
    private ObservableList<Point> gx;
    @FXML private TableView<Point> tableViewF;
    @FXML private TableView<Point> tableViewG;
    @FXML private TableColumn<Point, Double> fxcol;
    @FXML private TableColumn<Point, Double> fycol;
    @FXML private TableColumn<Point, Double> gxcol;
    @FXML private TableColumn<Point, Double> gycol;
    @FXML private TextField a;
    @FXML private TextField b;
    @FXML private TextField e;
    public static FileChooser getFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML-files (*.xml)", "*.xml"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
        fileChooser.setTitle(title);
        return fileChooser;
    }
    @FXML
    private void doNew(ActionEvent event) {
        sc = new SolutionCascade(null);
        fx = null;
        gx = null;
        a.setText("");
        b.setText("");
        e.setText("");
        tableViewF.setItems(null);
        tableViewG.setItems(null);
        tableViewF.setPlaceholder(new Label(""));
        tableViewG.setPlaceholder(new Label(""));
        updateTable();
    }
    @FXML
    private void doOpen(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Open XML-file");
        File file;
        try{
        if ((file = fileChooser.showOpenDialog(null)) != null) {
            sc = new SolutionCascade(file.getCanonicalPath());
            sc.read();
            a.setText("");
            b.setText("");
            e.setText("");
            tableViewF.setItems(null);
            tableViewG.setItems(null);
            updateTable();
        }
        }catch(Exception e)
            {
                e.printStackTrace();
            }

    }
    @FXML
    private void doSave(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Save XML-file");
        File file;
        if ((file = fileChooser.showSaveDialog(null)) != null) {
            try {
                updateSourceData();
                sc.saveXML(file);
                showMessage("Results saved successfully");
            }
            catch (Exception e) {
                e.printStackTrace();
                showError("Error writing to the file");
            }
        }
    }
    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    @FXML
    private void doClose(ActionEvent event) {
        Platform.exit();
    }
    @FXML
    private void doAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About program...");
        alert.setHeaderText("A graphical user-interface program for numerically finding the roots of the chord equation.");
        alert.setContentText("Version 1.0. 14.12.2019. Created by Flameaxe. Flameaxio@gmail.com");
        alert.showAndWait();
    }
    private void updateXCol(TableColumn.CellEditEvent<Point, Double> t)
    {
        Point p = t.getTableView().getItems().get(t.getTablePosition().getRow());
        p.setX(t.getNewValue());
    }
    private void updateYCol(TableColumn.CellEditEvent<Point, Double> t)
    {
        Point p = t.getTableView().getItems().get(t.getTablePosition().getRow());
        p.setY(t.getNewValue());
    }
    private void updateTable() {
        List<Point> listF = new ArrayList<Point>();
        List<Point> listG = new ArrayList<Point>();
        listF.addAll(sc.getInputF());
        listG.addAll(sc.getInputG());
        fx = FXCollections.observableList(listF);
        gx = FXCollections.observableList(listG);
        tableViewF.setItems(fx);
        tableViewG.setItems(gx);
        tableViewF.setEditable(true);
        tableViewG.setEditable(true);

        fxcol.setCellValueFactory(new PropertyValueFactory<>("x"));
        fxcol.setCellFactory(
                TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        fxcol.setEditable(true);
        fxcol.setSortable(false);
        fxcol.setOnEditCommit(t -> updateXCol(t));
        fycol.setCellValueFactory(new PropertyValueFactory<>("y"));
        fycol.setCellFactory(
                TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        fycol.setEditable(true);
        fycol.setSortable(false);
        fycol.setOnEditCommit(t -> updateYCol(t));
        gxcol.setCellValueFactory(new PropertyValueFactory<>("x"));
        gxcol.setCellFactory(
                TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        gxcol.setEditable(true);
        gxcol.setSortable(false);
        gxcol.setOnEditCommit(t -> updateXCol(t));
        gycol.setCellValueFactory(new PropertyValueFactory<>("y"));
        gycol.setCellFactory(
                TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        gycol.setEditable(true);
        gycol.setSortable(false);
        gycol.setOnEditCommit(t -> updateYCol(t));
    }

    public void doAddF(ActionEvent actionEvent) {
        sc.getInputF().add(new Point(0.0,0.0));
        updateTable();
    }

    public void doAddG(ActionEvent actionEvent) {
        sc.getInputG().add(new Point(0.0,0.0));
        updateTable();
    }

    public void doRemoveF(ActionEvent actionEvent) {
        TablePosition tp = tableViewF.getFocusModel().getFocusedCell();
        sc.removePointF(tp.getRow());
        updateTable();
    }

    public void doRemoveG(ActionEvent actionEvent) {
        TablePosition tp = tableViewG.getFocusModel().getFocusedCell();
        sc.removePointG(tp.getRow());
        updateTable();
    }

    public void doCalc(ActionEvent actionEvent) {
    }

    public void doOutput(ActionEvent actionEvent) {
    }

    private void updateSourceData()
    {
        sc.getInputF().clear();
        sc.getInputG().clear();
        for(Point p : fx)
        {
            sc.getInputF().add(p);
        }
        for(Point p : gx)
        {
            sc.getInputG().add(p);
        }
    }
}
