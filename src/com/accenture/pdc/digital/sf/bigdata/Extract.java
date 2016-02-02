package com.accenture.pdc.digital.sf.bigdata;

/*
 * TweetiePie main class. It accepts a text file of hashtags/mentions.
 * Primary purpose: to extract significant user sentiment.
 * Output file is in the form of .CSV
 */

import java.util.ArrayList;
import java.util.List;

import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Extract {
	
	// Connection configuration to Twitter API
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
		
		// TO CHANGE: Main class to accept ARGS instead of static values; in cases that registered account reaches limit
		// Sets values  for keys and tokens, best to keep this hidden/encapsulate the keys and tokens
		String consumerKey = "VGBD5O2Y4TGWwwFrVSFQeH8oA";
		String consumerSecret = "gln71DwWHt49PV4PzSKr7in6QrGFBnX9QzuCI0lo7uoT3nuiUD";
		String accessToken = "303762449-dQvHXhvtz3AlaOWK2vECkglHNhLE62IIbXxSWEel";
		String accessTokenSecret = "APvYs2OQLcZYs16mZBPrIKLqzGkN3UlfjIuPpJ9ONK4Kd";
		
		// Setting connection
		Twitter twitter = initConfiguration(consumerKey, consumerSecret,accessToken, accessTokenSecret);
		
		
		SearchQuery sq = new SearchQuery(twitter);
		
		// List of lists of searches for each hashtag
		List<ArrayList<Status>> listOfLists = new ArrayList<ArrayList<Status>>();
		
		// Hashtags/usernames list
		if(args.length<0){
			System.err.println("Arguments Required: Text file containing the list of hashtags/username");
			System.exit(1);
		}
		
		List<String> hashtags = Utils.getHashtags(args[0]);
		
		// Create a temporary list for the output hashtag/username column
		List<String> outputHashtagColumn = new ArrayList<String>();
		
		// Search for hashtags
		for(String hashtag : hashtags) {
			ArrayList<Status> tweets = sq.getResults(hashtag);
			
		    if(tweets.size()>0) {
				listOfLists.add(tweets);
				outputHashtagColumn.add(hashtag);
			}
			System.out.println();
		}
		
		// output to CSV
		Output.consolidated(listOfLists,outputHashtagColumn);
	}
}
