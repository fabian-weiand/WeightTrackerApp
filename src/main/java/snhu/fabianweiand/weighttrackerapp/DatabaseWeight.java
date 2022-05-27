package snhu.fabianweiand.weighttrackerapp;

//HEADER INCLUSIONS
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;
import androidx.annotation.Nullable;


public class DatabaseWeight extends SQLiteOpenHelper {

    //DATABASE VARIABLES
    public static final String TAG = "Weight Database";
    public static final String DATABASE_NAME ="weightdatabase.db";
    public static final String TABLE_NAME ="user_weight";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="tweight";
    public static final String COL_3 ="cweight";
    public static final String COL_4 ="date";

    //CREATE SQLITE DATABASE
    public DatabaseWeight(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //CREATE TABLE IN DATABASE
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY  KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 +" TEXT)";
        sqLiteDatabase.execSQL(createTable);

    }

    //UPDATE TABLE ROUTINE
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //ADD WEIGHT INFORMATION TO DATABASE
    public boolean addData(String tWeight, String cWeight,String mDate){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, tWeight);
        contentValues.put(COL_3, cWeight);
        contentValues.put(COL_4, mDate);
        Log.d(TAG, "addData: Adding " + mDate + "and " + tWeight + "and " + cWeight +" to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    //METHOD TO GET CONTENTS FROM DATABASE TO DISPLAY
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    //METHOD TO CLEAR HISTORY
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.execSQL("DELETE FROM "+ TABLE_NAME);
        db.close();
    }

}
