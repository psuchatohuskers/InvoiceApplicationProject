package classes;



/*This class product is an abstract class. It is a parent class for MovieTicket,ParkingPass,SeasonPass
 * and Refreshment*/
public abstract class Products {
	private String productCode;
	private String productType;

	
	public Products(String productCode, String productType) {
		super();
		this.productCode = productCode;
		this.productType = productType;
	}
	

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	





	




		





	
	
	
	
	
	
}
