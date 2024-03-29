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
import com.nihar.final_group4_groupproject.model.ListItemUsers;
import com.nihar.final_group4_groupproject.other.CircleTransform;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ListItemUsers> userList;

    public UsersAdapter(Context context, ArrayList<ListItemUsers> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItemUsers user = userList.get(position);

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
        holder.tvPosition.setText(user.getPosition());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserDetails.class);
                intent.putExtra("user_id", user.getUserId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateList(ArrayList<ListItemUsers> list) {
        userList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvPosition = itemView.findViewById(R.id.tvPosition);
        }
    }

}
