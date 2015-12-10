package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;


public class Exercicio1 {

	public static void main(String[] args) {
		
		Exercicio1Functions f = new Exercicio1Functions();
		
		/*
		 * Exercicio 1.2
		 */
		RealMatrix X = f.getDermatolgy_data().getSubMatrix(0, f.getDermatolgy_data().getRowDimension() -1, 0, f.getDermatolgy_data().getColumnDimension() - 2);
		
		/*
		 * Exercicio 1.3
		 */
		RealVector l = f.getDermatolgy_data().getColumnVector(f.getDermatolgy_data().getColumnDimension() -1);
		
		/*
		 * Exercicio 1.4
		 */
		RealMatrix T = f.formarMatrizT(l);
		
		/*
		 * Exercicio 1.5
		 */
		RealMatrix X1 = f.matrixToZeroMeanAndVariance(X);
		
		/*
		 * Exercicio 1.6
		 */
		List<Integer> ordemAleatoria = f.gerarOrdemRandomica(X.getRowDimension());
		RealMatrix X2 = f.matrizParaOrdemEspecifica(X1, ordemAleatoria);
		RealMatrix T2 = f.matrizParaOrdemEspecifica(T, ordemAleatoria);
		
		/*
		 * Exercicio 1.7
		 */
		int numeroDeLinhasLearning = (int)(X2.getRowDimension() * 80 / 100);
		RealMatrix Xl = X2.getSubMatrix(0, numeroDeLinhasLearning - 1, 0, X2.getColumnDimension() - 1);
		RealMatrix Xt = X2.getSubMatrix(numeroDeLinhasLearning, X2.getRowDimension() -1, 0, X2.getColumnDimension() - 1);
		RealMatrix Tl = T2.getSubMatrix(0, numeroDeLinhasLearning - 1, 0, T2.getColumnDimension() -1);
		RealMatrix Tt = T2.getSubMatrix(numeroDeLinhasLearning, T2.getRowDimension() -1, 0, T2.getColumnDimension() -1);
	
		/*
		 * Exercicio 1.8
		 */
		RealVector labelsLearning = f.matrixToLabels(Tl);
		SVM svm = new SVM(Xl, labelsLearning);
		
		List<Integer> labelsPreditos = new ArrayList<Integer>();
		for (int i = 0; i < Xt.getRowDimension(); i++) {
			RealVector elemento = Xt.getRowVector(i);
			int classe = (int)svm.classificar(elemento);
			labelsPreditos.add(classe);
		}
		
		RealVector labelsTesting = f.matrixToLabels(Tt);
		System.out.println(labelsPreditos);
		System.out.println(labelsTesting);
		
	}

}
