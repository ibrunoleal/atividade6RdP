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
		
		executar();
	}
	
	private void executar() {
		iniciarModeloDeAtributos();
		instancias = new Instances("instancias", atributos, data.getRowDimension());
		carregarInstancias();
		treinar();
	}

	private void iniciarModeloDeAtributos() {
		String name = "x";
		for (int i = 0; i < data.getColumnDimension(); i++) {
			Attribute atributo = new Attribute(name + i);
			atributos.addElement(atributo);
		}
		
		List<Integer> labels = getDistinctLabels();
		FastVector fvClassVal = new FastVector(labels.size());
		for (Integer label : labels) {
			fvClassVal.addElement(String.valueOf(label));
		}
		Attribute classAttribute = new Attribute("classe", fvClassVal);
		atributos.addElement(classAttribute);
	}
	
	private void carregarInstancias() {
		for (int i = 0; i < data.getRowDimension(); i++) {
			RealVector dados = data.getRowVector(i);
			double[] c = {labels.getEntry(i)};
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
			libSVM.setDoNotReplaceMissingValues(true);
			libSVM.buildClassifier(instancias);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int classificar(RealVector elemento) {
		Instance instancia = new Instance(elemento.getDimension());
		for (int j = 0; j < elemento.getDimension(); j++) {
			instancia.setValue((Attribute)atributos.elementAt(j), elemento.getEntry(j));
		}
		
		try {
			Instances instancias = new Instances("instanciaTesting", atributos, 1);
			instancias.add(instancia);
			instancias.setClassIndex(instancias.numAttributes() - 1);
			
			double classificacao = libSVM.classifyInstance(instancias.firstInstance());
			return (int)classificacao;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Erro de classificacao: por algum motivo para classe 6 acontece erro de ArrayIndexOutOfBoundsException");
			e.printStackTrace();
			return 6;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	private List<Integer> getDistinctLabels() {
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
