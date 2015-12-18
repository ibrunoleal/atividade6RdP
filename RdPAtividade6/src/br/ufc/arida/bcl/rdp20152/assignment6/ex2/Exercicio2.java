package br.ufc.arida.bcl.rdp20152.assignment6.ex2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rdp20152.assignment6.algoritmos.regressores.GPR;
import br.ufc.arida.bcl.rdp20152.assignment6.algoritmos.regressores.SVR;
import br.ufc.arida.bcl.rdp20152.assignment6.arquivos.ArrfCreatorToRegression;
import br.ufc.arida.bcl.rdp20152.assignment6.ex1.experimentos.Testador;
import br.ufc.arida.bcl.rdp20152.assignment6.graficos.GraficoDePontos2D;
import br.ufc.arida.bcl.rdp20152.assignment6.graficos.Ponto2D;

public class Exercicio2 {
	
	public static void main(String[] args) {
			
		Exercicio2Functions f = new Exercicio2Functions();
		
		/*
		 * Une os dados de atributos com os labels em uma unica matriz
		 */
		RealMatrix L = f.unirMatrixComLabels(f.getDataLearning(), f.getLabelslearning());
		RealMatrix T = f.unirMatrixComLabels(f.getDataTesting(), f.getLabelsTesting());

		/*
		 * Cria o arquivo da fase de treinamento para a API do weka.
		 */
		ArrfCreatorToRegression arrfCreatorL = new ArrfCreatorToRegression();
		arrfCreatorL.gerarArquivoArff(L, Testador.PATH_ARQUIVO_DE_TREINAMENTO_WEKA);
		
		/*
		 * Cria o arquivo da fase de teste para a API do weka.
		 */
		ArrfCreatorToRegression arrfCreatorT = new ArrfCreatorToRegression();
		arrfCreatorT.gerarArquivoArff(T, Testador.PATH_ARQUIVO_DE_TESTE_WEKA);
				
		
		/*
		 * SVR
		 */
		SVR svr = new SVR(Testador.PATH_ARQUIVO_DE_TREINAMENTO_WEKA, Testador.PATH_ARQUIVO_DE_TESTE_WEKA);
		System.out.println("SVR measured outputs:");
		System.out.println(f.getLabelsTesting());
		RealVector labelsSVR = svr.getLabelsPreditos();
		System.out.println("SVR predicted outputs:");
		System.out.println(labelsSVR);
		
			/*
			 * SVR - Grafico de Medidos x Preditos 
			 */
		GraficoDePontos2D gSVR = new GraficoDePontos2D("SVR");
		gSVR.adicionarPontos2D("SVR", f.getLabelsTesting().toArray(), labelsSVR.toArray());
		gSVR.exibirGrafico();
		
			/*
			 * SVR - MSE
			 */
		System.out.println("\nSVR MSE: " + f.getMSE(labelsSVR, f.getLabelsTesting()));
		
		List<Ponto2D> pontosSVRPreditos = new ArrayList<Ponto2D>();
		List<Ponto2D> pontosSVRMedidos = new ArrayList<Ponto2D>();
		double xi = 0;
		for (int i = 0; i < f.getLabelsTesting().getDimension(); i++) {
			xi += 1;
			Ponto2D pi = new Ponto2D(xi, labelsSVR.getEntry(i));
			pontosSVRPreditos.add(pi);
			Ponto2D pm = new Ponto2D(xi, f.getLabelsTesting().getEntry(i));
			pontosSVRMedidos.add(pm);
		}
			/*
			 * SVR - Grafico de Ordem da Instancia x Valor Predito
			 */
		GraficoDePontos2D gSVRPredito = new GraficoDePontos2D("SVR Predito");
		gSVRPredito.adicionarLinha("SVR Predito", pontosSVRPreditos);
		gSVRPredito.exibirGrafico();
		
			/*
			 * Grafico de Ordem da Instancia x Valor Predito (independente de regressor)
			 */
		GraficoDePontos2D gSVRMedido = new GraficoDePontos2D("SVR Label");
		gSVRMedido.adicionarLinha("SVR Medido", pontosSVRMedidos);
		gSVRMedido.exibirGrafico();
		
		System.out.println("\n-----------------------------------------------\n");
		
		/*
		 * GPR
		 */
		GPR gpr = new GPR(Testador.PATH_ARQUIVO_DE_TREINAMENTO_WEKA, Testador.PATH_ARQUIVO_DE_TESTE_WEKA);
		System.out.println("GPR measured outputs:");
		System.out.println(f.getLabelsTesting());
		RealVector labelsGPR = gpr.getLabelsPreditos();
		System.out.println("GPR predicted outputs:");
		System.out.println(labelsGPR);
		
			/*
			 * Grafico de Medidos x Preditos
			 */
		GraficoDePontos2D gGPR = new GraficoDePontos2D("GPR");
		gGPR.adicionarPontos2D("SVR", f.getLabelsTesting().toArray(), labelsGPR.toArray());
		gGPR.exibirGrafico();
		
			/*
			 * GPR - MSE
			 */
		System.out.println("\nGPR MSE: " + f.getMSE(labelsGPR, f.getLabelsTesting()));
		
		List<Ponto2D> pontosGPRPreditos = new ArrayList<Ponto2D>();
		double xii = 0;
		for (int i = 0; i < f.getLabelsTesting().getDimension(); i++) {
			xii += 1;
			Ponto2D pi = new Ponto2D(xii, labelsGPR.getEntry(i));
			pontosGPRPreditos.add(pi);
		}
		/*
		 * GPR - Grafico de Ordem da Instancia x Valor Predito
		 */
	GraficoDePontos2D gGPRPredito = new GraficoDePontos2D("GPR Predito");
	gGPRPredito.adicionarLinha("GPR Predito", pontosGPRPreditos);
	gGPRPredito.exibirGrafico();
		
	}

}
