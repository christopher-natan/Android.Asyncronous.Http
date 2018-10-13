# Android.Http.Asyncronous
This class is used to connect asynchronously to http server with callbacks implementation and returns JSON object

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