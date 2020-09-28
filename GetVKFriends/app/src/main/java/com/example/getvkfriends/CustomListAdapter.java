package com.example.getvkfriends;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<VKFriend> {

    List<VKFriend> VKFriends;
    Context context;
    int resource;

    public CustomListAdapter(@NonNull Context context, int resource, @NonNull List<VKFriend> VKFriends) {
        super(context, resource, VKFriends);
        this.VKFriends = VKFriends;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_friends, null, true);
        }
        VKFriend VKFriend = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_thumbnail);
        Picasso.get().load(VKFriend.getThumbnail_url()).into(imageView);

        TextView txtFirstName = (TextView) convertView.findViewById(R.id.first_name);
        txtFirstName.setText(VKFriend.getFirst_name());

        TextView txtLastName = (TextView) convertView.findViewById(R.id.last_name);
        txtLastName.setText(VKFriend.getLast_name());

        TextView txtOnline = (TextView) convertView.findViewById(R.id.online);
        txtOnline.setText(VKFriend.isOnline() ? "Online" : "");

        TextView txtLastSeen = (TextView) convertView.findViewById(R.id.last_seen);
        txtLastSeen.setText(VKFriend.isOnline() ? "" : "Был в сети " + VKFriend.getLast_seen());

        return convertView;
    }
}
