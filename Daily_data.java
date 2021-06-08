
public class Daily_data {
	
	 private int id ;
	 private String date;
	 private String symbol;
	 private String price;
	 private String scaler;
	 
	 public Daily_data(int id, String date, String symbol, String price, String scaler) {
	        this.id = id;
	        this.date = date;
	        this.symbol = symbol;
	        this.price = price;
	        this.scaler = scaler;
	 }
	 
	 public int getId() {
		 return id;
	 }
	 public void setId(int id) {
	     this.id = id;
	 }
	 public String get_date() {
	     return date;
	 }
	 public void set_date(String date) {
	       this.date = date;
	 }
	 public String get_symbol() {
	     return symbol;
	 }
	 public void set_symbol(String symbol) {
	        this.symbol = symbol;
	 }
	 public String get_price() {
	     return price;
	 }
	 public void set_price(String price) {
	        this.price = price;
	 }
	 public String get_scaler() {
	     return scaler;
	 }
	 public void set_scaler(String scaler) {
	        this.scaler = scaler;
	 }
}


