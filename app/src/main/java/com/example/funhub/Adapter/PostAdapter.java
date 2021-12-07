/*

package com.example.funhub.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.funhub.Model.Post;
import com.example.funhub.Model.User;
import com.example.funhub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    public android.content.Context mContext;
    public List<Post> mPost;


    private FirebaseUser firebaseUser;
    private ViewGroup parent;
    private int viewType;

    public PostAdapter(RequestManager glide,android.content.Context mContext, List<Post> mPost) {

        this.mContext = mContext;
        this.mPost = mPost;
    }

    public PostAdapter(android.content.Context context, List<Post> postLists) {

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       // View view = LayoutInflater.from().inflate(R.layout.post_item,viewGroup,false);

        View view= LayoutInflater.from(mContext).inflate(R.layout.post_item,viewGroup,false);
        //return null;

       return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(i);
       // Glide.with(mContext).load(post.getPostimage()).into(viewHolder.post_image);
        Glide.with((Activity) mPost).load(post.getPostimage()).into(viewHolder.post_image);
        if ( post.getDescription().equals("")){
            viewHolder.description.setVisibility(View.GONE);
        }else {
            viewHolder.description.setVisibility(View.VISIBLE);
            viewHolder.description.setText(post.getDescription());

        }



        //publisherinfo(viewHolder.image_profile,viewHolder.username, viewHolder.publisher, post.getPublisher());


    }





    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_profile, post_image, like, comment, save;
        public TextView username, likes, publisher, description, comments;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            comments = itemView.findViewById(R.id.comments);

            like = itemView.findViewById(R.id.like);
            save = itemView.findViewById(R.id.save);

            comment = itemView.findViewById(R.id.comment);

            username = itemView.findViewById(R.id.username);
            likes = itemView.findViewById(R.id.likes);
            publisher = itemView.findViewById(R.id.publisher);
            description = itemView.findViewById(R.id.description);


        }

        private void publisherInfo(ImageView image_profile, TextView username, TextView publisher, final String userid) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    Glide.with(itemView.getContext()).load(user.getImageurl()).into(image_profile);
                    username.setText(user.getUsername());
                    publisher.setText(user.getUsername());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }}
*/
