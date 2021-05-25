package com.example.rest_service_github;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button bGet;
    EditText etOwner, etRepo;
    TextView tvName, tvURL, tvStars, tvDesc;
    String URL = "";
    String Owner;
    String Repo;
    String name="",cloneURL="",stars="",desc="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);

        bGet=findViewById(R.id.bGET2);
        etOwner =findViewById(R.id.ptOwner2);
        etRepo =findViewById(R.id.ptRepoName2);
        tvName=findViewById(R.id.tvName2);
        tvURL=findViewById(R.id.tvCloneURL2);
        tvStars=findViewById(R.id.tvStars2);
        tvDesc=findViewById(R.id.tvDesc2);



        bGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Processing", Toast.LENGTH_SHORT).show();
                Owner=etOwner.getText().toString();
                Repo=etRepo.getText().toString();
                URL = "https://api.github.com/repos/"+Owner+"/"+Repo;
                //Toast.makeText(MainActivity.this,URL ,Toast.LENGTH_SHORT).show();
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    name = response.getString("full_name");
                                    cloneURL=response.getString("clone_url");
                                    stars=response.getString("stargazers_count");
                                    desc=response.getString("description");
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                                tvName.setText(name);
                                tvURL.setText(cloneURL);
                                tvStars.setText(stars);
                                tvDesc.setText(desc);
                                tvDesc.setMovementMethod(new ScrollingMovementMethod());

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Something wrong...", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);

            }
        });



    }
}