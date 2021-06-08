
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class Currency_calculator {

	static JFrame jframe = null;
	
    public static void createAndShowGUI() {
    	
    	JFrame jf = new JFrame("Currency Calculator");
    	jf.setPreferredSize(new Dimension(600, 400));
    	jframe=jf;
    	
 //////////////////////////////////////////////////////     	
        JPanel panel_up = new JPanel();
        jf.add(panel_up, BorderLayout.NORTH);
        GridLayout up_layout = new GridLayout(1,5,10,50);
        panel_up.setLayout(up_layout);
        panel_up.setPreferredSize(new Dimension(1, 50));
        
        Font font1 = new Font("SansSerif", Font.BOLD, 15);				//font for text fields
        JTextField left_text_field = new JTextField();					//left text field
        left_text_field.setHorizontalAlignment(SwingConstants.RIGHT);
        left_text_field.setFont(font1);
        left_text_field.setText("0.00");
        JTextField right_text_field = new JTextField();					//right text field
        right_text_field.setHorizontalAlignment(SwingConstants.RIGHT);
        right_text_field.setFont(font1);
        right_text_field.setText("0.00");
        right_text_field.setEditable(false);
        
        String[] currency_codes = {"---currency---", "PLN", "THB", "USD", "AUD", "HKD", "NZD", "SGD", "EUR", "HUF", "CHF", "GBP", "UAH", "JPY", "CZK", "DKK", "ISK",
        		"NOK","SEK", "HRK", "RON", "BGN", "TRY", "ILS", "CLP", "PHP", "MXN", "ZAR", "BRL", "MYR", "RUB", "IDR", "INR", "KRW", "CNY", "XDR"};
        JComboBox<String[]> left_box = new JComboBox(currency_codes);				//left combo box
        JComboBox<String[]> right_box = new JComboBox(currency_codes);				//right combo box
        JButton button1 = new JButton("Convert");									//Convert button

        panel_up.add(left_text_field);
        panel_up.add(left_box);
        panel_up.add(button1);
        panel_up.add(right_text_field);
        panel_up.add(right_box);
 //////////////////////////////////////////////////////          
        JPanel panel_middle = new JPanel();
        jf.add(panel_middle, BorderLayout.CENTER);
        GridLayout middle_layout = new GridLayout(1,1);
        panel_middle.setLayout(middle_layout);
        Font font2 = new Font("SansSerif", Font.BOLD, 12);				//font for text Area
        JTextArea down_text_area = new JTextArea();						//left data field
        down_text_area.setFont(font2);									//font
        down_text_area.setEditable(false);								//edition blocked
        
        panel_middle.setPreferredSize(new Dimension(50, 50));
        panel_middle.add(down_text_area);   
///////////////////////////////////////////////////////              
        JPanel panel_down = new JPanel();
        jf.add(panel_down, BorderLayout.SOUTH);
        GridLayout down_layout = new GridLayout(1,1);
        panel_down.setLayout(down_layout);
        
        JButton button2 = new JButton("Clear All");							//Clear button
        button2.setPreferredSize(new Dimension(600, 50));
        panel_down.add(button2);    
///////////////////////////////////////////////////////        
        
        MyActionListener myActionListener = new MyActionListener(left_text_field,right_text_field, down_text_area, left_box, right_box);
        button1.addActionListener(myActionListener);
        button2.addActionListener(myActionListener);
        left_box.addActionListener(myActionListener);
        right_box.addActionListener(myActionListener);
        
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() { createAndShowGUI(); }
        });   
        
        ReadWebPage run = new ReadWebPage();
        run.ReadWeb(); 
    }
}
