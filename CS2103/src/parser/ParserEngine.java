package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class ParserEngine {
	
	private static Logger logger= Logger.getLogger(ParserEngine.class);

	public static void main (String args[]) {
		
		String inputString = null;
		BufferedReader reader;
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Input string:");
		try {
			inputString = reader.readLine();
		}
		catch(IOException ioe) {
			System.out.println("An unexpected error occured for your input");
		}
		
		
		Parser parserObject = new Parser();
		
		logger.debug(parserObject.fetchTaskId(inputString));
		
		/*
		DateParser dpObject = new DateParser();
		dpObject.dummyFunction();
		*/
		/*
		Parser pObject = new Parser();
		pObject.dummyFunction();
		*/
	}
}
