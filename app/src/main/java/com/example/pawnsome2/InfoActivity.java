package com.example.pawnsome2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {
    ImageView imageView1;
    String breedgroup;
    String origin;
    String breedfor;
    String breedlife;
    String breedtemperament;
    TextView weight1,height1,origin1,breedname,lifespan1,breed_group1,temperament1,descripation1;
    private RequestQueue mRequestQueue;
    int id1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_info);

        String ImageUrl=getIntent().getStringExtra("ImgUrl");
        ImageView imageView=findViewById(R.id.image_view);
        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.transition_image));
        imageView.setTransitionName("example_transition");
        //String ImgUrl=getIntent().getStringExtra("ImgUrl");
        Picasso.with(InfoActivity.this).load(ImageUrl).fit().centerInside().into(imageView);
        //imageView1=findViewById(R.id.image_view);
        weight1=findViewById(R.id.breed_weight);
        height1=findViewById(R.id.breed_height);
        origin1=findViewById(R.id.breed_origin);
        breedname=findViewById(R.id.breed_name);
        lifespan1=findViewById(R.id.breed_life_span);
        breed_group1=findViewById(R.id.breed_group);
        descripation1=findViewById(R.id.breed_description1);
        temperament1=findViewById(R.id.breed_temperament);

        mRequestQueue= Volley.newRequestQueue(this);
        id1=getIntent().getIntExtra("ID",0);
        parseJason(imageView1,id1);

    }

    private void parseJason(ImageView imageView, int id1) {
        String url="https://api.thedogapi.com/v1/breeds";


        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < 172; i++) {
                        JSONObject Breed = response.getJSONObject(i);
                        String breedName = Breed.getString("name");
                        int id = Breed.getInt("id");
                        try {
                            breedfor =Breed.getString("bred_for");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            breedgroup=Breed.getString("breed_group");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            breedlife=Breed.getString("life_span");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            breedtemperament =Breed.getString("temperament");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                             origin =Breed.getString("origin");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONObject weight =Breed.getJSONObject("weight");
                        String weight_in_metric=weight.getString("metric");
                        JSONObject height =Breed.getJSONObject("height");
                        String height_in_metric=height.getString("metric");
                        JSONObject image = Breed.getJSONObject("image");
                        String imgUrl = image.getString("url");

                        if (id == id1) {
                            breedname.setText(breedName);
                            weight1.setText(weight_in_metric);
                            height1.setText(height_in_metric);
                            try {
                                origin1.setText(origin);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                breed_group1.setText(breedgroup);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                descripation1.setText(breedfor);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                lifespan1.setText(breedlife);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                temperament1.setText(breedtemperament);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            //Picasso.with(InfoActivity.this).load(imgUrl).fit().centerInside().into(imageView1);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(InfoActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(InfoActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(request);

    }
}