package br.ufc.arida.bcl.rdp20152.assignment6.algoritmos.regressores;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import weka.classifiers.functions.GaussianProcesses;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class GPR {

	Instances instanciasDeTreinamento;
	
	Instances instanciasDeTeste;
	
	GaussianProcesses gpr;
	
	public GPR(String trainingFileName, String testingFileName) {
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
			
			
			gpr = new GaussianProcesses();
			String[] opt = {"-N", "2"};
			gpr.setOptions(opt);;
			gpr.buildClassifier(instanciasDeTreinamento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public RealVector getLabelsPreditos() {
		RealVector labelsPreditos = new ArrayRealVector(instanciasDeTeste.numInstances());
		try {
			for (int i = 0; i < instanciasDeTeste.numInstances(); i++) {
				double pred = gpr.classifyInstance(instanciasDeTeste.instance(i));
				labelsPreditos.setEntry(i, pred);
			}
			return labelsPreditos;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
