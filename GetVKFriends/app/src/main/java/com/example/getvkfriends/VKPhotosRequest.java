package com.example.getvkfriends;

import com.vk.api.sdk.requests.VKRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;


public class VKPhotosRequest extends VKRequest<VKUserPhotos> {
    private int userId;

    public VKPhotosRequest(String userId) {
        super("photos.getAll");
        this.userId = Integer.parseInt(userId);
//        addParam("owner_id", "210700286");
        addParam("owner_id", userId);
        addParam("v","5.77");
        addParam("count", "20");
    }

    @Override
    public VKUserPhotos parse(@NotNull JSONObject r) throws Exception {
        JSONObject response = r.getJSONObject("response");
        JSONArray items = response.getJSONArray("items");
        VKUserPhotos userPhotos = VKUserPhotos.parse(userId,items);
        return userPhotos;
    }
}
