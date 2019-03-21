package classes;

/*General is a subclass of Customer, it inherits some attributes from Customer. General also contains
 * two methods: getDiscount and getFee. */

public class General extends Customer {

	


	public General(int customerID, String customerCode, String type, Person contactPerson, String name,
			Address address) {
		super(customerID, customerCode, type, contactPerson, name, address);
		// TODO Auto-generated constructor stub
	}
	// getDiscount return 0.00 since general customer does not get any discount.
	public double getDiscount() {
		return 0.00;
	}
	// getFee return 0.00 since there is no processing fee for public.
	@Override
	public double getFee() {
		return 0.00;
	}
	

}
