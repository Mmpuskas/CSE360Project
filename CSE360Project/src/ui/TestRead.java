package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class TestRead {
	String introText;
	
	public static void main(String[] args) throws IOException 
	{
		TestRead tr = new TestRead();
		tr.initEvents();
	}

	public void initEvents() throws IOException
	{
		String line = null;
		String path = new File("").getAbsolutePath() + "/src/assets/EventText.txt";
		String fullText = new String();
		introText = new String();
	
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(path));
			while((line = br.readLine()) != null)
			{
				fullText += line + "\n";
			}
		}
		catch(FileNotFoundException ex){
			System.out.println("ERROR: Event file not found.");
		}
		
		String intro = fullText.substring(fullText.indexOf('$') + 2, fullText.indexOf('@'));
		
		System.out.println(intro);
	}
}