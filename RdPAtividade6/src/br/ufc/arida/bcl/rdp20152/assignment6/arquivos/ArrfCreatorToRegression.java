package br.ufc.arida.bcl.rdp20152.assignment6.arquivos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import org.apache.commons.math3.linear.RealMatrix;

public class ArrfCreatorToRegression {
	
	public void gerarArquivoArff(RealMatrix matrix, String nomeDoArquivo) {
		String texto = getNomeRelation(nomeDoArquivo);
		texto += "\n\n";
		
		for (int i = 0; i < matrix.getColumnDimension() - 1; i++) {
			texto += getDeclaracaoDeAtributo(i+1) + "\n";
		}
		
		texto += getDeclaracaoDeClasse() + "\n\n";
		
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
	
	private String getNomeRelation(String nomeDaRelacao) {
		return "@RELATION " + nomeDaRelacao.toLowerCase();
	}
	
	private String getDeclaracaoDeAtributo(int numeroDoAtributo) {
		return "@ATTRIBUTE 'X" + numeroDoAtributo + "' NUMERIC";
	}
	
	private String getDeclaracaoDeClasse() {
		String texto = "@ATTRIBUTE 'class' NUMERIC";
		return texto;
	}
	
	private String getData(RealMatrix matrix) {
		DecimalFormat df = new DecimalFormat("0.00000");
		String texto = "@DATA\n";
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			for (int j = 0; j < matrix.getColumnDimension(); j++) {
				String elemento = String.valueOf(df.format(matrix.getEntry(i, j)));
				
				if (j == (matrix.getColumnDimension() -1)) {
					texto += Double.parseDouble(elemento);
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
