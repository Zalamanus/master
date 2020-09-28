package com.example.getnews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog progressDialog;
    private ListView listView;

    // URL to get contacts JSON
    private static String url = "https://newsapi.org/v2/top-headlines?country=ru&apiKey=9285fd6b3f0e4d3da2c3311eabb48ab3";

    ArrayList<HashMap<String,String>> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsList = new ArrayList<>();

        listView = findViewById(R.id.list);


        new GetNews().execute();
    }
    private class GetNews extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler handler = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = handler.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray news = jsonObject.getJSONArray("articles");

                    for (int i = 0; i < news.length(); i++) {
                    JSONObject n = news.getJSONObject(i);
                    JSONObject source = n.getJSONObject("source");
                    String id = source.getString("id");
                    String name = source.getString("name");
                    String author = n.getString("author");
                    String title = n.getString("title");
                    String description = n.getString("description");
                    String itemUrl = n.getString("url");
                    String imageUrl = n.getString("urlToImage");
                    String publishedAt = n.getString("publishedAt");
                    String content = n.getString("content");

                    publishedAt = publishedAt.replaceAll("[TZ]", " ");

                    HashMap<String,String> item = new HashMap<>();
                    item.put("title", title);
                    item.put("description", description);
                    item.put("publishedAt", publishedAt);
                    item.put("name", name);
                    item.put("itemUrl", itemUrl);

                    newsList.add(item);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing()) progressDialog.dismiss();
            // Updating parsed JSON data into ListView
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, newsList,R.layout.list_item, new String[]{"title", "description", "publishedAt"}, new int[]{R.id.title, R.id.description,R.id.publishedAt});
            listView.setAdapter(adapter);
            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String toShow = newsList.get(position).get("itemUrl");

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(toShow));
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(itemClickListener);
        }
    }
}
