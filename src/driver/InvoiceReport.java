package driver;

import java.util.ArrayList;


import classes.Customer;
import classes.Invoice;
import classes.Person;
import classes.Products;
import fileReader.DBReader;
import fileWriter.InvoiceWriter;
import linkList.InvoiceList;

public class InvoiceReport {

	public static void main(String[] args) {
		DBReader dbReader = new DBReader(); //Initiate the object DBreader 


		ArrayList<Person> personTest = dbReader.readDBPerson(); //Read person into Arraylist
		ArrayList<Customer> customerTest = dbReader.readDBCustomer(personTest); //Read customer into arraylist
		ArrayList<Products> productTest = dbReader.readDBProducts();//Read products into ArrayList
		ArrayList<Invoice> invoiceTest = dbReader.readDBInvoice(personTest, customerTest, productTest); // Read invoice into ArrayList

		
		InvoiceList invList = new InvoiceList(); // Intiate new link list
		//Put invoice into link list
		for(Invoice inv:invoiceTest) {
			invList.addInvoice(inv);
		}
		
		//Print executive summary
		InvoiceWriter invWriter = new InvoiceWriter();
		invWriter.WriteExecutiveSummary(invList);
		
		//Print detail report
		invList.printDetailReport();
		
	}

}
