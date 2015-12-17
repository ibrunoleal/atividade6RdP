package br.ufc.arida.bcl.rdp20152.assignment6.arquivos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para leitura de dados numericos de arquivos em formato de matriz.
 * @author Bruno Leal(ibrunoleal@gmail.com)
 *
 */
public class MatrizFileHandler {
	
	/**
	 * nome(ou path) do arquivo que contem o dataset
	 */
	private String arquivo;
	
	/**
	 * Separador de elementos da linha
	 * obs: o separador entre linhas é a quebra de linha por padrao("\n")
	 */
	private String delimitador;
	
	/**
	 * Cria um gerenciador de arquivos.
	 * @param arquivo
	 * 		Nome (ou PATH) do arquivo
	 * @param delimitador
	 * 		Texto que separa um elemento de linha do elemento seguinte.
	 * 		Exemplo: a "," em arquivos CSV ou um espaço.
	 */
	public MatrizFileHandler(String arquivo, String delimitador) {
		this.arquivo = arquivo;
		this.delimitador = delimitador;
	}
	
	/**
	 * Computa o numero de linhas contidas no arquivo.
	 * Obs.: se o arquivo representar os dados de uma matriz, computará o numero de linhas de uma matriz.
	 * @return
	 * 		o numero de linhas no arquivo.
	 */
	public int getNumeroDeLinhas() {
		try {
			File arquivoLeitura = new File(arquivo);
			LineNumberReader linhaLeitura = new LineNumberReader(new FileReader(arquivoLeitura));
			linhaLeitura.skip(arquivoLeitura.length());
			int qtdLinha = linhaLeitura.getLineNumber();
			linhaLeitura.close();
			return qtdLinha;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Contabiliza o numero de elementos na primeira linha do arquivo.
	 * Obs.: se o arquivo representar os dados de uma matriz, computará o numero 
	 * de colunas de uma matriz.
	 * @return
	 * 		O numero de elementos na primeira linha do arquivo.
	 */
	public int getNumeroDeColunas() {
		FileReader fileReader;
		int numeroDeColunas = 0;
		
		try {
			fileReader = new FileReader(arquivo);
			BufferedReader br = new BufferedReader(fileReader);
			String linha = br.readLine();
			br.close();
			numeroDeColunas = getElementosDaLinha(linha).size();
//			if (linha != null) {
//				String[] elementos = linha.split(delimitador);
//				numeroDeColunas = elementos.length;
//			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return numeroDeColunas;
	}
	
	/**
	 * Recupera os elementos de uma dada linha do arquivo.
	 * @param numLinha
	 * 		O numero da linha do aquivo do qual serão recuperados os elementos.
	 * 		Obs.: a primeira linha é dada por 0.
	 * @return
	 * 		Uma List contendo os elementos da linha dada.
	 */
	public List<Double> getLineAsList(int numLinha) {
		FileReader fileReader;
		int numeroDeLinhas = getNumeroDeLinhas();
		List<Double> vetor = new ArrayList<Double>();
		
		if (numLinha <= numeroDeLinhas) {
			try {
				fileReader = new FileReader(arquivo);
				BufferedReader br = new BufferedReader(fileReader);
				for (int i = 0; i < (numLinha - 1); i++) {
					br.readLine();
				}
				String linha = br.readLine();
				vetor = getElementosDaLinha(linha);
//				String[] valores  = linha.split(delimitador);
//				for (int i = 0; i < valores.length; i++) {
//					vetor.add(Double.parseDouble(valores[i]));
//				}
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return vetor;
		}
		return null;
	}
	
	/**
	 * Recupera os elementos de uma dada linha do arquivo.
	 * @param numLine
	 * 		O numero da linha do aquivo do qual serão recuperados os elementos.
	 * 		Obs.: a primeira linha é dada por 0.
	 * @return
	 * 		Um vetor contendo os elementos da linha dada.
	 */
	public double[] getLineAsArray(int numLine) {
		List<Double> lista = getLineAsList(numLine);
		double[] vetor = new double[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			vetor[i] = lista.get(i);
		}
		return vetor;
	}
	
	/**
	 * Recupera os elementos de uma dada coluna do arquivo.
	 * @param numColumn
	 * 		O numero da coluna do aquivo do qual serão recuperados os elementos.
	 * 		Obs.: a primeira coluna é dada por 0.
	 * @return
	 * 		Uma List contendo os elementos da coluna dada.
	 */
	public List<Double> getColumnAsList(int numColumn) {
		FileReader fileReader;
		int numeroDeLinhas = getNumeroDeLinhas();
		List<Double> vetor = new ArrayList<Double>();
		
		if (numeroDeLinhas > 0) {
			try {
				fileReader = new FileReader(arquivo);
				BufferedReader br = new BufferedReader(fileReader);

				String linha = br.readLine();
				while (linha != null) {	
					List<Double> linhaList = getElementosDaLinha(linha);
					double valor = linhaList.get(numColumn);
					vetor.add(valor);
					linha = br.readLine();
				}
				br.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return vetor;
	}
	
	/**
	 * Recupera os elementos de uma dada coluna do arquivo.
	 * @param numColumn
	 * 		O numero da coluna do aquivo do qual serão recuperados os elementos.
	 * 		Obs.: a primeira coluna é dada por 0.
	 * @return
	 * 		Um vetor contendo os elementos da coluna dada.
	 */
	public double[] getColumnAsArray(int numColumn) {
		List<Double> lista = getColumnAsList(numColumn);
		double[] vetor = new double[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			vetor[i] = lista.get(i);
		}
		return vetor;
	}
	
	/**
	 * Recupera os elementos de um arquivo na forma de Matriz.
	 * @return
	 * 		Uma matriz contendo os todos os elementos do arquivo na forma de matriz.
	 * 		Obs.: as linhas devem ter a mesma quantidade de elementos uma das outras.
	 * 		Obs.: as colunas devem ter a mesma quantidade de elementos uma das outras.
	 */
	public double[][] getMatriz() {
		int numeroDeLinhas = getNumeroDeLinhas();
		int numeroDeColunas = getNumeroDeColunas();
		double[][] matriz = new double[numeroDeLinhas][numeroDeColunas];
		
		if (numeroDeColunas > 0) {
			for (int j = 0; j < numeroDeColunas; j++) {
				double[] coluna = getColumnAsArray(j);
				for (int i = 0; i < coluna.length; i++) {
					matriz[i][j] = coluna[i];
				}
			}
		}
		return matriz;	
	}
	
	/**
	 * Dado uma linha da matriz em formato texto, forma uma lista de elementos
	 * double com os valores da linha da matriz.
	 * @param linha
	 * 		O texto referente a linha da matriz.
	 * @return
	 * 		Uma List com os elementos da linha.
	 */
	public List<Double> getElementosDaLinha(String linha) {
		String[] valoresTexto = linha.split(delimitador);
		List<Double> lista = new ArrayList<Double>();
		for (String valor : valoresTexto) {
			if (!valor.equalsIgnoreCase("")) {
				lista.add(Double.parseDouble(valor));
			}
		}
		return lista;
	}
	
}