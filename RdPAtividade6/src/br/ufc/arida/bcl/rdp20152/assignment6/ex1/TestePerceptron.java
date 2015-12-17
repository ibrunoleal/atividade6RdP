package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import br.ufc.arida.bcl.rdp20152.assignment6.ex1.classificadores.MOAPerceptron;
import br.ufc.arida.bcl.rdp20152.assignment6.ex1.experimentos.Testador;

public class TestePerceptron {

	public static void main(String[] args) {
		
		MOAPerceptron perceptron = new MOAPerceptron(Testador.PATH_ARQUIVO_DE_TREINAMENTO_WEKA, Testador.PATH_ARQUIVO_DE_TESTE_WEKA);
		perceptron.getLabelsPreditos();
	}

}
