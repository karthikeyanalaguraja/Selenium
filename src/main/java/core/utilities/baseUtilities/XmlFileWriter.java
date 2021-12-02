package core.utilities.baseUtilities;

import java.io.*;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlFileWriter {

    public static void createAllureEnvXml() throws IOException {

        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        String response = "";

//        InputStream in = new URL( System.getProperty("environment")+"/piles/status.pile").openStream();
//
//        try {
//            String[] str = IOUtils.toString( in ).split("\n");
//            for(String s:str){
//                if(s.contains("APPVERSION")){
//                    response = s;
//                }
//            }
//        } finally {
//            IOUtils.closeQuietly(in);
//        }
//
//        System.out.println("ENV BUILT BRANCH: "+response);
//
//        String builtBranch = response.substring(response.lastIndexOf("=")+1);

        try {
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement = doc.createElement("environment");
            doc.appendChild(mainRootElement);

            // append child elements to root element
            mainRootElement.appendChild(getParameter(doc, "Environment:", System.getProperty("environment")));
            //mainRootElement.appendChild(getParameter(doc, "Environment branch:", builtBranch));
            mainRootElement.appendChild(getParameter(doc, " Browser:", System.getProperty("browser")));
            mainRootElement.appendChild(getParameter(doc, " OS:", System.getProperty("os.name")));
            //mainRootElement.appendChild(getParameter(doc, "Java Version:", System.getProperty("java.runtime.version")));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //enable indent on the xml file
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(System.getProperty("user.dir")+"/allure-results/"+File.separator+"environment.xml"));

            transformer.transform(source, result);

            System.out.println("\nAllure Env XML is Created Successfully..");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getParameter(Document doc, String key, String value) {
        Element parameter = doc.createElement("parameter");
        parameter.appendChild(getParameterElements(doc, parameter, "key", key));
        parameter.appendChild(getParameterElements(doc, parameter, "value", value));
        return parameter;
    }

    // utility method to create text node
    private static Node getParameterElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;

    }

}