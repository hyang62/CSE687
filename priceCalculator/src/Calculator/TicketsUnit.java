package Calculator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class TicketsUnit {
	TicketsInformation t1;
	TicketsInformation t2;
	double priceSum;
	TicketsUnit(){};
	TicketsUnit(TicketsInformation t1, TicketsInformation t2, double priceSum){
		this.t1 = t1;
		this.t2 = t2;
		this.priceSum = priceSum;
	}
	
	@SuppressWarnings("unused")
	public boolean equals(TicketsUnit tu) {
		if(this == null || tu == null) {
			if(this == null && tu == null) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if(this.priceSum == tu.priceSum && ((this.t1 == tu.t1 && this.t2 == tu.t2) || (this.t1 == tu.t2 && this.t2 == tu.t1))) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
