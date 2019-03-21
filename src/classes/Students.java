package classes;

/*This class inherit attributes from Customer. It has two methods: getDiscount and getFee*/
public class Students extends Customer {

	
	




	public Students(int customerID, String customerCode, String type, Person contactPerson, String name,
			Address address) {
		super(customerID, customerCode, type, contactPerson, name, address);
		// TODO Auto-generated constructor stub
	}

	// getDiscount takes subtotal and tax as an input, then return the total discount as an output
	public double getDiscount(double subtotal,double tax) {
		return -((subtotal*0.08)+tax);
	}
	
	// getFee return 6.75 since student need to pay 6.75$ processing fee
	@Override
	public double getFee() {
		return 6.75;
	}
	

}
