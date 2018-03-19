package Controllers;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdoul.ikpmdproject.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Database.UserDAO;
import Models.UserModel;
import Models.getIP;

/**
 * Created by abdoul on 13/01/2018.
 */

public class UserController {

    private UserDAO dao;
    private ArrayList<UserModel> users;
    private LoginActivity activity;
    private Toast toast;
    RequestQueue requestQueue;
    private getIP ip = new getIP();
    String showUrl = "http://" + ip.getIP() + "/getUser.php";
    public UserModel currentuser;


    public UserController(){

    }

    public UserController(Activity context) {
        users = new ArrayList<>();
        activity = new LoginActivity();
        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray gebruikers = response.getJSONArray("gebruikers");
                    for (int i = 0; i < gebruikers.length(); i++) {
                        JSONObject student = gebruikers.getJSONObject(i);

                        int GebruikerID = student.getInt("GebruikerID");
                        String Gebruikersnaam = student.getString("Gebruikersnaam");
                        String Wachtwoord = student.getString("Wachtwoord");

                        users.add(new UserModel(GebruikerID, Gebruikersnaam, Wachtwoord));

                    }

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

        public boolean loginHey(String username, String password){
        System.out.println("A test.");
        System.out.println(users);
        currentuser = new UserModel();

            for(UserModel user: users){

            if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
                currentuser.setUsername(user.getUsername());
                currentuser.setPassword(user.getPassword());
                currentuser.setID(user.getId());
                return true;
            }
        }
        return false;
        }

        public UserModel getUser(){
            return currentuser;

    }

    }




