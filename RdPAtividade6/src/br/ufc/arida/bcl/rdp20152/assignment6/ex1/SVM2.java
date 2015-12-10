package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import weka.classifiers.functions.LibSVM;
import weka.core.Instances;


public class SVM2 {
	

	Instances instanciasDeTreinamento;
	Instances instanciasDeTeste;
	LibSVM svm;
	
	public SVM2(String trainingFileName, String testingFileName) {
		
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
