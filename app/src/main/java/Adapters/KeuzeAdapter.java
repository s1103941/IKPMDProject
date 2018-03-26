package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdoul.ikpmdproject.KeuzeActivity;
import com.example.abdoul.ikpmdproject.ListActivity;
import com.example.abdoul.ikpmdproject.LoginActivity;
import com.example.abdoul.ikpmdproject.R;
import com.example.abdoul.ikpmdproject.studentListActivity;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Controllers.UserController;
import Database.DatabaseHelper;
import Models.KeuzeModel;
import Models.UserModel;
import Models.getIP;

/**
 * Created by AndroidBash on 09/05/2016.
 */
public class KeuzeAdapter extends RecyclerView.Adapter<KeuzeAdapter.ViewHolder> {

    private Context context;
    private List<KeuzeModel> vakken;
    private UserModel currentUser;
    public static KeuzeModel clickedVak;
    private getIP ip = new getIP();
    private static DatabaseHelper mHelper;


    public KeuzeAdapter(Context context, List<KeuzeModel> vakken, UserModel currentUser) {
        this.context = context;
        this.vakken = vakken;
        this.currentUser = currentUser;
        mHelper = new DatabaseHelper(context);
    }


    public KeuzeAdapter(Context context){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.customlayout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.movieName.setText(vakken.get(position).getModuleCode());
    }




    @Override
    public int getItemCount() {
        return vakken.size();
    }



    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView movieName;
        public TextView movieGenre;
        public Button btnButton1;
        public Button btnButton2;
        public Button btnButton3;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            final RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(context);


            movieName = (TextView) itemView.findViewById(R.id.moviename);
            movieGenre = (TextView) itemView.findViewById(R.id.genre);
            btnButton1 = (Button) itemView.findViewById(R.id.button1);
            btnButton1.setText("Toevoegen");
            btnButton3 = (Button) itemView.findViewById(R.id.button3);
            btnButton3.setText("Studenten");


            btnButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedVak = vakken.get(getAdapterPosition());
                    Intent intent = new Intent(context, studentListActivity.class);
                    intent.putExtra("Vak", new Gson().toJson(clickedVak));
                    context.startActivity(intent);
                }
            });


            btnButton1.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    final int position = getAdapterPosition();

                    final int vak = vakken.get(position).getID();

                    if (LoginActivity.networkConnected) {
                        String insertUrl = "http://" + ip.getIP() + "/addvak.php";
                        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.toLowerCase().contains("duplicate")) {
                                    Toast.makeText(context, "U staat hier al voor ingeschreven", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<String, String>();
                                parameters.put("GebruikerID", Integer.toString(UserController.currentuser.getId()));
                                parameters.put("KeuzevakID", Integer.toString(vak));

                                return parameters;
                            }

                        };
                        requestQueue.add(request);
                    }

                    else {
                       mHelper.addUserVak(UserController.currentuser.getId(), vak);
                    }

                }


                });





        }

        @Override
        public void onClick(View view) {

        }


    }

    }
