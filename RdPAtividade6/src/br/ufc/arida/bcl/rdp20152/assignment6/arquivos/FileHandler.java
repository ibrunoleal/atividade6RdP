package br.ufc.arida.bcl.rdp20152.assignment6.arquivos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
	
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
	public FileHandler(String arquivo, String delimitador) {
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
	 * 		o numero de elementos na primeira linha do arquivo.
	 */
	public int getNumeroDeColunas() {
		FileReader fileReader;
		int numeroDeColunas = 0;
		
		try {
			fileReader = new FileReader(arquivo);
			BufferedReader br = new BufferedReader(fileReader);
			
			String linha = br.readLine();
			if (linha != null) {
				String[] elementos = linha.split(delimitador);
				numeroDeColunas = elementos.length;
			}
			br.close();

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
	 * Recupera os elementos de uma dada coluna do arquivo.
	 * @param coluna
	 * 		O numero da coluna do aquivo do qual serão recuperados os elementos.
	 * 		Obs.: a primeira coluna é dada por 0.
	 * @return
	 * 		Uma List contendo os elementos da coluna dada.
	 */
	public List<Double> getColumnAsList(int coluna) {
		FileReader fileReader;
		int numeroDeLinhas = getNumeroDeLinhas();
		List<Double> vetor = new ArrayList<Double>();
		
		if (numeroDeLinhas > 0) {
			try {
				fileReader = new FileReader(arquivo);
				BufferedReader br = new BufferedReader(fileReader);

				String linha = br.readLine();
				while (linha != null) {
					String[] valores = linha.split(delimitador);
					
					double valor = Double.parseDouble(valores[coluna]);
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
	 * @param coluna
	 * 		O numero da coluna do aquivo do qual serão recuperados os elementos.
	 * 		Obs.: a primeira coluna é dada por 0.
	 * @return
	 * 		Um vetor contendo os elementos da coluna dada.
	 */
	public double[] getColumnAsArray(int coluna) {
		List<Double> lista = getColumnAsList(coluna);
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
	
}