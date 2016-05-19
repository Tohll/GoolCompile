package gool;

import gool.ast.core.ClassDef;
import gool.generator.GeneratorHelper;
import gool.generator.GoolGeneratorController;
import gool.generator.common.GeneratorMatcher;
import gool.generator.common.Platform;
import gool.generator.cpp.CppPlatform;
import gool.generator.csharp.CSharpPlatform;
import gool.parser.java.JavaParser;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import logger.Log;

public class GOOLHTMLCompile {

	public GOOLHTMLCompile () {


	}

	//*************************
	public static Map<String, String> launchTranslation(String inputLang,
			String outputLang, Map<String, String> input)
					throws Exception {

		ParseGOOL parser = null;
		Collection<ClassDef> goolPort = null;
		Platform plt = null;

		if (inputLang.equalsIgnoreCase("java")) {
			parser = new JavaParser();
		}
		/*else if(inputLang.equalsIgnoreCase("cpp")){
			parser = new CppParser();
		}*/
		else{
			throw new Exception("Unknown input language.");
		}

		//Recognition
		goolPort = concreteToAbstractGool(parser, input);


		if (outputLang.equalsIgnoreCase("cpp")) {
			plt = CppPlatform.getInstance();
		}
		else if(outputLang.equalsIgnoreCase("c#")){
			plt = CSharpPlatform.getInstance();
		}
		else{
			throw new Exception("Unknown output language.");
		}

		//Generation
		return abstractGool2Target(goolPort, plt);
	}
	
	private static Collection<ClassDef> concreteToAbstractGool(
			ParseGOOL parserIn, Map<String, String> input) throws Exception {
		GeneratorMatcher.init(null);
		GoolGeneratorController.setCodeGenerator(null);
		return parserIn.parseGool(input);
	}
	
	private static Map<String, String> abstractGool2Target(
			Collection<ClassDef> classDefs, Platform plt){
		GeneratorMatcher.init(plt);
		GoolGeneratorController.setCodeGenerator(plt.getCodePrinter().getCodeGenerator());	
		for(ClassDef cl : classDefs){
			Log.d(String.format("<GOOLCompiler - abstractGool2Target> Platform %s", plt.getName()));
			cl.setPlatform(plt);
		}
		return GeneratorHelper.printClassDefs(classDefs);
	}
	
	//*************************
	/*public Map<String, String> launchHTMLTranslation (String inputLang, String outputLang, String input) throws Exception {

		ParseGOOL Parser = null;
		Collection<ClassDef> GOOLPort = null;
		Map<String, String> toTarget = null;

		if (inputLang.equalsIgnoreCase("java")) {

			Parser = new JavaParser();

			if (outputLang.equalsIgnoreCase("c++")) {

				GOOLPort = concretePlatformeToAbstractGool(
						Parser,
						CppPlatform.getInstance(),
						input
						);

				toTarget = GeneratorHelper.printClassDefs(GOOLPort);

			}

		}

		return toTarget;

	}
	
	private Collection<ClassDef> concretePlatformeToAbstractGool(
			ParseGOOL parserIn, Platform outPlatform, String input) throws Exception {
		return parserIn.parseGool(outPlatform, input);
	}*/

	public String commandBuilder (Map<String,String> fichiers) {
		
		Map.Entry<String, String> firstFile = fichiers.entrySet().iterator().next();
		String compileFile = firstFile.getKey();   /* docker run gcc:4.9*/
		
		String commande = "docker run reaverproject/gcc-boost:5_1_0-1.60.0 /bin/bash -c '";
		//String commande = "/bin/bash -c '";
		
		for (Map.Entry<String, String> entree : fichiers.entrySet()) {
			
			commande += "echo -e \"" + StringEscapeUtils.escapeJava(entree.getValue()) + "\" > " + entree.getKey() + " && ";
		}
		commande += "g++ " + compileFile + "&& ./a.out'";
		//commande += "g++ " + compileFile + " && ./a.out " +"'";
		
		return commande;
	}
	
	
}
