package com.accenture.pdc.digital.sf.bigdata;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class SearchQuery {
	private Twitter twitter;
	private String searchQuery;
	
	public SearchQuery(Twitter twitter) {
		this.twitter = twitter;
	}
	
	public ArrayList<Status> getResults(String searchQuery) {
		Query query = new Query(searchQuery);
		int numberOfTweets = 9999;
		long lastID = Long.MAX_VALUE;

		ArrayList<Status> tweets = new ArrayList<Status>();
		int lastTweetSize = -1;
		while (tweets.size() < numberOfTweets) {
			
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
