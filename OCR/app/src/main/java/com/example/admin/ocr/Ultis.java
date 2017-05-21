package com.example.admin.ocr;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by ADMIN on 4/21/2017.
 */

public class Ultis {
    public static int getRow(String s){
        int r = 1;
        for(int i = 0 ;i < s.length(); i ++){
            if(s.charAt(i) == '\n')
                r++;
        }
        return r;

    }

    public static int getColumn(String s){
        StringTokenizer st = new StringTokenizer(s);
        int c = 1;
        while(st.hasMoreTokens()){
            String t = st.nextToken();
            c++;

        }
        return c / getRow(s);
    }

    public static Matrix createMatrix(String s){

        StringTokenizer st = new StringTokenizer(s);
        ArrayList<Integer> b = new ArrayList();

        while(st.hasMoreTokens()){
            String t = st.nextToken();
            int k = Integer.parseInt(t);
            b.add(k);

        }
        int m[][] = new int[getRow(s)][getColumn(s)];
        int n = 0;
        for(int i = 0; i < getRow(s); i ++){
            for(int  j = 0; j < getColumn(s); j ++){
                m[i][j] = b.get(n);
                n++;
            }
        }
        return new Matrix(m);
    }

    public static String hanldingString(String s){
        StringBuilder sblr = new StringBuilder();
        for(int i = 0; i < s.length(); i ++){
            if(s.charAt(i) != '\n'){
                sblr.append(s.charAt(i));
                sblr.append(" ");
            }
            else
                sblr.append("\n");


        }
        return sblr.toString();
    }


}
