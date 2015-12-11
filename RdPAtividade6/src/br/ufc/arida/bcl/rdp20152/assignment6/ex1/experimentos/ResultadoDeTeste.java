package br.ufc.arida.bcl.rdp20152.assignment6.ex1.experimentos;

public class ResultadoDeTeste {
	
	public static final int RESULTADO_NAO_DEFINIDO = -1;
	
	private double acertos;
	
	private double erros;
	
	private double mse;
	
	private int tipoDeClassificadorUtilizado;

	public ResultadoDeTeste(double acertos, double erros) {
		super();
		this.acertos = acertos;
		this.erros = erros;
		this.mse = RESULTADO_NAO_DEFINIDO;
		this.tipoDeClassificadorUtilizado = RESULTADO_NAO_DEFINIDO;
	}
	
	public ResultadoDeTeste() {
		this.acertos = RESULTADO_NAO_DEFINIDO;
		this.erros = RESULTADO_NAO_DEFINIDO;
		this.mse = RESULTADO_NAO_DEFINIDO;
		this.tipoDeClassificadorUtilizado = RESULTADO_NAO_DEFINIDO;
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

	public void setTipoDeClassificadorUtilizado(int tipoDeClassificadorUtilizado) {
		this.tipoDeClassificadorUtilizado = tipoDeClassificadorUtilizado;
	}

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
