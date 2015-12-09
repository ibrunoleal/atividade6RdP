package br.ufc.arida.bcl.rdp20152.assignment6.graficos;

public class Ponto2D {

	private double x;
	
	private double y;
	
	public Ponto2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public String toStringCSV() {
		return x + "," + y;
	}
	
}
