package com.example.getvkfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

public class UserInformation extends AppCompatActivity {

    static final String LOG_TAG = "MyLog";

    TextView txtUserName;
    ImageView imgUserPhoto;
    TextView txtUserLastSeen;
    TextView txtUserSex;
    TextView txtUserBirthDate;
    TextView txtUserCity;
    TextView txtUserCountry;
    TextView txtUserInterests;
    TextView txtUserMusic;
    TextView txtUserActivities;
    TextView txtUserMovies;
    TextView txtUserQuotes;
    ExpendableHeightGridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        txtUserName = findViewById(R.id.user_name);
        imgUserPhoto = findViewById(R.id.user_photo);
        txtUserLastSeen = findViewById(R.id.user_lastSeen);
        txtUserSex = findViewById(R.id.user_sex);
        txtUserBirthDate = findViewById(R.id.user_birthDate);
        txtUserCity = findViewById(R.id.user_city);
        txtUserCountry = findViewById(R.id.user_country);
        txtUserInterests = findViewById(R.id.user_interests);
        txtUserMusic = findViewById(R.id.user_music);
        txtUserActivities = findViewById(R.id.user_activities);
        txtUserMovies = findViewById(R.id.user_movies);
        txtUserQuotes = findViewById(R.id.user_quotes);
        gridView = findViewById(R.id.gridView);


        Intent incomeIntent = getIntent();
        int user_id = incomeIntent.getIntExtra("user_id", 0);
        VK.execute(new VKUsersRequest(String.valueOf(user_id)), new VKApiCallback<VKUser>() {
            @Override
            public void success(VKUser vkUser) {
                if (vkUser.getFirst_name() != null && vkUser.getLast_name() != null)
                    txtUserName.setText(vkUser.getFirst_name() + " " + vkUser.getLast_name());
                if (vkUser.getPhoto() != null)
                    Picasso.get().load(vkUser.getPhoto()).into(imgUserPhoto);
                if (vkUser.isOnline()) txtUserLastSeen.setText("В сети");
                else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy H:mm");
                    txtUserLastSeen.setText("Был в сети: " + dateFormat.format(Long.parseLong(vkUser.getLastSeen())));
                }
                if (vkUser.getSex() != null && vkUser.getSex().equals("1"))
                    txtUserSex.setText("Пол: Женский");
                if (vkUser.getSex() != null && vkUser.getSex().equals("2"))
                    txtUserSex.setText("Пол: Мужской");
                if (vkUser.getBirthDate() != null)
                    txtUserBirthDate.setText("Дата рождения: " + vkUser.getBirthDate());
                else txtUserBirthDate.setVisibility(View.GONE);
                if (vkUser.getCity() != null) txtUserCity.setText("Город: " + vkUser.getCity());
                else txtUserCity.setVisibility(View.GONE);
                if (vkUser.getCountry() != null)
                    txtUserCountry.setText("Страна: " + vkUser.getCountry());
                else txtUserCountry.setVisibility(View.GONE);
                if (vkUser.getInterests() != null && !vkUser.getInterests().equals(""))
                    txtUserInterests.setText("Интересы: " + vkUser.getInterests());
                else txtUserInterests.setVisibility(View.GONE);
                if (vkUser.getMusic() != null && !vkUser.getMusic().equals(""))
                    txtUserMusic.setText("Любимая музыка: " + vkUser.getMusic());
                else txtUserMusic.setVisibility(View.GONE);
                if (vkUser.getActivities() != null && !vkUser.getActivities().equals(""))
                    txtUserActivities.setText("Любимые занятия: " + vkUser.getActivities());
                else txtUserActivities.setVisibility(View.GONE);
                if (vkUser.getMovies() != null && !vkUser.getMovies().equals(""))
                    txtUserMovies.setText("Любимые фильмы: " + vkUser.getMovies());
                else txtUserMovies.setVisibility(View.GONE);
                if (vkUser.getQuotes() != null && !vkUser.getQuotes().equals(""))
                    txtUserQuotes.setText("Цитаты: " + vkUser.getQuotes());
                else txtUserQuotes.setVisibility(View.GONE);

            }

            @Override
            public void fail(@NotNull Exception e) {
                Toast.makeText(getApplicationContext(), "API users.get failed", Toast.LENGTH_LONG).show();

            }
        });
        VK.execute(new VKPhotosRequest(String.valueOf(user_id)), new VKApiCallback<VKUserPhotos>() {
            @Override
            public void success(final VKUserPhotos vkUserPhotos) {
                CustomGridAdapter adapter = new CustomGridAdapter(
                        getApplicationContext(), vkUserPhotos.getArrayPhotos()
                );
                gridView.setExpanded(true);
                gridView.setAdapter(adapter);


                AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), FullImage.class);
                        intent.putExtra("imageUrl", vkUserPhotos.getArrayBigPhotos().get(position));
                        startActivity(intent);
                    }
                };
                gridView.setOnItemClickListener(onItemClickListener);

            }

            @Override
            public void fail(@NotNull Exception e) {
                Toast.makeText(getApplicationContext(), "API photos.get failed", Toast.LENGTH_LONG).show();

            }
        });

    }
}
