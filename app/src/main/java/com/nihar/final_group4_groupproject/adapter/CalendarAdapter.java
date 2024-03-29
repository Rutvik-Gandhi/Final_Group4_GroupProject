package com.nihar.final_group4_groupproject.adapter;

import static com.nihar.final_group4_groupproject.api.APIClient.postClient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.model.ListItemCalendar;
import com.nihar.final_group4_groupproject.other.CircleTransform;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ListItemCalendar> userList;

    public CalendarAdapter(Context context, ArrayList<ListItemCalendar> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_calendar_events, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItemCalendar user = userList.get(position);

        String imageURL = postClient().baseUrl() + "FileUploader/Uploads/ProfilePictures/" + user.getUserId() + ".jpg";

        Glide.with(context)
                .load(imageURL)
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CircleTransform(context))
                .into(holder.imageView);

        holder.tvName.setText(user.getName());
        holder.tvDOB.setText(user.getDate());

        setIcon(holder, user.getType());

        holder.itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, UserDetails.class)
                    .putExtra("user_id", user.getUserId()));
        });
    }

    private void setIcon(ViewHolder holder, String type) {
        if (type.equals(context.getString(R.string.type_birthday))) {
            holder.imgCake.setVisibility(View.VISIBLE);
            holder.imgCouple.setVisibility(View.GONE);
        } else {
            holder.imgCake.setVisibility(View.GONE);
            holder.imgCouple.setVisibility(View.VISIBLE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView tvName;
        private final TextView tvDOB;
        private final ImageView imgCake;
        private final ImageView imgCouple;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvDOB = itemView.findViewById(R.id.tvDOB);
            imgCake = itemView.findViewById(R.id.imgCake);
            imgCouple = itemView.findViewById(R.id.imgCouple);
        }
    }

}