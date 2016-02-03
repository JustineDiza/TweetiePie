package com.accenture.pdc.digital.sf.bigdata;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Place;
import twitter4j.Status;

/*
 * TweetiePie coding playground. It's a freestyle class that can do anything required.
 * TESTING: obtaining location of user
 * SUCCESS: 100%
 * */

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class Tweetsie {

		public static void main(String args[]) throws TwitterException{

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setOAuthConsumerKey("S5iE1Cd5tTGZsd2rBNLqSQ");
			cb.setOAuthConsumerSecret("XrlbAjPwdY98jebtb1WXCoFiOUmOSz6F3sVsqsmOfdc");
			cb.setOAuthAccessToken("412965545-86BpIFq6P2jU7kWDJVKBfL9AFYmzgoxNlDV3JmQZ");
			cb.setOAuthAccessTokenSecret("EraAs5QdqSrWeOi3VALnL3bGWPQZ8qB3qq5wtt9DU");

			Twitter twitter = new TwitterFactory(cb.build()).getInstance();

			int pageno = 1;
			String user1 = "cnn";
			List statuses1 = new ArrayList();


			while (true) {

			  try {
			    int size = statuses1.size(); 
			    Paging page = new Paging(pageno++, 100);
			    statuses1.addAll(twitter.getUserTimeline(user1, page));

				Status status = (Status)statuses1.get(pageno);
			    
			    System.out.println("Taking quite a while here!");
				System.out.println(status.getUser().getName() + ": " + status.getText());
			    if (statuses1.size() == size)
			      break;
			  }
			  catch(TwitterException e) {

			    e.printStackTrace();
			  }
			}

			System.out.println("Total: "+statuses1.size());
			System.out.println(twitter.getUserTimeline(user1));
		}
}
