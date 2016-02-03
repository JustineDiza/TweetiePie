package com.accenture.pdc.digital.sf.bigdata;

/*
 * TweetiePie input file reader. It reads the input text file containing hashtags/mentions.
 * Primary purpose: to convert text into String hashtag.
 * Output is a list of hashtags.
 * */

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

	// To get hashtags
	public static List<String> getHashtags(String filePath) {
		List<String> hashtags = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(
							new File(filePath)));
			String hashtag;
			
			try {
				// Getting hashtags that start with #
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
	
	// To get mentions
	public static List<String> getMentions(String filePath){

		List<String> mentions = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(
							new File(filePath)));
			String mention;
			
			try {
				// Getting hashtags that start with @
				while((mention = br.readLine()) != null)
					mentions.add(mention);
				
			} catch (IOException e) {
				System.err.println("Error while reading the file");
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found (" + filePath + ")");
			e.printStackTrace();
		}
		
		return mentions;
		}
}

