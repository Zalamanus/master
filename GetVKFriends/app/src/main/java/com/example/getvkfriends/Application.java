package com.example.getvkfriends;

import android.content.Intent;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKTokenExpiredHandler;

public class Application extends android.app.Application {
    VKTokenExpiredHandler tokenTracker = new VKTokenExpiredHandler() {
        @Override
        public void onTokenExpired() {
            Intent intent = new Intent(Application.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        VK.addTokenExpiredHandler(tokenTracker);

    }
}
