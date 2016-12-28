package sqlite.android.vogella.de.first;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by marsgao on 12/27/16.
 */

public class RepetitiveGroupSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_REPETITIVEGROUP = "repetitivegroup";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_INGROUP = "ingroup";

    public static final String COLUMN_RESPOND = "respond";
    public static final String COLUMN_RELEVENCE = "relevence";

    private static final String DATABASE_NAME = "reg.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_REPETITIVEGROUP + "( " + COLUMN_ID
            + " INT, "+ COLUMN_INGROUP + " TEXT, " + COLUMN_RESPOND
            + " text not null, "+ COLUMN_RELEVENCE + " FLOAT "+" );";

    public RepetitiveGroupSQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + i + " to "
                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REPETITIVEGROUP);
        onCreate(sqLiteDatabase);
    }
}