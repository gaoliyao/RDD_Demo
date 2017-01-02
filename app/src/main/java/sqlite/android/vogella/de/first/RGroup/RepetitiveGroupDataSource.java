package sqlite.android.vogella.de.first.RGroup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marsgao on 12/27/16.
 */

public class RepetitiveGroupDataSource {

    /**************************   VAR   **************************************/
    private SQLiteDatabase database;
    private RepetitiveGroupSQLiteHelper dhelper;
    private String[] allColumns = {RepetitiveGroupSQLiteHelper.COLUMN_ID,RepetitiveGroupSQLiteHelper.COLUMN_INGROUP,
            RepetitiveGroupSQLiteHelper.COLUMN_RESPOND,RepetitiveGroupSQLiteHelper.COLUMN_RELEVENCE};
    /*******************************************************************/


    public RepetitiveGroupDataSource(Context context){dhelper = new RepetitiveGroupSQLiteHelper(context);}

    public void open() throws SQLException{
        database = dhelper.getWritableDatabase();
    }

    public void close(){dhelper.close();}


    /**************************   INSERT   **************************************/
    public boolean createComment(int id , String incoming, String outgoing, double relevence) {
        ContentValues values = new ContentValues();
        values.put(RepetitiveGroupSQLiteHelper.COLUMN_ID, id);
        values.put(RepetitiveGroupSQLiteHelper.COLUMN_INGROUP, incoming);
        values.put(RepetitiveGroupSQLiteHelper.COLUMN_RESPOND, outgoing);
        values.put(RepetitiveGroupSQLiteHelper.COLUMN_RELEVENCE, relevence);

        long insertId = database.insert(RepetitiveGroupSQLiteHelper.TABLE_REPETITIVEGROUP, null,
                values);

        return true;
    }
    /*******************************************************************/

    /**************************   DELETE   **************************************/
    public void deleteincoming(RepetitiveGroup repetitiveGroup) {
        String incoming = repetitiveGroup.getMess().get(0);
        System.out.println("Message deleted with ingroup: " + incoming);
        database.delete(RepetitiveGroupSQLiteHelper.TABLE_REPETITIVEGROUP, RepetitiveGroupSQLiteHelper.COLUMN_INGROUP
                + " = " + incoming, null);
    }
    /*******************************************************************/

    /*************************   DELETE   ***************************************/
    public void deleteoutgoing(RepetitiveGroup repetitiveGroup) {
        String outgoing = repetitiveGroup.getMess().get(0);
        System.out.println("Message deleted with respond: " + outgoing);
        database.delete(RepetitiveGroupSQLiteHelper.TABLE_REPETITIVEGROUP, RepetitiveGroupSQLiteHelper.COLUMN_RESPOND
                + " = " + outgoing, null);
    }
    /*******************************************************************/

    /************************   QUERY   ****************************************/
    //Method of get all the elements in the database
    //RepetitiveGroup
    public HashMap<String, ArrayList<String>> getAllComments() {
        HashMap<String, ArrayList<String>> related_group = new HashMap<String, ArrayList<String>>();

        Cursor cursor = database.query(RepetitiveGroupSQLiteHelper.TABLE_REPETITIVEGROUP,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        float num = 0;
        while (!cursor.isAfterLast()) {
//            Messages message = cursorToMessages(cursor);
//            messages.add(message);
            if(related_group.keySet().contains(cursor.getInt(0))){
                if(cursor.getFloat(3)>=num){
                    num = cursor.getFloat(3);
                related_group.get(related_group.size()-1).add(0,cursor.getString(2));
                }
                else
                    related_group.get(related_group.size()-1).add(cursor.getString(2));
            }
            else{
                num = cursor.getFloat(3);
                ArrayList<String> res_list = new ArrayList<String>();
                res_list.add(cursor.getString(2));
                related_group.put(cursor.getString(1), res_list);
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return related_group;
    }
    /*******************************************************************/


}
