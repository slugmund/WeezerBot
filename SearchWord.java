package weezerDetect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchWord {
	private String Word;
	
	public SearchWord() {
		Word = "";
	}
	
	public SearchWord(String wordFile) {
		int size = 0;
		
		try(BufferedReader br = new BufferedReader(new FileReader(wordFile))){
			String s;
			while((s = br.readLine()) != null) {
				size++;
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		int randNum = (int)(Math.random()*size);
		
		try(BufferedReader br = new BufferedReader(new FileReader(wordFile))){
			String s;
			int countUp = 0;
			while((s = br.readLine()) != null) {
				if(countUp == randNum) {
					Word = s;
					break;
				}
				countUp++;
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getWord() {
		return Word;
	}
}
