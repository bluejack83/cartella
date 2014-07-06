package com.inqbarna.tablefixheaders.samples;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.samples.adapters.MatrixTableAdapter;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class SimpleTable extends Activity {

	String[] parametriVitaliHeaders = new String[]{
			"Data","Ora","Dolore","PA","FC","TC","HGT","Saturazione ossigeno","Bilancio idrico entrate","Bilancio idrico uscite",
			//"Bilancio idrico totale",
			"Modalità evacuazione","Evacuazione","Esito","Peso","Autore"};
	
    String parametriVitaliValues[][] = new String[][]{
    		// primi parametri da generare
    		new String[]{"","","3","120/80","70","36.5","90","98%","1l","800ml","200ml","spontanea","formate","75kg","Armando De Santis"},
    		new String[]{"","","2","110/70","75","36.3","100","97%","1l","800ml","200ml","spontanea","formate","75kg","Marco Rossi"},
    		new String[]{"","","2","110/80","80","36.6","95","97%","1l","750ml","250ml","spontanea","formate","75kg","Nadia Verdi"},
    		
    		//parametri allarme 3,4,7
    		new String[]{"","","2","160/110","40","36.6","95","85%","1l","750ml","250ml","spontanea","formate","75kg","Nadia Verdi"},
    };
    
    public static <T> T[] concat(T[] first, T[] second) {
    	  T[] result = Arrays.copyOf(first, first.length + second.length);
    	  System.arraycopy(second, 0, result, first.length, second.length);
    	  return result;
    	}
    protected String[][] getValues(){
    	String parametriVitaliGeneratedValues[][] = parametriVitaliValues;
    	for(int m=0;m<4;m++)
    		parametriVitaliGeneratedValues = concat(parametriVitaliGeneratedValues, parametriVitaliGeneratedValues);
    	
    	String[][] ret = new String[parametriVitaliGeneratedValues.length+1][];
    	
    	ret[0]=parametriVitaliHeaders;
    	
    	for(int i=0;i<parametriVitaliGeneratedValues.length;i++)
    		ret[i+1]=parametriVitaliGeneratedValues[i];
    	
    	return ret;
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
		MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<String>(this,getValues());
		tableFixHeaders.setAdapter(matrixTableAdapter);
	}
	
	// cards
	class Terapia {
		Map<Date,String[][]> calendario = new HashMap<Date, String[][]>();
		public Terapia() {
			
			Date today = new Date();
			
			// TODO warning alle 12 del giorno prima
			calendario.put(today,new String[][]{
					new String[]{"Folina","5mg","1","compressa","orale"},
			});
			// TODO alle 6
			calendario.put(today,new String[][]{
					new String[]{"Mepral","40mg","1","fiala","endovena"},
					new String[]{"Targosid","400mg","1","fiala","endovena"},
			});
			
			// TODO alle 8
			calendario.put(today,new String[][]{
					new String[]{"Lioresal","10mg","1","compressa","orale"},
					new String[]{"Keppra","1000mg","1","compressa","orale"},
			});
			// TODO alle 20
			calendario.put(today,new String[][]{
					new String[]{"Lioresal","10mg","1","compressa","orale"},
					new String[]{"Keppra","1000mg","1","compressa","orale"},
			});
		}
	}
	
	// elenco suddiviso in sottoelenchi
	class DiarioClinico{
		// mostriamo solo il giorno attuale
		Map<String,String[]> diario = new HashMap<String, String[]>();
		public DiarioClinico() {
			diario.put("mattina", new String[]{
					"cure igieniche parziali","sostituzione pannolone","posizionamento agocannula",
			});
			diario.put("pomeriggio", new String[]{
					"ossigenoterapia",
			});
			diario.put("notte", new String[]{
					"assistenza al pasto",
			});
			// attività giornaliere
			diario.put("tutto il giorno", new String[]{
					"terapia",
					"parametri vitali e dolore",
					"cateterismi",
					"bilancio idrico 24h"
			});
		}
		//pulsante + per aggiungere attività non programmate
		String[] attivita = new String[]{
				"posizionamento agocannula",
				"rimzione agocannula",
				"gestione tracheostomia",
				"gestione peg",
				"gestione sng",
				"rilevazione peso corporeo",
				"posizionamento su wc",
				"sostituzione pannolone",
				"assistenza incontinenza fecale",
				"assistenza incontinenza urinaria",
				"posizionamento catetere vescicale",
				"rimozione catetere vescicale",
				"cure igieniche totali",
				"cure igieniche parziali",
				"vestire e spogliare",
				"cambio biancheria",
				"passaggio letto-carrozzina",
				"passaggio carrozzina-letto",
				"uso del sollevatore",
				"prevenzione cadute",
				"gestione del paziente agitato",
		};
	}
	
	class CartellaPaziente {
		String dolore[]=new String[]{
				"peggior dolore possibile",
				"dolore moderato",
				"nessun dolore"
		};
	}
	
	class CalcoloRischioLesioniDaPressione {
		// da associare al punteggio del risultato
		
		Map<String,String[]> form = new HashMap<String, String[]>();
		Map<Integer,String> valoriRisultati = new HashMap<Integer, String>(); 
		
		public CalcoloRischioLesioniDaPressione() {
			// i punteggi delle risposte vanno da 1 a 4 per il calcolo finale
			form.put("percezione sensoriale", new String[]{"completamente limitata","molto limitata","leggermente limitata","non limitata"});
			form.put("umidità cutanea", new String[]{
					"costantemente bagnato",
					"spesso bagnato",
					"occasionalmente bagnato",
					"raramente bagnato",
			});
			form.put("attività", new String[]{
					"completamente allettato",
					"in poltrona",
					"cammina occasionalmente",
					"cammina di frequente",
			});
			form.put("mobilità", new String[]{
					"completamente immobile",
					"molto limitata",
					"parzialmente limitata",
					"limitazioni assenti",
			});
			form.put("nutrizione", new String[]{
					"molto povera",
					"inadeguata",
					"adeguata",
					"eccellente",
			});
			form.put("frizione e sfregamento", new String[]{
					"problema",
					"problema potenziale",
					"nessun problema apparente",
					//manca un quarto valore
			});
			
			// valore minimo -> risultato
			valoriRisultati.put(0, "altissimo");
			valoriRisultati.put(9, "alto");
			valoriRisultati.put(13, "medio");
			valoriRisultati.put(16, "basso");
			
		}
	}
}
