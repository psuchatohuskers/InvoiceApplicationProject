package fileReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import classes.Address;
import classes.Customer;
import classes.General;
import classes.Invoice;
import classes.MovieTicket;
import classes.ParkingPass;
import classes.Person;
import classes.Products;
import classes.Refreshment;
import classes.SeasonPass;
import classes.Students;
import sql.DatabaseInfo;

/*The purpose in this class is to load data from the database then assign data to ArrayList of object */
public class DBReader {
	
	public ArrayList<Person> readDBPerson(){
		
		// ArrayList to contain person object
		ArrayList<Person> personList = new ArrayList<Person>();
		
		//query to obtain data for person from database
		String query1 = "SELECT P.PersonID,P.PersonCode,P.FirstName,P.LastName,A.Street,A.City,A.State,A.Zip,A.Country \n" + 
				"FROM Persons AS P\n" + 
				"JOIN Address AS A ON A.AddressID = P.AddressID;";
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			// Initialize the Person first without email
			PreparedStatement ps = conn.prepareStatement(query1);
			ResultSet rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					int personID = rs.getInt("PersonID");
					String personCode = rs.getString("PersonCode");
					String firstName = rs.getString("FirstName");
					String lastName = rs.getString("LastName");
					String street = rs.getString("Street");
					String city = rs.getString("City");
					String state = rs.getString("State");
					String zip = rs.getString("Zip");
					String country = rs.getString("Country");
					Address tempAddress = new Address(street,city,state,zip,country);
					Person p = new Person(personID,personCode,firstName,lastName,tempAddress);
					personList.add(p);	
				}
			}
			conn.close();
			ps.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		//Finding an email then add to the Person
		String query2 = "SELECT E.Email \n"
				+ "FROM Email AS E \n"
				+ "WHERE PersonID = ?";
		Connection conn2 = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn2.prepareStatement(query2);
			for(Person p:personList) {
				ps.setInt(1, p.getPersonID());
				ResultSet rs = ps.executeQuery();
				if(rs != null) {
					ArrayList<String> tempEmail = new ArrayList<String>();
					while(rs.next()) {
						tempEmail.add(rs.getString("Email"));
					}
					p.setEmail(tempEmail);	
				}
				rs.close();
			}
			conn2.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return personList;
		
	}
	
	public ArrayList<Customer> readDBCustomer(ArrayList<Person> person){
		
		//ArrayList to temporary contains customers
		ArrayList<Customer> c = new ArrayList<Customer>();
		
		//Query to obtain person from database
		String query = "SELECT C.CustomerID,C.CustomerCode, C.CustomerType, C.PrimaryContactID, C.CustomerName, A.Street, A.City, A.State,A.Zip, A.Country\n" + 
				"FROM Customers AS C\n" + 
				"JOIN Address AS A ON A.AddressID = C.AddressID;";
		//Create a connection
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					int customerID = rs.getInt("CustomerID");
					String customerCode = rs.getString("CustomerCode");
					String customerType = rs.getString("CustomerType");
					Person tempPerson = null;
					for(Person p: person) {
						if(p.getPersonID() == rs.getInt("PrimaryContactID")) {
							tempPerson = p;
						}
					}
					String customerName = rs.getString("CustomerName");
					String street = rs.getString("Street");
					String city = rs.getString("City");
					String state = rs.getString("State");
					String zip = rs.getString("Zip");
					String country = rs.getString("Country");
					Address tempAddress = new Address(street,city,state,zip,country);
					if(customerType.equals("Student")) {
						Students s = new Students(customerID,customerCode,customerType,tempPerson,customerName,tempAddress);
						c.add(s);
					}else if(customerType.equals("General")) {
						General g = new General(customerID,customerCode,customerType,tempPerson,customerName,tempAddress);
						c.add(g);
					}
					
				}
			}
			ps.close();
			rs.close();
			conn.close();
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		
		
		

	}
	
	public ArrayList<Products> readDBProducts(){
		 //Create ArrayList to contain Products
		ArrayList<Products> pro = new ArrayList<Products>();
		
		//Query to obtain products from Database
		String query = "SELECT P.ProductCode, P.ProductType,\n" + 
				"M.MovieDate,M.MovieName,M.ScreenNo,M.PricePerUnit,\n" + 
				"A.Street, A.City, A.State,A.Zip, A.Country,\n" + 
				"S.SeasonPassName, S.StartDate, S.EndDate, S.Cost,\n" + 
				"PP.ParkingFee, R.RefreshmentName, R.RefreshmentCost\n" + 
				"FROM Products AS P\n" + 
				"LEFT JOIN MovieTicket AS M ON P.ProductID = M.ProductID\n" + 
				"LEFT JOIN Address AS A ON M.AddressID = A. AddressID\n" + 
				"LEFT JOIN SeasonPass AS S ON P.ProductID = S.ProductID\n" + 
				"LEFT JOIN ParkingPass AS PP ON P.ProductID = PP.ProductID\n" + 
				"LEFT JOIN Refreshments AS R ON P.ProductID = R.ProductID;";
		
		//Generate connection with database
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					String productCode = rs.getString("ProductCode");
					String productType = rs.getString("ProductType");
					if(productType.equals("M")){
						DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
						DateTime dTime = new DateTime();
						
						dTime = DateTime.parse(rs.getString("MovieDate"), fmt);
						String movieName = rs.getString("MovieName");
						String street = rs.getString("Street");
						String city = rs.getString("City");
						String state = rs.getString("State");
						String zip = rs.getString("Zip");
						String country = rs.getString("Country");
						Address a = new Address(street,city,state,zip,country);
						String screenNo = rs.getString("ScreenNo");
						double pricePerUnit = rs.getDouble("PricePerUnit");
						
						MovieTicket m = new MovieTicket(productCode,productType,dTime,movieName,a,screenNo,pricePerUnit);
						pro.add(m);
					}else if(productType.equals("S")) {
						String name = rs.getString("SeasonPassName");
						DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
						DateTime startDate = DateTime.parse(rs.getString("StartDate")+" 00:00",fmt);
						DateTime endDate = DateTime.parse(rs.getString("EndDate")+" 00:00",fmt);
						double cost = rs.getDouble("Cost");
						
						SeasonPass s = new SeasonPass(productCode,productType,name,startDate,endDate,cost);
						pro.add(s);
					}else if(productType.equals("P")) {
						double cost = rs.getDouble("ParkingFee");
						ParkingPass pass = new ParkingPass(productCode,productType,cost);
						pro.add(pass);
					}else if (productType.equals("R")) {
						String name = rs.getString("RefreshmentName");
						double price = rs.getDouble("R.RefreshmentCost");
						Refreshment r = new Refreshment(productCode,productType,name,price);
						pro.add(r);
					}
				}
			}
			conn.close();
			ps.close();
			rs.close();
			return pro;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		
	}
	
	public ArrayList<Invoice> readDBInvoice(ArrayList<Person> person,ArrayList<Customer> customer,ArrayList<Products> products){
		//ArrayList to contain invoice object
		ArrayList<Invoice> invList = new ArrayList<Invoice>();
		
		//Query to obtain invoice from database
		String query1 = "SELECT I.InvoiceID,I.InvoiceCode, I.CustomerID, I.SalesPersonID, I.InvoiceDate\n" + 
				"FROM Invoice AS I;";
		//Generate connection with database
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query1);
			ResultSet rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					int invoiceID = rs.getInt("InvoiceID");
					String invoiceCode = rs.getString("InvoiceCode");
					int customerID = rs.getInt("CustomerID");
					Customer tempCustomer = null;
					for(Customer c:customer) {
						if(c.getCustomerID()==customerID) {
							tempCustomer = c;
						}
					}
					int personID = rs.getInt("SalesPersonID");
					Person tempPerson = null;
					for(Person p:person) {
						if(p.getPersonID()==personID) {
							tempPerson = p;
						}
					}
					
					DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
					DateTime invoiceDate = new DateTime();
					invoiceDate = DateTime.parse(rs.getString("InvoiceDate"),fmt);
					
					Invoice inv = new Invoice(invoiceID,invoiceCode,tempCustomer,tempPerson,invoiceDate);
					invList.add(inv);
				}
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Adding Product to the Invoice*/
		String query2 = "SELECT I.InvoiceID,IP.Quantity,IP.AttachPassCode,P.ProductCode\n" + 
				"FROM Invoice AS I\n" + 
				"JOIN InvoiceProducts AS IP ON I.InvoiceID = IP.InvoiceID\n" + 
				"JOIN Products AS P ON IP.ProductID = P.ProductID\n" + 
				"WHERE I.InvoiceID = ?;";
		
		Connection conn2 = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn2.prepareStatement(query2);
			for(Invoice inv:invList) {
				ArrayList<Products> productTemp = new ArrayList<Products>();
				ps.setInt(1, inv.getInvoiceID());
				ResultSet rs = ps.executeQuery();
				if(rs != null) {
					while(rs.next()) {
						for(Products pro:products) {
							// Check type of product then instantiate the object
							if(rs.getString("ProductCode").equals(pro.getProductCode())) {
								if(pro instanceof MovieTicket) {
									MovieTicket m = new MovieTicket((MovieTicket) pro);
									m.setUnit(rs.getInt("Quantity"));
									productTemp.add(m);
								}else if(pro instanceof SeasonPass) {
									SeasonPass season = new SeasonPass((SeasonPass) pro);
									season.setUnit(rs.getInt("Quantity"));
									productTemp.add(season);
								}else if(pro instanceof ParkingPass) {
									ParkingPass park = new ParkingPass((ParkingPass) pro);
									park.setUnit(rs.getInt("Quantity"));
									park.setProductCodeAttach(rs.getString("AttachPassCode"));
									productTemp.add(park);
								}else if(pro instanceof Refreshment) {
									Refreshment r = new Refreshment((Refreshment) pro);
									r.setUnit(rs.getInt("Quantity"));
									productTemp.add(r);
								}
							}
						}
					}
				}
				rs.close();
				inv.setProductList(productTemp);
			}
			ps.close();
			conn.close();
			return invList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}


		
		
		
		
	}
	
	
	

}
