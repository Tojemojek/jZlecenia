package pl.kostrowski.doka.jzlecenia.helpers;

import java.util.Arrays;
import java.util.HashSet;

public class StringConverters2 {







    public static String insertTable =
            "COPY public.salary_grade (grade, min_sal, max_sal) FROM stdin;";

    private static String original = "1\t800\t1300\n" +
            "2\t1301\t1500\n" +
            "3\t1501\t2100\n" +
            "4\t2101\t3100\n" +
            "5\t3101\t9999";


    private static int ileKolumn = 3;
    private static Integer[] tekstyInt = {};
    private static HashSet<Integer> teksty = new HashSet<Integer>();



    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();

        teksty.addAll(Arrays.asList(tekstyInt));

        String insert = insertTable
                .replace("COPY public.","insert into ")
                .replace("FROM stdin;", "values");


        sb.append(insert);
        sb.append("\n");

        String s = "";
        s = s.concat(original);
        s = s.replace("\n","\t");
        s = s.replace("\\N","null");

        String[] split = s.split("\t");


        int i = 1;
        for (String s1 : split) {

            if ((i)%ileKolumn==1){
                sb.append("(");
            }

            if (teksty.contains((i)%ileKolumn) || (teksty.contains(ileKolumn) && (i)%ileKolumn ==0)) {
                sb.append("'");
            }

            sb.append(s1);

            if (teksty.contains((i)%ileKolumn) || (teksty.contains(ileKolumn) && (i)%ileKolumn ==0)){
                sb.append("'");
            }

            if ((i)%ileKolumn==0){
                sb.append("),\n");
            } else {
                sb.append(",");
            }


            i++;
        }

        sb.replace(sb.lastIndexOf(","),sb.lastIndexOf(",")+1,";");

        System.out.println(sb.toString());


    }


}
