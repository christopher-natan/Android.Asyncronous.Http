# Asyncronous Http
This class is used to connect asynchronously to http server with callbacks implementation and returns JSON object.
This is one of my Android dependency source code on project called EasyLoad

#### EasyLoad Project:
This is my latest project of year 2018. 
A machine that sends prepaid Top-up to any prepaid(Globe, Smart, Sun) mobile phone. And also serve  as Piso Wifi hotspot.

#### Programming Languages:
Android Java, CakePHP, PHP, MySQL, nGinx, Python3, NodeJs, Shell script

#### Hardware Components are: 
Raspberry Pi 3,  Coin Acceptor, APEX 7000 Bill Acceptor, Channel Relay, OptoCoupler,  Android, Router that supports OpenWRT firmware and Android Tablet 10 inch

#### Kiosk Mode:
The Android should be rooted which I made it and customized most of the settings to make it as Kiosk Mode 
which displaying full screen with the EasyLoad app

#### Demo:
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