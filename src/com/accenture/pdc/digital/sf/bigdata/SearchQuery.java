package com.accenture.pdc.digital.sf.bigdata;

/*
 * TweetiePie search engine. It searches for relevant tweets.
 * Primary purpose: to extract and handle subsequent errors.
 * Output is an array list of tweets.
 */

import java.util.ArrayList;
import java.util.List;

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
		
		int numberOfTweets = 500; // Limit of tweets to search
		long lastID = Long.MAX_VALUE; // Assume last attainable ID
		
		ArrayList<Status> tweets = new ArrayList<Status>();
		int lastTweetSize = -1; // Added to avoid infinite loop
		
		while (tweets.size() < numberOfTweets) {
			
			// Handles Twitter API limit
			RateLimitStatus rls = twitter.getRateLimitStatus().get("/search/tweets");
			System.out.println("Rate Limit Status: " + rls.getRemaining()  + "/" + rls.getLimit() + " SecToReset:" + rls.getSecondsUntilReset());
			
			// Let thread sleep to wait for refresh for rate limit
			if(rls.getRemaining()==50 && rls.getSecondsUntilReset()>0) {
				System.out.println("Rate Limit Exceeded. Sleeping for " + rls.getSecondsUntilReset() + " seconds.");
				//Thread.sleep((rls.getSecondsUntilReset())*1000);
				
				// Simple Countdown Timer
				int time = rls.getSecondsUntilReset();
				        long delay = time * 1000;

				        do{
				            Thread.sleep(1000);
				            System.out.println("\b\b\b\b" + time / 1);
				            time = time - 1;
				            delay = delay - 1000;
				        }while (delay != 0);

			}
			
			// maximum of 100 tweets returned per page
			if (numberOfTweets - tweets.size() > 100)
				query.setCount(100);
			else
				query.setCount(numberOfTweets - tweets.size());
			
			
			try {
				// for hashtags
				QueryResult result = twitter.search(query);
				tweets.addAll(result.getTweets());
				
				
				System.out.println("Gathered " + tweets.size() + " tweets for " + searchQuery);
				System.out.println("-------------------------");
				for (Status t : tweets) {
					if (t.getId() < lastID)
						lastID = t.getId();
				}
				
				
			} catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}
			
			query.setMaxId(lastID - 1); // returns tweets with status ID lower than specified value
			
			// If there are no more tweets extracted, either show total or show no tweets found
			if(lastTweetSize == tweets.size()) {
				if(tweets.size() == 0)
					System.out.println("No tweets found for " + searchQuery);
				break;
			}
			
			lastTweetSize = tweets.size();
		}
		System.out.println("Total tweets found for " + searchQuery + ": " + tweets.size());
		return tweets;
	}
}
