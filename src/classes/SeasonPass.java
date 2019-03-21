package classes;



import org.joda.time.DateTime;
import org.joda.time.Days;
/*SeasonPass is a subclass of products. It has five attributes and two attributes inherit form
 * the Products class.*/
public class SeasonPass extends Products {
	
	private String name;
	private DateTime startDate;
	private DateTime endDate;
	private double passCost;
	private int unit;

	
	// unit will be set later
	public SeasonPass(String productCode, String productType, String name, DateTime startDate, DateTime endDate,
			double passCost) {
		super(productCode, productType);
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.passCost = passCost;
	}
	//copy constructor
	public SeasonPass(SeasonPass seasonPass) {
		super(seasonPass.getProductCode(), seasonPass.getProductType());
		this.name = seasonPass.getName();
		this.startDate = seasonPass.getStartDate();
		this.endDate = seasonPass.getEndDate();
		this.passCost = seasonPass.getPassCost();
	}
	

	


	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public DateTime getStartDate() {
		return startDate;
	}





	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}





	public DateTime getEndDate() {
		return endDate;
	}





	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}





	public double getPassCost() {
		return passCost;
	}





	public void setPassCost(double passCost) {
		this.passCost = passCost;
	}





	public int getUnit() {
		return unit;
	}





	public void setUnit(int unit) {
		this.unit = unit;
	}



	// cost of seasonPass is prorated to days available in the pass
	public double getSubtotal(DateTime invoiceDate) {
		double cost;
		int numberOfDaysInPass = Days.daysBetween(this.startDate, this.endDate).getDays();
		double costPerDay = this.passCost/numberOfDaysInPass;
		
		if(invoiceDate.isAfter(this.endDate) || invoiceDate.isEqual(this.endDate)) {
			cost = 8;
		}else if(invoiceDate.isAfter(this.startDate) && invoiceDate.isBefore(this.endDate)) {
			int daysValid = Days.daysBetween(invoiceDate, this.endDate).getDays();
			cost = (daysValid*costPerDay)+8;
		}else {
			cost = this.passCost+8;
		}
		return cost*unit;
	}
	
	// Tickets has 6% tax
	public double getTax(DateTime invoiceDate) {
		
		double tax;
		tax = this.getSubtotal(invoiceDate)*0.06;
		return tax;
		
	}
	
	//get total cost after tax
	public double getTotal(DateTime invoiceDate) {
		return this.getSubtotal(invoiceDate)+this.getTax(invoiceDate);
	}

	// Return days left in the pass
	public int getDayLeft(DateTime invoiceDate) {
		if(invoiceDate.isAfter(this.startDate) && invoiceDate.isBefore(this.endDate)) {
			int daysValid = Days.daysBetween(invoiceDate, this.endDate).getDays();
			return daysValid;
		}else {
			return 0;
		}
	}
	@Override
	public String toString() {
		return "SeasonPass [name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + ", passCost="
				+ passCost + ", unit=" + unit + "]";
	}

	




	

}
