package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rdp20152.assignment6.entidades.Matriz;


public class Exercicio1 {

	public static void main(String[] args) {
		
		Exercicio1Functions f = new Exercicio1Functions();
		
		RealMatrix X = f.getDermatolgy_data().getSubMatrix(0, f.getDermatolgy_data().getRowDimension() -1, 0, f.getDermatolgy_data().getColumnDimension() - 2);
		RealVector l = f.getDermatolgy_data().getColumnVector(f.getDermatolgy_data().getColumnDimension() -1);
		
		RealMatrix T = f.formarMatrizT(l, 6);
		
		RealMatrix X1 = f.matrixToZeroMeanAndVariance(X);
		
		f.matrixToRandomMatrixRows(X);
	}

}
