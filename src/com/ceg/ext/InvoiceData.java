package com.ceg.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sql.DatabaseInfo;

/*
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 15 methods in total, add more if required.
 * Donot change any method signatures or the package name.
 * 
 */

public class InvoiceData {

	/**
	 * 1. Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		
		String query1 = "DELETE FROM Email;";
		String query2 = "DELETE FROM InvoiceProducts;";
		String query3 = "DELETE FROM Invoice;";
		String query4 = "DELETE FROM Customers;";
		String query5 = "DELETE FROM Persons;";
		
		Connection conn = DatabaseInfo.getConnection();
		

		
		try {
			PreparedStatement ps = conn.prepareStatement(query1);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = conn.prepareStatement(query2);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = conn.prepareStatement(query3);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = conn.prepareStatement(query4);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = conn.prepareStatement(query5);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {
		int addressID = 0;
		
		String query1 = "INSERT INTO Address (Street,City,State,Zip,Country) VALUES (?,?,?,?,?);";
		
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				addressID = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		String query3 = "INSERT INTO Persons(PersonCode,FirstName,LastName,AddressID)\r\n" + 
				"	VALUES(?,?,?,?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query3);
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setInt(4, addressID);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		}

		


	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		int personID = 0;
		
		String query1 = "SELECT PersonID FROM Persons WHERE PersonCode = ?";
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query1);
			ps.setString(1, personCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				personID = rs.getInt("PersonID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "INSERT INTO Email (PersonID,Email) \r\n" + 
				"	VALUES(?,?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query2);
			ps.setInt(1, personID);
			ps.setString(2, email);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() {
		String query1 = "DELETE FROM InvoiceProducts;";
		String query2 = "DELETE FROM Invoice;";
		String query3 = "DELETE FROM Customers;";
		
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			conn.setAutoCommit(false);
			stmt.addBatch(query1);
			stmt.addBatch(query2);
			stmt.addBatch(query3);
			stmt.executeBatch();
			conn.commit();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
	}
	

	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,String name, String street, String city, String state, String zip, String country) {
		int addressID = 0;
		int personID = 0;
		
		String query1 = "INSERT INTO Address (Street,City,State,Zip,Country) VALUES (?,?,?,?,?);";
		
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				addressID = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		String query2 = "SELECT PersonID FROM Persons WHERE PersonCode = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query2);
			ps.setString(1, primaryContactPersonCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				personID = rs.getInt("PersonID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query3 = "INSERT INTO Customers (CustomerCode,CustomerType,PrimaryContactID,CustomerName,AddressID)\r\n" + 
				"VALUES(?,?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(query3);
			ps.setString(1, customerCode);
			ps.setString(2, customerType);
			ps.setInt(3, personID);
			ps.setString(4, name);
			ps.setInt(5,addressID);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 5. Removes all product records from the database
	 */
	public static void removeAllProducts() {
		
		String query = "DELETE FROM InvoiceProducts;";
		String query1 = "DELETE FROM MovieTicket;";
		String query2 = "DELETE FROM SeasonPass;";
		String query3 = "DELETE FROM ParkingPass;";
		String query4 = "DELETE FROM Refreshments;";
		String query5 = "DELETE FROM Products;";
		
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			conn.setAutoCommit(false);
			stmt.addBatch(query);
			stmt.addBatch(query1);
			stmt.addBatch(query2);
			stmt.addBatch(query3);
			stmt.addBatch(query4);
			stmt.addBatch(query5);
			stmt.executeBatch();
			conn.commit();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 */
	public static void addMovieTicket(String productCode, String dateTime, String movieName, String street, String city,String state, String zip, String country, String screenNo, double pricePerUnit) {
		int productID = 0;
		int addressID = 0;
		
		String query1 = "INSERT INTO Products(ProductCode,ProductType)\r\n" + 
				"	VALUES (?,?);";
		
		Connection conn = DatabaseInfo.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, productCode);
			ps.setString(2, "M");
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				productID = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "INSERT INTO Address (Street,City,State,Zip,Country) VALUES (?,?,?,?,?);";
		
		
		try {
			PreparedStatement ps = conn.prepareStatement(query2,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				addressID = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query3 = "INSERT INTO MovieTicket(ProductID,MovieDate,MovieName,AddressID,ScreenNo,PricePerUnit)\r\n" + 
				"	VALUES(?,?,?,?,?,?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query3);
			ps.setInt(1, productID);
			ps.setString(2, dateTime);
			ps.setString(3, movieName);
			ps.setInt(4, addressID);
			ps.setString(5, screenNo);
			ps.setDouble(6, pricePerUnit);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 */
	public static void addSeasonPass(String productCode, String name, String seasonStartDate, String seasonEndDate,	double cost) {
		int productID = 0;
		
		String query1 = "INSERT INTO Products(ProductCode,ProductType)\r\n" + 
				"	VALUES (?,?);";
		
		Connection conn = DatabaseInfo.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, productCode);
			ps.setString(2, "S");
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				productID = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "INSERT INTO SeasonPass(ProductID,SeasonPassName,StartDate,EndDate,Cost)\r\n" + 
				"	VALUE(?,?,?,?,?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query2);
			ps.setInt(1, productID);
			ps.setString(2, name);
			ps.setString(3, seasonStartDate);
			ps.setString(4, seasonEndDate);
			ps.setDouble(5, cost);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 */
	public static void addParkingPass(String productCode, double parkingFee) {
		int productID = 0;
		
		String query1 = "INSERT INTO Products(ProductCode,ProductType)\r\n" + 
				"	VALUES (?,?);";
		
		Connection conn = DatabaseInfo.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, productCode);
			ps.setString(2, "P");
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				productID = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "INSERT INTO ParkingPass(ProductID,ParkingFee)\r\n" + 
				"	VALUE(?,?);";
		
			try {
				PreparedStatement ps = conn.prepareStatement(query2);
				ps.setInt(1, productID);
				ps.setDouble(2, parkingFee);
				ps.executeUpdate();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * 9. Adds a refreshment record to the database with the provided data.
	 */
	public static void addRefreshment(String productCode, String name, double cost) {
		int productID = 0;
		
		String query1 = "INSERT INTO Products(ProductCode,ProductType)\r\n" + 
				"	VALUES (?,?);";
		
		Connection conn = DatabaseInfo.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, productCode);
			ps.setString(2, "R");
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				productID = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "INSERT INTO Refreshments(ProductID,RefreshmentName,RefreshmentCost)\r\n" + 
				"	VALUE(?,?,?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query2);
			ps.setInt(1, productID);
			ps.setString(2, name);
			ps.setDouble(3, cost);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 10. Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
		String query1 = "DELETE FROM InvoiceProducts;";
		String query2 = "DELETE FROM Invoice;";
		
		Connection conn = DatabaseInfo.getConnection();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
			stmt.addBatch(query1);
			stmt.addBatch(query2);
			stmt.executeBatch();
			conn.commit();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate) {
		int customerID = 0;
		int personID = 0;
		
		String query1 = "SELECT PersonID FROM Persons WHERE PersonCode = ?";
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query1);
			ps.setString(1, salesPersonCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				personID = rs.getInt("PersonID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "SELECT CustomerID FROM Customers WHERE CustomerCode = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query2);
			ps.setString(1, customerCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				customerID = rs.getInt("CustomerID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query3 = "INSERT INTO Invoice(InvoiceCode,CustomerID,SalesPersonID,InvoiceDate)\r\n" + 
				"	VALUE(?,?,?,?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query3);
			ps.setString(1,invoiceCode);
			ps.setInt(2, customerID);
			ps.setInt(3, personID);
			ps.setString(4, invoiceDate);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 12. Adds a particular movieticket (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of units
	 */

	public static void addMovieTicketToInvoice(String invoiceCode, String productCode, int quantity) {
		int invoiceID = 0;
		int productID = 0;
		
		String query1 = "SELECT InvoiceID FROM Invoice WHERE InvoiceCode LIKE ?";
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query1);
			ps.setString(1, invoiceCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "SELECT ProductID FROM Products WHERE ProductCode LIKE ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query2);
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				productID = rs.getInt("ProductID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query3 = "INSERT INTO InvoiceProducts(InvoiceID,ProductID,Quantity)\r\n" + 
				"	VALUE(?,?,?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query3);
			ps.setInt(1, invoiceID);
			ps.setInt(2, productID);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*
	 * 13. Adds a particular seasonpass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given begin/end dates
	 */
	public static void addSeasonPassToInvoice(String invoiceCode, String productCode, int quantity) {
		int invoiceID = 0;
		int productID = 0;
		
		String query1 = "SELECT InvoiceID FROM Invoice WHERE InvoiceCode = ?";
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query1);
			ps.setString(1, invoiceCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "SELECT ProductID FROM Products WHERE ProductCode = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query2);
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				productID = rs.getInt("ProductID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query3 = "INSERT INTO InvoiceProducts(InvoiceID,ProductID,Quantity)\r\n" + 
				"	VALUE(?,?,?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query3);
			ps.setInt(1, invoiceID);
			ps.setInt(2, productID);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

     /**
     * 14. Adds a particular ParkingPass (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     * NOTE: ticketCode may be null
     */
    public static void addParkingPassToInvoice(String invoiceCode, String productCode, int quantity, String ticketCode) {
    	int invoiceID = 0;
		int productID = 0;
		
		String query1 = "SELECT InvoiceID FROM Invoice WHERE InvoiceCode = ?";
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query1);
			ps.setString(1, invoiceCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "SELECT ProductID FROM Products WHERE ProductCode = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query2);
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				productID = rs.getInt("ProductID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(ticketCode == null) {
			String query3 = "INSERT INTO InvoiceProducts(InvoiceID,ProductID,Quantity)\r\n" + 
					"	VALUE(?,?,?);";
			
			try {
				PreparedStatement ps = conn.prepareStatement(query3);
				ps.setInt(1, invoiceID);
				ps.setInt(2, productID);
				ps.setInt(3, quantity);
				ps.executeUpdate();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			String query3 = "INSERT INTO InvoiceProducts(InvoiceID,ProductID,Quantity,AttachPassCode)\r\n" + 
					"	VALUE(?,?,?,?);";
			
			try {
				PreparedStatement ps = conn.prepareStatement(query3);
				ps.setInt(1, invoiceID);
				ps.setInt(2, productID);
				ps.setInt(3, quantity);
				ps.setString(4, ticketCode);
				ps.executeUpdate();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
    }
	
    /**
     * 15. Adds a particular refreshment (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity. 
     */
    public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {
    	int invoiceID = 0;
		int productID = 0;
		
		String query1 = "SELECT InvoiceID FROM Invoice WHERE InvoiceCode = ?";
		Connection conn = DatabaseInfo.getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query1);
			ps.setString(1, invoiceCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "SELECT ProductID FROM Products WHERE ProductCode = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query2);
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				productID = rs.getInt("ProductID");
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query3 = "INSERT INTO InvoiceProducts(InvoiceID,ProductID,Quantity)\r\n" + 
				"	VALUE(?,?,?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query3);
			ps.setInt(1, invoiceID);
			ps.setInt(2, productID);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
