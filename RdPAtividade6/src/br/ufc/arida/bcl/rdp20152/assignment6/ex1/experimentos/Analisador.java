package br.ufc.arida.bcl.rdp20152.assignment6.ex1.experimentos;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;


public class Analisador {
	
	/**
	 * Lista de resultados de teste.
	 * Obs.: Pode conter resultdados de diferentes tipos de classificadores.
	 */
	private List<ResultadoDeTeste> listaDeResultados;

	public Analisador() {
		super();
		listaDeResultados = new ArrayList<ResultadoDeTeste>();
	}
	
	public void adicionarResultado(ResultadoDeTeste resultado) {
		listaDeResultados.add(resultado);
	}
	
	/**
	 * Recupera a maior accuracy dentre os resultados do tipo de classificador desejado.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador dos quais serão iutilizados os resultados. Os
	 * 		resultados de outros tipos de classificadores serão ignorados.
	 * 		Dica: utilizar as constantes da classe Testador.
	 * @return
	 * 		O maior valor de accuracy dentre todos os resultados desse tipo de classificador.
	 */
	private double getMaxAccuracy(int tipoDeClassificador) {
		List<ResultadoDeTeste> resultados = filtrarResultadosDesejados(tipoDeClassificador);
		double max = -1;
		for (ResultadoDeTeste resultadoDeTeste : resultados) {
			if (resultadoDeTeste.getAccuracy() > max) {
				max = resultadoDeTeste.getAccuracy();
			}
		}
		return max;
	}
	
	/**
	 * Recupera a menor accuracy dentre os resultados do tipo de classificador desejado.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador dos quais serão iutilizados os resultados. Os
	 * 		resultados de outros tipos de classificadores serão ignorados.
	 * 		Dica: utilizar as constantes da classe Testador.
	 * @return
	 * 		O menor valor de accuracy dentre todos os resultados desse tipo de classificador.
	 */
	private double getMinAccuracy(int tipoDeClassificador) {
		List<ResultadoDeTeste> resultados = filtrarResultadosDesejados(tipoDeClassificador);
		double min = Double.POSITIVE_INFINITY;
		for (ResultadoDeTeste resultadoDeTeste : resultados) {
			if (resultadoDeTeste.getAccuracy() < min) {
				min = resultadoDeTeste.getAccuracy();
			}
		}
		return min;
	}
	
	/**
	 * Recupera média(mean) de accuracy dentre os resultados do tipo de classificador desejado.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador dos quais serão iutilizados os resultados. Os
	 * 		resultados de outros tipos de classificadores serão ignorados.
	 * 		Dica: utilizar as constantes da classe Testador.
	 * @return
	 * 		O valor médio(mean) de accuracy dentre todos os resultados desse tipo de classificador.
	 */
	private double getMeanAccuracy(int tipoDeClassificador) {
		List<ResultadoDeTeste> resultados = filtrarResultadosDesejados(tipoDeClassificador);
		double sum = 0;
		for (ResultadoDeTeste resultadoDeTeste : resultados) {
			sum += resultadoDeTeste.getAccuracy();
		}
		return sum / (double)resultados.size();
	}
	
	/**
	 * Recupera o desvio padrao da accuracy dentre os resultados do tipo de classificador desejado.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador dos quais serão iutilizados os resultados. Os
	 * 		resultados de outros tipos de classificadores serão ignorados.
	 * 		Dica: utilizar as constantes da classe Testador.
	 * @return
	 * 		O desvio padrao de accuracy dentre todos os resultados desse tipo de classificador.
	 */
	private double getAccuracyStandardDeviation(int tipoDeClassificador) {
		List<ResultadoDeTeste> resultados = filtrarResultadosDesejados(tipoDeClassificador);
		RealVector valores = new ArrayRealVector(resultados.size());
		StandardDeviation desviopadrao = new StandardDeviation();
		
		for (int i = 0; i < resultados.size(); i++) {
			valores.setEntry(i, resultados.get(i).getAccuracy());
		}
		
		return desviopadrao.evaluate(valores.toArray());
	}
	
	/**
	 * Recupera a maior MSE dentre os resultados do tipo de classificador desejado.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador dos quais serão iutilizados os resultados. Os
	 * 		resultados de outros tipos de classificadores serão ignorados.
	 * 		Dica: utilizar as constantes da classe Testador.
	 * @return
	 * 		O maior valor de MSE dentre todos os resultados desse tipo de classificador.
	 */
	private double getMaxMSE(int tipoDeClassificador) {
		List<ResultadoDeTeste> resultados = filtrarResultadosDesejados(tipoDeClassificador);
		double max = Double.NEGATIVE_INFINITY;
		for (ResultadoDeTeste resultadoDeTeste : resultados) {
			if (resultadoDeTeste.getMse() > max) {
				max = resultadoDeTeste.getMse();
			}
		}
		return max;
	}
	
	/**
	 * Recupera a menor MSE dentre os resultados do tipo de classificador desejado.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador dos quais serão iutilizados os resultados. Os
	 * 		resultados de outros tipos de classificadores serão ignorados.
	 * 		Dica: utilizar as constantes da classe Testador.
	 * @return
	 * 		O menor valor de MSE dentre todos os resultados desse tipo de classificador.
	 */
	private double getMinMSE(int tipoDeClassificador) {
		List<ResultadoDeTeste> resultados = filtrarResultadosDesejados(tipoDeClassificador);
		double min = Double.POSITIVE_INFINITY;
		for (ResultadoDeTeste resultadoDeTeste : resultados) {
			if (resultadoDeTeste.getMse() < min) {
				min = resultadoDeTeste.getMse();
			}
		}
		return min;
	}
	
	/**
	 * Recupera média(mean) de MSE dentre os resultados do tipo de classificador desejado.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador dos quais serão iutilizados os resultados. Os
	 * 		resultados de outros tipos de classificadores serão ignorados.
	 * 		Dica: utilizar as constantes da classe Testador.
	 * @return
	 * 		O valor médio(mean) de MSE dentre todos os resultados desse tipo de classificador.
	 */
	private double getMeanMSE(int tipoDeClassificador) {
		List<ResultadoDeTeste> resultados = filtrarResultadosDesejados(tipoDeClassificador);
		double sum = 0;
		for (ResultadoDeTeste resultadoDeTeste : resultados) {
			sum += resultadoDeTeste.getMse();
		}
		return sum / (double)resultados.size();
	}
	
	/**
	 * Filtra os resultados por tipo de classificador.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador para filtrar os resultados de testes desejados. 
	 *		Os resultados de outros tipos de classificadores serão ignorados.
	 * 		Dica: utilizar as constantes da classe Testador.
	 * @return
	 * 		Lista contendo apenas os resultados do tipo de classificador desejado.
	 */
	private List<ResultadoDeTeste> filtrarResultadosDesejados(int tipoDeClassificador) {
		List<ResultadoDeTeste> resultadosDesejados = new ArrayList<ResultadoDeTeste>();
		for (ResultadoDeTeste resultadoDeTeste : listaDeResultados) {
			if (resultadoDeTeste.getTipoDeClassificadorUtilizado() == tipoDeClassificador) {
				resultadosDesejados.add(resultadoDeTeste);
			}
		}
		return resultadosDesejados;
	}
	
	/**
	 * Contabiliza quantos resultados de teste dentre todos sao do tipo desejado.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador para filtrar os resultados de testes desejados.
	 * @return
	 * 		O numero de resultados de teste para o tipo de classificador informado.
	 */		
	private int numeroDeResultadosDesejados(int tipoDeClassificador) {
		int cont = 0;
		for (ResultadoDeTeste resultadoDeTeste : listaDeResultados) {
			if (resultadoDeTeste.getTipoDeClassificadorUtilizado() == tipoDeClassificador) {
				cont++;
			}
		}
		return cont;
	}
	
	/**
	 * Resultado analitico contendo:
	 * 		valores maximo, minimo e medio de accuracy;
	 * 		desvio padrao da accuracy;
	 * 		valores maximo, minimo e medio de MSE.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador para filtrar os resultados de testes desejados.
	 * @return
	 * 		Um texto contendo o resumo de analise para o classificador informado.
	 */
	public String getAnalise(int tipoDeClassificador) {
		double minAccuracy = getMinAccuracy(tipoDeClassificador);
		double maxAccuracy = getMaxAccuracy(tipoDeClassificador);
		double meanAccuracy = getMeanAccuracy(tipoDeClassificador);
		double desvioPadraoAccuracy = getAccuracyStandardDeviation(tipoDeClassificador);
		double minMSE = getMinMSE(tipoDeClassificador);
		double maxMSE = getMaxMSE(tipoDeClassificador);
		double meanMSE = getMeanMSE(tipoDeClassificador);
		int numeroDeResultados = numeroDeResultadosDesejados(tipoDeClassificador);
		
		String texto = "Analise para " + numeroDeResultados + " execuções: \n" +
				"  -> Minimum classification rate: " + minAccuracy + "\n" +
				"  -> Maximum classification rate: " + maxAccuracy + "\n" +
				"  -> Mean classification rate: " + meanAccuracy + "\n" +
				"  -> Standard Deviation of the classification rate: " + desvioPadraoAccuracy + "\n" +
				"  -> Minimum MSE: " + minMSE + "\n" +
				"  -> Maximum MSE: " + maxMSE + "\n" +
				"  -> Mean MSE: " + meanMSE;	
		return texto;
	}
}
