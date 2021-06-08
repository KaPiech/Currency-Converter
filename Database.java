
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:MyDatabase.db";
    private Connection conn;
    private Statement stat;
  
    public Database() {
        try {
            Class.forName(Database.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC: "+ e.getMessage());
        }
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.err.println("Problem z polaczeniem: "+ e.getMessage());
        }
        createTable();
    }

    
    public void createTable()  {
        try {
        	stat = conn.createStatement();
        	String tableSQL = "CREATE TABLE IF NOT EXISTS " + "tab"
                    + " (ID INT PRIMARY KEY     NOT NULL,"
                    + " date			CHAR(15), "
                    + " symbol			CHAR(10), "
                    + " price			CHAR(10), "
                    + " scaler			CHAR(10))";
        	
            stat.execute(tableSQL);
            stat.close();
        } catch (SQLException e) {
            System.err.println("Blad tworzenia tabeli: "+ e.getMessage());
        }
    }

    public void update_data(Daily_data daily_data) {
        try {
        	stat = conn.createStatement();
        	String addSQL = "INSERT INTO " + "tab" + " (ID, date, symbol, price, scaler) "
        	
                    + "VALUES ("
                    + daily_data.getId() + ", "
                    + "'" + daily_data.get_date() + "', "
                    + "'" + daily_data.get_symbol() + "', "
                    + "'" + daily_data.get_price() + "', "
                    + "'" + daily_data.get_scaler() + "'" + " );";
            stat.executeUpdate(addSQL);
            stat.close();
        } catch (SQLException e) {
            System.err.println("Blad dodawania danych: "+ e.getMessage());
        	}
    }
  
 
    public String[] find_data(String field, String value) {
    	String[] array = new String[21];
    	int i = 0;
        try {
            stat = conn.createStatement();
            String findSQL = "SELECT * FROM " + "tab"
                    + " WHERE " + field + "='" + value + "';";
 
            ResultSet wynik = stat.executeQuery(findSQL);
            while (wynik.next()) {
            	
            	array[i] = wynik.getString("date");
            	i++;
            	array[i] = wynik.getString("price");
            	i++;
            	array[i] = wynik.getString("scaler");
            	i++;
            }
            wynik.close();
            stat.close();
            i = 0;
        } catch (Exception e) {
            System.out.println("Blad szukania danych: " + e.getMessage());
        }
        return array;
    }
    
    
    public void delete_data(String field, String value) {
        try {
            stat = conn.createStatement();
 
            String deleteSQL = "DELETE FROM "+ "tab" + " WHERE " + field +
                    "='" + value + "';";
            stat.executeUpdate(deleteSQL);
            stat.close();
        } catch (Exception e) {
            System.out.println("Blad usuwania danych: " + e.getMessage());
        }
    }
    
    
    public int get_max_ID() {
    	int max_id = 0;
        try{
        	PreparedStatement stat;
        	ResultSet rs;
        	String getidSQL = "SELECT MAX(ID) AS max_id FROM tab";
        	stat = conn.prepareStatement(getidSQL);
        	rs = stat.executeQuery();
        	if (rs.next()) {
        		max_id = rs.getInt("max_id") + 1;
        	}
    	
        	}catch (Exception e) {
             System.out.println("Blad szukania max_id: " + e.getMessage());
        	}
         return max_id;
    }
    
    
    public void change_data(String field_wanted, String value_wanted, String field_to_change, String value_to_change) {
    		try {
				stat = conn.createStatement();
				String changeSQL = "UPDATE " + "tab" + " SET "
				   + field_to_change + " = '" + value_to_change
				   + "' WHERE " + field_wanted + "='" + value_wanted + "';";
				stat.executeUpdate(changeSQL);
				stat.close();
    			} catch (Exception e) {
    				System.out.println("Blad przesuniecia danych w bazie: " + e.getMessage());
    				}
    }    
    
    
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia: "+ e.getMessage());
        }
    }
}