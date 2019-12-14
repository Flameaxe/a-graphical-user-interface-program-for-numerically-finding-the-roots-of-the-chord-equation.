package com.gmail.Flameaxio;

public class Point
{
    private double x,y;
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Point()
    {

    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }
}
