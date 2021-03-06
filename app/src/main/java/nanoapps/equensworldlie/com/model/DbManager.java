package nanoapps.equensworldlie.com.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "nanoclient.db";
    public static final int VERSION = 2;

    // Column User Table
    private static final String USER_TABLE_NAME = "users_table";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_USERNAME = "USERNAME";
    private static final String COLUMN_PASSWORD = "PASSWORD";
    private static final String COLUMN_WALLET_ID = "WALLET_ID";
    private static final String COLUMN_ACCOUNT_ID = "ACCOUNT_ID";
    private static final String COLUMN_PKEY = "PUBLIC_KEY";
    private static final String COLUMN_SKEY = "PRIVATE_KEY";

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String userTableSql = "create table users_table (ID integer primary key autoincrement, USERNAME text not null, PASSWORD text not null, WALLET_ID text, " +
                "ACCOUNT_ID text, PUBLIC_KEY text, PRIVATE_KEY)";


        db.execSQL(userTableSql);

        Log.e("DATABASE","onCreate invoked");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" drop table if exists " + USER_TABLE_NAME);
        onCreate(db);

        Log.d("DATABASE","onUpgrade invoked");
        }

    public long addUser(String username, String password, String walletID, String AccounId, String publicKey, String privateKey){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", username); // the first value is the COLUMN_NAME (USERNAME), then the value we want to store(username)
        contentValues.put("PASSWORD",password);
        contentValues.put("WALLET_ID",walletID);
        contentValues.put("ACCOUNT_ID", AccounId);
        contentValues.put("PUBLIC_KEY",publicKey);
        contentValues.put("PRIVATE_KEY",privateKey);

        long res = db.insert("users_table", null, contentValues);

        return res;
    }

//
//    public void addAccount(String accountId, String publicKey, String privateKey){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("ACCOUNT_ID", accountId); // the first value is the COLUMN_NAME (USERNAME), then the value we want to store(username)
//        contentValues.put("PUBLIC_KEY",publicKey);
//        contentValues.put("PRIVATE_KEY",privateKey);
//
//        db.insert("account_table", null, contentValues);
//    }

    public boolean checkUser(String username, String password){

        String[] columns = {COLUMN_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_USERNAME + "=?" + " and " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(USER_TABLE_NAME,columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return true;
        else
            return false;
    }
}
