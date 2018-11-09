# Asyncronous Http
This class is used to connect asynchronously to http server with callbacks implementation and returns JSON object.
This is one of my Android dependency source code on project called EasyLoad

#### EasyLoad Project:
This is my latest project of the year 2018. This machine send top-up to any prepaid mobile (Globe, Smart, Sun) networks. And also serve as Wi-Fi hotspot.

#### Programming Languages:
Android Java, CakePHP, PHP, MySQL, nGinx, Python3, NodeJs, Shell script

#### Hardware Components are: 
Raspberry Pi 3, Coin Acceptor, APEX 7000 Bill Acceptor, Channel Relay, OptoCoupler, Android, Modem Router that supports flashing to OpenWRT firmware, Android Tablet 10 inch.

#### Kiosk Mode:
+ Android tablet is rooted
+ It only allows EasyLoad App to run
+ Displays custom splash screen and animated logo on startup

#### Demo:
Facebook video: https://www.facebook.com/easyload.ph/videos/2101718383213214/

Youtube: https://www.youtube.com/watch?v=MmLp7KN3-MI

[![EasyLoad Machine](https://bit.ly/2RAzZ9z)](http://www.youtube.com/watch?v=MmLp7KN3-MI)


#### How to:

   ```java
     public void connect() {
        Http http = new Http();
        http.execute("http://192.168.1.1/get.json", null, null);
        http.setHttpListener(new Http.HttpListener() {
            @Override
            public void onResponseOk(JSONObject result) { 
            	/* process your result jsonObject here */
            }

            @Override
            public void onResponseNull() { }

            @Override
            public void onResponseError(String errorMessage) { }

            @Override
            public void onError(String errorMessage) { }

            @Override
            public void onBeforeConnect() { }
        });
    }