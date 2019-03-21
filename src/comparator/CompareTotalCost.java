package comparator;

import java.util.Comparator;

import classes.Invoice;

//This class is a comparator which compare two invoice base on total cost of the object.
public class CompareTotalCost implements Comparator<Invoice> {

	@Override
	public int compare(Invoice o1, Invoice o2) {
		double tc1 = o1.getSumTotalCost();
		double tc2 = o2.getSumTotalCost();
		if(tc1 == tc2) {
			return 0;
		}else if(tc1>tc2){
			return 1;
		}else {
			return -1;
		}
	}

}
