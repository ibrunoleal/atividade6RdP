package br.ufc.arida.bcl.rdp20152.assignment6.ex1.experimentos;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rdp20152.assignment6.algoritmos.classificadores.LDA;
import br.ufc.arida.bcl.rdp20152.assignment6.algoritmos.classificadores.MOAPerceptron;
import br.ufc.arida.bcl.rdp20152.assignment6.algoritmos.classificadores.SVM;
import br.ufc.arida.bcl.rdp20152.assignment6.algoritmos.regressores.SVR;
import br.ufc.arida.bcl.rdp20152.assignment6.arquivos.ArrfCreatorToClassify;
import br.ufc.arida.bcl.rdp20152.assignment6.arquivos.ArrfCreatorToRegression;
import weka.gui.arffviewer.ArffTableCellRenderer;

public class Testador extends Thread {
	
	public static final int CLASSIFICADOR_SVM = 111;
	public static final int CLASSIFICADOR_PERCEPTRON = 112;
	public static final int CLASSIFICADOR_LDA = 113;
	
	public static final String PATH_ARQUIVO_DE_TREINAMENTO_WEKA = "data/learning.arff";
	
	public static final String PATH_ARQUIVO_DE_TESTE_WEKA = "data/testing.arff";
	
	private RealMatrix dadosDeTreinamento;
	
	private RealVector labelsDeTreinamento;
	
	private RealMatrix dadosDeTeste;
	
	private RealVector labelsDeTeste;
	
	private int algoritmoAExecutar;
	
	private Analisador analisador;
	
	public Testador(RealMatrix dadosDeTreinamento, RealVector labelsDeTreinamento, RealMatrix dadosDeTeste,
			RealVector labelsDeTeste, int algoritmoAExecutar, Analisador analisador) {
		super();
		this.dadosDeTreinamento = dadosDeTreinamento;
		this.labelsDeTreinamento = labelsDeTreinamento;
		this.dadosDeTeste = dadosDeTeste;
		this.labelsDeTeste = labelsDeTeste;
		this.algoritmoAExecutar = algoritmoAExecutar;
		this.analisador = analisador;
		
		criarArquivosParaWeka();
	}
	
	public void run() {
		ResultadoDeTeste resultado = null;

		switch (algoritmoAExecutar) {
		case (CLASSIFICADOR_SVM):
			resultado = executarSVM();
			break;
		case (CLASSIFICADOR_PERCEPTRON):
			resultado = executarPerceptron();
			break;
		case (CLASSIFICADOR_LDA):
			resultado = executarLDA();
			break;
		default:
			/* Nao faz nada */
			System.out.println("Erro: Testador -> executar() -> nao foi indicado um algoritmo valido.");
			break;
		}
		
		analisador.adicionarResultado(resultado);
		
	}
	

//	/**
//	 * Valor que indica qual o algoritmo escolhido.
//	 * @param
//	 * 		algoritmoEscolhido indica qual o classificador a ser utilizado.
//	 * 		Dica: utilizar as constantes globais da classe.
//	 */
//	public ResultadoDeTeste executar(int algoritmoEscolhido) {
//		ResultadoDeTeste resultado = null;
//
//		switch (algoritmoEscolhido) {
//		case (CLASSIFICADOR_SVM):
//			resultado = executarSVM();
//			break;
//		case (CLASSIFICADOR_PERCEPTRON):
//			resultado = executarPerceptron();
//			break;
//		case (CLASSIFICADOR_LDA):
//			resultado = executarLDA();
//			break;
//		default:
//			/* Nao faz nada */
//			System.out.println("Erro: Testador -> executar() -> nao foi indicado um algoritmo valido.");
//			break;
//		}
//
//		return resultado;
//	}
	

	/**
	 * Executa o classificador SVM com base nos dados dos atributos
	 * de treinamento e teste.
	 * @return
	 * 		uma instancia de ResultadoDeTeste contendo os valores resultantes
	 * 		da execucao do classificador.
	 */
	private ResultadoDeTeste executarSVM() {
		System.out.println("executando SVM.....  ");
		long inicio = System.currentTimeMillis();
		SVM svm = new SVM(PATH_ARQUIVO_DE_TREINAMENTO_WEKA, PATH_ARQUIVO_DE_TESTE_WEKA);
		RealVector svmLabelsPreditos = svm.getLabelsPreditos();
		
		ResultadoDeTeste resultado = getResultadoDeTeste(Testador.CLASSIFICADOR_SVM, svmLabelsPreditos);
		resultado.setMse(svm.getMSECalculado());
		long fim  = System.currentTimeMillis();
		System.out.println("... SVM execuçao terminada! Tempo = " + (fim - inicio) + "ms");
		return resultado;
	}
	
	/**
	 * Executa o classificador Perceptron com base nos dados dos atributos
	 * de treinamento e teste.
	 * @return
	 * 		uma instancia de ResultadoDeTeste contendo os valores resultantes
	 * 		da execucao do classificador.
	 */
	private ResultadoDeTeste executarPerceptron() {
		System.out.println("executando Perceptron.....  ");
		long inicio = System.currentTimeMillis();
		MOAPerceptron perceptron = new MOAPerceptron(PATH_ARQUIVO_DE_TREINAMENTO_WEKA, PATH_ARQUIVO_DE_TESTE_WEKA);
		RealVector perceptronLabelsPreditos = perceptron.getLabelsPreditos();
		
		ResultadoDeTeste resultado = getResultadoDeTeste(Testador.CLASSIFICADOR_PERCEPTRON, perceptronLabelsPreditos);
		resultado.setMse(perceptron.getMSECalculado());
		long fim  = System.currentTimeMillis();
		System.out.println("... Perceptron execuçao terminada!  Tempo = " + (fim - inicio) + "ms");
		return resultado;
	}
	
	/**
	 * Executa o classificador LDA com base nos dados dos atributos
	 * de treinamento e teste.
	 * @return
	 * 		uma instancia de ResultadoDeTeste contendo os valores resultantes
	 * 		da execucao do classificador.
	 */
	private ResultadoDeTeste executarLDA() {
		System.out.println("executando LDA.....  ");
		long inicio = System.currentTimeMillis();
		LDA lda = new LDA(dadosDeTreinamento.getData(), toIntArray(labelsDeTreinamento), true);
		
		/*
		 *	Monta o vetor de labels preditos para o LDA 
		 */
		RealVector ldaLabelsPreditos = new ArrayRealVector(dadosDeTeste.getRowDimension());
		for (int i = 0; i < dadosDeTeste.getRowDimension(); i++) {
			RealVector xi = dadosDeTeste.getRowVector(i);
			int labelPredito = lda.predict(xi.toArray());
			ldaLabelsPreditos.setEntry(i, labelPredito);
		}
		
		ResultadoDeTeste resultado = getResultadoDeTeste(Testador.CLASSIFICADOR_LDA, ldaLabelsPreditos);
		long fim  = System.currentTimeMillis();
		System.out.println("... LDA execuçao terminada! Tempo = " + (fim - inicio) + "ms");
		return resultado;
	}
	
	/**
	 * Calcula a quantidade de acertos e erros para os dados de testing e gera o resultado de teste
	 * @param labelsPreditos
	 * 		conjunto de labels preditos
	 * @return
	 * 		um resultado de teste
	 */
	private ResultadoDeTeste getResultadoDeTeste(int tipoDeClassificador, RealVector labelsPreditos) {
		int n = labelsDeTeste.getDimension();
		double contAcertos = 0;
		double contErros = 0;
		for (int i = 0; i < n; i++) {
			if (labelsDeTeste.getEntry(i) == labelsPreditos.getEntry(i)) {
				contAcertos++;
			} else {
				contErros++;
			}
		}
		
		ResultadoDeTeste resultado = new ResultadoDeTeste(tipoDeClassificador, contAcertos, contErros);
		return resultado;
	}
	
	/**
	 * Cria, com base nos dados, dois arquivos no formato ARFF do WEKA.
	 * Obs.: O caminho e o nome dos arquivos estao definidos nas constantes
	 * desta classe (Testador).
	 */
	private void criarArquivosParaWeka() {
		RealMatrix XLearningWeka = unirMatrixComLabels(dadosDeTreinamento, labelsDeTreinamento);
		RealMatrix XTestingWeka = unirMatrixComLabels(dadosDeTeste, labelsDeTeste);

		ArrfCreatorToClassify arrfCreator = new ArrfCreatorToClassify();
		arrfCreator.gerarArquivoArff(XLearningWeka, PATH_ARQUIVO_DE_TREINAMENTO_WEKA);
		arrfCreator.gerarArquivoArff(XTestingWeka, PATH_ARQUIVO_DE_TESTE_WEKA);
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
	private RealMatrix unirMatrixComLabels(RealMatrix matrix, RealVector labels) {
		RealMatrix R = new Array2DRowRealMatrix(matrix.getRowDimension(), matrix.getColumnDimension() + 1);
		for (int j = 0; j < matrix.getColumnDimension(); j++) {
			RealVector colunaj = matrix.getColumnVector(j);
			R.setColumnVector(j, colunaj);
		}
		R.setColumnVector(R.getColumnDimension() -1, labels);
		return R;
	}
	
	private int[] toIntArray(RealVector vetor) {
		int[] r = new int[vetor.getDimension()];
		for (int i = 0; i < vetor.getDimension(); i++) {
			r[i] = (int)vetor.getEntry(i);
		}
		return r;
	}
}
