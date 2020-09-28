package com.example.getvkfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);

        VK.login(this, Arrays.asList(VKScope.FRIENDS, VKScope.PHOTOS));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKAuthCallback callback = new VKAuthCallback() {
            @Override
            public void onLogin(@NotNull VKAccessToken vkAccessToken) {
                // Run API request
                VK.execute(new VKFriendsRequest(), new VKApiCallback<List<VKFriend>>() {
                    @Override
                    public void success(final List<VKFriend> friendsList) {

                        CustomListAdapter adapter = new CustomListAdapter(
                                getApplicationContext(), R.layout.list_friends, friendsList
                        );
                        listView.setAdapter(adapter);
                        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getApplicationContext(), UserInformation.class);
                                intent.putExtra("user_id", friendsList.get(position).getId());
                                startActivity(intent);
                            }
                        };
                        listView.setOnItemClickListener(onItemClickListener);

                    }

                    @Override
                    public void fail(@NotNull Exception e) {
                        Toast.makeText(getApplicationContext(), "API friends.get failed", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onLoginFailed(int i) {

            }
        };
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
