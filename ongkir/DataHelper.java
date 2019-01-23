import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ongkir.db";
    public static final String TABLE_NAME = "ongkirTable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "DARIPROVINSI";
    public static final String COL_4 = "DARIKOTA";
    public static final String COL_5 = "ALAMAT";
    public static final String COL_6 = "KEPROVINSI";
    public static final String COL_7 = "KEKOTA";
    public static final String COL_8 = "BERAT";

    public DataHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DARIPROVINSI TEXT, DARIKOTA TEXT, ALAMAT TEXT, KEPROVINSI TEXT, KEKOTA TEXT, BERAT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String dariProvinsi, String dariKota,String alamat, String keProvinsi, String keKota, String berat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, dariProvinsi);
        contentValues.put(COL_4, dariKota);
        contentValues.put(COL_5, alamat);
        contentValues.put(COL_6, keProvinsi);
        contentValues.put(COL_7, keKota);
        contentValues.put(COL_8, berat);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }


    public boolean updateData(String id, String name, String surname, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
