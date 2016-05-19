package gool;

public class MainTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		String commands,errors,output;
		
		ProcessBuilder process = new ProcessBuilder("/bin/bash");
		
		System.out.println(process.redirectInput());
		System.out.println(process.redirectOutput());
		System.out.println(process.redirectError());
		
		/*process.redirectInput(commands);
		process.redirectError(errors);
		process.redirectOutput(output);
		 
		System.out.println(process.redirectInput());
		System.out.println(process.redirectOutput());
		System.out.println(process.redirectError());*/

	}

}
