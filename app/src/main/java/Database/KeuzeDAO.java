package Database;

import android.util.Log;

import java.util.ArrayList;

import Models.KeuzeModel;

/**
 * Created by abdoul on 14/01/2018.
 */

public class KeuzeDAO {

    private ArrayList<KeuzeModel> keuzeVakken;
    private ArrayList<KeuzeModel> userVakken;
    public KeuzeDAO(){
        keuzeVakken = new ArrayList<>();
        userVakken = new ArrayList<>();


    }

    public ArrayList getVakken(){
        return keuzeVakken;
    }

    public void addUserVak(KeuzeModel model){
        Log.d("HEHAFF", "HEY HALLOOTJES");
        userVakken.add(model);
        Log.d(model.getModuleCode(), model.getEcts());
    }

    public ArrayList getUserVakken(){
        return userVakken;
    }
}
