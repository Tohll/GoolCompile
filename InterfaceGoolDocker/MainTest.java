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
			srcinput = readFile("/home/artifact/Desktop/HelloWorld.java");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map <String, String> input = new HashMap<String,String>();
		input.put("HelloWorld.java", srcinput);
		
		Map <String, String> result = null;
		
		GOOLHTMLCompile compiler = new GOOLHTMLCompile();
		try {
			
			result = GOOLHTMLCompile.launchTranslation("java", "cpp", input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String commande = compiler.commandBuilder(result);
		System.out.println(commande);
		
		/*for (Map.Entry entree : result.entrySet()) {
			
			System.out.println(entree.getKey());
			System.out.println();
			System.out.println(entree.getValue());
			
		}*/
		
		/*Runtime runtime = Runtime.getRuntime();
		final Process process;
		
		//process = runtime.exec(new String [] {"/bin/bash","-c", commande}); 
		
		process = runtime.exec(new String [] {"/bin/bash","-c", "docker run gcc:4.9 /bin/bash -c echo 1 && echo 2"});*/
		
		
		
	}
	
	static String readFile(String path) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded);
			}

}
