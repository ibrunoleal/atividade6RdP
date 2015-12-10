package br.ufc.arida.bcl.rdp20152.assignment6.arquivos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class ArrfCreator {
	
	public void gerarArquivoArff(RealMatrix matrix, String nomeDoArquivo) {
		
		String texto = getNomeRelation(nomeDoArquivo);
		texto += "\n\n";
		
		for (int i = 0; i < matrix.getColumnDimension() - 2; i++) {
			texto += getDeclaracaoDeAtributo(i+1) + "\n";
		}
		
		RealVector colunaDeLabels = matrix.getColumnVector(matrix.getColumnDimension() - 1);
		List<Double> classes = getDistinctLabels(colunaDeLabels);
		texto += getDeclaracaoDeClasse(classes) + "\n\n";
		
		texto += getData(matrix);
		System.out.println(texto);
	}
	
	private List<Double> getDistinctLabels(RealVector colunaDeLabels) {
		List<Double> classes = new ArrayList<Double>();
		for (int i = 0; i < colunaDeLabels.getDimension(); i++) {
			double label = colunaDeLabels.getEntry(i);
			if (!classes.contains(label)) {
				classes.add(label);
			}
		}
		Collections.sort(classes);
		return classes;
	}
	
	private String getNomeRelation(String nomeDaRelacao) {
		return "@relation " + nomeDaRelacao.toLowerCase();
	}
	
	private String getDeclaracaoDeAtributo(int numeroDoAtributo) {
		return "@attribute X" + numeroDoAtributo + " numeric";
	}
	
	private String getDeclaracaoDeClasse(List<Double> classes) {
		String texto = "@attribute class {";
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
		//DecimalFormat df = new DecimalFormat("0.00000");
		String texto = "@data\n";
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			for (int j = 0; j < matrix.getColumnDimension(); j++) {
				String elemento = String.valueOf(matrix.getEntry(i, j));
				texto += elemento;
				if (j == (matrix.getColumnDimension() -1)) {
					if (i != (matrix.getRowDimension() -1)) {
						texto += "\n";
					}
				} else {
					texto += ",";
				}
			}
		}
		return texto;
	}

}
