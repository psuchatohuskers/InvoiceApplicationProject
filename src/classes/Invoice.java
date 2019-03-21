package classes;

import java.util.ArrayList;

import org.joda.time.DateTime;


/*This class is a data container for invoice. It has five attributes*/
public class Invoice {
	
	private int invoiceID;
	private String invoiceCode;
	private Customer customer;
	private Person salesPerson;
	private DateTime invoiceDate;
	private ArrayList<Products> productList;
	
	
	
	

	public Invoice(int invoiceID, String invoiceCode, Customer customer, Person salesPerson, DateTime invoiceDate,
			ArrayList<Products> productList) {
		super();
		this.invoiceID = invoiceID;
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.invoiceDate = invoiceDate;
		this.productList = productList;
	}
	
	

	public Invoice(int invoiceID, String invoiceCode, Customer customer, Person salesPerson, DateTime invoiceDate) {
		super();
		this.invoiceID = invoiceID;
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.invoiceDate = invoiceDate;
	}
	
	



	public String getInvoiceCode() {
		return invoiceCode;
	}



	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}





	@Override
	public String toString() {
		return "Invoice [invoiceID=" + invoiceID + ", invoiceCode=" + invoiceCode + ", customer=" + customer
				+ ", salesPerson=" + salesPerson + ", invoiceDate=" + invoiceDate + ", productList=" + productList
				+ "]";
	}



	public int getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(int invoiceID) {
		this.invoiceID = invoiceID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Person getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(Person salesPerson) {
		this.salesPerson = salesPerson;
	}

	public DateTime getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(DateTime invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public ArrayList<Products> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<Products> productList) {
		this.productList = productList;
	}


	
	/*getSumSubtotal return the sum of sub-total of each products in the invoice*/
	public double getSumSubtotal() {
		double sumSubtotal = 0.00;
		for(Products p:this.productList) {
			if(p instanceof MovieTicket) {
				sumSubtotal += ((MovieTicket) p).getSubtotal();
			}else if(p instanceof SeasonPass) {
				sumSubtotal += ((SeasonPass) p).getSubtotal(this.invoiceDate);
			}else if(p instanceof ParkingPass) {
				sumSubtotal += ((ParkingPass) p).getSubtotal(this.productList);
			}else if(p instanceof Refreshment) {
				sumSubtotal += ((Refreshment) p).getSubtotal(this.productList);
			}
		}
		return sumSubtotal;
	}
	/*getSumTax return the sum of tax of each products in the invoice*/
	public double getSumTax() {
		double sumTax = 0.00;
		for(Products p:this.productList) {
			if(p instanceof MovieTicket) {
				sumTax += ((MovieTicket) p).getTax();
			}else if(p instanceof SeasonPass) {
				sumTax += ((SeasonPass) p).getTax(this.invoiceDate);
			}else if(p instanceof ParkingPass) {
				sumTax += ((ParkingPass) p).getTax(this.productList);
			}else if(p instanceof Refreshment) {
				sumTax += ((Refreshment) p).getTax(this.productList);
			}	
		}
		return sumTax;
	}
	
	public double getSumTotal() {
		double sumTotal = 0.00;
		for(Products p:this.productList) {
			if(p instanceof MovieTicket) {
				sumTotal += ((MovieTicket) p).getTotal();
			}else if(p instanceof SeasonPass) {
				sumTotal += ((SeasonPass) p).getTotal(this.invoiceDate);
			}else if(p instanceof ParkingPass) {
				sumTotal += ((ParkingPass) p).getTotal(this.productList);
			}else if(p instanceof Refreshment) {
				sumTotal += ((Refreshment) p).getTotal(this.productList);
			}	
		}
		return sumTotal;
	}
	
	/*getDiscount return the discount and tax deduction. If customer is a student, then the get 8% discount
	 * and not tax. If not, the are not getting discount and pay regular tax*/
	public double getDiscount() {
		double discount = 0;
		if(this.customer instanceof Students) {
			discount = ((Students) this.customer).getDiscount(this.getSumSubtotal(), this.getSumTax());
		}else if(this.customer instanceof General) {
			discount = ((General) this.customer).getDiscount();
		}
		return discount;
	}
	
	/*getFee returns the fee that customer has to pay. Student need to pay 6.75$ processing fee 
	 * but general does not need to*/
	public double getFee() {
		double fee = 0;
		if(this.customer instanceof Students) {
			fee = this.customer.getFee();
		}else if(this.customer instanceof General) {
			fee = this.customer.getFee();
		}
		return fee;
	}
	
	// getSumTotalCost returns sum of getSubTotal, Fee, Tax and Duscount
	public double getSumTotalCost() {
		return this.getSumSubtotal()+this.getFee()+this.getSumTax()+this.getDiscount();
	}

	//return string of name and type of customer
	public String fullNameType() {
		String result = null;
		if (this.customer instanceof General) {
		result = "[General]";
		} else {
			result = "[Student]";
		}
		return this.getCustomer().getName()+ " " + result;
	}
	// Return name of the type, for formatting purpose
	public String nameTypeOnly() {
		String result = null;
		if (this.getCustomer().getType().equals("G")) {
		result = "[General]";
		} else {
			result = "[Student]";
		}
		return result;
	}
	
	// return a string of address
	public String fmtAddress() {
		return this.getCustomer().getAddress().getCity() + " " + this.getCustomer().getAddress().getState() + " " + this.getCustomer().getAddress().getZip() + " " + this.getCustomer().getAddress().getCountry();
	}
	
	// Print out detail invoices
	public void printDetailInvoice() {
		System.out.println("========================");
		System.out.printf("Invoice %s\n", this.invoiceCode);
		System.out.println("========================");
		System.out.printf("Salesperson: %s\n", this.salesPerson.getName());
		System.out.println("Customer Info:");
		System.out.println("\t" + this.customer.getName() + " (" + this.customer.getCustomerCode() + ")\n\t" + this.nameTypeOnly() + "\n\t" + this.customer.getContactPerson().getName() + "\n\t" + this.customer.getAddress().getStreet() + "\n\t"+this.fmtAddress());
		System.out.println("-------------------------------------------");
		System.out.printf("%-20s %-50s %-20s %-20s %-20s\n",  "Code", "Item", "SubTotal", "Tax", "Total");
		for(Products p:productList) {
			if(p instanceof MovieTicket) {
				System.out.printf("%-20s %-50s %-20.2f %-20.2f %-20.2f\n",p.getProductCode(),"MovieTicket"+":"+((MovieTicket) p).getMovieName()+" @ "+((MovieTicket) p).getAddress().getStreet(), ((MovieTicket) p).getSubtotal(), ((MovieTicket) p).getTax(), ((MovieTicket) p).getTotal());
				System.out.printf("%-20s %-50s\n", "",((MovieTicket) p).getDateTime().toString("yyyy-MM-dd HH:mm")+" $"+Double.toString(((MovieTicket) p).getPricePerUnit())+"/UNIT @ "+Double.toString(((MovieTicket) p).getUnit())+" UNIT(S)"+((MovieTicket) p).discountMessage());
			}else if(p instanceof SeasonPass) {
				System.out.printf("%-20s %-50s %-20.2f %-20.2f %-20.2f\n",p.getProductCode(),"SeasonPass"+"-"+((SeasonPass) p).getName(), ((SeasonPass) p).getSubtotal(this.invoiceDate), ((SeasonPass) p).getTax(this.invoiceDate), ((SeasonPass) p).getTotal(this.invoiceDate));
				System.out.printf("%-20s %-50s\n", "","$"+Double.toString(((SeasonPass) p).getPassCost())+"/UNIT @ "+Double.toString(((SeasonPass) p).getUnit())+" UNITS "+" prorated "+Integer.toString(((SeasonPass) p).getDayLeft(invoiceDate))+" Days"+" + $8 processing fees");
			}else if(p instanceof ParkingPass) {
				System.out.printf("%-20s %-50s %-20.2f %-20.2f %-20.2f\n",p.getProductCode(),"ParkingPass"+":"+((ParkingPass) p).getProductCodeAttach(), ((ParkingPass) p).getSubtotal(this.productList), ((ParkingPass) p).getTax(this.productList), ((ParkingPass) p).getTotal(this.productList));
				System.out.printf("%-20s %-50s\n", "","$"+Double.toString(((ParkingPass) p).getParkingFee())+"/UNIT @ "+Double.toString(((ParkingPass) p).getUnit())+" UNIT(S)"+" with "+((ParkingPass) p).getNumFreePass(this.productList)+" units free");
			}else if(p instanceof Refreshment) {
				System.out.printf("%-20s %-50s %-20.2f %-20.2f %-20.2f\n",p.getProductCode(),((Refreshment) p).getName(), ((Refreshment) p).getSubtotal(this.productList), ((Refreshment) p).getTax(this.productList), ((Refreshment) p).getTotal(this.productList));
				System.out.printf("%-20s %-50s\n", "","$"+Double.toString(((Refreshment) p).getProductCost())+"/UNIT @ "+Double.toString(((Refreshment) p).getUnit())+" UNIT(S)"+((Refreshment) p).discountMessage(this.productList));
			}
		}
		System.out.println("========================================================================================================================================");
		System.out.printf("%-20s %s %s %-48s %-20.2f %-20.2f %-20.2f\n", "SUB-TOTALS", "", "", "", this.getSumSubtotal(), this.getSumTax(), this.getSumTotal());
		if(this.customer instanceof Students) {
			System.out.printf("%-20s %s %s %-48s %-20.2s %-10.2s %-20.2f\n", "DISCOUNT (8% STUDENT & NO TAX)", "", "", "", "", "", ((Students)this.customer).getDiscount(this.getSumSubtotal(), this.getSumTax()));
			System.out.printf("%-20s %s %s %-48s %-20.2s %-10.2s %-20.2f\n", "ADDITIONAL FEE (Student)", "", "", "", "", "", 6.75);
			System.out.printf("%-20s %s %s %-48s %-20.2s %-20.2s %-20.2f\n", "TOTAL", "", "", "", "", "", this.getSumTotalCost()); 
		}else {
			System.out.printf("%-20s %s %s %-48s %-20.2s %-20.2s %-20.2f\n", "TOTAL", "", "", "", "", "", this.getSumTotalCost()); 
		}
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((invoiceCode == null) ? 0 : invoiceCode.hashCode());
		result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result + invoiceID;
		result = prime * result + ((productList == null) ? 0 : productList.hashCode());
		result = prime * result + ((salesPerson == null) ? 0 : salesPerson.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (invoiceCode == null) {
			if (other.invoiceCode != null)
				return false;
		} else if (!invoiceCode.equals(other.invoiceCode))
			return false;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (invoiceID != other.invoiceID)
			return false;
		if (productList == null) {
			if (other.productList != null)
				return false;
		} else if (!productList.equals(other.productList))
			return false;
		if (salesPerson == null) {
			if (other.salesPerson != null)
				return false;
		} else if (!salesPerson.equals(other.salesPerson))
			return false;
		return true;
	}
	
	
	

	
}
