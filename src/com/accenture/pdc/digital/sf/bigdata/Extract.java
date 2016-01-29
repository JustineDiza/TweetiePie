package com.accenture.pdc.digital.sf.bigdata;

import java.util.ArrayList;
import java.util.Date;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Extract {
	
	public static Twitter initConfiguration(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		 .setOAuthConsumerKey(consumerKey)
		 .setOAuthConsumerSecret(consumerSecret)
		 .setOAuthAccessToken(accessToken)
		 .setOAuthAccessTokenSecret(accessTokenSecret);
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		return twitter;
	}
	
	public static void main(String[] args) throws Exception {
		String consumerKey = "VGBD5O2Y4TGWwwFrVSFQeH8oA"; //args0
		String consumerSecret = "gln71DwWHt49PV4PzSKr7in6QrGFBnX9QzuCI0lo7uoT3nuiUD";
		String accessToken = "303762449-dQvHXhvtz3AlaOWK2vECkglHNhLE62IIbXxSWEel";
		String accessTokenSecret = "APvYs2OQLcZYs16mZBPrIKLqzGkN3UlfjIuPpJ9ONK4Kd";
		//String searchQuery = "Patrick Joshua";
		
		Twitter twitter = initConfiguration(consumerKey,
				consumerSecret,
				accessToken,
				accessTokenSecret);
		SearchQuery sq = new SearchQuery(twitter);
		
		//Search for hashtags
		for(String hashtag : Utils.getHashtags(args[0])) {
			ArrayList<Status> tweets = sq.getResults(hashtag);

			//output to csv
			if(tweets.size() > 0)
				Output.toTXT(tweets, hashtag);
			
			//let the API rest
			for(int i=0; i<10; i++) {
				System.out.print(".");
				Thread.sleep(1000);
			}
			System.out.println();
		}
	}
}
