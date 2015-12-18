package br.ufc.arida.bcl.rdp20152.assignment6.ex2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rdp20152.assignment6.algoritmos.regressores.GPR;
import br.ufc.arida.bcl.rdp20152.assignment6.algoritmos.regressores.SVR;
import br.ufc.arida.bcl.rdp20152.assignment6.arquivos.ArrfCreatorToRegression;
import br.ufc.arida.bcl.rdp20152.assignment6.ex1.experimentos.Testador;

public class Exercicio2 {
	
	public static void main(String[] args) {
			
		Exercicio2Functions f = new Exercicio2Functions();
		
		
//		RealMatrix L = new Array2DRowRealMatrix(f.getDataLearning().getRowDimension(), f.getDataLearning().getColumnDimension() + 1);
//		for (int i = 0; i < f.getDataLearning().getRowDimension(); i++) {
//			RealVector vi = f.getDataLearning().getRowVector(i).append(f.getLabelslearning().getEntry(i));
//			L.setRowVector(i, vi);
//		}
//
//		
//		RealMatrix T = new Array2DRowRealMatrix(f.getDataTesting().getRowDimension(), f.getDataTesting().getColumnDimension() + 1);
//		for (int i = 0; i < f.getDataTesting().getRowDimension(); i++) {
//			RealVector vi = f.getDataTesting().getRowVector(i).append(f.getLabelsTesting().getEntry(i));
//			T.setRowVector(i, vi);
//		}
//
//		ArrfCreatorToRegression arrfCreatorL = new ArrfCreatorToRegression();
//		arrfCreatorL.gerarArquivoArff(L, Testador.PATH_ARQUIVO_DE_TREINAMENTO_WEKA);
//		
//		ArrfCreatorToRegression arrfCreatorT = new ArrfCreatorToRegression();
//		arrfCreatorT.gerarArquivoArff(T, Testador.PATH_ARQUIVO_DE_TESTE_WEKA);
				
//		SVR svr = new SVR(Testador.PATH_ARQUIVO_DE_TREINAMENTO_WEKA, Testador.PATH_ARQUIVO_DE_TESTE_WEKA);
//		RealVector labelsSVR = svr.getLabelsPreditos();
//		System.out.println(labelsSVR);
		
		GPR gpr = new GPR(Testador.PATH_ARQUIVO_DE_TREINAMENTO_WEKA, Testador.PATH_ARQUIVO_DE_TESTE_WEKA);
		RealVector labelsGPR = gpr.getLabelsPreditos();
		System.out.println(labelsGPR);
		
		
		
	}

}
