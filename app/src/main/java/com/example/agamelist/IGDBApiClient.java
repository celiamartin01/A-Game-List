package com.example.agamelist;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IGDBApiClient {
    private static final String API_KEY = "a7q13lel148ofhqvxcio66uxgxpclj";
    private static final String BASE_URL = "https://api.igdb.com/v4/";

    public static String makeRequest(String endpoint, String query) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .header("Client-ID", API_KEY)
                .header("Authorization","Bearer 04650czvddongnfyzpdnmjplr9k1jp")
                .post(okhttp3.RequestBody.create(null, query))
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
