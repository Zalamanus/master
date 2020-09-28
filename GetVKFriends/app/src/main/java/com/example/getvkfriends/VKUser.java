package com.example.getvkfriends;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


import static com.example.getvkfriends.UserInformation.LOG_TAG;

public class VKUser {
    private int id;
    private String first_name;
    private String last_name;
    private boolean online;
    private String lastSeen;
    private String photo;
    private String sex;
    private String birthDate;
    private String city;
    private String country;
    private String interests;
    private String music;
    private String activities;
    private String movies;
    private String quotes;

    public VKUser(int id, String first_name, String last_name, String online, String lastSeen,
                  String photo, String sex, String birthDate, String city, String country, String interests,
                  String music, String activities, String movies, String quotes) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.online = online.equals("1");
        this.lastSeen = lastSeen;
        this.photo = photo;
        this.sex = sex;
        this.birthDate = birthDate;
        this.city = city;
        this.country = country;
        this.interests = interests;
        this.music = music;
        this.activities = activities;
        this.movies = movies;
        this.quotes = quotes;

    }

    public static VKUser parse(JSONObject object) {
        int id = 0;
        String first_name = null;
        String last_name = null;
        String online = null;
        String lastSeen = null;
        String photo = null;
        String sex = null;
        String birthDate = null;
        String city = null;
        String country = null;
        String interests = null;
        String music = null;
        String activities = null;
        String movies = null;
        String quotes = null;
        try {
            id = Integer.parseInt(object.getString("id"));
        } catch (JSONException e) {
            Log.d(LOG_TAG, "id not found");
        }
        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "names not found");
        }
        try {
            online = object.getString("online");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "online not found");
        }
        try {
            lastSeen = object.getJSONObject("last_seen").getString("time") + "000";
        } catch (JSONException e) {
            Log.d(LOG_TAG, "last_seen not found");
        }
        try {
            photo = object.getString("photo_400_orig");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "photo not found");
        }
        try {
            sex = object.getString("sex");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "sex not found");
        }
        try {
            birthDate = object.getString("bdate");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "bdate not found");
        }
        try {
            city = object.getJSONObject("city").getString("title");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "city not found");
        }
        try {
            country = object.getJSONObject("country").getString("title");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "country not found");
        }
        try {
            interests = object.getString("interests");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "interests not found");
        }
        try {
            music = object.getString("music");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "music not found");
        }
        try {
            activities = object.getString("activities");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "activities not found");
        }
        try {
            movies = object.getString("movies");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "movies not found");
        }
        try {
            quotes = object.getString("quotes");
        } catch (JSONException e) {
            Log.d(LOG_TAG, "quotes not found");
        }

        return new VKUser(id, first_name, last_name, online, lastSeen, photo, sex, birthDate, city, country, interests, music, activities, movies, quotes);

    }

    public int getId() {
        return id;
    }


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getMovies() {
        return movies;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }
}
