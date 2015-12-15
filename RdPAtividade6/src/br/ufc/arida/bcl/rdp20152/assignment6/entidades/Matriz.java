package br.ufc.arida.bcl.rdp20152.assignment6.entidades;

import java.text.DecimalFormat;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Classe para formatacao de saída de Matrizes.
 * Depende da API Commons Math: The Apache Commons Mathematics
 * Library 3.5 (https://commons.apache.org/proper/commons-math/)
 * @author Bruno Leal(ibrunoleal@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class Matriz extends Array2DRowRealMatrix {

	public Matriz(double[][] dados) {
		super(dados);
	}
	
	public Matriz(RealMatrix matrix) {
		super(matrix.getData());
	}
	
	public Matriz(int dimensaoDeLinhas, int dimensaoDeColunas) {
		super(dimensaoDeLinhas, dimensaoDeColunas);
	}
	
	public RealVector getDiagonalVector() {
		if (isSquare()) {
			RealVector vetorDiagonal = new ArrayRealVector(getRowDimension());
			for (int i = 0; i < getRowDimension(); i++) {
				vetorDiagonal.setEntry(i, getEntry(i, i));
			}
			return vetorDiagonal;
		}
		return null;
	}
	
	public String toString() {
		String texto = "";
		int maiortamanho = getMaiorQuantidadeDeDigitos();
		
		for (int i = 0; i < getRowDimension(); i++) {
			String linha = "[";
			for (int j = 0; j < (getColumnDimension()); j++) {
				int tamanhoDoElemento = ((Double)getEntry(i, j)).toString().length();
				int diferenca = maiortamanho - tamanhoDoElemento;
				String elemento = ((Double)getEntry(i, j)).toString();
				
				if (j == getColumnDimension() - 1) {
					linha += adicionarEspacos(elemento, diferenca) + "]\n";
				} else {
					linha += adicionarEspacos(elemento, diferenca) + "; ";
				}
			}
			texto += linha;
		}
		
		return texto;

	}
	
	/**
	 * Adiciona espaços ao final de um texto dado.
	 * @param texto
	 * 		Texto dado.
	 * @param quantidadeDeEspacos
	 * 		Quantidade de espaços a ser adicionado ao final do texto dado.
	 * @return
	 * 		O texto original adicionado dos espaços.
	 */
	private String adicionarEspacos(String texto, int quantidadeDeEspacos) {
		String novoTexto = texto;
		for (int i = 0; i < quantidadeDeEspacos; i++) {
			novoTexto += " ";
		}
		return novoTexto;
	}

	
	/**
	 * Forma o codigo latex referente a matriz.
	 * @param quantidadeDeDecimais
	 * 		Quantidade de "casas" decimais a serem consideradas após o ponto.
	 * @return
	 * 		Um texto contendo o codigo latex da matriz.
	 */
	public String toTexString(int quantidadeDeDecimais) {
		String decimalformat = "0";
		if (quantidadeDeDecimais > 0) {
			decimalformat += ".";
			for (int i = 0; i < quantidadeDeDecimais; i++) {
				decimalformat += "0";
			}
		}
		DecimalFormat df = new DecimalFormat(decimalformat);
		
		String texto = "$\n<nome> = \\begin{pmatrix}\n";
		for (int i = 0; i < getRowDimension(); i++) {
			for (int j = 0; j < getColumnDimension(); j++) {
				String elemento = df.format(getEntry(i, j));
				texto += elemento;
				if (j == (getColumnDimension() -1)) {
					if (i != (getRowDimension() -1)) {
						texto += " \\\\\n";
					}
				} else {
					texto += " & ";
				}
				
			}
		}
		texto += "\n\\end{pmatrix}\n$";
		return texto;
	}
	
	/**
	 * Forma o codigo CSV referente a matriz.
	 * @param quantidadeDeDecimais
	 * 		Quantidade de "casas" decimais a serem consideradas após o ponto.
	 * @return
	 * 		Um texto contendo o codigo CSV da matriz.
	 */
	public String toCSVString(int quantidadeDeDecimais) {
		String decimalformat = "0";
		if (quantidadeDeDecimais > 0) {
			decimalformat += ".";
			for (int i = 0; i < quantidadeDeDecimais; i++) {
				decimalformat += "0";
			}
		}
		DecimalFormat df = new DecimalFormat(decimalformat);
		
		/*
		 * Forma a linha de cabecalho considerando que a ultima coluna
		 * é o label dos elementos.
		 * Comentar se não forem dados de classificação com labels ou se não desejar cabecalho.
		 */
		String texto = "";
		String atributo = "X";
		int contAtributo = 1;
		for (int j = 0; j < getColumnDimension(); j++) {
			if ( !(j == (getColumnDimension() -1)) ) {
				texto += atributo + contAtributo + ",";
			} else {
				texto += "class" + "\n";
			}
			contAtributo++;
		}
		
		/*
		 * Forma as linhas de dados propriamente ditas.
		 * Obs.: o delimitador é comma(,)
		 */
		for (int i = 0; i < getRowDimension(); i++) {
			for (int j = 0; j < getColumnDimension(); j++) {
				String elemento = df.format(getEntry(i, j));
				
				if (j == (getColumnDimension() -1)) {
					texto += elemento;
					if (i != (getRowDimension() -1)) {
						texto += "\n";
					}
				} else {
					texto += elemento;
					texto += ","; // para alterar o delimitador mude essa linha
				}
				
			}
		}
		return texto;
	}
	
	/**
	 * Metodo auxiliar para formatacao de saída da matriz.
	 * Recupera a quantidade de digitos do elemento da matriz com a
	 * maior quantidade de dígitos.
	 * @return
	 * 		a quantidade de dígitos do elemento com mais dígitos
	 * 		nam matriz.
	 */
	private int getMaiorQuantidadeDeDigitos() {
		int maior = 0;
		for (int i = 0; i < getRowDimension(); i++) {
			for (int j = 0; j < getColumnDimension(); j++) {
				int tamanhoTemp = ((Double)getEntry(i, j)).toString().length();
				if (tamanhoTemp > maior) {
					maior = tamanhoTemp;
				}
			}
		}
		return maior;
	}
	
}
