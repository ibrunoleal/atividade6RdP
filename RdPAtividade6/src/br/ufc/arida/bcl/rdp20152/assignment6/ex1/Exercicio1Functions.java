package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import br.ufc.arida.bcl.rdp20152.assignment6.arquivos.MatrizFileHandler;

/**
 * Classe que contendo as funcoes auxiliares para execução do Exercício 1.
 * @author Bruno Leal (ibrunoleal@gmail.com)
 *
 */
public class Exercicio1Functions {
	
	/**
	 * Nome (ou path) do arquivo que contem os dados de entrada para o exercicio.
	 */
	public static final String DERMATOLOGY_DATA = "data/dermatology.data";
	
	/**
	 * Matriz contendo os dados de entrada do exercício.
	 */
	private RealMatrix dermatolgy_data;
	
	public Exercicio1Functions() {
		MatrizFileHandler fileHandler = new MatrizFileHandler(DERMATOLOGY_DATA, ",");
		dermatolgy_data = new Array2DRowRealMatrix(fileHandler.getMatriz());
	}

	/**
	 * Recupera os dados de entrada do Exercicio 1.
	 * @return
	 * 		Os dados de entrada do exercicio 1.
	 */
	public RealMatrix getDermatolgy_data() {
		return dermatolgy_data;
	}
	
	/**
	 * Converte o vetor de labels para o formato solicitado no exercício 1.4.
	 * @param labels
	 *		Vetor de labels a ser convertido.
	 * @return
	 * 		Uma matriz representante dos labels no formato pedido no exercício 1.4.
	 */
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
	
	/**
	 * Para cada coluna calcula o seu Mean e Standard Deviation. Em seguida divide cada elemento da coluna
	 * pelo respectivo mean e standard deviation.
	 * @param matrix
	 * 		Matriz a ser convertida.
	 * @return
	 * 		A matriz onde cada elemento foi dividido pelo respectivo mean e standard deviation da coluna.
	 */
	public RealMatrix matrixToZeroMeanAndVariance(RealMatrix matrix) {
		RealMatrix R = new Array2DRowRealMatrix(matrix.getRowDimension(), matrix.getColumnDimension());
		for (int j = 0; j < matrix.getColumnDimension(); j++) {
			Mean mean = new Mean();
			mean.setData(matrix.getColumnVector(j).toArray());
			double u = mean.evaluate();
			
			StandardDeviation desvioPadrao = new StandardDeviation();
			desvioPadrao.setData(matrix.getColumnVector(j).toArray());
			double dp = desvioPadrao.evaluate();
			
			RealVector colunaj =  matrix.getColumnVector(j).mapDivide(u).mapDivide(dp);
			R.setColumnVector(j, colunaj);
		}
		return R;
	}
	
	/**
	 * Gera uma sequencia aleatoria composta pelos elementos de 0 a (numero de elementos - 1) 
	 * @param numeroDeElementos
	 * 		Numero de numeros na sequencia aleatoria.
	 * @return
	 * 		Uma List contendo os elementos entre 0 e o (numero de elementos - 1) em ordem aleatória.
	 */
	public List<Integer> gerarOrdemRandomica(int numeroDeElementos) {
		List<Integer> listaDePosicoes = new ArrayList<Integer>();
		for (int i = 0; i < numeroDeElementos; i++) {
			listaDePosicoes.add(i);
		}
		Collections.shuffle(listaDePosicoes);
		return listaDePosicoes;
	}
	
	/**
	 * Ordena as linhas de uma matriz para a nova ordem informada.
	 * @param matrix
	 * 		Matriz cujas linhas serão ordenadas para a ordem dada.
	 * @param ordemDasLinhas
	 * 		Lista com a ordem para as linhas da matriz.
	 * 		Obs.: primeira linha da matriz é 0.
	 * 		A lista deve conter todos os números inteiros de 0 até o numero
	 * 		de linhas da matriz - 1 (em qualquer ordem).
	 * @return
	 */
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

	/**
	 * Recupera os diferentes labes de um dado vetor.
	 * @param labels
	 * 		Vetor de labels de um conjunto de dados.
	 * @return
	 * 		Uma lista contendo os diferentes labels ordenados de forma crescente.
	 */
	private List<Integer> getDistinctLabels(RealVector labels) {
		List<Integer> classes = new ArrayList<Integer>();
		for (int i = 0; i < labels.getDimension(); i++) {
			int label = (int)labels.getEntry(i);
			if (!classes.contains(label)) {
				classes.add(label);
			}
		}
		Collections.sort(classes);
		return classes;
	}
	
}
