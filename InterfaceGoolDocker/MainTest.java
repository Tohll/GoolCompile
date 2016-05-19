package gool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public class MainTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		String srcinput = new String();
		try {
			srcinput = readFile("/home/arrivault/Codes/GOOL_ALL/GOOL_Github/tests/GOOLINPUTJAVA/helloworld.java");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map <String, String> input = new HashMap<String,String>();
		input.put("helloworld.java", srcinput);
		
		Map <String, String> result = null;
		
		GOOLHTMLCompile compiler = new GOOLHTMLCompile();
		try {
			
			result = GOOLHTMLCompile.launchTranslation("java", "cpp", input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String commande = compiler.commandBuilder(result);
		System.out.println(commande);
		//System.out.println(StringEscapeUtils.escapeJava(commande));
		
		/*for (Map.Entry entree : result.entrySet()) {
			
			System.out.println(entree.getKey());
			System.out.println();
			System.out.println(entree.getValue());
			
		}*/
		
		Runtime runtime = Runtime.getRuntime();
		final Process process;
		
		process = runtime.exec(new String [] {"/bin/bash","-c", commande}); 
		
		//process = runtime.exec(new String [] {"/bin/bash","-c", "docker run gcc:4.9 /bin/bash -c echo 1 && echo 2"});
		
		new Thread() {
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = "";
					try {
						while((line = reader.readLine()) != null) {
							// Traitement du flux de sortie de l'application
							
							System.out.println(line);
							
						}
					} finally {
						reader.close();
					}
				} catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}.start();

		// Consommation de la sortie d'erreur de l'application externe dans un Thread separe
		new Thread() {
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					String line = "";
					try {
						while((line = reader.readLine()) != null) {
							// Traitement du flux d'erreur de l'application si besoin est
							
							System.err.println(line);
							
						}
						
					} finally {
						reader.close();
					}
				} catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}.start();
		
	}
	
	static String readFile(String path) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded);
			}

}
