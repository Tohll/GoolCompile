import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

	public static void main(String[] args) throws Exception {

		/* Commande qui marche en bash : docker run gcc:4.9 /bin/bash -c 'printf "#include <stdio.h> \nint main(void) { printf(\"Hello world\\\\n\");}"|gcc -xc - && ./a.out'; */
		Runtime runtime = Runtime.getRuntime();
		final Process process;
		
		String src = "#include <stdio.h> \nint main(void) { printf(\"Hello world\\\\n\");} ";
		System.out.println(src);
		//String src = "int main(void) {return 0;};";
		String srcsrc = "docker run gcc:4.9 /bin/bash -c \'printf \"#include <stdio.h> \\\\nint main(void) { printf(\\\"Hello world\\\\\\\n\\\");}\"|gcc -xc - && ./a.out\'";
		System.out.println(srcsrc);
		
		//process = runtime.exec(new String [] {"docker","run","docker-gcc","/bin/bash","echo '" + src2 + "' | gcc -xc -c - "});
		//process = runtime.exec(new String [] {"/bin/sh","-c","docker run docker-gcc echo '"+ src +"' | gcc -xc -c - "});
		process = runtime.exec(new String [] {"/bin/bash","-c",srcsrc});
		
		// Consommation de la sortie standard de l'application externe dans un Thread separe
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

}
