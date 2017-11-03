package com.example.usuario.algoritmosia;

import java.util.Random;

/**
 * Created by USUARIO on 10/07/2017.
 */

public class Matriz {

    private int maxFil;
    private int maxCol;
    private int cantFil;
    private int cantCol;
    private int elem[][];

    public Matriz(){
        ////////////////////////////
    }

    public Matriz(int maxFil, int maxCol) {
        this.maxFil = maxFil;
        this.maxCol = maxCol;
        this.cantFil=this.cantCol=0;
        this.elem=new int[maxFil][maxCol];
    }

    public int getCantFil() {
        return cantFil;
    }

    public int getMaxFil() {
        return maxFil;
    }

    public void setMaxFil(int maxFil) {
        this.maxFil = maxFil;
    }

    public int getMaxCol() {
        return maxCol;
    }

    public void setMaxCol(int maxCol) {
        this.maxCol = maxCol;
    }

    public void setCantFil(int cantFil) {
        this.cantFil = cantFil;
    }

    public int getCantCol() {
        return cantCol;
    }

    public void setCantCol(int cantCol) {
        this.cantCol = cantCol;
    }

    public void dimensionar(int filas,int columnas){
        this.cantFil=filas;
        this.cantCol=columnas;
    }

    public void cargarRandom(int max){
        for (int i=0;i<this.cantFil;i++){
            for (int j=0;j<this.cantCol;j++){
                this.set(i,j,new Random().nextInt(max));
            }
        }
    }

    public void set(int fil,int col,int ele){
        if (fil<this.cantFil && col<this.cantCol){
            elem[fil][col]=ele;
        }
    }

    public int get(int fil,int col){
        return (fil<this.cantFil&&col<this.cantCol?elem[fil][col]:-9999999);
    }

    public void intercambiarElem(int f1,int c1,int f2,int c2){
        int aux=elem[f1][c1];
        elem[f1][c1]=elem[f2][c2];
        elem[f2][c2]=aux;
    }

    public void intercambiarFilas(int f1,int f2){
        for (int j=0;j<this.cantCol;j++){
            this.intercambiarElem(f1, j, f2, j);
        }
    }
    public void eliminarFila(int f1){
        if (f1<this.cantFil){
            for (int i=f1+1;i<this.cantFil;i++){
                intercambiarFilas(f1, i);
                f1=i;
            }
            this.cantFil--;
        }
    }

    public void intercambiarColumnas(int c1,int c2){
        for (int i=0;i<this.cantFil;i++){
            this.intercambiarElem(i, c1, i, c2);
        }
    }

    public void eliminarColumna(int c1){
        if (c1<this.cantCol){
            for (int j=c1+1;j<this.cantCol;j++){
                intercambiarColumnas(c1, j);
                c1=j;
            }
            this.cantCol--;
        }
    }




}
