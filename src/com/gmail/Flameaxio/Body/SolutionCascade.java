package com.gmail.Flameaxio.Body;

import com.gmail.Flameaxio.Point;
import javafx.scene.image.WritableImage;

import java.io.File;
import java.util.Vector;

public class SolutionCascade {
    private File inputFile;
    private Vector<Point> inputF = new Vector<>();
    private Vector<Point> inputG = new Vector<>();
    private WritableImage graphShot;
    public Calculator calculator;
    private Vector<Double> roots = null;
    private fileReader fr = new fileReader(this);

    public SolutionCascade(String inputFile)  // receives input and output file directories, if file is null then will be prompted manual input
    {
        if (inputFile != null)
            this.inputFile = new File(inputFile);
        calculator = new Calculator(this);
    }

    public void read() {
        Vector<Vector<Point>> points = fr.readXML(inputFile);
        inputF = points.elementAt(0);
        inputG = points.elementAt(1);
    }
    public void removePointF(int index)
    {
        inputF.remove(index);
    }
    public void removePointG(int index)
    {
        inputG.remove(index);
    }

    public Vector<Point> getInputF() {
        return inputF;
    }

    public Vector<Point> getInputG() {
        return inputG;
    }

    public void saveXML(File f)
    {
        fr.saveXML(f);
    }

    public void setGraphShot(WritableImage graphShot) {
        this.graphShot = graphShot;
    }

    public WritableImage getGraphShot() {
        return graphShot;
    }

    public Vector<Double> getRoots() {
        return roots;
    }

    public void setRoots(Vector<Double> roots) {
        this.roots = roots;
    }

    public void saveHTML(File file, String encodedImage)
    {
        fr.saveHTML(file, encodedImage);
    }

}
