package br.ufc.arida.bcl.rdp20152.assignment6.ex1.classificadores;

import java.util.Enumeration;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
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
	
	public RealVector getLabelsPreditos() {
		RealVector labelsPreditos = new ArrayRealVector(instanciasDeTeste.numInstances());
		RealVector classesLabels = getClassesLabels();
		for (int i = 0; i < instanciasDeTeste.numInstances(); i++) {
			WekaToSamoaInstanceConverter converter = new WekaToSamoaInstanceConverter();
			Instance instancia = converter.samoaInstance(instanciasDeTeste.instance(i));
			double[] p = perceptron.getVotesForInstance(instancia);
			RealVector probabilities = new ArrayRealVector(p);
			double labelPredito = getClassLabel(probabilities, classesLabels);
			labelsPreditos.setEntry(i, labelPredito);
		}
		return labelsPreditos;
	}
	
	public double getMSECalculado() {
		RealMatrix D = new Array2DRowRealMatrix(instanciasDeTeste.numInstances(), instanciasDeTreinamento.numClasses());
		try {
			for (int i = 0; i < instanciasDeTeste.numInstances(); i++) {
				WekaToSamoaInstanceConverter converter = new WekaToSamoaInstanceConverter();
				Instance instancia = converter.samoaInstance(instanciasDeTeste.instance(i));
				double[] d = perceptron.getVotesForInstance(instancia);
				RealVector distribuicao = new ArrayRealVector(d);
				D.setRowVector(i, distribuicao);
			}
			double sumE = 0.0;
			for (int i = 0; i < D.getRowDimension(); i++) {
				RealVector ei = D.getRowVector(i);
				double e = Math.pow( (1.0 - ei.getMaxValue()) , 2);
				sumE += e;
			}
			return sumE / (double)D.getRowDimension(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	private RealVector getClassesLabels() {
		RealVector classes = new ArrayRealVector(instanciasDeTeste.numClasses());
		int cont = 0;
		Enumeration<Object> valores = instanciasDeTeste.firstInstance().classAttribute().enumerateValues();
		while (valores.hasMoreElements()) {
			double valor = Double.parseDouble("" + valores.nextElement());
			classes.setEntry(cont, valor);
			cont++;
		}
		return classes;
	}
	
	private double getClassLabel(RealVector probabilities, RealVector classesLabels) {
		int indexMax = probabilities.getMaxIndex();
		return classesLabels.getEntry(indexMax);
	}

}
