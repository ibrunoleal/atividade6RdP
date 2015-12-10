package br.ufc.arida.bcl.rdp20152.assignment6.entidades;

import java.text.DecimalFormat;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

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
	
	private String adicionarEspacos(String texto, int quantidadeDeEspacos) {
		String novoTexto = texto;
		for (int i = 0; i < quantidadeDeEspacos; i++) {
			novoTexto += " ";
		}
		return novoTexto;
	}
	
	public String toTexString() {
		DecimalFormat df = new DecimalFormat("0.00000");
		
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
	
	public String toCSVString() {
		String texto = "";
		
		DecimalFormat df = new DecimalFormat("0.00000");
		
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
					texto += ",";
				}
				
			}
		}
		//texto += "\n\\end{pmatrix}\n$";
		return texto;
	}
	
	/**
	 * Metodo auxiliar para formatacao de saída da matriz.
	 * Recupera a quantidade de digitos do elemento da matriz com a
	 * maior quantidade de dígitos.
	 * @return a quantidade de dígitos do elemento com mais dígitos
	 * nam matriz.
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
