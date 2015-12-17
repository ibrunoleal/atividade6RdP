package br.ufc.arida.bcl.rdp20152.assignment6.arquivos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Cria um arquivo .arff (formato padrao do WEKA) a partir dos dados de uma matriz.
 * Obs.: considera que a ultima coluna da matriz são os labels.
 * @author Bruno Leal(ibrunoleal@gmail.com)
 *
 */
public class ArrfCreatorToClassify {
	
	public void gerarArquivoArff(RealMatrix matrix, String nomeDoArquivo) {
		
		String texto = getNomeRelation(nomeDoArquivo);
		texto += "\n\n";
		
		for (int i = 0; i < matrix.getColumnDimension() - 1; i++) {
			texto += getDeclaracaoDeAtributo(i+1) + "\n";
		}
		
		RealVector colunaDeLabels = matrix.getColumnVector(matrix.getColumnDimension() - 1);
		List<Integer> classes = getDistinctLabels(colunaDeLabels);
		texto += getDeclaracaoDeClasse(classes) + "\n\n";
		
		texto += getData(matrix);
		
		texto += "\n%\n%\n%";
		
		try {
			FileWriter arquivo = new FileWriter(nomeDoArquivo);
			PrintWriter escritorDeArquivo = new PrintWriter(arquivo);
			escritorDeArquivo.print(texto);
			escritorDeArquivo.close();
			arquivo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private List<Integer> getDistinctLabels(RealVector colunaDeLabels) {
		List<Integer> classes = new ArrayList<Integer>();
		for (int i = 0; i < colunaDeLabels.getDimension(); i++) {
			int label = (int)colunaDeLabels.getEntry(i);
			if (!classes.contains(label)) {
				classes.add(label);
			}
		}
		Collections.sort(classes);
		return classes;
	}
	
	private String getNomeRelation(String nomeDaRelacao) {
		return "@RELATION " + nomeDaRelacao.toLowerCase();
	}
	
	private String getDeclaracaoDeAtributo(int numeroDoAtributo) {
		return "@ATTRIBUTE 'X" + numeroDoAtributo + "' NUMERIC";
	}
	
	private String getDeclaracaoDeClasse(List<Integer> classes) {
		String texto = "@ATTRIBUTE 'class' {";
		for (int i = 0; i < classes.size(); i++) {
			if ( !(i == classes.size() - 1)) {
				texto += classes.get(i) + ",";
			} else {
				texto += classes.get(i) + "}";
			}
		}
		return texto;
	}
	
	private String getData(RealMatrix matrix) {
		DecimalFormat df = new DecimalFormat("0.00000");
		String texto = "@DATA\n";
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			for (int j = 0; j < matrix.getColumnDimension(); j++) {
				String elemento = String.valueOf(df.format(matrix.getEntry(i, j)));
				
				if (j == (matrix.getColumnDimension() -1)) {
					texto += (int)Double.parseDouble(elemento);
					if (i != (matrix.getRowDimension() -1)) {
						texto += "\n";
					}
				} else {
					texto += elemento;
					texto += ",";
				}
			}
		}
		return texto;
	}

}
