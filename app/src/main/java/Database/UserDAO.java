package Database;

import java.util.ArrayList;

import Models.KeuzeModel;
import Models.UserModel;

/**
 * Created by abdoul on 13/01/2018.
 */

public class UserDAO {

    private ArrayList<UserModel> allUsers;
    private ArrayList<KeuzeModel> keuzeVakken;
    private KeuzeDAO dao;

    public UserDAO(){

    }

    public void addUser(UserModel user){
        allUsers.add(user);
    }


    public ArrayList getAllUser(){
        return allUsers;
    }

}
