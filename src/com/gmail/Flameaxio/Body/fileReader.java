package com.gmail.Flameaxio.Body;

import com.gmail.Flameaxio.Point;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

interface XMLReader
{
    Vector<Vector<Point>> readXML(File file);
    void saveXML(File file);
}

interface HTMLOutputter
{
    void saveHTML(File file, String encodedImage);
}

class fileReader implements XMLReader, HTMLOutputter
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
                    }
                    else if (element.getAttribute("function").equals("g"))
                    {
                        g.add(new Point(Double.parseDouble(element.getElementsByTagName("x").item(0).getTextContent()),Double.parseDouble(element.getElementsByTagName("y").item(0).getTextContent())));
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


    @Override
    public void saveHTML(File file, String encodedImage)
    {
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            String content = ("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Results</title>\n" +
                    "    <style>\n" +
                    "        td, th\n" +
                    "        {\n" +
                    "              border: 1px solid black;\n" +
                    "              border-collapse: collapse;\n" +
                    "              width: 50%;\n" +
                    "        }\n" +
                    "        table\n" +
                    "        {\n" +
                    "            width: 20vw; text-align: center;\n" +
                    "        }\n" +
                    "        .container\n" +
                    "        {\n" +
                    "            margin: auto;\n" +
                    "            display: flex;\n" +
                    "            flex-wrap: wrap;\n" +
                    "        }\n" +
                    "        .table_wrapper\n" +
                    "        {\n" +
                    "            display: flex;\n" +
                    "            width: 50%;\n" +
                    "            flex-direction: row;\n" +
                    "        }\n" +
                    "        .screenshot\n" +
                    "        {\n" +
                    "            position: relative;\n" +
                    "            display: flex;\n" +
                    "            width: 50%;\n" +
                    "        }\n" +
                    "        .roots\n" +
                    "        {\n" +
                    "            margin-left: 10vh;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "      <div class=\"container\">\n" +
                    "       <div class=\"table_wrapper\">\n" +
                    "        <table>\n" +
                    "          <tr>\n" +
                    "              <th colspan = \"2\">f(x)points</th>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <th>x</th>\n" +
                    "            <th>y</th>\n" +
                    "          </tr>\n");
            for(int i = 0; i < sc.getInputF().size();i++)
            {
                content +=("<tr>\n" +
                        "<td>"+sc.getInputF().get(i).getX()+"</td>\n" +
                        "<td>"+sc.getInputF().get(i).getY()+"</td>\n" +
                        "</tr>");
            }
            content+=(
                    " </table>\n" +
                    "          <table>\n" +
                    "          <tr>\n" +
                    "              <th colspan = \"2\">g(x)points</th>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <th>x</th>\n" +
                    "            <th>y</th>\n" +
                    "          </tr>\n");
            for(int i = 0; i < sc.getInputG().size();i++)
            {
                content+=("<tr>" +
                        "<td>"+sc.getInputG().get(i).getX()+"</td>\n" +
                        "<td>"+sc.getInputG().get(i).getY()+"</td>\n" +
                        "</tr>");
            }
            content+=(
                    "          </table>\n" +
                    "        </div>\n" +
                    "        <div class=\"screenshot\">\n" +
                    "            <img src=\"data:image/png;base64, ");
            content+= encodedImage + "\">\n";
            content+=(" </div>\n" +
                    "        <div class=\"roots\">\n" +
                    "            <table>\n" +
                    "                <tr>\n" +
                    "                <th>Roots</th>\n" +
                    "                </tr>");
            for(int i = 0; i < sc.getRoots().size();i++)
            {
                content+=("\n<tr><td>"+sc.getRoots().get(i)+"\n</td></tr>\n");
            }
            content+=("            </table>\n" +
                    "        </div>\n" +
                    "        </div>\n" +
                    "</body>\n" +
                    "</html>");
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
