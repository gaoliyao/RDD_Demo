package sqlite.android.vogella.de.first;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sqlite.android.vogella.de.first.Message.Messages;
import sqlite.android.vogella.de.first.Message.MessagesDataSource;
import sqlite.android.vogella.de.first.RGroup.RepetitiveGroupDataSource;

public class TestDatabaseActivity extends ListActivity {

    //Connection to messages database
    private MessagesDataSource datasource;
    //Connection to repetitivegroup database
    private RepetitiveGroupDataSource repetitiveGroupDataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);

        datasource = new MessagesDataSource(this);
        datasource.open();
        repetitiveGroupDataSource = new RepetitiveGroupDataSource(this);
        repetitiveGroupDataSource.open();

        List<Messages> messages = datasource.getAllComments(0);
        List<String> values = getMesses(messages);
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
//        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        Messages message = null;
        EditText editText = (EditText) findViewById(R.id.EditText);
        switch (view.getId()) {
            case R.id.add:
                int size = types.length < messages.length ? types.length:messages.length;
                for(int i=0;i<size;i++) {
                    message = datasource.createComment(types[i],messages[i]);
                }
                List<Messages> mlist = datasource.getAllComments(0);
                List<String> values = getMesses(mlist);
                for(String str:values)
                adapter.add(str);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    message = (Messages) getListAdapter().getItem(0);
                    datasource.deleteComment(message);
                    adapter.remove(message.getMess());
                }
                break;
            case R.id.analyze:
                GetRepetitiveGroupService getRepetitiveGroupService = new GetRepetitiveGroupService(datasource,repetitiveGroupDataSource);
                getRepetitiveGroupService.run();
                break;
            case R.id.Change:
                adapter.clear();
                HashMap<String, ArrayList<String>> related_g = repetitiveGroupDataSource.getAllComments();
                for(String str:related_g.keySet()){
                    for(String s:related_g.get(str)){
                        adapter.add(str+":"+s);
                    }
                }
                break;
            case R.id.search:
                editText.setText(getrespond(editText.getText().toString()));
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private String getrespond(String sentence) {
        if(sentence.equals(""))return "No Repetitive Response";
        HashMap<String, ArrayList<String>> related_g = repetitiveGroupDataSource.getAllComments();
        String result = "";
        for(String str:related_g.keySet()) {
            if (compare(sentence, str)>=0.76)
                for(String s:related_g.get(str)){
                    result = result + s + ";";
                }
        }
        if(result.equals("")) return "No Repetitive Response";
        else return result;
    }

    private double compare(String a, String b){
        Comparier_se comparier_se = new Comparier_se();
        Double db = 0.0;
        try {
            db = comparier_se.execute(a,b).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return db;
    }
    public List<String> getMesses(List<Messages> messages){
        List<String> messes = new ArrayList<>();
        for(Messages me:messages){
            messes.add(me.getMess());
        }
        return messes;
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }


    int[] types = {0,
            1,
            1,
            0,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            1,
            0,
            0,
            1,
            0,
            1,
            0,
            0,
            1,
            1,
            0,
            0,
            0,
            1,
            1,
            0,
            1,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            1,
            0,
            1,
            1,
            0,
            0,
            1,
            0,
            1,
            1,
            1,
            1,
            0,
            0,
            1,
            0,
            1,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            1,
            0,
            1,
            0,
            0,
            1,
            0,
            0,
            0,
            1,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            1,
            0
            ,1
            ,0
            ,1
            ,1
            ,0
            ,1
            ,1
            ,0
            ,1
            ,0
            ,1
            ,0
            ,1
            ,0
            ,1
            ,0
            ,0
            ,1
            ,0
            ,1
            ,1
            ,0
            ,1
            ,0
            ,1};

    String[] messages = {"Are you at work honey",
            "Yeah, still working",
            "I will reply you after work",
            "Okay honey","Are you attending class?",
            "Yeah, text mining class",
            "Did you complete?",
            "I will",
            "Did you start CP2?",
            "No",
            "You went to class?",
            "Yeah,I went to class",
            "I will reply you later",
            "Okay honey",
            "Hi!",
            "Hi!",
            "Assignment 7 is due thursday  right?",
            "Yes,Also Mars is saying that assignment 7 is due tomorrow",
            "Alright",
            "Take good care honey",
            "You too",
            "Love you",
            "Love you too",
            "Honey?",
            "Done?",
            "I'm done in 10 for sure.",
            "Sorry honey I'm late",
            "That's fine",
            "Honey you are so good",
            "Love you",
            "Love you",
            "Love you too",
            "What are you doing baby",
            "I am in tech talk",
            "LinkedIn techtalk?",
            "Yeah!",
            "Love you",
            "Love you too honey, I'll reply you later",
            "Okay",
            "Love you",
            "Can I call sweets?",
            "No,my class ends at 5:30",
            "But you can call me after class",
            "Sure",
            "Okay honey",
            "What are you doing baby",
            "I'm kinda swamped with work.",
            "Can you talk?",
            "I can talk now",
            "Shall I call now?",
            "I'm in class",
            "Maybe a little bit later",
            "You're still awake?",
            "Im not feeling sleepy at all",
            "Miss you honey",
            "Miss you too",
            "Love you",
            "Love you too",
            "Are you free",
            "I am online",
            "Can I Skype?",
            "Yeah","Awake?",
            "I am already awake",
            "Miss you so much",
            "Baby","Miss you too",
            "My love one",
            "You guys free for dinner tomorrow?",
            "Sorry, I already have plans for dinner and a meeting",
            "But we can do it next time",
            "Alright, see you next time",
            "See you"};
//    ,
//            "I'm assuming you're asleep?",
//            "Been half awake in bed for quite a bit long",
//            "I miss you so much",
//            "Miss you too",
//            "Love you baby",
//            "Love you too",
//            "Yes,Also Mars is saying that assignment 7 is due tomorrow",
//            "I miss you so much",
//            "Miss you too",
//            "Love you baby",
//            "You busy?",
//            "Yeah, I'm super busy till March 2nd too!",
//            "Okay",
//            "Maybe we can do it next time",
//            "See you next time",
//            "See you",
//            "I'm sorry",
//            "That's alright",
//            "Did you call",
//            "I called Mars. They should send someone soon",
//            "Did you get my Google slides mail?",
//            "No, I haven't seen it",
//            "Okay",
//            "I can send another email if necessary!",
//            "Thank you!",
//            "Not at all",
//            " I realized that I dont have your number.",
//            "4124994903",
//            "That's my phone number",
//            "Thanks",
//            "Not at all",
//            "Babe there?",
//            "Coming!",
//            "Where?",
//            "Lab",
//            "Okay",
//            "Tell me as soon as you arrived",
//            "Okay",
//            "So where do we meet?",
//            "If so we can meet up at scs",
//            "But maybe a little bit later",
//            "Sorry",
//            "That's okay",
//            "I'm gonna late",
//            "Take your time",
//            "Don't worry",
//            "Love you",
//            "Love you too",
//            "Baby I wanna be with you"
//            ,"So do I"
}
