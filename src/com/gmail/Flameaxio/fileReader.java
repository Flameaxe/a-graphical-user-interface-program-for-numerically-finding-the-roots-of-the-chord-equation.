package com.gmail.Flameaxio;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

interface XMLReader
{
    Vector<Vector<Point>> readXML(File file);
    void saveXML(File file);
}

class fileReader implements XMLReader
{
    private SolutionCascade sc;
    fileReader(SolutionCascade sc)
    {
        this.sc = sc;
    }
     public Vector<Vector<Point>> readXML(File file)
    {
        Vector<Vector<Point>> points;
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList nodeList = doc.getElementsByTagName("point");
            System.out.println("Parsing XML file...");
            System.out.println("Done! Number of points: " + nodeList.getLength() + "\n");
            points = new Vector<Vector<Point>>();
            Vector<Point> f = new Vector<>();
            Vector<Point> g = new Vector<>();
            for(int i = 0;i < nodeList.getLength();i++)
            {
                Node node = nodeList.item(i);
                if(node.getNodeType() == node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    System.out.println("Point number "+element.getAttribute("id"));
                    System.out.println(element.getAttribute("function"));
                    if(element.getAttribute("function").equals("f")) {
                        f.add(new Point(Double.parseDouble(element.getElementsByTagName("x").item(0).getTextContent()),Double.parseDouble(element.getElementsByTagName("y").item(0).getTextContent())));
                        System.out.println("x: " + Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent()) + "; y: " + Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent()) + ";");
                    }
                    else if (element.getAttribute("function").equals("g"))
                    {
                        g.add(new Point(Double.parseDouble(element.getElementsByTagName("x").item(0).getTextContent()),Double.parseDouble(element.getElementsByTagName("y").item(0).getTextContent())));
                        System.out.println("x: " + Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent()) + "; y: " + Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent()) + ";");
                    }
                }
            }
            points.add(f);
            points.add(g);
            return points;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public void saveXML(File file)
    {
        try
        {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            //Constructing document
            Element root = document.createElement("Points");
            document.appendChild(root);
            int number = 0;
            for(int i = 0; i < sc.getInputF().size();i++)
            {
                Element e = document.createElement("point");
                e.setAttribute("id",""+ ++number);
                e.setAttribute("function","f");
                Element x = document.createElement("x");
                Element y = document.createElement("y");
                x.setTextContent("" + sc.getInputF().get(i).getX());
                y.setTextContent("" + sc.getInputF().get(i).getY());
                e.appendChild(x);
                e.appendChild(y);
                root.appendChild(e);
            }
            for(int i = 0; i < sc.getInputG().size();i++)
            {
                Element e = document.createElement("point");
                e.setAttribute("id",""+ ++number);
                e.setAttribute("function","g");
                Element x = document.createElement("x");
                Element y = document.createElement("y");
                x.setTextContent("" + sc.getInputG().get(i).getX());
                y.setTextContent("" + sc.getInputG().get(i).getY());
                e.appendChild(x);
                e.appendChild(y);
                root.appendChild(e);
            }
            //Saving document
            DOMSource source = new DOMSource(document);
            FileWriter writer = new FileWriter(file);
            StreamResult result = new StreamResult(writer);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
