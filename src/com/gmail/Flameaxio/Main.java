package com.gmail.Flameaxio;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SolutionCascade sc = new SolutionCascade("Points.xml");
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the bounds of the calculation('a' and 'b'): ");
        double a = s.nextDouble();
        double b = s.nextDouble();
        System.out.println("Enter the precision of the calculation: ");
        double e = s.nextDouble();
        sc.calculate(a,b,e);
    }
}
