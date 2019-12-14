package com.gmail.Flameaxio;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

interface XMLReader
{
    Point[] read(File file);
}

class fileReader implements XMLReader
{
     public Point[] read(File file)
    {
        Point[] points;
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList nodeList = doc.getElementsByTagName("point");
            System.out.println("Parsing XML file...");
            System.out.println("Done! Number of points: " + nodeList.getLength() + "\n");
            int size = nodeList.getLength();
            points = new Point[size];
            for(int i = 0; i < size; i++)
                points[i] = new Point();
            for(int i = 0;i < nodeList.getLength();i++)
            {
                Node node = nodeList.item(i);
                if(node.getNodeType() == node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    System.out.println("Point number "+element.getAttribute("id"));
                    points[Integer.parseInt(element.getAttribute("id"))-1].setX(Double.parseDouble(element.getElementsByTagName("x").item(0).getTextContent()));
                    points[Integer.parseInt(element.getAttribute("id"))-1].setY(Double.parseDouble(element.getElementsByTagName("y").item(0).getTextContent()));
                    System.out.println("x: " + Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent()) + "; y: " + Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent()) + ";");
                }
            }
            return points;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
