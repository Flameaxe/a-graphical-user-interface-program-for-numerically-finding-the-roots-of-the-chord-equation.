package com.gmail.Flameaxio;


import java.util.Vector;

public class Calculator {
    private Point[] input;
    public Calculator(Point[] input)
    {
        this.input = input;
    }
    public static double InterpolateLagrangePolynomial (double x, Point[] points)
    {
        double lagrangePol = 0;

        for (int i = 0; i < points.length; i++)
        {
            double basicsPol = 1;
            for (int j = 0; j < points.length; j++)
            {
                if (j != i)
                {
                    basicsPol *= (x - points[j].getX())/(points[i].getX() - points[j].getX());
                }
            }
            lagrangePol += basicsPol * points[i].getY();
        }


        return lagrangePol;
    }

    public Vector<Double> chord_method(double infinum, double supremum, double epsilon)
    {
        Vector<Double> result = new Vector<>();
        double startingSup = supremum;
        double startingInf = infinum;

        int size =(int) (startingSup - ((startingInf > 0) ? startingInf : -startingInf));


        supremum = infinum + 1;
        outer:
        for(; supremum < startingSup; supremum++) {
            int n = 0;
            while (Math.abs(supremum - infinum) > epsilon) {
                infinum = supremum - (supremum - infinum) * Calculator.InterpolateLagrangePolynomial(supremum, input) / (Calculator.InterpolateLagrangePolynomial(supremum, input) - Calculator.InterpolateLagrangePolynomial(infinum, input));
                supremum = infinum - (infinum - supremum) * Calculator.InterpolateLagrangePolynomial(infinum, input) / (Calculator.InterpolateLagrangePolynomial(infinum, input) - Calculator.InterpolateLagrangePolynomial(supremum, input));
                if(n < 1000) {
                    n++;
                }
                else {
                    if(supremum < startingSup)
                        supremum++;
                    continue outer;
                }
            }
            for(int i = 0; i < result.size(); i++)
            {
                if(Math.abs(supremum - result.get(i)) > epsilon)
                    break;
                else
                {
                    result.add(supremum);
                }
            }
        }
        return result;
    }

    public void output(double a, double b,double e)
    {
        //System.out.println("x: " + x + "; " + "y: " + Calculator.InterpolateLagrangePolynomial(x, input) + ";\n");
        Vector<Double> result = chord_method(a,b,e);
        //if(result[1] != null && result[0] != null)
         //   System.out.println("The roots are: " + result[0] + " and " + result[1]);
        //else if(result[0] == null && result[1] != null)
         //   System.out.println("The root is: " + result[1]);
        //else if(result[1] == null && result[0] != null)
         //   System.out.println("The root is: " + result[0]);
        //else
         //   System.out.println("The root is non existent in these bounds");
        System.out.print(result);
    }
}
