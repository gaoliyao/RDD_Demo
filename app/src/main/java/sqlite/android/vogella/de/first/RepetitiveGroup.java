package sqlite.android.vogella.de.first;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marsgao on 12/27/16.
 */

public class RepetitiveGroup {

    private ArrayList<String> mess = new ArrayList<>();
    private ArrayList<Integer> mess_index = new ArrayList<>();
    private int type;

    public HashMap<RepetitiveGroup, Double> getRelated_group() {
        return related_group;
    }

    public void setRelated_group(HashMap<RepetitiveGroup, Double> related_group) {
        this.related_group = related_group;
    }

    public ArrayList<String> getMess() {
        return mess;
    }

    public void setMess(ArrayList<String> mess) {
        this.mess = mess;
    }

    public ArrayList<Integer> getMess_index() {
        return mess_index;
    }

    public void setMess_index(ArrayList<Integer> mess_index) {
        this.mess_index = mess_index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private HashMap<RepetitiveGroup,Double> related_group = new HashMap<>();
    private int id;

    public RepetitiveGroup(Messages m){
        mess.add(m.getMess());
        mess_index.add(m.getId());
        type = m.getType();
    }

}