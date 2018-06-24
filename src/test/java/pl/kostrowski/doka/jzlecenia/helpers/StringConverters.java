package pl.kostrowski.doka.jzlecenia.helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class StringConverters {


    public static String insertTable =
            "COPY public.employees (emp_id, emp_name, job_name, manager_id, hire_date, salary, commission, dep_id) FROM stdin;";

    private static String original = "68319\tKAYLING\tPRESIDENT\t\\N\t1991-11-18\t6000.00\t\\N\t1001\n" +
            "66928\tBLAZE\tMANAGER\t68319\t1991-05-01\t2750.00\t\\N\t3001\n" +
            "67832\tCLARE\tMANAGER\t68319\t1991-06-09\t2550.00\t\\N\t1001\n" +
            "65646\tJONAS\tMANAGER\t68319\t1991-04-02\t2957.00\t\\N\t2001\n" +
            "64989\tADELYN\tSALESMAN\t66928\t1991-02-20\t1700.00\t400.00\t3001\n" +
            "65271\tWADE\tSALESMAN\t66928\t1991-02-22\t1350.00\t600.00\t3001\n" +
            "66564\tMADDEN\tSALESMAN\t66928\t1991-09-28\t1350.00\t1500.00\t3001\n" +
            "68454\tTUCKER\tSALESMAN\t66928\t1991-09-08\t1600.00\t0.00\t3001\n" +
            "68736\tADNRES\tCLERK\t67858\t1997-05-23\t1200.00\t\\N\t2001\n" +
            "69000\tJULIUS\tCLERK\t66928\t1991-12-03\t1050.00\t\\N\t3001\n" +
            "69324\tMARKER\tCLERK\t67832\t1992-01-23\t1400.00\t\\N\t1001\n" +
            "67858\tSCARLET\tANALYST\t65646\t1997-04-19\t3100.00\t\\N\t2001\n" +
            "69062\tFRANK\tANALYST\t65646\t1991-12-03\t3100.00\t\\N\t2001\n" +
            "63679\tSANDRINE\tCLERK\t69062\t1990-12-18\t900.00\t\\N\t2001";


    private static int ileKolumn = 8;
    private static Integer[] tekstyInt = {2,3,5};
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

            if ((i)%8==1){
                sb.append("(");
            }

            if (teksty.contains((i)%8)){
                sb.append("'");
            }

            sb.append(s1);

            if (teksty.contains((i)%8)){
                sb.append("'");
            }

            if ((i)%8==0){
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
