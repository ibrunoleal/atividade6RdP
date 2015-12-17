package br.ufc.arida.bcl.rdp20152.assignment6.ex1.experimentos;

public class ResultadoDeTeste {
	
	public static final int RESULTADO_NAO_DEFINIDO = -1;
	
	/**
	 * Numero de instancias classificadas corretamente.
	 */
	private double acertos;
	
	/**
	 * Numero de instancias classificadas erroneamente.
	 */
	private double erros;
	
	/**
	 * Mean Squared Error das instancias classificadas.
	 * Obs.: O MSE de cada instancia é dado
	 * por (1 - valor de probabilidade da classificacao predita). 
	 */
	private double mse;
	
	/**
	 *	Tipo de classificador utilizado para a obtencao do resultado. 
	 */
	private int tipoDeClassificadorUtilizado;

	/**
	 * Representa o resultado de uma execucao.
	 * Obs.: os demais atributos sao inicializados
	 * como NAO definidos (constante da classe)
	 * @param acertos
	 * 		Numero de classificacoes preditas corretamente.
	 * @param erros
	 * 		Numero de classificacoes preditas erroneamente.
	 */
	public ResultadoDeTeste(int tipoDeClassificador, double acertos, double erros) {
		super();
		this.acertos = acertos;
		this.erros = erros;
		this.mse = RESULTADO_NAO_DEFINIDO;
		this.tipoDeClassificadorUtilizado = tipoDeClassificador;
	}
	
	/**
	 * Representa o resultado de uma execucao.
	 * Os valores de resultado sao inicializados como
	 * NAO definidos(constante da classe).
	 */
	public ResultadoDeTeste(int tipoDeClassificador) {
		this.acertos = RESULTADO_NAO_DEFINIDO;
		this.erros = RESULTADO_NAO_DEFINIDO;
		this.mse = RESULTADO_NAO_DEFINIDO;
		this.tipoDeClassificadorUtilizado = tipoDeClassificador;
	}
	
	public double getAcertos() {
		return acertos;
	}

	public void setAcertos(double acertos) {
		this.acertos = acertos;
	}

	public double getErros() {
		return erros;
	}

	public void setErros(double erros) {
		this.erros = erros;
	}

	public double getMse() {
		return mse;
	}

	public void setMse(double mse) {
		this.mse = mse;
	}

	public int getTipoDeClassificadorUtilizado() {
		return tipoDeClassificadorUtilizado;
	}

//	/**
//	 * Define qual o classificador utilizado na execucao do teste.
//	 * @param tipoDeClassificadorUtilizado
//	 * 		Classificador utilizado.
//	 * 		Obs.: classificador definido de acordo
//	 * 		com as constantes da classe Testador.
//	 */
//	public void setTipoDeClassificadorUtilizado(int tipoDeClassificadorUtilizado) {
//		this.tipoDeClassificadorUtilizado = tipoDeClassificadorUtilizado;
//	}

	/**
	 * Calcula a % instancias classificadas corretamente.
	 * @return
	 * 		O valor em % de instancias classificadas corretamente.
	 */
	public double getAccuracy() {
		if (acertos != RESULTADO_NAO_DEFINIDO && erros != RESULTADO_NAO_DEFINIDO) {
			return (acertos * 100) / (acertos + erros);
		} else {
			return RESULTADO_NAO_DEFINIDO;
		}
	}

	@Override
	public String toString() {
		return "ResultadoDeTeste [acertos=" + acertos + ", erros=" + erros + 
				", mse=" + mse + ", accuracy=" + getAccuracy() + "]";
	}

	
}
