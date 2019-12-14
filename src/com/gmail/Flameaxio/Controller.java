package com.gmail.Flameaxio;

public class Controller
{
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
}
