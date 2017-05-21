package com.example.admin.ocr;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ADMIN on 4/22/2017.
 */

public class Filter {
    public static Matrix createFilterMatrix(Matrix a){
        int b[][] = new int[a.getRow() + 2][a.getColumn() + 2];
        for(int i = 0; i < a.getRow() + 2; i ++){
            for(int j = 0; j < a.getColumn() + 2; j ++){
                if(j == 0 || j == a.getColumn()+ 1)    b[i][j] = 0;
                if(i == 0 || i == a.getRow() + 1)    b[i][j] = 0;
            }
        }
        int c[][] = a.getData();
        for(int i = 1; i < a.getRow() + 1; i ++){
            for(int j = 1; j < a.getColumn() + 1; j ++){
                b[i][j] = c[i - 1][j - 1];
            }
        }
        return new Matrix(b);
    }

    public static Matrix AverageFilter(Matrix m){
        Matrix a = createFilterMatrix(m);
        float b[][] = new float[a.getRow() - 2][a.getColumn() - 2];
        int d[][] = new int[a.getRow() - 2][a.getColumn() - 2];
        int c[][]  = a.getData();
        for(int i = 1; i < a.getRow() - 1; i ++){
            for(int  j = 1; j < a.getColumn() - 1; j ++){
                b[i - 1][j - 1] = (float)(c[i - 1][j - 1] + c[i - 1][j] + c[i - 1][j + 1]
                        + c[i][j - 1] + c[i][j] + c[i][j + 1] + c[i + 1][j - 1] + c[i + 1][j] + c[i + 1][j + 1])/9;
                d[i - 1][j - 1] = Math.round(b[i - 1][j - 1]);
            }
        }
        return new Matrix(d);
    }

    public static Matrix MedianFilter(Matrix m){
        Matrix a = createFilterMatrix(m);
        int b[][] = new int[a.getRow() - 2][a.getColumn() - 2];
        int c[][]  = a.getData();
        for(int i = 1; i < a.getRow() - 1; i ++){
            for(int  j = 1; j < a.getColumn() - 1; j ++){
                ArrayList<Integer> d = new ArrayList();
                d.add(c[i - 1][j - 1]);
                d.add(c[i - 1][j]);
                d.add(c[i - 1][j + 1]);
                d.add(c[i][j - 1]);
                d.add(c[i][j]);
                d.add(c[i][j + 1]);
                d.add(c[i + 1][j - 1]);
                d.add(c[i + 1][j]);
                d.add(c[i + 1][j + 1]);
                Collections.sort(d);
                b[i - 1][j - 1] = d.get(4);
            }
        }

        return new Matrix(b);
    }

    public static Matrix MinFilter(Matrix m){
        Matrix a = createFilterMatrix(m);
        int b[][] = new int[a.getRow() - 2][a.getColumn() - 2];
        int c[][]  = a.getData();
        for(int i = 1; i < a.getRow() - 1; i ++){
            for(int  j = 1; j < a.getColumn() - 1; j ++){
                ArrayList<Integer> d = new ArrayList();
                d.add(c[i - 1][j - 1]);
                d.add(c[i - 1][j]);
                d.add(c[i - 1][j + 1]);
                d.add(c[i][j - 1]);
                d.add(c[i][j]);
                d.add(c[i][j + 1]);
                d.add(c[i + 1][j - 1]);
                d.add(c[i + 1][j]);
                d.add(c[i + 1][j + 1]);
                Collections.sort(d);
                b[i - 1][j - 1] = d.get(0);
            }
        }

        return new Matrix(b);
    }

    public static Matrix MaxFilter(Matrix m){
        Matrix a = createFilterMatrix(m);
        int b[][] = new int[a.getRow() - 2][a.getColumn() - 2];
        int c[][]  = a.getData();
        for(int i = 1; i < a.getRow() - 1; i ++){
            for(int  j = 1; j < a.getColumn() - 1; j ++){
                ArrayList<Integer> d = new ArrayList();
                d.add(c[i - 1][j - 1]);
                d.add(c[i - 1][j]);
                d.add(c[i - 1][j + 1]);
                d.add(c[i][j - 1]);
                d.add(c[i][j]);
                d.add(c[i][j + 1]);
                d.add(c[i + 1][j - 1]);
                d.add(c[i + 1][j]);
                d.add(c[i + 1][j + 1]);
                Collections.sort(d);
                b[i - 1][j - 1] = d.get(8);
            }
        }

        return new Matrix(b);
    }

}
