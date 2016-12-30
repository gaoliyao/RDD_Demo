package sqlite.android.vogella.de.first.Message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marsgao on 12/27/16.
 */

public class MessagesSQLiteHelper extends SQLiteOpenHelper {

    //The table name and column name of table messages
    //
    //   _id    _io    _mess
    //   int    int    text
    //

    /*******************************************************************/
    public static final String TABLE_MESSAGES = "messages";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TYPE = "_io";
    public static final String COLUMN_MESS = "_mess";

    private static final String DATABASE_NAME = "history.db";
    private static final int DATABASE_VERSION = 1;

    //SQL to create database
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MESSAGES + "( " + COLUMN_ID
            + " integer primary key autoincrement, "+ COLUMN_TYPE + " INT, " + COLUMN_MESS
            + " text not null);";
    /*******************************************************************/


    public MessagesSQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    //Create Database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        Log.w(MySQLiteHelper.class.getName(),
//                "Upgrading database from version " + i + " to "
//                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(sqLiteDatabase);
    }
}
