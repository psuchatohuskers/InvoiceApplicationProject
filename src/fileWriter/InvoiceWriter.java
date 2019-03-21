package fileWriter;


import classes.Invoice;
import linkList.InvoiceList;
import linkList.InvoiceNode;

// The propose of this class is to print executive summary for all in invoices in invoice list. 
public class InvoiceWriter {

	public void WriteExecutiveSummary(InvoiceList invoice) {
		double finalResultSubtotal = 0;
		double finalResultFees = 0;
		double finalResultTaxes = 0;
		double finalResultDiscount = 0;
		double finalResultTotal = 0;
		System.out.println("=========================");
		System.out.println("Executive Summary Report");
		System.out.println("=========================");	
		System.out.printf("%-10s %-50s %-20s %-10s %-10s %-10s %-10s %-10s\n", "Invoice", "Customer", "Salesperson", "Subtotal", "Fees", "Taxes", "Discount", "Total");
	for(InvoiceNode invNode:invoice) {
		Invoice inv = invNode.getInv();
		System.out.printf("%-10s %-50s %-20s %-10.2f %-10.2f %-10.2f %-10.2f %-10.2f \n", inv.getInvoiceCode(), inv.fullNameType(), inv.getSalesPerson().getName(), inv.getSumSubtotal(), inv.getFee(), inv.getSumTax(), inv.getDiscount(), inv.getSumTotalCost());
		
		finalResultSubtotal += inv.getSumSubtotal();
		finalResultFees += inv.getSumTax();
		finalResultTaxes += inv.getSumTax();
		finalResultDiscount += inv.getDiscount();
		finalResultTotal += inv.getSumTotalCost();
	}
		System.out.println("========================================================================================================================================");
		System.out.printf("%-10s %-50s %-20s %-10.2f %-10.2f %-10.2f %-10.2f %-10.2f \n\n\n\n", "TOTALS", "", "", finalResultSubtotal, finalResultFees, finalResultTaxes, finalResultDiscount, finalResultTotal);
	} 
	
}
