# Asyncronous Http
Java class to asynchronously connect to an HTTP server. Dependency class in my vending machine project

#### Connected Project:
http://www.cmnworks.com/vending-machine.html

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
