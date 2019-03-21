package linkList;

import classes.Invoice;

//This class pack the object invoice into the node for link list
public class InvoiceNode {
	
	private InvoiceNode next;
	private Invoice inv;
	
	public InvoiceNode(Invoice inv) {
		super();
		this.inv = inv;
		this.next = null;
	}

	public InvoiceNode getNext() {
		return next;
	}
	
	// this method is use to set the next node for the link list
	public void setNext(InvoiceNode next) {
		this.next = next;
	}

	public Invoice getInv() {
		return inv;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inv == null) ? 0 : inv.hashCode());
		result = prime * result + ((next == null) ? 0 : next.hashCode());
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
		InvoiceNode other = (InvoiceNode) obj;
		if (inv == null) {
			if (other.inv != null)
				return false;
		} else if (!inv.equals(other.inv))
			return false;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		return true;
	}
	
	

}
