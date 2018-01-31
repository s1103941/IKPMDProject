package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {

    private int id;
    private String username;
    private String password;
    private ArrayList<KeuzeModel> keuzeVakken;


    public UserModel(int id, String username, String password){
        this.keuzeVakken = new ArrayList<>();
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserModel(){

    }



    public void setID(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setKeuzeVakken(ArrayList<KeuzeModel> vakken){
        this.keuzeVakken = vakken;
    }

    public ArrayList<KeuzeModel> getKeuzeVakken() {
        return keuzeVakken;
    }

    public void addVak(KeuzeModel vak){
        keuzeVakken.add(vak);
    }
}
