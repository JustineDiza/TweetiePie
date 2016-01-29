package com.accenture.pdc.digital.sf.bigdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import twitter4j.Place;
import twitter4j.Status;

public class Output {
	static final String PIPE_DELIMITER = "|";
	
	public static void toTXT(ArrayList<Status> tweets, String searchQuery) {
		try {
			File file = new File(searchQuery + "_" + new SimpleDateFormat("MM-dd-YYYY_HHmm").format(new Date()) + ".txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
			bw.write("Tweet ID" + PIPE_DELIMITER +
					"Date" + PIPE_DELIMITER +
					"Hour" + PIPE_DELIMITER +
					"User Name" + PIPE_DELIMITER +
					"Nickname" + PIPE_DELIMITER +
					"Tweet Content" + PIPE_DELIMITER +
					"Country" + PIPE_DELIMITER + "\n");
			
			for (int i = 0; i < tweets.size(); i++) {
				Status t = (Status) tweets.get(i);
				String username = t.getUser().getScreenName();
				String nickname = t.getUser().getName();
				String msg = t.getText();
				Date date = t.getCreatedAt();
				String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String hour = new SimpleDateFormat("HH:mm").format(date);
				long id = t.getId();
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
						country + "\n");
			}
		} catch (IOException e) {
			System.err.println("Error while writing file (Output.toCSV)");
			e.printStackTrace();
		}
	}
}
