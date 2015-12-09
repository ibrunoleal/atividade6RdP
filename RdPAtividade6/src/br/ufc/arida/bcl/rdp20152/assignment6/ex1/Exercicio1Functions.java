package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

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
	
	public RealMatrix getMatrizT(RealVector labels, int numeroDeAtributos) {
		RealMatrix T = new Array2DRowRealMatrix(labels.getDimension(), numeroDeAtributos);
		for (int i = 0; i < labels.getDimension(); i++) {
			int label = (int)labels.getEntry(i);
			RealVector vtemp = new ArrayRealVector(numeroDeAtributos);
			for (int j = 0; j < numeroDeAtributos; j++) {
				if ( (label - 1) == j) {
					vtemp.setEntry(j, 1);
				} else {
					vtemp.setEntry(j, -1);
				}
			}
			T.setRowVector(i, vtemp);
		}
		return T;
	}

}
