package com.example.funhub.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.funhub.Fragment.ProfileFragment;
import com.example.funhub.Model.User;
import com.example.funhub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context mcontext;
    private List<User> mUsers;

    private FirebaseUser firebaseUser;

    public UserAdapter(Context mcontext, List<User> mUsers) {
        this.mcontext = mcontext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.user_item, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final User user = mUsers.get(i);
        viewHolder.btn_follow.setVisibility(View.VISIBLE);

        viewHolder.username.setText(user.getUsername());
        viewHolder.fullname.setText(user.getFullname());
        Glide.with(mcontext).load(user.getImageurl()).into(viewHolder.imageprofile);

        if (user.getId().equals(firebaseUser.getUid())){
            viewHolder.btn_follow.setVisibility(View.GONE);

        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor= mcontext.getSharedPreferences("PREPS",Context.MODE_PRIVATE).edit();
                editor.putString("Profilefield",user.getId());
                editor.apply();

                ((FragmentActivity)mcontext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });
        viewHolder.btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.btn_follow.getText().toString().equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(user.getId()).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
                            .child("followers").child(firebaseUser.getUid()).setValue(true);
                }else {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(user.getId()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
                            .child("followers").child(firebaseUser.getUid()).removeValue();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView fullname;
        public CircleImageView imageprofile;
        public Button btn_follow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            fullname = itemView.findViewById(R.id.fullname);
            imageprofile=itemView.findViewById(R.id.image_profile);
            btn_follow= itemView.findViewById(R.id.btn_follow);
        }
    }
    private void isFollowing(String userid, Button button ){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

}
        );
    }}