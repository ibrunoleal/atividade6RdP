package br.ufc.arida.bcl.rdp20152.assignment6.ex1.classificadores;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.WekaToSamoaInstanceConverter;

import moa.classifiers.functions.Perceptron;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MOAPerceptron {

	Instances instanciasDeTreinamento;

	Instances instanciasDeTeste;

	Perceptron perceptron;

	public MOAPerceptron(String trainingFileName, String testingFileName) {
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
			
			perceptron = new Perceptron();
			
			treinar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void treinar() {
		perceptron.resetLearning();
		for (int i = 0; i < instanciasDeTreinamento.numInstances(); i++) {
			WekaToSamoaInstanceConverter converter = new WekaToSamoaInstanceConverter();
			Instance instancia = converter.samoaInstance(instanciasDeTreinamento.instance(i));
			perceptron.trainOnInstance(instancia);
		}
		
	}
	
	public void executar() {
		for (int i = 0; i < instanciasDeTeste.numInstances(); i++) {
			WekaToSamoaInstanceConverter converter = new WekaToSamoaInstanceConverter();
			Instance instancia = converter.samoaInstance(instanciasDeTeste.instance(i));
			double[] p = perceptron.getVotesForInstance(instancia);
			RealVector v = new ArrayRealVector(p);
			System.out.println(v);
		}
	}

}
