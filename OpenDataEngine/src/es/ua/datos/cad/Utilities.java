package es.ua.datos.cad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Object;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.io.*;

import java.util.*;


import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;




public class Utilities {
	
	public List<String> stopwords = new ArrayList<String>(Arrays.asList(
                       "a","acá","ahí","ajena","ajenas","ajeno","ajenos","al","algo","algún","alguna","algunas",
                       "alguno","algunos","allá","alli","allí","ambos","ampleamos","ante","antes","aquel","aquella",
                       "aquellas","aquello","aquellos","aqui","aquí","arriba","asi","atras","aun","aunque","bajo",
                       "bastante","bien","cabe","cada","casi","cierta","ciertas","cierto","ciertos","como","cómo",
                       "con","conmigo","conseguimos","conseguir","consigo","consigue","consiguen","consigues","contigo",
                       "contra","cual","cuales","cualquier","cualquiera","cualquieras","cuan","cuán","cuando","cuanta",
                       "cuánta","cuantas","cuántas","cuanto","cuánto","cuantos","cuántos","de","dejar","del","demás",
                       "demas","demasiada","demasiadas","demasiado","demasiados","dentro","desde","donde","dos","el",
                       "él","ella","ellas","ello","ellos","empleais","emplean","emplear","empleas","empleo","en",
                       "encima","entonces","entre","era","eramos","eran","eras","eres","es","esa","esas","ese","eso",
                       "esos","esta","estaba","estado","estais","estamos","estan","estar","estas","este","esto",
                        "estos","estoy","etc","fin","fue","fueron","fui","fuimos","gueno","ha","hace","haceis",
                       "hacemos","hacen","hacer","haces","hacia","hago","hasta","incluso","intenta","intentais","intentamos",
                       "intentan","intentar","intentas","intento","ir","jamás","junto","juntos","la","largo","las",
                       "lo","los","mas","más","me","menos","mi","mía","mia","mias","mientras","mio","mío","mios","mis",
                       "misma","mismas","mismo","mismos","modo","mucha","muchas","muchísima","muchísimas","muchísimo","muchísimos",
                       "mucho","muchos","muy","nada","ni","ningun","ninguna","ningunas","ninguno","ningunos","no","nos",
                       "nosotras","nosotros","nuestra","nuestras","nuestro","nuestros","nunca","os","otra","otras","otro",
                       "otros","para","parecer","pero","poca","pocas","poco","pocos","podeis","podemos","poder","podria","podriais",
                       "podriamos","podrian","podrias","por","porqué","porque","primero","primerodesde","puede","pueden",
                       "puedo","pues","que","qué","querer","quien","quién","quienes","quienesquiera","quienquiera","quiza",
                       "quizas","sabe","sabeis","sabemos","saben","saber","sabes","se","segun","ser","si","sí","siempre","siendo",
                       "sin","sín","sino","so","sobre","sois","solamente","solo","somos","soy","sr","sra","sres","sta","su",
                       "sus","suya","suyas","suyo","suyos","tal","tales","también","tambien","tampoco","tan","tanta","tantas",
                       "tanto","tantos","te","teneis","tenemos","tener","tengo","ti","tiempo","tiene","tienen","toda","todas",
                       "todo","todos","tomar","trabaja","trabajais","trabajamos","trabajan","trabajar","trabajas","trabajo",
                       "tras","tú","tu","tus","tuya","tuyo","tuyos","ultimo","un","una","unas","uno","unos","usa","usais","usamos",
                       "usan","usar","usas","uso","usted","ustedes","va","vais","valor","vamos","van","varias","varios","vaya","verdad",
                       "verdadera","vosotras","vosotros","voy","vuestra","vuestras","vuestro","vuestros","y","ya","yo"));
	
	public List<String> stopwordsVa = new ArrayList<String>(Arrays.asList("de","es","i","a","o","un","una","unes","uns","un","tot","també",
						"altre","algun","alguna","alguns","algunes","ser","és","soc","ets","som","estic","està","estem","esteu","estan",
						"com","en","per","perquè","que","estat","estava","ans","abans","éssent","ambdós","però","poder","potser","puc",
						"podem","podeu","poden","vaig","va","van","fer","faig","fa","fem","feu","fan","cada","fi","inclòs","primer","des",
						"conseguir","consegueixo","consigueix","consigueixes","conseguim","consigueixen","anar","haver","tenir","tinc",
						"te","tenim","teniu","tene","el","la","les","els","seu","aquí","meu","teu","ells","elles","ens","nosaltres",
						"vosaltres","si","dins","sols","solament","saber","saps","sap","sabem","sabeu","saben","últim","llarg","bastant",
						"fas","molts","aquells","aquelles","seus","llavors","sota","dalt","ús","molt","era","eres","erem","eren","mode",
						"bé","quant","quan","on","mentre","qui","amb","entre","sense","jo","aquell"));

	
	public List<String> stopwordsEn =new ArrayList<String>(Arrays.asList("a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", 
			"alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", 
            "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", 
            "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", 
            "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt",
            "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else",
            "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", 
            "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", 
            "front", "full", "further", "get", "give", "go", "had", "has", "hasnt",
            "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", 
            "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", 
            "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", 
            "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", 
            "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", 
            "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", 
            "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps",
            "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she",
            "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", 
            "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", 
            "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", 
            "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", 
            "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", 
            "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever",
            "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", 
            "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet",
            "you", "your", "yours", "yourself", "yourselves","1","2","3","4","5","6","7","8","9","10","1.","2.","3.","4.","5.","6.","11",
            "7.","8.","9.","12","13","14","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "terms","CONDITIONS","conditions","values","interested.","care","sure",".","!","@","#","$","%","^","&","*","(",")","{","}","[","]",":",";",",","<",".",">","/","?","_","-","+","=",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "contact","grounds","buyers","tried","said,","plan","value","principle.","forces","sent:","is,","was","like",
            "discussion","tmus","diffrent.","layout","area.","thanks","thankyou","hello","bye","rise","fell","fall","psqft.","http://","km","miles"));
	
	public List<String> removeStopWordsTXT(List<String> splitDescription){
		
		
		List<String> descWithoutStopwords = new ArrayList<String>();
		
		try{

			for(int i = 0; i < splitDescription.size();i++){
				if(!stopwords.contains(splitDescription.get(i))){
					//System.out.println(splitDescription.get(i));
					descWithoutStopwords.add(splitDescription.get(i));
				}
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
			
		
		return descWithoutStopwords;
	}

	//Elimina las StopWords en Valenciano
	public List<String> removeStopWordsTXTVa(List<String> splitDescription){
		
		
		List<String> descWithoutStopwordsVa = new ArrayList<String>();
		
		try{

			for(int i = 0; i < splitDescription.size();i++){
				if(!stopwordsVa.contains(splitDescription.get(i))){
					//System.out.println(splitDescription.get(i));
					descWithoutStopwordsVa.add(splitDescription.get(i));
				}
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
			
		
		return descWithoutStopwordsVa;
	}
	
	//Elimina las StopWords en Ingles
	public List<String> removeStopWordsTXTEn(List<String> splitDescription){
			
			
			List<String> descWithoutStopwordsEn = new ArrayList<String>();
			
			try{

				for(int i = 0; i < splitDescription.size();i++){
					if(!stopwordsEn.contains(splitDescription.get(i))){
						//System.out.println(splitDescription.get(i));
						descWithoutStopwordsEn.add(splitDescription.get(i));
					}
				}
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
				
			
			return descWithoutStopwordsEn;
		}
	
	//Funcion que comprueba si lo que se le pasa se puede convertir en numero
	public static boolean isNumeric(String input) {
		  try {
		    Integer.parseInt(input);
		    return true;
		  }
		  catch (NumberFormatException e) {
		    // s is not numeric
		    return false;
		  }
	}
	
	
}
