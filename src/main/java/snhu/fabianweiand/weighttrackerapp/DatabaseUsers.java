package snhu.fabianweiand.weighttrackerapp;

//HEADER INCLUSIONS
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseUsers extends SQLiteOpenHelper {

    //DATABASE VARIABLES
    public static final String DATABASE_NAME ="registerUser.db";
    public static final String TABLE_NAME ="registeruser";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="username";
    public static final String COL_3 ="password";
    public static final String COL_4 ="cellnumber";

    //CREATE SQLITE DATABASE
    public DatabaseUsers(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //CREATE TABLE IN DATABASE
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE registeruser (ID INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT, password TEXT, cellnumber TEXT)";
        sqLiteDatabase.execSQL(createTable);

    }

    //UPDATE TABLE ROUTINE
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //ADD NEW MEMBER INFORMATION
    public long addNewMember (String user, String password, String cellnumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        contentValues.put("cellnumber",cellnumber);
        long res = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return res;
    }

    //CHECK IF USERNAME EXISTS FOR PASSWORD RESET
    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where username = ?", new String[] {username});
        return cursor.getCount() > 0;
    }

    //RESET PASSWORD
    public Boolean updatepassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3, password);
        long result = myDB.update(TABLE_NAME, contentValues, "username = ?", new String[]{username});
        return result != -1;
    }

    //CHECK IN LOGIN CREDENTIALS
    public boolean checkLoginCreds(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }
}
