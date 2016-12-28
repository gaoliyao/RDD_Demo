package sqlite.android.vogella.de.first;

import java.util.ArrayList;
import java.util.Hashtable;


/**
 * Created by marsgao on 12/27/16.
 */

public class GetRepetitiveGroupService {

    MessagesDataSource messagesDataSource;
    RepetitiveGroupDataSource repetitiveGroupDataSource;
    GetRepeatMessages getRepeatMessages;

    GetRepetitiveGroupService(MessagesDataSource messagesDataSourcee, RepetitiveGroupDataSource repetitiveGroupDataSourcee)
    {messagesDataSource = messagesDataSourcee;
        repetitiveGroupDataSource = repetitiveGroupDataSourcee;
    getRepeatMessages = new GetRepeatMessages();}

    public  void run() {
        // write your code here


        System.out.println("Are you going to start the Program?");

        ArrayList<Integer> m_r = new ArrayList<>();

//        ArrayList<Message> m = new ArrayList<Message>();
//        m = select_type(0);
        ArrayList<RepetitiveGroup> G = new ArrayList<>();

        ArrayList<RepetitiveGroup> G_O = new ArrayList<>();

        G = Category(0);
        G_O = Category(1);

        for (int i = 0; i < G.size(); i++) {
            if (G.get(i).getMess_index().size() < 2) ;
            else {
                for (int i_o = 0; i_o < G_O.size(); i_o++) {
                    //&& G_O.get(i_o).getRelated_group()==null
                    if (G_O.get(i_o).getMess_index().size() >= 2) {
                        Hashtable<Double, Double> n = is_Related(G.get(i).getMess_index(), G_O.get(i_o).getMess_index());
                        double d = 0;
                        if (n != null) {
                            for (double db : n.keySet()) d = db;
                            if (d <= 3) {
//                                G.get(i).getRelatd_group().add(G_O.get(i_o));
//                                G_O.get(i_o).getRelated_group().add(G.get(i));
                                G.get(i).getRelated_group().put(G_O.get(i_o), n.get(d));
                                G_O.get(i_o).getRelated_group().put(G.get(i), n.get(d));
                            }
                        }
                    }
                }
            }
        }

        ArrayList<RepetitiveGroup> result_group = new ArrayList<>();
        for (RepetitiveGroup rg : G) {
            if (rg.getRelated_group().size() != 0) result_group.add(rg);
        }

        int i = 0;
        for(RepetitiveGroup r:result_group){
            i++;
            for(RepetitiveGroup g:r.getRelated_group().keySet()){
                System.out.println(i+" "+ r.getMess().get(0)+" "+g.getMess().get(0)+ " "+r.getRelated_group().get(g));
                repetitiveGroupDataSource.createComment(i,r.getMess().get(0),g.getMess().get(0),r.getRelated_group().get(g));
            }
        }

//            String strr = "";
//            while(1==1){
//                System.out.println("Please enter the sentence you would like to analyze:");
//                System.out.println("no to exit:");
//                strr = s.next();
//                if(strr.toLowerCase().equals( "no")) break;
//                for(RepetitiveGroup rg:result_group){
//                    if(relation(strr,rg)>=0.76){
//                        for(RepetitiveGroup rgg:rg.getRelated_group()){
//                            System.out.println(rgg.getMess().get(0));
//                            System.out.println("-------Response---------");
//                        }
//                    }
//                }
//            }

//        G = RDD_Detection(m,0.2);
//
//        ArrayList<RepetitiveGroup> G_add = new ArrayList<>();
//
//        ArrayList<Integer> i_list = new ArrayList<>();
//
//        for(int i=0;i<G.size();i++){
//            if(G.get(i).getMess_index().size()>2) {
//                ArrayList<RepetitiveGroup> G_n = RDD_Detection(G.get(i), 0.76);
//                for (RepetitiveGroup g_n : G_n) {
//                    G_add.add(g_n);
//                }
//                i_list.add(i);
//            }
//        }
//
//        for(int i=0;i<i_list.size();i++){G.remove(i_list.get(i)-i);}
//        for (RepetitiveGroup g_n : G_add) {
//            G.add(0,g_n);
//        }

//        for (Message me  : m) {
//            System.out.println(me.getMess());
//            System.out.println(me.getId());
//            System.out.println(Compare_se("Can we meet at noon?",me.getMess()));
//            System.out.println("-----------------------------");
//
//
//        }


        for (RepetitiveGroup g  : G) {
            for(String str:g.getMess()){System.out.println(str);}
            for(int k:g.getMess_index()){System.out.println(k);}

            if (g.getRelated_group().size()!=0){
                System.out.println("Related Group!");
                for(RepetitiveGroup rg:g.getRelated_group().keySet()) {
                    for (String strr : rg.getMess()) {
                        System.out.println(strr);
                    }
                    for (int kk : rg.getMess_index()) {
                        System.out.println(kk);
                    }
                }
            }

            System.out.println("-----------------------------");


        }





    }

    public  Hashtable<Double,Double> is_Related(ArrayList<Integer> ds1, ArrayList<Integer> ds2){


        Hashtable<Double,Double> n = new Hashtable<Double, Double>();
        if(ds1.size()<ds2.size()*0.6 || ds2.size()<ds1.size()*0.6) return null;

        int smallestnum = 0;
        int count = 0;
        double boundry = ds1.size()*0.35;
        ArrayList<Integer> subs = new ArrayList<>();

        if(ds1.size()>ds2.size()){
            ArrayList<Integer> d3 = new ArrayList<>();
            d3=ds2;ds2=ds1;ds1=d3;}

        for(int i1:ds1){

            smallestnum = getSmallestSubstract(i1,ds2);
            if(smallestnum<=5) subs.add(smallestnum);
            else{
                count++;
                if(count<=boundry);
                else return null;
            }

        }

        int sum_subs = 0;
        double relevence = ((double)subs.size()/((double)ds1.size()+(double)ds2.size())*2.0);
        for(int i:subs){sum_subs = sum_subs + i;}
        double ave_subs = sum_subs / subs.size();
        if(Math.abs(ave_subs)<=3){n.put(Math.abs(ave_subs),relevence);
            System.out.print("relevence"+relevence);
            return n;}
        else return null;
    }

    public  int getSmallestSubstract(int i, ArrayList<Integer> ds){

        int sub = 100000;
        int sub_s = 0;
        for(int is:ds){
            sub_s = i-is;
            if(Math.abs(sub_s)>=Math.abs(sub)) break;
            else sub = sub_s;
        }

        return sub;

    }

    public  ArrayList<RepetitiveGroup> Category(int index){
        ArrayList<Messages> m = new ArrayList<Messages>();
        m = messagesDataSource.getAllComments(index);
        ArrayList<RepetitiveGroup> G = new ArrayList<>();

        G = getRepeatMessages.RDD_Detection(m,0.2);

        ArrayList<RepetitiveGroup> G_add = new ArrayList<>();

        ArrayList<Integer> i_list = new ArrayList<>();

        for(int i=0;i<G.size();i++){
            if(G.get(i).getMess_index().size()>2) {
                ArrayList<RepetitiveGroup> G_n = getRepeatMessages.RDD_Detection(G.get(i), 0.76);
                for (RepetitiveGroup g_n : G_n) {
                    G_add.add(g_n);
                }
                i_list.add(i);
            }
        }

        for(int i=0;i<i_list.size();i++){G.remove(i_list.get(i)-i);}
        for (RepetitiveGroup g_n : G_add) {
            G.add(0,g_n);
        }
        return G;
    }

}