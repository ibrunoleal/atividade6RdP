package br.ufc.arida.bcl.rdp20152.assignment6.ex1.experimentos;

import java.util.ArrayList;
import java.util.List;


public class Analisador {
	
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
	public double getMaxAccuracy(int tipoDeClassificador) {
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
	public double getMinAccuracy(int tipoDeClassificador) {
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
	 * Filtra os resultados por tipo de classificador.
	 * @param tipoDeClassificador
	 * 		Tipo de classificador para filtrar os resultados de testes desejados. 
	 *		Os resultados de outros tipos de classificadores serão ignorados.
	 * 		Dica: utilizar as constantes da classe Testador.
	 * @return
	 * 		Lista contendo apenas os resultados do tipo de classificador desejado.
	 */
	public List<ResultadoDeTeste> filtrarResultadosDesejados(int tipoDeClassificador) {
		List<ResultadoDeTeste> resultadosDesejados = new ArrayList<ResultadoDeTeste>();
		for (ResultadoDeTeste resultadoDeTeste : listaDeResultados) {
			if (resultadoDeTeste.getTipoDeClassificadorUtilizado() == tipoDeClassificador) {
				resultadosDesejados.add(resultadoDeTeste);
			}
		}
		return resultadosDesejados;
	}
}
