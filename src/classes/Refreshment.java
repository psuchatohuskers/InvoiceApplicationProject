package classes;

import java.util.ArrayList;

/*Refreshment is a subclass of products. */
public class Refreshment extends Products {
	private String name;
	private double productCost;
	private int unit;
	
	
	public Refreshment(String productCode, String productType, String name, double productCost) {
		super(productCode, productType);
		this.name = name;
		this.productCost = productCost;
	}
	// copy constructor
	public Refreshment(Refreshment refreshment) {
		super(refreshment.getProductCode(), refreshment.getProductType());
		this.name = refreshment.getName();
		this.productCost = refreshment.getProductCost();
	}
	


	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public double getProductCost() {
		return productCost;
	}




	public void setProductCost(double productCost) {
		this.productCost = productCost;
	}




	public int getUnit() {
		return unit;
	}




	public void setUnit(int unit) {
		this.unit = unit;
	}




	@Override
	public String toString() {
		return "Refreshment [name=" + name + ", productCost=" + productCost + ", unit=" + unit + "]";
	}

	

	// getSubtotal takes an ArrayList of Products then count the number of tickets since there will be
	// a 5% discount for 
	public double getSubtotal(ArrayList<Products> products) {
		int numberOfTickets = 0;
		for(Products p:products) {
			if(p instanceof MovieTicket || p instanceof SeasonPass) {
				numberOfTickets ++;
			}
		}
		if(numberOfTickets > 0) {
			return (this.productCost*this.unit)-(this.productCost*this.unit)*0.05;
		}else {
			return this.productCost*this.unit;
		}
		
	}
	
	// the service type get 4% tax
	public double getTax(ArrayList<Products> products) {
		double tax;
		tax = this.getSubtotal(products)*0.04;
		return tax;
	}


	//get total cost after tax
	public double getTotal(ArrayList<Products> product) {
		return this.getSubtotal(product)+this.getTax(product);
	}

	
	public String discountMessage(ArrayList<Products> products) {
		int numberOfTickets = 0;
		for(Products p:products) {
			if(p instanceof MovieTicket || p instanceof SeasonPass) {
				numberOfTickets ++;
			}
		}
		if(numberOfTickets > 0) {
			return " 5% discount";
		}else {
			return " No discount";
		}
		
	}


	
	
}
