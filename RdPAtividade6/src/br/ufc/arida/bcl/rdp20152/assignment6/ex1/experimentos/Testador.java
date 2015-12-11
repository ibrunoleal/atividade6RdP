package br.ufc.arida.bcl.rdp20152.assignment6.ex1.experimentos;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rdp20152.assignment6.arquivos.ArrfCreator;
import br.ufc.arida.bcl.rdp20152.assignment6.ex1.classificadores.LDA;
import br.ufc.arida.bcl.rdp20152.assignment6.ex1.classificadores.Perceptron;
import br.ufc.arida.bcl.rdp20152.assignment6.ex1.classificadores.SVM;

public class Testador {
	
	public static final int CLASSIFICADOR_SVM = 1;
	public static final int CLASSIFICADOR_PERCEPTRON = 2;
	public static final int CLASSIFICADOR_LDA = 3;
	
	public static final String PATH_ARQUIVO_DE_TREINAMENTO_WEKA = "data/learning.arff";
	
	public static final String PATH_ARQUIVO_DE_TESTE_WEKA = "data/testing.arff";
	
	private RealMatrix dadosDeTreinamento;
	
	private RealVector labelsDeTreinamento;
	
	private RealMatrix dadosDeTeste;
	
	private RealVector labelsDeTeste;
	
	public Testador(RealMatrix dadosDeTreinamento, RealVector labelsDeTreinamento, RealMatrix dadosDeTeste,
			RealVector labelsDeTeste) {
		super();
		this.dadosDeTreinamento = dadosDeTreinamento;
		this.labelsDeTreinamento = labelsDeTreinamento;
		this.dadosDeTeste = dadosDeTeste;
		this.labelsDeTeste = labelsDeTeste;
		
		criarArquivosParaWeka();
	}
	
	/**
	 * Valor que indica qual o algoritmo escolhido.
	 * @param
	 * 		algoritmoEscolhido indica qual o classificador a ser utilizado.
	 * 		Dica: utilizar as constantes globais da classe.
	 */
	public ResultadoDeTeste executar(int algoritmoEscolhido) {
		ResultadoDeTeste resultado = null;

		switch (algoritmoEscolhido) {
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

		return resultado;
	}

	private ResultadoDeTeste executarSVM() {
		SVM svm = new SVM(PATH_ARQUIVO_DE_TREINAMENTO_WEKA, PATH_ARQUIVO_DE_TESTE_WEKA);
		RealVector svmLabelsPreditos = svm.getLabelsPreditos();
		
		ResultadoDeTeste resultado = getResultadoDeTeste(svmLabelsPreditos);
		resultado.setMse(svm.getMSECalculado());
		//resultado.setMse(svm.getMSEApi());
		resultado.setTipoDeClassificadorUtilizado(CLASSIFICADOR_SVM);
		return resultado;
	}
	
	private ResultadoDeTeste executarPerceptron() {
		Perceptron perceptron = new Perceptron(PATH_ARQUIVO_DE_TREINAMENTO_WEKA, PATH_ARQUIVO_DE_TESTE_WEKA);
		RealVector perceptronLabelsPreditos = perceptron.getLabelsPreditos();
		
		ResultadoDeTeste resultado = getResultadoDeTeste(perceptronLabelsPreditos);
		resultado.setMse(perceptron.getMSECalculado());
		//resultado.setMse(perceptron.getMSEApi());
		resultado.setTipoDeClassificadorUtilizado(CLASSIFICADOR_PERCEPTRON);
		return resultado;
	}
	
	private ResultadoDeTeste executarLDA() {
		LDA lda = new LDA(dadosDeTreinamento.getData(), toIntVector(labelsDeTreinamento), true);
		
		/*
		 *	Monta o vetor de labels preditos para o LDA 
		 */
		RealVector ldaLabelsPreditos = new ArrayRealVector(dadosDeTeste.getRowDimension());
		for (int i = 0; i < dadosDeTeste.getRowDimension(); i++) {
			RealVector xi = dadosDeTeste.getRowVector(i);
			int labelPredito = lda.predict(xi.toArray());
			ldaLabelsPreditos.setEntry(i, labelPredito);
		}
		
		ResultadoDeTeste resultado = getResultadoDeTeste(ldaLabelsPreditos);
		resultado.setTipoDeClassificadorUtilizado(CLASSIFICADOR_LDA);
		return resultado;
	}
	
	/**
	 * Calcula a quantidade de acertos e erros para os dados de testing e gera o resultado de teste
	 * @param labelsPreditos
	 * 		conjunto de labels preditos
	 * @return
	 * 		um resultado de teste
	 */
	private ResultadoDeTeste getResultadoDeTeste(RealVector labelsPreditos) {
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
		
		ResultadoDeTeste resultado = new ResultadoDeTeste(contAcertos, contErros);
		return resultado;
	}
	
	private void criarArquivosParaWeka() {
		RealMatrix XLearningWeka = unirMatrixComLabels(dadosDeTreinamento, labelsDeTreinamento);
		RealMatrix XTestingWeka = unirMatrixComLabels(dadosDeTeste, labelsDeTeste);
		ArrfCreator arrfCreator = new ArrfCreator();
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
	
	private int[] toIntVector(RealVector vetor) {
		int[] r = new int[vetor.getDimension()];
		for (int i = 0; i < vetor.getDimension(); i++) {
			r[i] = (int)vetor.getEntry(i);
		}
		return r;
	}
}
