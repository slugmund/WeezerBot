const config=require('./config');
const twit=require('twit');
const T=new twit(config);
const fs = require("fs");
const dotenv = require("dotenv");

const imageData = fs.readFileSync( "C:\\Users\\alexei\\Desktop\\weezer2\\result.jpg", { encoding: 'base64' } ); //replace with the path to your image

T.post("media/upload", {media: imageData}, function(error, media, response) {
  if (error) {
    console.log(error);
  } else {
      
    const status = {
      status: " ",
      media_ids: media.media_id_string
    };
    
    T.post("statuses/update", status, function(error, tweet, response) {
      if (error) {
        console.log(error);
      } else {
        console.log("Successfully tweeted an image!");
      }
    });
  }
});
