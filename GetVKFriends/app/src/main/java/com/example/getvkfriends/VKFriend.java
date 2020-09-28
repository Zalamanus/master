package com.example.getvkfriends;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class VKFriend {
    private int id;
    private String first_name;
    private String last_name;
    private boolean online;
    private String last_seen;
    private String thumbnail_url;

    public VKFriend(int id, String first_name, String last_name, String online, String last_seen, String thumbnail_url) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.online = online.equals("1");
        this.last_seen = last_seen;
        this.thumbnail_url = thumbnail_url;

    }

    public static VKFriend parse(JSONObject object) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy H:mm");

        int id = 0;
        String first_name = null;
        String last_name = null;
        String last_seen = null;
        String online = null;
        String thumbnail = null;
        try {
            id = Integer.parseInt(object.getString("id"));
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            last_seen = dateFormat.format(Long.parseLong(object.getJSONObject("last_seen").getString("time") + "000"));
            online = object.getString("online");
            thumbnail = object.getString("photo_50");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new VKFriend(id, first_name, last_name, online, last_seen, thumbnail);

    }


    public int getId() {
        return id;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
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

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

}
