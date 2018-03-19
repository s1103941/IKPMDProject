package com.example.abdoul.ikpmdproject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
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
import Models.KeuzeModel;
import Models.UserModel;
import Models.getIP;

/**
 * Created by abdoul on 16/01/2018.
 */

public class ListActivity extends AppCompatActivity {

    private ArrayList<KeuzeModel> userVakken;
    private KeuzeAdapter adapter;
    private UserModel currentUser;
    private ArrayList<String> Vakken = new ArrayList<>();
    private getIP ip = new getIP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_vak_list);
        currentUser = KeuzeActivity.currentGebruiker;
        getVakkenById();





    }

    public void getVakkenById(){
        userVakken = new ArrayList<>();
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        String showUrl = "http://" + ip.getIP() + "/getUserVak.php?GebruikerID="+ currentUser.getId();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("Debug time");
                    System.out.println("Current user id is" + currentUser.getId());
                    JSONArray gebruikers = response.getJSONArray("vakken");
                    System.out.println(gebruikers);
                    for (int i = 0; i < gebruikers.length(); i++) {
                        JSONObject student = gebruikers.getJSONObject(i);

                        int vakID = student.getInt("KeuzevakID");
                        String modulecode  = student.getString("ModuleCode");
                        String ects = student.getString("Ects");
                        String periode = student.getString("Periode");
                        int inschrijvingen = student.getInt("Inschrijvingen");
                        userVakken.add(new KeuzeModel(vakID,modulecode,ects,periode, inschrijvingen));

                    }

                    ListView listView = (ListView) findViewById(R.id.mylist);

                    for (int i = 0; i < userVakken.size(); i++ ) {
                        Vakken.add(userVakken.get(i).getModuleCode());
                        System.out.println(userVakken.get(i).getModuleCode());
                    }

                    ListAdapter myAdapter = new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_list_item_1, Vakken);
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




