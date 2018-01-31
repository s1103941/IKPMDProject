package com.example.abdoul.ikpmdproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.KeuzeAdapter;
import Models.UserModel;

public class studentListActivity extends AppCompatActivity {

    private ArrayList<UserModel> theUsers;
    private ArrayList<String> studentNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        getStudentsByVak();
    }

    public void getStudentsByVak(){
        theUsers = new ArrayList<>();
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        String showUrl = "http://145.52.148.55/getUsers.php?KeuzevakID="+ KeuzeAdapter.clickedVak.getID();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray gebruikers = response.getJSONArray("users");
                    System.out.println(gebruikers);
                    for (int i = 0; i < gebruikers.length(); i++) {
                        JSONObject student = gebruikers.getJSONObject(i);

                        int GebruikerID = student.getInt("GebruikerID");
                        String Gebruikersnaam = student.getString("Gebruikersnaam");
                        String Wachtwoord = student.getString("Wachtwoord");

                        theUsers.add(new UserModel(GebruikerID, Gebruikersnaam, Wachtwoord));

                    }

                    ListView listView = (ListView) findViewById(R.id.studentList);

                    for (int i = 0; i < theUsers.size(); i++ ) {
                        studentNames.add(theUsers.get(i).getUsername());
                    }

                    ListAdapter myAdapter = new ArrayAdapter<String>(studentListActivity.this, android.R.layout.simple_list_item_1, studentNames);
                    listView.setAdapter(myAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);



    }

}
