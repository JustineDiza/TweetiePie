# TweetiePie
Simple tweet extractor for data visualization purposes. 

Requires an input text file via Java arguments:
- a text file containing hashtags (#), usernames (@), and keywords (no prefix)

Output file is a text file delimited with pipe character containing columns namely:
- Tweet ID,
- Date,
- Hour,
- Username,
- Nickname,
- Tweet content,
- Location(as indicated in user's profile),
- Country(country code),
- Hashtag/Username (used as search query)
