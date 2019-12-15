package com.gmail.Flameaxio.GUI;

import com.gmail.Flameaxio.Body.Calculator;
import com.gmail.Flameaxio.Body.SolutionCascade;
import com.gmail.Flameaxio.Point;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.DoubleStringConverter;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable
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
    @FXML private LineChart graph;
    @FXML private TextArea rootsArea;
    public static FileChooser getFileChooser(String title, String format) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        if(format.equals("XML")) {
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("XML-files (*.xml)", "*.xml"));
        }
        else if(format.equals("HTML")) {
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("HTML-files (*.html)", "*.html"));
        }
        else if(format.equals("PNG")){
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PNG-files (*.png)", "*.png"));
        }
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
        FileChooser fileChooser = getFileChooser("Open XML-file", "XML");
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
        FileChooser fileChooser = getFileChooser("Save XML-file", "XML");
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

    public void doCalc(ActionEvent actionEvent)
    {
        graph.getData().clear();
        if(sc.getRoots() != null)
        sc.getRoots().clear();
        try {
            //draw graph
            Double A = Double.parseDouble(a.getText());
            Double B = Double.parseDouble(b.getText());
            XYChart.Series seriesX = new XYChart.Series();
            XYChart.Series seriesY = new XYChart.Series();
            for (Double i =  A; i <= B; i++) {
                double x = Calculator.InterpolateLagrangePolynomial(i,sc.getInputF());
                seriesX.getData().add(new XYChart.Data(i, x));
            }
            for (Double i =  A; i <= B; i++) {
                double x = Calculator.InterpolateLagrangePolynomial(i,sc.getInputG());
                seriesY.getData().add(new XYChart.Data(i, x));
            }
            graph.getData().add(seriesX);
            graph.getData().add(seriesY);

            //calc roots
            sc.calculator.roots(Double.parseDouble(a.getText()),Double.parseDouble(b.getText()),Double.parseDouble(e.getText()));
            Vector<Double> roots = sc.calculator.getRoots();
            sc.setRoots(roots);
            rootsArea.clear();
            System.out.println(sc.getRoots());
            for(int i = 0; i < roots.size(); i++)
            {
                rootsArea.appendText("Root #" + (i+1) + ": " + roots.get(i) + "\n");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void doOutput(ActionEvent actionEvent)
    {
        sc.setGraphShot(graph.snapshot(new SnapshotParameters(), null));
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(sc.getGraphShot(), null);

            ImageIO.write(renderedImage, "PNG", bos);
            byte[] imageBytes = bos.toByteArray();

            imageString = Base64.getEncoder().encodeToString(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileChooser fileChooser = getFileChooser("Save HTML-file", "HTML");
        File file;
        if ((file = fileChooser.showSaveDialog(null)) != null) {
            try {
                updateSourceData();
                sc.saveHTML(file, imageString);
                showMessage("Results saved successfully");
            }
            catch (Exception e) {
                e.printStackTrace();
                showError("Error writing to the file");
            }
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootsArea.setEditable(false);
    }

    public void doInstructions(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to use...");
        alert.setHeaderText("A graphical user-interface program for numerically finding the roots of the chord equation.");
        alert.setContentText("To make the app work you can create a new XML file or import your file(look for an example in the root folder). Once created a new file you can add points to f(x) and g(x) using the tables on the left of the UI and the buttons below the tables. After you added enough points on both of the tables you can proceed and click calculate button." +
                "The roots will be displayed in the area below the calculate button." +
                "\n You also have an option to save results as .html file(this file will include points, roots and screenshot of the graph)");
        alert.showAndWait();
    }
}
