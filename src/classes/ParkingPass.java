package classes;

import java.util.ArrayList;

/*This ParkingPass is a subclass of Products*/
public class ParkingPass extends Products {
	
	private double parkingFee;
	private int unit;
	private String productCodeAttach;
	// both unit and productCodeAttach will be set after the object is initialized.
	public ParkingPass(String productCode, String productType, double parkingFee) {
		super(productCode, productType);
		this.parkingFee = parkingFee;
	}
	// This is a copy constructor
	public ParkingPass(ParkingPass parkingPass) {
		super(parkingPass.getProductCode(), parkingPass.getProductType());
		this.parkingFee = parkingPass.getParkingFee();
	}

	public double getParkingFee() {
		return parkingFee;
	}

	public void setParkingFee(double parkingFee) {
		this.parkingFee = parkingFee;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}


	public String getProductCodeAttach() {
		return productCodeAttach;
	}

	public void setProductCodeAttach(String productCodeAttach) {
		this.productCodeAttach = productCodeAttach;
	}
	// This method take an ArrayList of product since the customer is getting one free pass 
	// for each product purchased.
	public double getSubtotal(ArrayList<Products> product) {
		Products passAttach = null;
		for(Products p:product) {
			if(p.getProductCode().equals(this.productCodeAttach)) {
				passAttach = p;
			}
		}
		int unitPassAttach = 0;
		if(passAttach instanceof MovieTicket) {
			unitPassAttach += ((MovieTicket) passAttach).getUnit();
		}else if(passAttach instanceof SeasonPass) {
			unitPassAttach += ((SeasonPass) passAttach).getUnit();
		}
		
		if(unitPassAttach >= this.unit) {
			return 0.00;
		}else {
			return (this.unit - unitPassAttach)*this.parkingFee;
		}
	}
	
	//The tax for service is 4%
	public double getTax(ArrayList<Products> product) {
		return this.getSubtotal(product)*0.04;
		
	}
	

	//get total cost after tax
	public double getTotal(ArrayList<Products> product) {
		return this.getSubtotal(product)+this.getTax(product);
	}
	
	// Return number of fee passes
	public double getNumFreePass(ArrayList<Products> product) {
		Products passAttach = null;
		for(Products p:product) {
			if(p.getProductCode().equals(this.productCodeAttach)) {
				passAttach = p;
			}
		}
		int unitPassAttach = 0;
		if(passAttach instanceof MovieTicket) {
			unitPassAttach += ((MovieTicket) passAttach).getUnit();
		}else if(passAttach instanceof SeasonPass) {
			unitPassAttach += ((SeasonPass) passAttach).getUnit();
		}
		
		return unitPassAttach;
	}

	@Override
	public String toString() {
		return "ParkingPass [parkingFee=" + parkingFee + ", unit=" + unit + ", productCodeAttach=" + productCodeAttach
				+ "]";
	}

	
	
	
	

	
	
	

}
