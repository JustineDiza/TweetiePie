package com.accenture.pdc.digital.sf.bigdata;

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

		public static void main(String args[]){

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setOAuthConsumerKey("S5iE1Cd5tTGZsd2rBNLqSQ");
			cb.setOAuthConsumerSecret("XrlbAjPwdY98jebtb1WXCoFiOUmOSz6F3sVsqsmOfdc");
			cb.setOAuthAccessToken("412965545-86BpIFq6P2jU7kWDJVKBfL9AFYmzgoxNlDV3JmQZ");
			cb.setOAuthAccessTokenSecret("EraAs5QdqSrWeOi3VALnL3bGWPQZ8qB3qq5wtt9DU");

			Twitter twitter = new TwitterFactory(cb.build()).getInstance();

			try {
			User u= twitter.showUser("KaekoEspiritu");

			String user = u.getScreenName();
			String location = u.getLocation();
			String description = u.getDescription();

			System.out.println("Namn: " + user);
			System.out.println("Location: " + location);
			System.out.println("Description: " + description);

			}

			catch (TwitterException te) {
			System.out.println("Couldn't connect: " + te);
			}
		}
}
