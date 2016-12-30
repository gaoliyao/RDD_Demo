package sqlite.android.vogella.de.first.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsgao on 12/27/16.
 */
/*
    Purpose of each function below

    Insert: createComment

    Delete: deleteComment

    Query: getAllComments

    QueryByType: getAllComments(int type)

 */
public class MessagesDataSource {

    /*********************  VAR   **************************************/
    private SQLiteDatabase database;
    private MessagesSQLiteHelper dhelper;
    private String[] allColumns = { MessagesSQLiteHelper.COLUMN_ID,
            MessagesSQLiteHelper.COLUMN_TYPE,MessagesSQLiteHelper.COLUMN_MESS};
    /*******************************************************************/


    /*******************************************************************/
    public MessagesDataSource(Context context){
        dhelper = new MessagesSQLiteHelper(context);
    }

    public void open() throws SQLException{
        database  = dhelper.getWritableDatabase();
    }

    public void close(){
        dhelper.close();
    }



    /********************  INSERT  **************************************/
    public Messages createComment(int type , String message) {
        ContentValues values = new ContentValues();
        values.put(MessagesSQLiteHelper.COLUMN_TYPE, Integer.toString(type));
        values.put(MessagesSQLiteHelper.COLUMN_MESS, message);
        long insertId = database.insert(MessagesSQLiteHelper.TABLE_MESSAGES, null,
                values);
        Cursor cursor = database.query(MessagesSQLiteHelper.TABLE_MESSAGES,
                allColumns, MessagesSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Messages newMessage = cursorToMessages(cursor);
        cursor.close();
        return newMessage;
    }
    /*******************************************************************/

    /********************   DELETE   ***************************************/
    public void deleteComment(Messages messages) {
        long id = messages.getId();
        System.out.println("Message deleted with id: " + id);
        database.delete(MessagesSQLiteHelper.TABLE_MESSAGES, MessagesSQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }
    /*******************************************************************/

    /**********************   QUERY   **************************************/
    public List<Messages> getAllComments() {
        List<Messages> messages = new ArrayList<Messages>();

        Cursor cursor = database.query(MessagesSQLiteHelper.TABLE_MESSAGES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Messages message = cursorToMessages(cursor);
            messages.add(message);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }
    /*******************************************************************/

    /*********************   QUERY BY TYPE  ****************************************/
    public ArrayList<Messages> getAllComments(int type) {
        ArrayList<Messages> messages = new ArrayList<Messages>();

//        Cursor cursor = database.query(MessagesSQLiteHelper.TABLE_MESSAGES,
//                allColumns, null, null, null, null, null);

        Cursor cursor = database.query(MessagesSQLiteHelper.TABLE_MESSAGES,
                allColumns, MessagesSQLiteHelper.COLUMN_TYPE + " = " + type, null,
                null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Messages message = cursorToMessages(cursor);
            messages.add(message);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }
    /*******************************************************************/

    /*******************************************************************/
    private Messages cursorToMessages(Cursor cursor) {
        Messages message = new Messages();
        message.setId(cursor.getInt(0));
        message.setType(cursor.getInt(1));
        message.setMess(cursor.getString(2));
        return message;
    }
    /*******************************************************************/

}
