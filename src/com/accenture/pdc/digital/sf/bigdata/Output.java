package com.accenture.pdc.digital.sf.bigdata;

/*
 * TweetiePie output file writer. It accepts an array list of lists.
 * Primary purpose: to write data into specified format.
 * Output is in .CSV form.
 * */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Place;
import twitter4j.Status;

public class Output {
	static final String PIPE_DELIMITER = "|";
	
//	public static void toTXT(ArrayList<Status> tweets, String searchQuery) {
//		try {
//			File file = new File(searchQuery + "_" + new SimpleDateFormat("MM-dd-YYYY_HHmm").format(new Date()) + ".txt");
//			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//		
//			bw.write("Tweet ID" + PIPE_DELIMITER +
//					"Date" + PIPE_DELIMITER +
//					"Hour" + PIPE_DELIMITER +
//					"User Name" + PIPE_DELIMITER +
//					"Nickname" + PIPE_DELIMITER +
//					"Tweet Content" + PIPE_DELIMITER +
//					"Place" + PIPE_DELIMITER + 
//					"Hashtag/Username" + "\n");
//			
//			for (int i = 0; i < tweets.size(); i++) {
//				Status t = (Status) tweets.get(i);
//				String username = t.getUser().getScreenName();
//				String nickname = t.getUser().getName();
//				String msg = t.getText();
//				Date date = t.getCreatedAt();
//				String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
//				String hour = new SimpleDateFormat("HH:mm").format(date);
//				long id = t.getId();
//				String country = t.getUser().getLocation();
//				Place place = t.getPlace();
//				if(place!=null)
//					country = place.getCountryCode();
//				
//				bw.write(id + PIPE_DELIMITER + 
//						day + PIPE_DELIMITER +
//						hour + PIPE_DELIMITER +
//						username + PIPE_DELIMITER +
//						nickname + PIPE_DELIMITER +
//						msg + PIPE_DELIMITER +
//						country + PIPE_DELIMITER +
//						searchQuery + "\n");
//			}
//		} catch (IOException e) {
//			System.err.println("Error while writing file (Output.toCSV)");
//			e.printStackTrace();
//		}
//	}
//	

	public static void consolidated(List<ArrayList<Status>> list, List<String> hashtags) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(new SimpleDateFormat("MM-dd-yyyy_HH-mm").format(new Date()) + "_Consolidated.txt")));
			
			bw.write("Tweet ID" + PIPE_DELIMITER +
					"Date" + PIPE_DELIMITER +
					"Hour" + PIPE_DELIMITER +
					"User Name" + PIPE_DELIMITER +
					"Nickname" + PIPE_DELIMITER +
					"Tweet Content" + PIPE_DELIMITER +
					"Location" + PIPE_DELIMITER + 
					"Country" + PIPE_DELIMITER +
					"Hashtag/Username" + "\n");
			for(int i=0; i<list.size(); i++) {
				ArrayList<Status> tweets = list.get(i);
				String hashtag = hashtags.get(i);
				for (int j = 0; j < tweets.size(); j++) {
					Status t = (Status) tweets.get(j);
					String username = t.getUser().getScreenName();
					String nickname = t.getUser().getName();
					String msg = t.getText();
					Date date = t.getCreatedAt();
					String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
					String hour = new SimpleDateFormat("HH:mm").format(date);
					long id = t.getId();
					String location = t.getUser().getLocation();
					String country = "";
					Place place = t.getPlace();
					if(place!=null)
						country = place.getCountryCode();
					
					bw.write(id + PIPE_DELIMITER + 
							day + PIPE_DELIMITER +
							hour + PIPE_DELIMITER +
							username + PIPE_DELIMITER +
							nickname + PIPE_DELIMITER +
							msg + PIPE_DELIMITER +
							location + PIPE_DELIMITER +
							country + PIPE_DELIMITER +
							hashtag + "\n");
				}
			}
			System.out.println("Output file has been created at folder TweetiePie.");
		} catch (IOException e) {
			System.err.println("Error while writing consolidated file.");
			e.printStackTrace();
		}
	
	}
}
