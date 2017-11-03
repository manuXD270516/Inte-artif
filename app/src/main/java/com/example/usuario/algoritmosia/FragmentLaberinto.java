package com.example.usuario.algoritmosia;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by USUARIO on 09/07/2017.
 */

public class FragmentLaberinto extends Fragment implements View.OnClickListener {

    private int camino=0;
    Button btnCargarLab,btnSolLab;
    GridView gvLaberinto;
    EditText txtFilas,txtColumnas;
    private String[] datos;
    int filas,columnas;
    int vezClic=0;
    Regla r1,r2;
    int m[][];
    boolean clickeable=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.laberinto_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnCargarLab=(Button)getView().findViewById(R.id.btnCargarLab);
        btnSolLab=(Button)getView().findViewById(R.id.btnSolucionarLab);
        btnCargarLab.setOnClickListener(this);
        btnSolLab.setOnClickListener(this);
        txtColumnas=(EditText)getView().findViewById(R.id.txtColumnas);
        txtFilas=(EditText)getView().findViewById(R.id.txtFilas);
        gvLaberinto=(GridView)getView().findViewById(R.id.gvLaberinto);
        gvLaberinto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x,y;
                x=position/columnas;
                y=position%columnas;
                if (clickeable==false){
                    if (m[x][y]!=1){
                        if (vezClic==0){
                            r1=new Regla(x,y);
                            vezClic=1;
                            Toast.makeText(getActivity().getApplicationContext(),"punto de partida : ( fila ="+x+", columna = "+y+" )".toUpperCase(),Toast.LENGTH_SHORT).show();
                        } else {
                            r2=new Regla(x,y);
                            vezClic=0;
                            Toast.makeText(getActivity().getApplicationContext(),"punto de llegada : ( fila ="+x+", columna = "+y+" )".toUpperCase(),Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        alerta("Seleccionar una posicion valida porfavor...");
                    }
                }else {
                    View f=gvLaberinto.getChildAt(position);
                    f.setBackgroundColor(Color.RED);
                }
            }
        });
    }

    public void alerta(String mensaje){
        AlertDialog.Builder alertEnProgreso = new AlertDialog.Builder(getActivity());
        alertEnProgreso.setTitle("Solucion Laberinto");
        alertEnProgreso.setMessage(mensaje);
        alertEnProgreso.setCancelable(false);
        alertEnProgreso.setIcon(android.R.drawable.ic_menu_info_details);
        alertEnProgreso.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alertEnProgreso.create();
        alert11.show();
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnCargarLab){
            r1=null; r2=null;
            clickeable=false;
            filas=Integer.parseInt(txtFilas.getText().toString());
            columnas=Integer.parseInt(txtColumnas.getText().toString());
            datos=new String[filas*columnas];
            m=cargarLaberinto(filas,columnas);
            mostrarResultado(m);
            btnSolLab.setVisibility(View.VISIBLE);
        } else if (v.getId()==R.id.btnSolucionarLab){

            if (r1==null || r2==null){
                alerta("\nPrimero debe seleccionar los puntos de partia y llegada para la resolucion del ejercicio");
            } else {
                alerta("\nSe iniciara una secuencia de Numeros: 2,3,..,etc la cual llevara a la solucion del laberinto planteado");
                laberintoDiagonalOnly(m,r1.getFil(),r1.getCol(),r2.getFil(),r2.getCol(),2);
                camino=0;
                btnSolLab.setVisibility(View.INVISIBLE);
                clickeable=true;
            }

        }
    }

    private int[][] cargarLaberinto(int filas, int columnas) {
        int m[][]=new int[filas][columnas];
        for (int i=0;i<filas;i++){
            for (int j=0;j<columnas;j++){
                if (j%3==0){
                    m[i][j]=new Random().nextInt(2);
                } else {
                    m[i][j]=0;
                }
            }
        }
        return m;
    }


    // ALGORITMO DEL LABERINTO
    public  void laberintoDiagonalOnly(int m[][],int i1,int j1,int i2,int j2,int paso){
        m[i1][j1]=paso;
            if (i1==i2 && j1==j2 && camino==0){
            //mostrar(m)
            mostrarResultado(m);
            camino++;
        }
        LinkedList<Regla> L1=reglasAplicablesDiagonales(m, i1, j1);
        while (!L1.isEmpty()){
            Regla regla=L1.removeFirst();
            laberintoDiagonalOnly(m, regla.getFil(), regla.getCol(), i2, j2, paso+1);
            m[regla.getFil()][regla.getCol()]=0;
        }
    }

    private void mostrarResultado(int[][] m) {
        gvLaberinto.setAdapter(null);
        int pos=0;
        gvLaberinto.setNumColumns(m[0].length);
        for (int i=0;i<m.length;i++){
            for (int j=0;j<m[i].length;j++){
                datos[pos]=String.valueOf(m[i][j]);
                pos++;
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datos);
        gvLaberinto.setAdapter(adaptador);
    }


    private  LinkedList<Regla> reglasAplicablesDiagonales(int[][] m, int i, int j) {
        LinkedList<Regla> L1=new LinkedList<>();
        if (posicionValida(m, i, j-1)){ // izq
            L1.add(new Regla(i, j-1));
        }
        if (posicionValida(m, i-1, j-1)){ // izq dia sup izq
            L1.add(new Regla(i-1, j-1));
        }
        if (posicionValida(m, i-1, j)){ // arriba
            L1.add(new Regla(i-1, j));
        }
        if (posicionValida(m, i-1, j+1)){ // arriba diag sup der
            L1.add(new Regla(i-1, j+1));
        }
        if (posicionValida(m, i, j+1)){ // derecha
            L1.add(new Regla(i, j+1));
        }
        if (posicionValida(m, i+1, j+1)){ // abajo diag inf der
            L1.add(new Regla(i+1, j+1));
        }
        if (posicionValida(m, i+1, j)){ // abajo
            L1.add(new Regla(i+1, j));
        }
        if (posicionValida(m, i+1, j-1)){ // abajo diag inf izq
            L1.add(new Regla(i+1, j-1));
        }
        return L1;
    }

    public boolean posicionValida(int m[][],int i,int j){
        return i>=0 && i<m.length && j>=0 && j<m[i].length && m[i][j]==0;
    }

    private class Regla{
        public int Fil;
        public int Col;

        public Regla(int fil, int col){
            this.Fil=fil;
            this.Col=col;
        }

        public int getFil() {
            return Fil;
        }

        public void setFil(int Fil) {
            this.Fil = Fil;
        }

        public int getCol() {
            return Col;
        }

        public void setCol(int Col) {
            this.Col = Col;
        }

    }
}
