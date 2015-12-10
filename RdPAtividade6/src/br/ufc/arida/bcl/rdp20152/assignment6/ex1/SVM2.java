package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class SVM2 {
	

	Instances instanciasDeTreinamento;
	Instances instanciasDeTeste;
	LibSVM svm;
	
	public SVM2(String trainingFileName, String testingFileName) {
		try {
			DataSource dataSourceLearning = new DataSource(trainingFileName);
			instanciasDeTreinamento = dataSourceLearning.getDataSet();
			if (instanciasDeTreinamento.classIndex() == -1) {
				instanciasDeTreinamento.setClassIndex(instanciasDeTreinamento.numAttributes() - 1);
			}
			
			svm = new LibSVM();
			svm.buildClassifier(instanciasDeTreinamento);
			
			DataSource dataSourceTesting = new DataSource(testingFileName);
			instanciasDeTeste = dataSourceTesting.getDataSet();
			if (instanciasDeTeste.classIndex() == -1) {
				instanciasDeTeste.setClassIndex(instanciasDeTeste.numAttributes() - 1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double classificar(int indiceDaInstanciaDeTeste) {
		try {
			double label = svm.classifyInstance(instanciasDeTeste.instance(indiceDaInstanciaDeTeste));
			instanciasDeTeste.instance(indiceDaInstanciaDeTeste).setClassValue(label);
			return label;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}
