package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

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
	
	public RealMatrix formarMatrizT(RealVector labels) {
		int numeroDeAtributos = getDistinctLabels(labels).size();
		
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
	
	public List<Integer> gerarOrdemRandomica(int numeroDeElementos) {
		List<Integer> listaDePosicoes = new ArrayList<Integer>();
		for (int i = 0; i < numeroDeElementos; i++) {
			listaDePosicoes.add(i);
		}
		Collections.shuffle(listaDePosicoes);
		return listaDePosicoes;
	}
	
//	public RealVector vetorParaOrdemEspecifica(RealVector vetor, List<Integer> ordemDosElementos) {
//		RealVector r = new ArrayRealVector(vetor.getDimension());
//		int posicao = 0;
//		for (Integer ordem : ordemDosElementos) {
//			double elemento = vetor.getEntry(ordem);
//			r.setEntry(posicao, elemento);
//			posicao++;
//		}
//		return r;
//	}
	
	public RealMatrix matrizParaOrdemEspecifica(RealMatrix matrix, List<Integer> ordemDasLinhas) {
		RealMatrix R = new Array2DRowRealMatrix(matrix.getRowDimension(), matrix.getColumnDimension());
		int linha = 0;
		for (Integer ordem : ordemDasLinhas) {
			RealVector vi = matrix.getRowVector(ordem);
			R.setRowVector(linha, vi);
			linha++;
		}	
		return R;
	}
	
	public RealVector matrixToLabels(RealMatrix matrix) {
		RealVector r = new ArrayRealVector(matrix.getRowDimension());
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			RealVector vi = matrix.getRowVector(i);
			for (int j = 0; j < vi.getDimension(); j++) {
				if (vi.getEntry(j) == 1) {
					int classe = j + 1;
					r.setEntry(i, classe);
				}
			}
		}
		return r;
	}

	private List<Integer> getDistinctLabels(RealVector labels) {
		List<Integer> classes = new ArrayList<Integer>();
		for (int i = 0; i < labels.getDimension(); i++) {
			int label = (int)labels.getEntry(i);
			if (!classes.contains(label)) {
				classes.add(label);
			}
		}
		return classes;
	}
}
