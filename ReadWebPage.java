
import java.io.*;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadWebPage {
	
	private int length_counter = 0;

	public void ReadWeb() throws SAXException, IOException, ParserConfigurationException {        
		
		String date;
		String last_date;
	    String table_path = "https://www.nbp.pl";
	    String web_line;
		BufferedReader reader;
		reader = new BufferedReader(new InputStreamReader((new URL("https://www.nbp.pl/home.aspx?f=/kursy/kursya.html")).openStream()));
		while((web_line=reader.readLine()) != null) {
			if(web_line.contains("/kursy/xml/") == true) {
				int a = 0, b = 0;
				if(web_line.contains("/kursy/xml/")) {
					a = web_line.indexOf("/kursy/xml/");
					b = web_line.indexOf(".xml") + 4;
					if (a != -1){
					    table_path += web_line.substring(a,b);
					}
				}					 
			}
		}
			
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(table_path);
        Element root =  doc.getDocumentElement();
        NodeList children = root.getElementsByTagName("pozycja");
        
        date = root.getElementsByTagName("data_publikacji").item(0).getTextContent();
        Database currency_data_base = new Database();	//database       
        
		length_counter = currency_data_base.get_max_ID();				//get max ID ->end of the tab
		last_date = currency_data_base.find_data("ID", String.valueOf(length_counter-1))[0];
		if(last_date == null) {last_date = "1111-11-11";}

		if((last_date).equals(date) == false) {
			if(length_counter == 246) {
				//delete old data
				for(int k = 1; k <= 35; k++) {
					currency_data_base.delete_data("ID", String.valueOf(k));
				}
				//prepare space at the end of tab
			    for(int j = 36; j <= length_counter; j++) {
			    	currency_data_base.change_data("ID", String.valueOf(j) , "ID", String.valueOf(j-35));
			    }
			length_counter = currency_data_base.get_max_ID();	
			}
			
			for (int i = 0; i < children.getLength(); i++)
			{
			    Node child = children.item(i);
			    if(child.getNodeType() == Node.ELEMENT_NODE)
			    {
			        Element childElement = (Element) child;         
			        Daily_data d = new Daily_data(length_counter, date, childElement.getElementsByTagName("kod_waluty").item(0).getTextContent(), childElement.getElementsByTagName("kurs_sredni").item(0).getTextContent().replace(",", "."), childElement.getElementsByTagName("przelicznik").item(0).getTextContent().replace(",", "."));
			        currency_data_base.update_data(d);
			        length_counter++;
			    }
			} 
		}
	}	
}
