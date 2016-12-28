package sqlite.android.vogella.de.first;

/**
 * Created by marsgao on 12/27/16.
 */
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by marsgao on 12/5/16.
 */
public class GetRepeatMessages {


    public  ArrayList<RepetitiveGroup> RDD_Detection(ArrayList<Messages> messages, double num){
        ArrayList<RepetitiveGroup> Groups = new ArrayList<RepetitiveGroup>();
        Groups.add(new RepetitiveGroup(messages.get(0)));

        boolean is_get = false;
        for(Messages me:messages){
            is_get = false;
            for(RepetitiveGroup g:Groups){
                if(relation(me,g)>=num){g.getMess().add(me.getMess());g.getMess_index().add(me.getId());is_get = true;break;}
            }
            if(is_get == false) Groups.add(new RepetitiveGroup(me));

        }

        return Groups;
    }


    public  ArrayList<RepetitiveGroup> RDD_Detection(RepetitiveGroup messages_group, double num){
        ArrayList<RepetitiveGroup> Groups = new ArrayList<RepetitiveGroup>();



        ArrayList<Messages> messages = new ArrayList<>();

        for(int i=0;i<messages_group.getMess_index().size();i++){
            messages.add(new Messages(messages_group.getMess_index().get(i),messages_group.getType(),messages_group.getMess().get(i)));
        }

        Groups.add(new RepetitiveGroup(messages.get(0)));

        boolean is_get = false;
        for(Messages me:messages){
            is_get = false;
            for(RepetitiveGroup g:Groups){
                if(relation(me,g)>=num){g.getMess().add(me.getMess());g.getMess_index().add(me.getId());is_get = true;break;}
            }
            if(is_get == false) Groups.add(new RepetitiveGroup(me));

        }

        return Groups;
    }
    public double compare(String a, String b){
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

    public   double relation(Messages me, RepetitiveGroup rg){
        double result = 0;

        if(rg.getMess_index().size() == 1){
            result = compare(me.getMess(),rg.getMess().get(0));
        }

        else if(rg.getMess_index().size() == 2){result = (compare(me.getMess(),rg.getMess().get(0))+compare(me.getMess(),rg.getMess().get(1)))/2;}

        else {result = (compare(me.getMess(),rg.getMess().get(0))
                +compare(me.getMess(),rg.getMess().get(1))
                +compare(me.getMess(),rg.getMess().get(2)))/3;}

        return result;
    }

    public  double relation(String str, RepetitiveGroup rg){
        double result = 0;

        if(rg.getMess_index().size() == 1){
            result = compare(str,rg.getMess().get(0));
        }

        else if(rg.getMess_index().size() == 2){result = (compare(str,rg.getMess().get(0))+compare(str,rg.getMess().get(1)))/2;}

        else {result = (compare(str,rg.getMess().get(0))
                +compare(str,rg.getMess().get(1))
                +compare(str,rg.getMess().get(2)))/3;}

        return result;
    }

//
//    public static double Compare_se(String sentence1, String sentence2) {
//
//        String sentence_1 = "";
//        String sentence_2 = "";
//        for(char c:sentence1.toCharArray()){
//            if(c!=' ') sentence_1 = sentence_1 + c;
//            else sentence_1 = sentence_1+"%20";
//        }
//
//        for(char c:sentence2.toCharArray()){
//            if(c!=' ') sentence_2 = sentence_2 + c;
//            else sentence_2 = sentence_2+"%20";
//        }
//
////        String site = "https://concept.research.microsoft.com/api/Concept/ScoreByProb?instance="+word+"&topK=3&smooth=0&api_key=rgCs0o6ogUMlaCuxodg3stcCuPu9VzDj";
//        String site = "http://swoogle.umbc.edu/StsService/GetStsSim?operation=api&phrase1="+sentence_1+"&phrase2="+sentence_2;
//        ArrayList<String> results = new ArrayList<String>();
//        ArrayList<String> scores = new ArrayList<String>();
//        String result="";
//        String result_score = "";
//        String result_final = "";
//
//        try {
//            URL url = new URL(site);
//            URLConnection urlConnection = url.openConnection();
//            InputStream inputStream = urlConnection.getInputStream();
//            String encoding = urlConnection.getContentEncoding();
//            encoding = encoding == null? "UTF-8":encoding;
//            String body = getStringFromInputStream(inputStream);
//            result = body;
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(result);
//
//        return Double.parseDouble(result);
//
//
//    }
//
//    private static String getStringFromInputStream(InputStream is) {
//
//        BufferedReader br = null;
//        StringBuilder sb = new StringBuilder();
//
//        String line;
//        try {
//
//            br = new BufferedReader(new InputStreamReader(is));
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return sb.toString();
//
//    }

}