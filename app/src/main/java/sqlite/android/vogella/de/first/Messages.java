package sqlite.android.vogella.de.first;

/**
 * Created by marsgao on 12/27/16.
 */

public class Messages {
    public int id;
    public int type;
    public String mess;

    public Messages(int idd, int typee, String messs){
        id = idd;
        type = typee;
        mess = messs;
    }

    public Messages(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}