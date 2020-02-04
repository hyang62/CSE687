package Calculator;

public class Calculator {
	TicketsInformation t1;
	TicketsInformation t2;
	
	Calculator(TicketsInformation t1, TicketsInformation t2){
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public double sumPrice(TicketsInformation t1, TicketsInformation t2) {
		double p1 = t1 == null ? 0 : t1.price;
		double p2 = t2 == null ? 0 : t2.price;
		return p1 + p2;
	}
}
