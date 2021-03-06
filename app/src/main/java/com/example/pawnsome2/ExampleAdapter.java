package com.example.pawnsome2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawnsome2.database.AppDataBase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>{
    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;

    public ExampleAdapter(Context context,ArrayList<ExampleItem> exampleList){
        mContext=context;
        mExampleList =exampleList;

    }

    @Override
    public ExampleViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.example_item,parent,false);

        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ExampleAdapter.ExampleViewHolder holder, int position) {
        ExampleItem currentItem= mExampleList.get(position);

        String imageUrl= currentItem.getImageUrl();
        String breedName=currentItem.getBreedName();
        int id=currentItem.getId();
        holder.mFavoriteButton.setTag(position);

        holder.mBreedName.setText(breedName);
        holder.mId.setText(Integer.toString(id));
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
        holder.mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mFavoriteButton.setBackgroundResource(R.drawable.ic_favorite2);
                Toast.makeText(MainActivity.getInstance(),"Added to Favorite",Toast.LENGTH_SHORT).show();
                MainActivity.getInstance().SaveNewFavDog(breedName,imageUrl,id);
            }
        });
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext,InfoActivity.class);
                intent.putExtra("ID",id);
                intent.putExtra("ImgUrl",imageUrl);
                Pair<View, String> p1 = Pair.create((View)holder.mImageView, "example_transition");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) mContext, p1);
                mContext.startActivity(intent,options.toBundle());

            }
        });

    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView,mFavoriteButton;
        public TextView mBreedName;
        public TextView mId;


        public ExampleViewHolder( View itemView) {
            super(itemView);
            mFavoriteButton=itemView.findViewById(R.id.Favorite_btn);
            mImageView =itemView.findViewById(R.id.image_view);
            mBreedName=itemView.findViewById(R.id.Breed_Name);
            mId=itemView.findViewById(R.id.Breed_Id);
        }
    }
}
