package Models;

/**
 * Created by abdoul on 14/01/2018.
 */

public class KeuzeModel {

    private String moduleCode;
    private String ects;
    private String periode;
    private int ID;
    private int inschrijvingen;

    public KeuzeModel(int ID, String moduleCode, String ects, String periode, int inschrijvingen){
        this.ID = ID;
        this.moduleCode = moduleCode;
        this.ects = ects;
        this.periode = periode;
        this.inschrijvingen = inschrijvingen;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public void setInschrijvingen(int inschrijvingen) {
        this.inschrijvingen = inschrijvingen;
    }

    public int getInschrijvingen() {
        return inschrijvingen;
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

