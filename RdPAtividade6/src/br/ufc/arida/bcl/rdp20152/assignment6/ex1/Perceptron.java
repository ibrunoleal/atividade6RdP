package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Perceptron {

	Instances instanciasDeTreinamento;

	Instances instanciasDeTeste;

	MultilayerPerceptron perceptron;

	public Perceptron(String trainingFileName, String testingFileName) {
		try {
			DataSource dataSourceTraining = new DataSource(trainingFileName);
			instanciasDeTreinamento = dataSourceTraining.getDataSet();
			if (instanciasDeTreinamento.classIndex() == -1) {
				instanciasDeTreinamento.setClassIndex(instanciasDeTreinamento.numAttributes() - 1);
			}

			DataSource dataSourceTesting = new DataSource(testingFileName);
			instanciasDeTeste = dataSourceTesting.getDataSet();
			if (instanciasDeTeste.classIndex() == -1) {
				instanciasDeTeste.setClassIndex(instanciasDeTeste.numAttributes() - 1);
			}
			
			perceptron = new MultilayerPerceptron();
			perceptron.buildClassifier(instanciasDeTreinamento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public RealVector getLabelsPreditos() {
		RealVector labelsPreditos = new ArrayRealVector(instanciasDeTeste.numInstances());
		try {
			for (int i = 0; i < instanciasDeTeste.numInstances(); i++) {
				double pred = perceptron.classifyInstance(instanciasDeTeste.instance(i));
				labelsPreditos.setEntry(i, Double.parseDouble(instanciasDeTeste.classAttribute().value((int) pred)));
			}
			return labelsPreditos;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int getClassificacaoPredita(int indiceDaInstanciaDeTeste) {
		try {
			double pred = perceptron.classifyInstance(instanciasDeTeste.instance(indiceDaInstanciaDeTeste));
			return Integer.parseInt(instanciasDeTeste.classAttribute().value((int) pred));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}
