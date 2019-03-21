package classes;

import org.joda.time.DateTime;

/*MovieTicket has five instances, it is a subclass of product*/
public class MovieTicket extends Products {
	
	private DateTime dateTime;
	private String movieName;
	private Address address;
	private String screenNo;
	private Double pricePerUnit;
	private int unit;
	
	
	
	// Note that constructor does not have unit since it will be set later in readInvoice
	public MovieTicket(String productCode, String productType, DateTime dateTime, String movieName, Address address,
			String screenNo, Double pricePerUnit) {
		super(productCode, productType);
		this.dateTime = dateTime;
		this.movieName = movieName;
		this.address = address;
		this.screenNo = screenNo;
		this.pricePerUnit = pricePerUnit;
	}
	
	// Copy constructor for MovieTicket for deep copy
	public MovieTicket(MovieTicket movieTicket) {
		super(movieTicket.getProductCode(), movieTicket.getProductType());
		this.dateTime = movieTicket.getDateTime();
		this.movieName = movieTicket.getMovieName();
		this.address = movieTicket.getAddress();
		this.screenNo = movieTicket.getScreenNo();
		this.pricePerUnit = movieTicket.getPricePerUnit();
	}
	


	public DateTime getDateTime() {
		return dateTime;
	}




	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}




	public String getMovieName() {
		return movieName;
	}




	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}




	public Address getAddress() {
		return address;
	}




	public void setAddress(Address address) {
		this.address = address;
	}




	public String getScreenNo() {
		return screenNo;
	}




	public void setScreenNo(String screenNo) {
		this.screenNo = screenNo;
	}




	public Double getPricePerUnit() {
		return pricePerUnit;
	}




	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}




	public int getUnit() {
		return unit;
	}




	public void setUnit(int unit) {
		this.unit = unit;
	}

	// if the day of movie is tuesday or thursday, the customer get 7% discount
	public double getSubtotal() {
		
		double priceBeforeDiscount = this.pricePerUnit*this.unit;
		int dayOfSell = Integer.parseInt(this.dateTime.dayOfWeek().getAsString());
		if(dayOfSell == 2 || dayOfSell == 4) {
			return priceBeforeDiscount-(priceBeforeDiscount*0.07);
		}else {
			return priceBeforeDiscount;
		}
	}
		
	// object of type ticket get 6% tax rate.	
	public double getTax() {
		
		double tax;
		tax = this.getSubtotal()*0.06;
		return tax;
	}
	
	//get total cost after tax
	public double getTotal() {
		return this.getSubtotal()+this.getTax();
	}
	
	public String discountMessage() {
		int dayOfSell = Integer.parseInt(this.dateTime.dayOfWeek().getAsString());
		if(dayOfSell == 2 || dayOfSell == 4) {
			return " Tuesday/Thursdat 7% Discount";
		}else {
			return " No Discount";
		}
		
	}
	
	

	
}
