package com.example.pawnsome2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private ExampleAdapter mEampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;
    private EditText searchtxt;
    private ImageButton searchbtn,clearbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        searchtxt=findViewById(R.id.SearchText);
        searchbtn=findViewById(R.id.Searchbtn);
        clearbtn=findViewById(R.id.Clearbtn);
        mRecycleView=findViewById(R.id.recyler_view);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList=new ArrayList<>();
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
}