package com.example.admin.ocr;

/**
 * Created by ADMIN on 4/22/2017.
 */

public class Matrix {
    private static int r;
    private static int c;
    private int[][] data;

    public Matrix(int r, int c){
        this.r = r;
        this.c = c;
        data = new int[r][c];
    }

    public Matrix(int[][] data){
        r = data.length;
        c = data[0].length;
        this.data = new int[r][c];
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                this.data[i][j] = data[i][j];
    }

    private Matrix(Matrix A){
        this(A.data);
    }

    public int getRow() {
        return r;
    }

    public int getColumn() {
        return c;
    }

    public int[][] getData() {
        return data;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                s +=data[i][j];
                if(j<c-1) {
                    s += " ";
                }
            }
            s +="\n";
        }
        return s;
    }


}
