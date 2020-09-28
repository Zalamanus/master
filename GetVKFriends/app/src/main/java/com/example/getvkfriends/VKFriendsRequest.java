package com.example.getvkfriends;

import com.vk.api.sdk.requests.VKRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VKFriendsRequest extends VKRequest<List<VKFriend>> {
    public VKFriendsRequest() {
        super("friends.get");
        addParam("fields", "sex,photo_50,education,city,last_seen,online");
    }

    @Override
    public List<VKFriend> parse(@NotNull JSONObject r) throws Exception {
        JSONObject response = r.getJSONObject("response");
        JSONArray items = response.getJSONArray("items");
        ArrayList<VKFriend> arrayVKFriends = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            arrayVKFriends.add(VKFriend.parse(items.getJSONObject(i)));
        }
        return arrayVKFriends;
    }
}
