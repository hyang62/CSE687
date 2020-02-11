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
}
