package com.accenture.pdc.digital.sf.bigdata;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class SearchQuery {
	
	private Twitter twitter;
	private String searchQuery;
	
	// Returns searchQuery configuration
	public SearchQuery(Twitter twitter) {
		this.twitter = twitter;
	}
	
	public ArrayList<Status> getResults(String searchQuery) throws TwitterException, InterruptedException {
		
		Query query = new Query(searchQuery);
		int numberOfTweets = 5000;
		long lastID = Long.MAX_VALUE;
		ArrayList<Status> tweets = new ArrayList<Status>();
		int lastTweetSize = -1;
		
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
			
			//
			if (numberOfTweets - tweets.size() > 100)
				query.setCount(100);
			else
				query.setCount(numberOfTweets - tweets.size());
			try {
				QueryResult result = twitter.search(query);
				tweets.addAll(result.getTweets());
				System.out.println("Gathered " + tweets.size() + " tweets for " + searchQuery);
				for (Status t : tweets)
					if (t.getId() < lastID)
						lastID = t.getId();
			} catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}
			;
			query.setMaxId(lastID - 1);
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
