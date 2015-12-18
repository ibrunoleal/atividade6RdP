package br.ufc.arida.bcl.rdp20152.assignment6.ex2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rdp20152.assignment6.arquivos.MatrizFileHandler;

public class Exercicio2Functions {

	public static final String DATA_INPUT_LEARNING = "data/data-input-learning.txt";
	
	public static final String DATA_OUTPUT_LEARNING = "data/data-output-learning.txt";
	
	public static final String DATA_INPUT_TESTING = "data/data-input-testing.txt";
	
	public static final String DATA_OUTPUT_TESTING = "data/data-output-testing.txt";
	
	private final RealMatrix dataLearning;
	
	private final RealVector labelslearning;
	
	private final RealMatrix dataTesting;

	private final RealVector labelsTesting;
	
	public Exercicio2Functions() {
		MatrizFileHandler fileHandler = new MatrizFileHandler(DATA_INPUT_LEARNING, " ");
		dataLearning = new Array2DRowRealMatrix(fileHandler.getMatriz());
		
		fileHandler = new MatrizFileHandler(DATA_OUTPUT_LEARNING, " ");
		labelslearning = new ArrayRealVector(fileHandler.getColumnAsArray(0));
		
		fileHandler = new MatrizFileHandler(DATA_INPUT_TESTING, " ");
		dataTesting = new Array2DRowRealMatrix(fileHandler.getMatriz());
		
		fileHandler = new MatrizFileHandler(DATA_OUTPUT_TESTING, " ");
		labelsTesting = new ArrayRealVector(fileHandler.getColumnAsArray(0));
	}

	public RealMatrix getDataLearning() {
		return dataLearning;
	}

	public RealVector getLabelslearning() {
		return labelslearning;
	}

	public RealMatrix getDataTesting() {
		return dataTesting;
	}

	public RealVector getLabelsTesting() {
		return labelsTesting;
	}
	
	public double getMSE(RealVector yPreditos, RealVector yOutput) {
		int n = yPreditos.getDimension();
		double sum = 0.0;
		for (int i = 0; i < n; i++) {
			sum += Math.pow(yPreditos.getEntry(i) - yOutput.getEntry(i), 2);
		}
		return sum / n;
	}
	
	/**
	 * 
	 * @param matrix
	 *		cada linha da matriz representa uma instancia de dado.
	 * @param labels
	 * 		cada elemento do vetor representa a classe(label) da linah correspondente da matriz.
	 * @return
	 * 		a matriz de entrada com uma coluna adicional com os valores de labels.
	 */
	public RealMatrix unirMatrixComLabels(RealMatrix matrix, RealVector labels) {
		RealMatrix R = new Array2DRowRealMatrix(matrix.getRowDimension(), matrix.getColumnDimension() + 1);
		for (int j = 0; j < matrix.getColumnDimension(); j++) {
			RealVector colunaj = matrix.getColumnVector(j);
			R.setColumnVector(j, colunaj);
		}
		R.setColumnVector(R.getColumnDimension() -1, labels);
		return R;
	}
}
