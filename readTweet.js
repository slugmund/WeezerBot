const config=require('./config');
const twit=require('twit');
const T=new twit(config);
const fs = require('fs');
var exec = require('child_process').exec, child;


var SEARCHWORD;
var TweetURL;
var ImageUrl;

fs.readFile('../file.out','utf8', function (err,data){
    if(err){
        return console.log(err);
    }
    console.log(data);
    SEARCHWORD = data;
    searchTweets(SEARCHWORD);
});

function searchTweets(params){
    var params = {
        q: SEARCHWORD,
        count: 50
      }

      T.get('search/tweets', params, function(err, data, response) {
        let none = true;
        // If there is no error, proceed
        if(!err){
          // Loop through the returned tweets
          for(let i = 0; i < data.statuses.length; i++){
            // Get the tweet Id from the returned data
            let id = { id: data.statuses[i].id_str }
            let theTweet = { tweet: data.statuses[i] }
            TweetURL = "https://twitter.com/i/web/status/"+id.id;
            console.log(TweetURL);
            if(theTweet.tweet.entities && theTweet.tweet.entities.media){
                console.log("has media");
                none = false;
                ImageUrl = TweetURL;
                final();
            }
            else{
                console.log("doesnt have media");
                
            }
          }
          if(none == true){
              console.log("none");
          }

        }
        else{
            console.log("error1");
            console.log(err);
        }
    });

    function final(){
        fs.writeFile('../img.txt',ImageUrl,function(err){
            console.log(err);
        });
    }
}
