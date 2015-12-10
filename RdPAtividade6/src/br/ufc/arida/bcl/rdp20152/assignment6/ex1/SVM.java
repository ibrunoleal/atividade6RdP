package br.ufc.arida.bcl.rdp20152.assignment6.ex1;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class SVM {
	
	
	private RealMatrix dataLearning;
	
	private RealVector labelsLearning;
	
	private LibSVM libSVM;
	
	/**
	 * Deve conter o numero de atributos de informacao + 1 para a classe(label)
	 */
	private FastVector atributos;
	
	private Instances instancias;
	
	public SVM(RealMatrix dataLearning, RealVector labelsLearning) {
		this.dataLearning = dataLearning;
		this.labelsLearning = labelsLearning;
		this.libSVM = new LibSVM();
		atributos = new FastVector(dataLearning.getColumnDimension() + 1);
		
		executar();
	}
	
	private void executar() {
		iniciarModeloDeAtributos();
		instancias = new Instances("instancias", atributos, dataLearning.getRowDimension());
		carregarInstancias();
		treinar();
	}

	private void iniciarModeloDeAtributos() {
		String name = "x";
		for (int i = 1; i <= dataLearning.getColumnDimension(); i++) {
			Attribute atributo = new Attribute(name + i);
			atributos.addElement(atributo);
		}
		
		List<Integer> labels = getDistinctLabels();
		FastVector fvClassVal = new FastVector();
		for (int i = 1; i <= labels.size() + 1; i++) {
			fvClassVal.addElement(String.valueOf(i));
		}
		Attribute classAttribute = new Attribute("classe", fvClassVal);
		atributos.addElement(classAttribute);
	}
	
	private void carregarInstancias() {
		for (int i = 0; i < dataLearning.getRowDimension(); i++) {
			RealVector dados = dataLearning.getRowVector(i);
			double[] c = {labelsLearning.getEntry(i)};
			RealVector classe = new ArrayRealVector(c);
			RealVector valores = dados.append(classe);

			Instance instancia = new Instance(valores.getDimension());
			for (int j = 0; j < valores.getDimension(); j++) {
				instancia.setValue((Attribute)atributos.elementAt(j), valores.getEntry(j));
				instancias.add(instancia);
			}
		}
		instancias.setClassIndex(instancias.numAttributes() -1);
	}
	
	private void treinar() {
		try {
			//String[] options = weka.core.Utils.splitOptions("-K 0 -D 3 -split-percentage 10");
			//libSVM.setOptions(options);
			libSVM.setDoNotReplaceMissingValues(true);
			libSVM.buildClassifier(instancias);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double classificar(RealVector elemento) {
		Instance instancia = new Instance(elemento.getDimension());
		for (int j = 0; j < elemento.getDimension(); j++) {
			instancia.setValue((Attribute)atributos.elementAt(j), elemento.getEntry(j));
		}
		
		try {
			Instances instancias = new Instances("instanciaTesting", atributos, 0);
			instancias.add(instancia);
			instancias.setClassIndex(instancias.numAttributes() - 1);
			
			double classificacao = libSVM.classifyInstance(instancias.firstInstance());
			return classificacao;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	private List<Integer> getDistinctLabels() {
		List<Integer> classes = new ArrayList<Integer>();
		for (int i = 0; i < labelsLearning.getDimension(); i++) {
			int label = (int)labelsLearning.getEntry(i);
			if (!classes.contains(label)) {
				classes.add(label);
			}
		}
		return classes;
	}
	
}
