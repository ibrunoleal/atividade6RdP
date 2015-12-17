package br.ufc.arida.bcl.rdp20152.assignment6.algoritmos.regressores;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import weka.classifiers.functions.SMOreg;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class SVR {
	
	Instances instanciasDeTreinamento;
	
	Instances instanciasDeTeste;
	
	SMOreg svr;
	
	public SVR(String trainingFileName, String testingFileName) {
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
			
			svr = new SMOreg();
			
			/*
			 * Definie a probabilidade ao invés de apenas 0 e 1 na classificacao
			 */
			//svr.setProbabilityEstimates(true);
			
			svr.buildClassifier(instanciasDeTreinamento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public RealVector getLabelsPreditos() {
		RealVector labelsPreditos = new ArrayRealVector(instanciasDeTeste.numInstances());
		try {
			for (int i = 0; i < instanciasDeTeste.numInstances(); i++) {
				double pred = svr.classifyInstance(instanciasDeTeste.instance(i));
				labelsPreditos.setEntry(i, pred);
				//labelsPreditos.setEntry(i, Double.parseDouble(instanciasDeTeste.classAttribute());
			}
			return labelsPreditos;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
