package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.moment.VectorialMean;

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
	
	public RealMatrix formarMatrizT(RealVector labels, int numeroDeAtributos) {
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
	
	public RealMatrix matrixToZeroMeanAndVariance(RealMatrix matrix) {
		RealMatrix R = new Array2DRowRealMatrix(matrix.getRowDimension(), matrix.getColumnDimension());
		for (int j = 0; j < matrix.getColumnDimension(); j++) {
			Mean mean = new Mean();
			mean.setData(matrix.getColumnVector(j).toArray());
			double u = mean.evaluate();
			
			Variance variance = new Variance();
			variance.setData(matrix.getColumnVector(j).toArray());
			double v = variance.evaluate();
			
			RealVector colunaj =  matrix.getColumnVector(j).mapDivide(u).mapDivide(v);
			R.setColumnVector(j, colunaj);
		}
		return R;
	}
	
	public void matrixToRandomMatrixRows(RealMatrix matrix) {
		//RealMatrix R = new Array2DRowRealMatrix(matrix.getRowDimension(), matrix.getColumnDimension());
		
		List<Integer> listaDePosicoes = new ArrayList<Integer>();
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			listaDePosicoes.add(i);
		}
		Collections.shuffle(listaDePosicoes);
		System.out.println(listaDePosicoes);
		
		//return R;
	}

}
