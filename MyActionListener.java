import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MyActionListener implements ActionListener{
	
	String button;
	String firstbox;
	String secondbox;
	String table_area;
	String[] array1;
	String[] array2; 
	float source;
	float target;
	float rate_to_pln = 0;
	float rate_from_pln = 0;
	JTextField right_text_field;
	JTextField left_text_field;
	JTextArea down_text_area;
	JComboBox<String[]> left_box;
	JComboBox<String[]> right_box;
	Database currency_data_base = new Database();
	
	public MyActionListener(JTextField text1,JTextField text2, JTextArea text3, JComboBox<String[]> box1, JComboBox<String[]> box2) {
		left_text_field = text1;
		right_text_field = text2;
		down_text_area = text3;
		left_box = box1;
		right_box = box2;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		button = e.getActionCommand();
		firstbox = (String)left_box.getSelectedItem();
		secondbox = (String)right_box.getSelectedItem();
		
		switch(button) {
		case "Convert":
			source = Float.valueOf(String.valueOf(left_text_field.getText().replace(",", ".")));
			if(rate_to_pln == 0 || rate_from_pln == 0 || source < 0) 
			{
				right_text_field.setText("error");
				break;
			}
			target = source * rate_to_pln * rate_from_pln; 
			right_text_field.setText(String.valueOf(target));
			break;
		case "Clear All":
			source = 0;
			target = 0;
			rate_to_pln = 0;
			rate_from_pln = 0;
			left_text_field.setText("0.00");
			right_text_field.setText("0.00");
			left_box.setSelectedItem("---currency---");
			right_box.setSelectedItem("---currency---");
			firstbox = (String)left_box.getSelectedItem();
			secondbox = (String)right_box.getSelectedItem();
			break;
		}
	
		switch(firstbox) {
			case "PLN":
				rate_to_pln = 1;
				break;
			case "THB":
			case "USD":
			case "AUD":
			case "HKD":
			case "NZD":
			case "SGD":
			case "EUR":
			case "HUF":
			case "CHF":
			case "GBP":
			case "UAH":
			case "JPY":
			case "CZK":
			case "DKK":
			case "ISK":
			case "NOK":
			case "SEK":
			case "HRK":
			case "RON":
			case "BGN":
			case "TRY":
			case "ILS":
			case "CLP":
			case "PHP":
			case "MXN":
			case "ZAR":
			case "BRL":
			case "MYR":
			case "RUB":
			case "IDR":
			case "INR":
			case "KRW":
			case "CNY":
			case "XDR":
				array1 = currency_data_base.find_data("symbol", firstbox);
			    for(int j = 0; j < array1.length; j++) {
			    	if(array1[j] != null && array1[j+1] == null) {
			    	rate_to_pln = Float.valueOf(array1[j-1])/Float.valueOf(array1[j]);
			    	}
			    }
			    break;
		}
		
		switch(secondbox) {
			case "PLN":
				rate_from_pln = 1;
				break;
			case "THB":
			case "USD":
			case "AUD":
			case "HKD":
			case "NZD":
			case "SGD":
			case "EUR":
			case "HUF":
			case "CHF":
			case "GBP":
			case "UAH":
			case "JPY":
			case "CZK":
			case "DKK":
			case "ISK":
			case "NOK":
			case "SEK":
			case "HRK":
			case "RON":
			case "BGN":
			case "TRY":
			case "ILS":
			case "CLP":
			case "PHP":
			case "MXN":
			case "ZAR":
			case "BRL":
			case "MYR":
			case "RUB":
			case "IDR":
			case "INR":
			case "KRW":
			case "CNY":
			case "XDR":
				array2 = currency_data_base.find_data("symbol", secondbox); 
			    for(int j = 0; j < array2.length; j++) {
			    	if(array2[j] != null && array2[j+1] == null) {
			    	rate_from_pln = Float.valueOf(array2[j])/Float.valueOf(array2[j-1]);  	
			    	}
			    }
			    break;
		}
		
		
		if(firstbox != "---currency---" && secondbox != "---currency---") {		
			String date_from_base=null;
			String rate_from_base=null;
			int lines = 0;
			int j = 20;
			table_area = " Last exchange rate from database: \n";
			table_area += ("-----------------------------------------------------------------------------------------------------------------------\n");
			
			for(int i = 0; i < 7; i++) {
				if(firstbox == secondbox) {
					rate_from_base = "1";
					date_from_base ="ALWAYS";
				}else if(firstbox == "PLN") {
					if(array2[j] !=null && array2[j-1] !=null && array2[j-2] !=null) {
						rate_from_base = String.valueOf(Float.valueOf(array2[j])/Float.valueOf(array2[j-1]));
						date_from_base = array2[j-2];
					}else {
						date_from_base = "---";
						rate_from_base = "---";
					}	
				}else if(secondbox == "PLN") {
					if(array1[j] !=null && array1[j-1] !=null && array1[j-2] !=null) {
						rate_from_base = String.valueOf(Float.valueOf(array1[j-1])/Float.valueOf(array1[j]));
						date_from_base = array1[j-2];
					}else {
						date_from_base = "---";
						rate_from_base = "---";
					}	
				}else if(array1[j] !=null && array1[j-1] !=null && array1[j-2] !=null && array2[j] !=null && array2[j-1] !=null && array2[j-2] !=null)
					{
					date_from_base = array1[j-2];
					rate_from_base = String.valueOf((Float.valueOf(array1[j-1])/Float.valueOf(array1[j]))*(Float.valueOf(array2[j])/Float.valueOf(array2[j-1])));
					}else {
						date_from_base = "---";
						rate_from_base = "---";
					}
				
				if(date_from_base != "---" && rate_from_base != "---") {
				table_area += (date_from_base + "\t\t1 " + firstbox + "\t=\t" + rate_from_base + " " + secondbox +"\n");	
				table_area += ("-----------------------------------------------------------------------------------------------------------------------\n");	
				lines++;
				}
				j-=3;
			}
			for(int i = 0; i < 7-lines; i++) {
				table_area += ("---" + "\t\t1 " + firstbox + "\t=\t" + "---" + " " + secondbox +"\n");
				table_area += ("-----------------------------------------------------------------------------------------------------------------------\n");	
			}
			down_text_area.setText(table_area);
		}else {
			down_text_area.setText("");
		}
	}
}  


	

