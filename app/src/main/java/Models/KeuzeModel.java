package Models;

/**
 * Created by abdoul on 14/01/2018.
 */

public class KeuzeModel {

    private String moduleCode;
    private String ects;
    private String periode;
    private int ID;

    public KeuzeModel(int ID, String moduleCode, String ects, String periode){
        this.ID = ID;
        this.moduleCode = moduleCode;
        this.ects = ects;
        this.periode = periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public void setEcts(String ects) {
        this.ects = ects;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getEcts() {
        return ects;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getPeriode() {
        return periode;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}

