package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import Models.KeuzeModel;
import Models.UserModel;

/**
 * Created by abdoul on 26/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "androiddabase";
    public final String createGebruiker = "CREATE TABLE Gebruiker (GebruikerID int (11) NOT NULL PRIMARY KEY UNIQUE, Gebruikersnaam varchar (255) NOT NULL, Wachtwoord varchar (255) NOT NULL);";
    public final String createGebruikerVak = "CREATE TABLE GebruikerVak (GebruikerID int (11) NOT NULL REFERENCES Gebruiker (GebruikerID) ON DELETE NO ACTION ON UPDATE NO ACTION, KeuzevakID int (11) NOT NULL REFERENCES Keuzevak (KeuzevakID) ON DELETE NO ACTION ON UPDATE NO ACTION, PRIMARY KEY (GebruikerID, KeuzevakID));";
    public final String createKeuzeVak = "CREATE TABLE Keuzevak (KeuzevakID int (11) NOT NULL PRIMARY KEY UNIQUE, ModuleCode varchar (255) DEFAULT NULL, Ects varchar (255) DEFAULT NULL, Periode varchar (255) DEFAULT NULL, Inschrijving INT (11));";

    final String insert1 = "INSERT INTO Gebruiker (GebruikerID, Gebruikersnaam, Wachtwoord) VALUES (0, '', '');";
    final String insert2 = "INSERT INTO Gebruiker (GebruikerID, Gebruikersnaam, Wachtwoord) VALUES (1, 'Abdoul', 'Etaoil');";
    final String insert3 = "INSERT INTO Gebruiker (GebruikerID, Gebruikersnaam, Wachtwoord) VALUES (3, 'Jack', 'Jackson');";

    final String insert4 = "INSERT INTO GebruikerVak (GebruikerID, KeuzevakID) VALUES (1, 1);";
    final String insert5 = "INSERT INTO GebruikerVak (GebruikerID, KeuzevakID) VALUES (1, 3);";
    final String insert6 = "INSERT INTO GebruikerVak (GebruikerID, KeuzevakID) VALUES (1, 4);";
    final String insert7 = "INSERT INTO GebruikerVak (GebruikerID, KeuzevakID) VALUES (1, 6);";
    final String insert8 = "INSERT INTO GebruikerVak (GebruikerID, KeuzevakID) VALUES (3, 1);";

    final String insert9 = "INSERT INTO Keuzevak (KeuzevakID, ModuleCode, Ects, Periode, Inschrijving) VALUES (1, 'IOPR1', '3', '1', 25);";
    final String insert10 = "INSERT INTO Keuzevak (KeuzevakID, ModuleCode, Ects, Periode, Inschrijving) VALUES (2, 'IOPR2', '3', '3', 20);";
    final String insert11 = "INSERT INTO Keuzevak (KeuzevakID, ModuleCode, Ects, Periode, Inschrijving) VALUES (3, 'ISCRIP', '3', '2', 10);";
    final String insert12 = "INSERT INTO Keuzevak (KeuzevakID, ModuleCode, Ects, Periode, Inschrijving) VALUES (4, 'IIBUI', '3', '2', 5);";
    final String insert13 = "INSERT INTO Keuzevak (KeuzevakID, ModuleCode, Ects, Periode, Inschrijving) VALUES (5, 'IIBPM', '3', '1', 15);";
    final String insert14 = "INSERT INTO Keuzevak (KeuzevakID, ModuleCode, Ects, Periode, Inschrijving) VALUES (6, 'IKLO', '3', '3', 30);";
    final String insert15 = "INSERT INTO Keuzevak (KeuzevakID, ModuleCode, Ects, Periode, Inschrijving) VALUES (7, 'IPSEN3', '6', '2', 20);";



    public final String deleteTables = "DROP TABLE Gebruiker;\n" +
            "DROP TABLE GebruikerVak;\n" +
            "DROP TABLE Keuzevak;";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createGebruiker);
        sqLiteDatabase.execSQL(createGebruikerVak);
        sqLiteDatabase.execSQL(createKeuzeVak);
        sqLiteDatabase.execSQL(insert1);
        sqLiteDatabase.execSQL(insert2);
        sqLiteDatabase.execSQL(insert3);
        sqLiteDatabase.execSQL(insert4);
        sqLiteDatabase.execSQL(insert5);
        sqLiteDatabase.execSQL(insert6);
        sqLiteDatabase.execSQL(insert7);
        sqLiteDatabase.execSQL(insert8);
        sqLiteDatabase.execSQL(insert9);
        sqLiteDatabase.execSQL(insert10);
        sqLiteDatabase.execSQL(insert11);
        sqLiteDatabase.execSQL(insert12);
        sqLiteDatabase.execSQL(insert13);
        sqLiteDatabase.execSQL(insert14);
        sqLiteDatabase.execSQL(insert15);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(deleteTables);
        onCreate(sqLiteDatabase);
    }

    public boolean addUserVak(UserModel user, KeuzeModel vak){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("GebruikerID", user.getId());
        values.put("KeuzevakID", vak.getID());
        long result = db.insert("GebruikerVak", null, values);

        if (result == -1){
            return false;
        }

        else {
            return true;
        }
    }

    public Cursor getUsers(){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Gebruiker";
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public Cursor getVakken(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Keuzevak";
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public void addUserVak(int userID, int vakID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("GebruikerID",userID);
        cv.put("KeuzevakID",vakID);
        db.insert("GebruikerVak", null, cv);
    }

    public Cursor getUsersByVak(int vakid){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select Gebruiker.*\n" +
                "FROM Gebruiker, GebruikerVak, Keuzevak\n" +
                "WHERE Keuzevak.KeuzevakID = GebruikerVak.KeuzevakID\n" +
                "AND Gebruiker.GebruikerID = GebruikerVak.GebruikerID\n" +
                "AND Keuzevak.KeuzevakID =" + vakid;
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public Cursor getVakkenById(int gebruikerid){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Keuzevak.*\n" +
                "FROM Keuzevak, GebruikerVak, Gebruiker\n" +
                "WHERE GebruikerVak.KeuzevakID = Keuzevak.KeuzevakID \n" +
                "AND GebruikerVak.GebruikerID = Gebruiker.GebruikerID\n" +
                "AND GebruikerVak.GebruikerID =" + gebruikerid;
        Cursor data = db.rawQuery(query, null);

        return data;


    }
}
