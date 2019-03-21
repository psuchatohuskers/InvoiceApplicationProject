package linkList;

import java.util.Iterator;

import classes.Invoice;
import comparator.CompareTotalCost;

public class InvoiceList implements Iterable<InvoiceNode>   {
	
	private InvoiceNode start;
	private InvoiceNode end;
	private int size;
	
	
	public int getSize() {
		return size;
	}
	
	public void clear() {
    	this.start.setNext(null);
    	this.start = null;
    	this.end = null;
    	this.size = 0;
    }
	
	// This method add invoice to the link list. It order by total cost of 
	public void addInvoice(Invoice inv) {
		InvoiceNode invNode = new InvoiceNode(inv);
		CompareTotalCost c = new CompareTotalCost();
		// This handle the case here there is no object in the list
		if(this.start == null) {
			this.start = invNode;
			this.end = this.start;
			this.size++;
			return;
			

		}
		
		// This handle the case where there is only one object in the list
		if(this.size == 1) {
			int compare = c.compare(invNode.getInv(), this.start.getInv());
			if(compare > 0) {
				invNode.setNext(this.start);
				this.start = invNode;
				this.end = invNode.getNext();
				this.size++;
				return;
			}else {
				this.start.setNext(invNode);
				this.end = invNode;
				this.size++;
				return;
			}
		}
		// This handle the case where there is more than one element in the list
		if(this.size > 1) {
			InvoiceNode currentNode = this.start.getNext();
			InvoiceNode previousNode = this.start;
			// Handle the corner case where invNode has the highest total cost
			if(c.compare(invNode.getInv(), this.start.getInv()) > 0) {
			invNode.setNext(this.start);
			this.start = invNode;
			this.size++;
			return;
			}
			
			while(currentNode.getNext() != null) {
				if(c.compare(invNode.getInv(), currentNode.getInv()) < 0) {
					previousNode = currentNode;
					currentNode = currentNode.getNext();
				}else {
					previousNode.setNext(invNode);
					invNode.setNext(currentNode);
					this.size++;
					return;
				}
			}
			
			// Handle the corner case at the end of the list.
			if(c.compare(invNode.getInv(), this.end.getInv()) > 0) {
				previousNode.setNext(invNode);
				invNode.setNext(this.end);
				this.size ++;
				return;
			}else {
				InvoiceNode nodeBeforeLast = this.end;
				previousNode.setNext(nodeBeforeLast);
				nodeBeforeLast.setNext(invNode);
				this.end = invNode;
				this.size++;
				return;
			}

		}

	}
	
	//This method print detail report for each invoice in this link list
	public void printDetailReport() {
		InvoiceNode currentNode = this.start;
		for(int i = 1;i<=this.size;i++) {
			currentNode.getInv().printDetailInvoice();
			currentNode = currentNode.getNext();
		}
	}
	
	//This method remove the node form the list.
	public boolean remove(int position) {
		if(position > this.size) {
    		throw new IndexOutOfBoundsException();
    	}
		if(position == 1) {
			this.start = this.start.getNext();
			this.size --;
			return true;
		}else if(position == this.size) {
			InvoiceNode currentNode = this.start;
			for(int i = 1; i < this.size - 1; i++) {
				if(currentNode.getNext() == null) {
					return false;
				}
				currentNode = currentNode.getNext();
			}
			currentNode.setNext(null);
			this.end = currentNode;
			this.size --;
			return true;
		}else{
			InvoiceNode previousNode = this.start;
			for(int i = 1; i < position-1;i++) {
				previousNode = previousNode.getNext();
			}
			InvoiceNode nodeToBeRemove = previousNode.getNext();
			InvoiceNode nextNode = nodeToBeRemove.getNext();
			previousNode.setNext(nextNode);
			this.size --;
			return true;
		}
	}
	
	//This method return a node correspond to the position
	private InvoiceNode getInvoiceNode(int position) {
		if(position == 1) {
			return this.start;
		}else if(position == this.size) {
			return this.end;
		}else {
			InvoiceNode previousNode = this.start;
			for(int i = 1; i < position-1; i++) {
				previousNode = previousNode.getNext();
			}
			InvoiceNode nodeToReturn = previousNode.getNext();
			return nodeToReturn;
		}
	}
	
	//This method return invoice for correspond position
	public Invoice getInvoice(int position) {
		return this.getInvoiceNode(position).getInv();
	}
	
	//This method print out all invoices, for debugging only
	public void printInvoice() {
		for(int i = 1;i<=this.size;i++) {
			System.out.println(this.getInvoice(i).toString());
		}
	}
	
	//This method print single detial report
	public void printIndividualReport(int position) {
		this.getInvoice(position).printDetailInvoice();
	}
		

	@Override
	public Iterator<InvoiceNode> iterator() {
		return new LinkListIterator(this.start);
		
	}
	

}

// This is iterator method which allows InvoiceList to be iterable.
class LinkListIterator implements Iterator<InvoiceNode> {
	private InvoiceNode currentNode = null;
	public LinkListIterator(InvoiceNode invNode) {
		this.currentNode = invNode;
	}

	@Override
	public boolean hasNext() {
		if(currentNode == null ) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public InvoiceNode next() {
		InvoiceNode previousNode = currentNode;
		this.currentNode = this.currentNode.getNext();
		return previousNode;
	}
	
}



