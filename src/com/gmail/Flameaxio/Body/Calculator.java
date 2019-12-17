package com.gmail.Flameaxio.Body;


import com.gmail.Flameaxio.Point;

import java.util.Vector;

public class Calculator {
    private SolutionCascade sc;
    private Vector<Double> root = new Vector<>();

    public Calculator(SolutionCascade sc) {
        this.sc = sc;
    }
    public static double InterpolateLagrangePolynomial (double x, Vector<Point> points)
    {
        double lagrangePol = 0;

        for (int i = 0; i < points.size(); i++)
        {
            double basicsPol = 1;
            for (int j = 0; j < points.size(); j++)
            {
                if (j != i) {
                    basicsPol *= (x - points.get(j).getX()) / (points.get(i).getX() - points.get(j).getX());
                }
            }
            lagrangePol += basicsPol * points.get(i).getY();
        }


        return lagrangePol;
    }

    public void roots(double a, double b, double e, boolean round) {
        Double k = 0.0;
        for (double i = a; i < b; i++) {
            k = Fxord(i, i + 1, e);
            if (k != null) {
                if (round)
                    k = (double) Math.round(k);
                if (k >= a && k <= b && !contains(k))
                    root.add(k);
            }
        }
    }

    public boolean contains(double x)
    {
        for(int i = 0; i < root.size(); i++)
        {
            if(x == root.get(i))
            {
                return true;
            }
        }
        return false;
    }

    public Vector<Double> getRoots()
    {
        return root;
    }

    public double F2(double x,double eps) {
        double x0=x+eps;
        double fx= (iteration(x + eps,1) - iteration(x0 + eps,1))/((x + eps) - (x0 + eps)) - (iteration(x,1) - iteration(x0,1))/(x - x0);
        double gy = (iteration(x + eps,0) - iteration(x0 + eps,0))/((x + eps) - (x0 + eps)) - (iteration(x,0) - iteration(x0,0))/(x - x0);
        return fx - gy;
    }

    public double Dx(double x_i) {
        double dx =  iteration(x_i,1) -iteration(x_i,0) ;
        return dx;
    }

    public Double Fxord(double as, double bs, double e) {

        //func.search();
        double E = e;
        double a=  as;
        double b = bs;
        double x ;
        double x0;
        if (F2(a,E)*Dx(a) < 0) {
            //System.out.println("BA2 "+F2(a,E)*Dx(a));
            x = b;
            x0= a;
            double fa = Dx(a);
            int p = 0;
            while (mod (x0 - x) > E){
                x0 = x;
                //System.out.println("X = " + x );
                double Fx = Dx(x0);
                x = x0-Fx*(x0-a)/(Fx-fa);
                p ++;

                if(p > 100) {
                    return null;
                }
            }
            return  x;


        }
        if (F2(b,E)*Dx(b) < 0) {
            //System.out.println("BF2 "+ F2(b,E)*iteration(b,1));
            x = a;
            x0= b;
            double fa = Dx(b);
            int p = 0;
            while (mod (x0 - x) > E){
                x0 = x;
                //System.out.println("X = " + x );
                double Fx = Dx(x0);
                x = x0-Fx*(x0-b)/(Fx-fa);

                if(p > 100) {
                    return null;
                }
            }
            return  x;
        }

        return  null;
    }


    private double mod(double d) {
        if(d < 0) {
            return d*= -1 ;
        }
        return d;
    }


    public double iteration(double x,int hlp) {
        double F= 0;
        int a = 0;
        double ch = 1;
        double zn = 1;
        if( hlp ==1 ) {
            for(int i = 0 ;i < sc.getInputF().size(); i++) {

                if(i== 0) {
                    for(int j = 0; j < sc.getInputF().size() ;j++) {
                        if( j !=a) {
                            ch *= x - sc.getInputF().get(j).getX() ;
                            zn *= sc.getInputF().get(i).getX() -sc.getInputF().get(j).getX();
                        }

                    }
                    a++;
                    F += (ch/zn) * sc.getInputF().get(i).getY();
                }
                else {
                    ch =1;
                    zn = 1;
                    for(int j = 0; j < sc.getInputF().size() ;j++) {
                        if( j !=a) {
                            ch *= x - sc.getInputF().get(j).getX() ;
                            zn *= sc.getInputF().get(i).getX() -sc.getInputF().get(j).getX();
                        }

                    }
                    a++;
                    F += (ch/zn) * sc.getInputF().get(i).getY();
                }
            }
        }
        else {
            for(int i = 0 ;i < sc.getInputG().size(); i++) {

                if(i== 0) {
                    for(int j = 0; j < sc.getInputG().size() ;j++) {
                        if( j !=a) {
                            ch *= x - sc.getInputG().get(j).getX() ;
                            zn *= sc.getInputG().get(i).getX() -sc.getInputG().get(j).getX();
                        }

                    }
                    a++;
                    F += (ch/zn) *sc.getInputG().get(i).getY();
                }
                else {
                    ch =1;
                    zn = 1;
                    for(int j = 0; j < sc.getInputG().size() ;j++) {
                        if( j !=a) {
                            ch *= x - sc.getInputG().get(j).getX() ;
                            zn *= sc.getInputG().get(i).getX() -sc.getInputG().get(j).getX();
                        }

                    }
                    a++;
                    F += (ch/zn) *sc.getInputG().get(i).getY();
                }
            }


        }


        return F;
    }
}
