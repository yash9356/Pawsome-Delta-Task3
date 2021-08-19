package com.example.pawnsome2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.room.processor.Context;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pawnsome2.database.AppDataBase;
import com.example.pawnsome2.database.FavDog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;
    private RecyclerView mRecycleView;
    private ExampleAdapter mEampleAdapter;
    private ArrayList<ExampleItem> mExampleList,mExampleFavList;
    private RequestQueue mRequestQueue;
    private EditText searchtxt;
    private LinearLayoutManager manager;
    Boolean isScrolling=false;
    int currrentItem,totalItem,scrollOutItem;
    private ImageButton searchbtn,clearbtn,Favbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        instance = this;

        searchtxt=findViewById(R.id.SearchText);
        searchbtn=findViewById(R.id.Searchbtn);
        clearbtn=findViewById(R.id.Clearbtn);
        Favbutton=findViewById(R.id.FavButton);
        mRecycleView=findViewById(R.id.recyler_view);
        mRecycleView.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(manager);

        mExampleList=new ArrayList<>();
        mExampleFavList=new ArrayList<>();
        mRequestQueue= Volley.newRequestQueue(this);
        parseJSON();
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExampleList.clear();
                mEampleAdapter.notifyDataSetChanged();
                searchfun();
            }
        });

        Favbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Favorite_Dog_List",Toast.LENGTH_SHORT).show();
                loadsFavDogList();
            }
        });
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchtxt.setText("");
                mExampleList.clear();
                mEampleAdapter.notifyDataSetChanged();
                parseJSON();
            }
        });

    }


    private void parseJSON() {
        String url="https://api.thedogapi.com/v1/breeds";


        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {


                    for(int i=0;i<172;i++){
                        JSONObject Breed=response.getJSONObject(i);
                        String breedName =Breed.getString("name");
                        int id =Breed.getInt("id");
                        JSONObject image=Breed.getJSONObject("image");
                        String imgUrl= image.getString("url");


                        mExampleList.add(new ExampleItem(breedName,imgUrl,id));
                    }

                    mEampleAdapter =new ExampleAdapter(MainActivity.this,mExampleList);
                    mRecycleView.setAdapter(mEampleAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(request);

    }
    public void searchfun(){
        String url="https://api.thedogapi.com/v1/breeds";


        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String searchBreed =searchtxt.getText().toString();

                    String empty=" ";

                    for(int i=0;i<172;i++){
                        JSONObject Breed=response.getJSONObject(i);
                        String breedName =Breed.getString("name");
                        int id =Breed.getInt("id");
                        JSONObject image=Breed.getJSONObject("image");
                        String imgUrl= image.getString("url");

                        if(!searchBreed.equals(empty)){
                            String BreedNameCapital=capitalizeWord(searchBreed);
                            if(breedName.equals(searchBreed) || breedName.equals(BreedNameCapital)  ){
                                mExampleList.add(new ExampleItem(breedName,imgUrl,id));
                                mEampleAdapter =new ExampleAdapter(MainActivity.this,mExampleList);
                                mRecycleView.setAdapter(mEampleAdapter);
                            }

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(request);
    }
    public static String capitalizeWord(String str){
        String words[]=str.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";
        }
        return capitalizeWord.trim();
    }
    public static MainActivity getInstance() {
        return instance;
    }

    public void SaveNewFavDog(String BreedName,String BreedUrl,int id1){

        //AppDataBase db=AppDataBase.getDbInstance(this.getApplicationContext());
//        AppDataBase db=AppDataBase.getDbInstance();
//        FavDog favDogs=new FavDog();
//        favDogs.breedname=BreedName;
//        favDogs.breedid=id1;
//        favDogs.breedurl=BreedUrl;
//        db.favDogDao().insertFavDog(favDogs);
//        finish();
        mExampleFavList.add(new ExampleItem(BreedName,BreedUrl,id1));
    }
    public void loadsFavDogList(){
        //AppDataBase db=AppDataBase.getDbInstance(this.getApplicationContext());
//        AppDataBase db=AppDataBase.getDbInstance();
//        List<FavDog> favDogList=db.favDogDao().getAllFavDog();
//        favDogList.get(1);

        Toast.makeText(this,"Favorite Dog List",Toast.LENGTH_SHORT).show();
        mEampleAdapter =new ExampleAdapter(MainActivity.this,mExampleFavList);
        mRecycleView.setAdapter(mEampleAdapter);
        
    }
}