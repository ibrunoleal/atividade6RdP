package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rdp20152.assignment6.arquivos.ArrfCreator;


public class Exercicio1 {

	public static void main(String[] args) {
		
		Exercicio1Functions f = new Exercicio1Functions();
		
		//RealMatrix M = f.getDermatolgy_data();
		
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
		RealVector labelsTesting = f.matrixToLabels(Tt);
		
		
		RealMatrix XLearningWeka = f.unirMatrixComLabels(Xl, labelsLearning);
		RealMatrix XTestingWeka = f.unirMatrixComLabels(Xt, labelsTesting);
		
		ArrfCreator arrfCreator = new ArrfCreator();
		arrfCreator.gerarArquivoArff(XLearningWeka, "xlearning");
		arrfCreator.gerarArquivoArff(XTestingWeka, "xtesting");
		
		/*
		 * Utilizando SVM		
		 */
		
		SVM svm = new SVM("data/xlearning.arff", "data/xtesting.arff");
		RealVector svmLabelsPreditos = svm.getLabelsPreditos();
		
		Perceptron perceptron = new Perceptron("data/xlearning.arff", "data/xtesting.arff");
		RealVector perceptronLabelsPreditos = perceptron.getLabelsPreditos();

		/*
		 * Exercicio 1.9
		 */
		System.out.println("\nSVM:");
		System.out.println(f.getClassificationAccuracy(svmLabelsPreditos, labelsTesting));
		System.out.println();
		System.out.println("\nPerceptron:");
		System.out.println(f.getClassificationAccuracy(perceptronLabelsPreditos, labelsTesting));
		
		int[] labelsL = new int[labelsLearning.getDimension()];
		for (int i = 0; i < labelsLearning.getDimension(); i++) {
			labelsL[i] = (int)labelsLearning.getEntry(i);
		}
		LDA lda = new LDA(Xl.getData(), labelsL, true);
		
		RealVector vteste = Xt.getRowVector(0);
		int predicao = lda.predict(vteste.toArray());
		System.out.println(predicao);
	}

}
