package sqlite.android.vogella.de.first;

/**
 * Created by marsgao on 12/27/16.
 */

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by marsgao on 12/5/16.
 */
public class Comparier_se extends AsyncTask<String, String, Double> {

    public double result = 0;

    public static double Compare_se(String sentence1, String sentence2) {


        if(sentence1 == sentence2) return 1.0;
        String sentence_1 = "";
        String sentence_2 = "";
        for(char c:sentence1.toCharArray()){
            if(c!=' ') sentence_1 = sentence_1 + c;
            else sentence_1 = sentence_1+"%20";
        }

        for(char c:sentence2.toCharArray()){
            if(c!=' ') sentence_2 = sentence_2 + c;
            else sentence_2 = sentence_2+"%20";
        }

//        String site = "https://concept.research.microsoft.com/api/Concept/ScoreByProb?instance="+word+"&topK=3&smooth=0&api_key=rgCs0o6ogUMlaCuxodg3stcCuPu9VzDj";
        String site = "http://swoogle.umbc.edu/StsService/GetStsSim?operation=api&phrase1="+sentence_1+"&phrase2="+sentence_2;
        ArrayList<String> results = new ArrayList<String>();
        ArrayList<String> scores = new ArrayList<String>();
        String result="";
        String result_score = "";
        String result_final = "";

        try {
            URL url = new URL(site);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            String encoding = urlConnection.getContentEncoding();
            encoding = encoding == null? "UTF-8":encoding;
            String body = getStringFromInputStream(inputStream);
            result = body;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);

        return Double.parseDouble(result);


    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    @Override
    protected Double doInBackground(String... strings) {
        String sentence1 = strings[0];
        String sentence2 = strings[1];
        if(sentence1 == sentence2) return 1.0;
        String sentence_1 = "";
        String sentence_2 = "";
        for(char c:sentence1.toCharArray()){
            if(c!=' ') sentence_1 = sentence_1 + c;
            else sentence_1 = sentence_1+"%20";
        }

        for(char c:sentence2.toCharArray()){
            if(c!=' ') sentence_2 = sentence_2 + c;
            else sentence_2 = sentence_2+"%20";
        }

//        String site = "https://concept.research.microsoft.com/api/Concept/ScoreByProb?instance="+word+"&topK=3&smooth=0&api_key=rgCs0o6ogUMlaCuxodg3stcCuPu9VzDj";
        String site = "http://swoogle.umbc.edu/StsService/GetStsSim?operation=api&phrase1="+sentence_1+"&phrase2="+sentence_2;
        ArrayList<String> results = new ArrayList<String>();
        ArrayList<String> scores = new ArrayList<String>();
        String result="";
        String result_score = "";
        String result_final = "";

        try {
            URL url = new URL(site);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            String encoding = urlConnection.getContentEncoding();
            encoding = encoding == null? "UTF-8":encoding;
            String body = getStringFromInputStream(inputStream);
            result = body;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);

        return Double.parseDouble(result);
    }

    protected void onPostExecute(Double resultt) {
        result = resultt;
    }
}
