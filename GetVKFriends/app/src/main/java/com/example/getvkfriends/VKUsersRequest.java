package com.example.getvkfriends;

import com.vk.api.sdk.requests.VKRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class VKUsersRequest extends VKRequest<VKUser> {
    public VKUsersRequest(String userId) {
        super("users.get");
//        addParam("user_ids", "210700286");
        addParam("user_ids", userId);
        addParam("v","5.8");
        addParam("fields", "photo_id, verified, sex, bdate, city, country, home_town, has_photo, photo_50, photo_100, photo_200_orig, photo_200, photo_400_orig, photo_max, photo_max_orig, online, domain, has_mobile, contacts, site, education, universities, schools, status, last_seen, followers_count, common_count, occupation, nickname, relatives, relation, personal, connections, exports, activities, interests, music, movies, tv, books, games, about, quotes, can_post, can_see_all_posts, can_see_audio, can_write_private_message, can_send_friend_request, is_favorite, is_hidden_from_feed, timezone, screen_name, maiden_name, crop_photo, is_friend, friend_status, career, military, blacklisted, blacklisted_by_me, can_be_invited_group");
    }

    @Override
    public VKUser parse(@NotNull JSONObject r) throws Exception {
        JSONArray response = r.getJSONArray("response");
        JSONObject object = response.getJSONObject(0);
        VKUser vkUser = VKUser.parse(object);
        return vkUser;
    }
}
