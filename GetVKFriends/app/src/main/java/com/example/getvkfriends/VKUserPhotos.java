package com.example.getvkfriends;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.example.getvkfriends.UserInformation.LOG_TAG;

public class VKUserPhotos {
    private int id;
    private ArrayList<String> arrayPhotos;
    private ArrayList<String> arrayBigPhotos;

    public VKUserPhotos(int id, ArrayList<String> arrayPhotos, ArrayList<String> arrayBigPhotos) {
        this.id = id;
        this.arrayPhotos = arrayPhotos;
        this.arrayBigPhotos = arrayBigPhotos;
    }

    public static VKUserPhotos parse(int userId, JSONArray jsonArray) {
        int id = userId;
        String photoUrl = null;
        ArrayList<String> arrayPhotos = new ArrayList<>();
        ArrayList<String> arrayBigPhotos = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                int lastPhotoIndex = jsonArray.getJSONObject(i).getJSONArray("sizes").length()-1;
                arrayPhotos.add(jsonArray.getJSONObject(i).getJSONArray("sizes").getJSONObject(2).getString("url"));
                arrayBigPhotos.add(jsonArray.getJSONObject(i).getJSONArray("sizes").getJSONObject(lastPhotoIndex).getString("url"));
            }
        } catch (JSONException e) {
            Log.d(LOG_TAG, "Could not get photos");
        }

        return new VKUserPhotos(id,arrayPhotos,arrayBigPhotos);
    }


    public int getId() {
        return id;
    }

    public ArrayList<String> getArrayPhotos() {
        return arrayPhotos;
    }

    public void setArrayPhotos(ArrayList<String> arrayPhotos) {
        this.arrayPhotos = arrayPhotos;
    }

    public ArrayList<String> getArrayBigPhotos() {
        return arrayBigPhotos;
    }

    public void setArrayBigPhotos(ArrayList<String> arrayBigPhotos) {
        this.arrayBigPhotos = arrayBigPhotos;
    }
}
