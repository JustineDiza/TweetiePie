package com.accenture.pdc.digital.sf.bigdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Status;

public class Utils {

	public static List<String> getHashtags(String filePath) {
		List<String> hashtags = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(
							new File(filePath)));
			String hashtag;
			
			try {
				while((hashtag = br.readLine()) != null) 
					hashtags.add(hashtag);

			} catch (IOException e) {
				System.err.println("Error while reading the file");
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found (" + filePath + ")");
			e.printStackTrace();
		}
		
		return hashtags;
	}
}
