package com.gmail.Flameaxio;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class SolutionCascade {
    //private File outputFile;
    private File inputFile;
    private Point[] input;
    private Calculator calculator;
    private fileReader fr;
    public SolutionCascade(String inputFile)  // receives input and output file directories, if file is null then will be prompted manual input
    {
       // this.outputFile = new File(outputFile);
        fr = new fileReader();
        if(inputFile != null) {
            this.inputFile = new File(inputFile);
            try {
                input = fr.read(this.inputFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter the number of points: ");
            int size = s.nextInt();
            input  = new Point[size];
            for(int i = 0; i < input.length; i++)
            {
                System.out.println("\nEnter x for point #" + (i+1) + ": ");
                double x = s.nextDouble();
                System.out.println("\nEnter y for point #" + (i+1) + ": ");
                double y = s.nextDouble();
                input[i] = new Point(x,y);
                System.out.println("Point #" + (i+1) + ";\n X: " + x + ";\n Y: " + y + "\n\n");
            }
        }
    }
    /*public void saveAsXML()
    {
        if(calculator == null)
            calculator = new Calculator(input);
        calculator.saveAsXML();
    }*/
    public void calculate(double a, double b,double e) // Receives two values as boundaries and 'epsilon' as the level of precision
    {
        if(calculator == null)
            calculator = new Calculator(input);
        calculator.output(a,b,e);
    }

}
