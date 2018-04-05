package com.example.abdoul.ikpmdproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.KeuzeAdapter;
import Database.DatabaseHelper;
import Models.KeuzeModel;
import Models.UserModel;
import Models.getIP;

public class studentListActivity extends AppCompatActivity {

    private ArrayList<UserModel> theUsers;
    private ArrayList<String> studentNames = new ArrayList<>();
    private getIP ip = new getIP();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        getStudentsByVak();
        Button btnButton1 = (Button) findViewById(R.id.button);
        btnButton1.setText("Grafische weergave");

        final Context context = this;
        btnButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = getIntent();

                String s = (String) i.getSerializableExtra("Vak");
                KeuzeModel vak = new Gson().fromJson(s, KeuzeModel.class);
                Intent intent = new Intent(context, StudentChartActivity.class);
                intent.putExtra("Vak", new Gson().toJson(vak));
                intent.putExtra("aantal", theUsers.size());
                context.startActivity(intent);
            }
        });
    }

    public void getStudentsByVak(){
        theUsers = new ArrayList<>();
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        String showUrl = "http://" + ip.getIP() + "/getUsers.php?KeuzevakID="+ KeuzeAdapter.clickedVak.getID();
        listView = (ListView) findViewById(R.id.studentList);

        if(LoginActivity.networkConnected){
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

        else {
            DatabaseHelper mHelper = new DatabaseHelper(this);
            Cursor data = mHelper.getUsersByVak(KeuzeAdapter.clickedVak.getID());
            Log.d("Hewa", "Jackson");
            while(data.moveToNext()){
                int GebruikerID = data.getInt(0);
                String Gebruikersnaam = data.getString(1);
                String Wachtwoord = data.getString(2);

                theUsers.add(new UserModel(GebruikerID, Gebruikersnaam, Wachtwoord));
                for (int i = 0; i < theUsers.size(); i++ ) {
                    studentNames.add(theUsers.get(i).getUsername());
                }

                ListAdapter myAdapter = new ArrayAdapter<String>(studentListActivity.this, android.R.layout.simple_list_item_1, studentNames);
                listView.setAdapter(myAdapter);
            }
        }


    }

}