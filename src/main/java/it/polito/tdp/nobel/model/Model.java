package it.polito.tdp.nobel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	EsameDAO dao;
	private List<Esame> partenza;
	private Set<Esame> soluzioneMigliore;
	private double mediaMigliore;
	
	public Model() {   //costrutore
		dao = new EsameDAO();
		partenza = dao.getTuttiEsami();
		
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
	//	System.out.println("TODO!");
		soluzioneMigliore = new HashSet<>();
		mediaMigliore=0;
		
		Set<Esame> parziale = new HashSet<>(); //parziale è la soluzione che andremo a costruire man mano
	   cerca1(parziale,numeroCrediti,0);
		//cerca2(parziale,numeroCrediti,0);
		return soluzioneMigliore;	
		
	}
    
	//complessita N! comme per gli anagrammi
	public void cerca1(Set<Esame> parziale, int m, int livello) {
		//generiamo i casi terminali
		int crediti = sommaCrediti(parziale); //num di crediti della soluzione parziale attuale
		
		if(crediti>m) {
			return;   //parziale non valida, e questo albero lo lascio perdere
		}
		
		if(crediti==m) {
			double media = calcolaMedia(parziale);
			  if(media>mediaMigliore) {  //soluzione parziale migliore di quella che avevamo prima
				  soluzioneMigliore = new HashSet<>(parziale);
				  mediaMigliore= media;
			  }
			 // return;
		}
		
		//se sono arrivato qui questo significa che m<crediti 
		//ma c'è anche un altro caso in cui non ci sono piu esami da aggiungere
		if(livello==partenza.size()) {
			return;
		}
		
		/*for(Esame e: partenza) {
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca1(parziale, m, livello+1);
				parziale.remove(e);
	      }
		}*/
		List<Esame> primiEsami = new ArrayList<>();
		
		  for(int i=0; i<partenza.size(); i++) { //si può migliorare
			  
			  primiEsami = partenza.subList(0, i);
		    if(!parziale.contains(partenza.get(i)) && !primiEsami.contains(partenza.get(i))){
				  parziale.add(partenza.get(i));
				  cerca1(parziale, m, livello+1);
				  parziale.remove(partenza.get(i));
		     }
		  }
		
	}
	
	/*
	 * cui l'idea èdi considerare la lista degli esami dal primo verso l'ultimo
	 * complessita : 2^N
	 */
public void cerca2(Set<Esame> parziale, int m, int livello) { 
	//generiamo i casi terminali
			int crediti = sommaCrediti(parziale); //num di crediti della soluzione parziale attuale
			
			if(crediti>m) {
				return;   //parziale non valida, e questo albero lo lascio perdere
			}
			
			if(crediti==m) {
				double media = calcolaMedia(parziale);
				  if(media>mediaMigliore) {  //soluzione parziale migliore di quella che avevamo prima
					  soluzioneMigliore = new HashSet<>(parziale);
					  mediaMigliore= media;
				  }
				 // return;
			}
			
			//se sono arrivato qui questo significa che m<crediti 
			//ma c'è anche un altro caso in cui non ci sono piu esami da aggiungere
			if(livello==partenza.size()) {
				return;
			}
			
			parziale.add(partenza.get(livello));
			cerca2(parziale, m, livello+1); //vado avanti con la ricorsione, fin quando arriverò a N che si iniziera a togliere gli elementi
			
			parziale.remove(partenza.get(livello));
			cerca2(parziale,m,livello+1);
			
   }
	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;  //punteggio
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
