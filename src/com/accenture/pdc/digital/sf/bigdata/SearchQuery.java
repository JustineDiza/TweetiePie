package com.accenture.pdc.digital.sf.bigdata;

/*
 * TweetiePie search engine. It searches for relevant tweets.
 * Primary purpose: to extract and handle subsequent errors.
 * Output is an array list of tweets.
 */

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class SearchQuery {
	
	private Twitter twitter;
	private String searchQuery;
	
	// Sets Twitter configuration for searching purposes
	public SearchQuery(Twitter twitter) {
		this.twitter = twitter;
	}
	
	// Gets results of given hashtag/mention
	public ArrayList<Status> getResults(String searchQuery) throws TwitterException, InterruptedException {
		
		Query query = new Query(searchQuery);
		int numberOfTweets = 5000; // Limit of tweets to search
		long lastID = Long.MAX_VALUE; // Assume last attainable ID
		
		ArrayList<Status> tweets = new ArrayList<Status>();
		int lastTweetSize = -1; // Added to avoid infinite loop
		
		while (tweets.size() < numberOfTweets) {
			
			// Handles Twitter api limit
			RateLimitStatus rls = twitter.getRateLimitStatus().get("/search/tweets");
			//System.out.println("Limit:" + rls.getLimit() + " Remaining:" + rls.getRemaining() + " SecToReset:" + rls.getSecondsUntilReset());
			
			if(rls.getRemaining()<3) {
				System.out.println("Rate Limit Exceeded. Sleeping for " + rls.getSecondsUntilReset() + " seconds.");
				//Thread.sleep((rls.getSecondsUntilReset()*1000)+2000);
				for(int i=1; i<rls.getSecondsUntilReset()+2; i++) {
					System.out.print("\b\b\b\b\b\b\b\b");
					System.out.print(i);
					Thread.sleep(1000);
				}
			}
			
			// maximum of 100 tweets returned per page
			if (numberOfTweets - tweets.size() > 100)
				query.setCount(100);
			else
				query.setCount(numberOfTweets - tweets.size());
			
			
			try {
				QueryResult result = twitter.search(query);
				tweets.addAll(result.getTweets()); //removed this for now, need to add location to each user who tweeted before adding to the list
				
				System.out.println("Gathered " + tweets.size() + " tweets for " + searchQuery);
				for (Status t : tweets) {
					if (t.getId() < lastID)
						lastID = t.getId();
					
//					// Adding location to search query
//					if(!t.getUser().getLocation().isEmpty()){
//						System.out.println("Location of user " + t.getUser() +" is at: " + t.getUser().getLocation());
//						Thread.sleep(1000);
//					}
//					
//					// tweets.add(t.getUser().getLocation());
				}
				
				
			} catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}
			
			query.setMaxId(lastID - 1); // returns tweets with status ID lower than specified value
			
			// If there are no more tweets extracted, either show total or show no tweets found
			if(lastTweetSize == tweets.size()) {
				if(tweets.size() == 0)
					System.out.println("No tweets found for " + searchQuery);
				else
					System.out.println("Total tweets found for " + searchQuery + ": " + tweets.size());
				break;
			}
			
			lastTweetSize = tweets.size();
		}
		
		return tweets;
	}
}
