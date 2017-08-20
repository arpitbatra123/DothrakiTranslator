package com.megahertzlabs.dothrakitranslator;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class DothrakiConverter {

    private static OkHttpClient client = new OkHttpClient();
    private static String baseUrl = "http://api.funtranslations.com/translate/dothraki.json?text=";

    static String run(String url)
    {
        Request request= new Request.Builder().url(baseUrl+url).build();
        try {
            Response response=client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return "Error in Connection";

        }


    }


}