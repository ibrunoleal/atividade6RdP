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
	
	RealMatrix data;
	
	RealVector labels;
	
	LibSVM libSVM;
	
	FastVector atributos;
	
	Instances instancias;
	
	public SVM(RealMatrix data, RealVector labels) {
		this.data = data;
		this.labels = labels;
		this.libSVM = new LibSVM();
		atributos = new FastVector(data.getColumnDimension() + 1);
	}
	
	public void executar() {
		iniciarModeloDeAtributos();
		instancias = new Instances("instancias", atributos, data.getRowDimension());
		carregarInstancias();
		treinar();
	}

	public void iniciarModeloDeAtributos() {
		String name = "x";
		for (int i = 0; i < data.getColumnDimension(); i++) {
			Attribute atributo = new Attribute(name + i);
			atributos.addElement(atributo);
		}
		
		List<Integer> classes = getDistinctLabels();
		FastVector fvClassVal = new FastVector(classes.size());
		for (Integer label : classes) {
			fvClassVal.addElement(label); 
		}
		Attribute classAttribute = new Attribute("class", fvClassVal);
		atributos.addElement(classAttribute);
	}
	
	public void carregarInstancias() {
		for (int i = 0; i < data.getRowDimension(); i++) {
			RealVector dados = data.getRowVector(i);
			double[] c = {labels.getEntry(i)};
			RealVector classe = new ArrayRealVector(c);
			RealVector valores = dados.append(classe);
			Instance instancia = new Instance(valores.getDimension());
			for (int j = 0; j < valores.getDimension(); j++) {
				instancia.setValue((Attribute)atributos.elementAt(0), valores.getEntry(j));
			}
			instancias.add(instancia);
		}
	}
	
	public void treinar() {
		try {
			libSVM.buildClassifier(instancias);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Integer> getDistinctLabels() {
		List<Integer> classes = new ArrayList<Integer>();
		for (int i = 0; i < labels.getDimension(); i++) {
			int label = (int)labels.getEntry(i);
			if (!classes.contains(label)) {
				classes.add(label);
			}
		}
		return classes;
	}
	
}
