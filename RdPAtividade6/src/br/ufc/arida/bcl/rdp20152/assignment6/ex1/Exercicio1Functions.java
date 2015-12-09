package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import br.ufc.arida.bcl.rdp20152.assignment6.arquivos.FileHandler;

public class Exercicio1Functions {
	
	public static final String DERMATOLOGY_DATA = "data/dermatology.data";
	
	private RealMatrix dermatolgy_data;
	
	public Exercicio1Functions() {
		FileHandler fileHandler = new FileHandler(DERMATOLOGY_DATA, ",");
		dermatolgy_data = new Array2DRowRealMatrix(fileHandler.getMatriz());
	}

	public RealMatrix getDermatolgy_data() {
		return dermatolgy_data;
	}
	
	

}
