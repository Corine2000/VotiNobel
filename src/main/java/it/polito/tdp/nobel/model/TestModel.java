package it.polito.tdp.nobel.model;

import java.util.Set;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Model model = new Model();
		Set<Esame> esami = model.calcolaSottoinsiemeEsami(10);
		
		double media =model.calcolaMedia(esami);
		String s= "";
		for(Esame e: esami) {
			s+= e.getCodins()+" "+e.getCrediti()+" "+e.getVoto()+" "+e.getNomeCorso()+"\n";
		}
		
		System.out.println("media="+media+ "\n gli esami sono: \n" + s);
	}

}
