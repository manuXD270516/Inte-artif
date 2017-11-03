package com.example.usuario.algoritmosia;

import android.content.DialogInterface;
import android.icu.lang.UCharacter;
import android.icu.text.DisplayContext;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyRep;

/**
 * Created by USUARIO on 09/07/2017.
 */

public class FragmentDeterminante extends Fragment implements View.OnClickListener{

    private SeekBar seekBarDimension;
    private TextView lblDimesion;
    EditText txtElementos;
    Button btnCargarMatriz,btnDeterminante;
    Matriz M1;
    GridView gvMatriz;
    private String datos[];
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.determinante_fragment,container,false);
        //final EditText txtApellidos = (EditText)getView().findViewById(R.id.txtDato);
        M1=new Matriz(100,100);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lblDimesion=(TextView)getView().findViewById(R.id.textView5);
        btnCargarMatriz=(Button)getView().findViewById(R.id.btnCargarM);
        btnDeterminante=(Button)getView().findViewById(R.id.btnDeterminante);
        btnCargarMatriz.setOnClickListener(this);
        btnDeterminante.setOnClickListener(this);
        gvMatriz=(GridView)getView().findViewById(R.id.gvMatriz);

        seekBarDimension=(SeekBar)getView().findViewById(R.id.skDimension);


        seekBarDimension.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getActivity(), "N = "+progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getActivity(), "Started tracking seekbar ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                lblDimesion.setText(progress + "/" + seekBar.getMax());
                String message;
                if (progress<10){
                    message="Dimesion Seleccionada Correctamente!!!";
                } else {
                    message="Dimesion Maxima de la Matriz Seleccionada";
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                M1.dimensionar(progress,progress);
                datos=new String[progress*progress];
            }
        });


    }




    public static int signo(int i,int j){
        return (int)Math.pow(-1, i+j);
    }

    public static Matriz copiarMatriz(Matriz M1){
        Matriz M3=new Matriz(M1.getMaxFil(), M1.getMaxCol());
        M3.setCantFil(M1.getCantFil());
        M3.setCantCol(M1.getCantCol());
        for (int i=0;i<M3.getCantFil();i++){
            for (int j=0;j<M3.getCantCol();j++){
                M3.set(i, j, M1.get(i, j));
            }
        }
        return M3;
    }
    public static Matriz menor(Matriz M1,int i,int j){
        Matriz M2=copiarMatriz(M1);
        M2.eliminarFila(i);
        M2.eliminarColumna(j);
        return M2;
    }

    public static int getDeterminante(Matriz M1){
        if (M1.getCantFil()==1){
            return M1.get(0, 0);
        }
        int i=0; int j=0; int sum=0;
        while (i<M1.getCantFil()){
            Matriz M2=menor(M1,i,j);
            sum=sum+signo(i, j)*M1.get(i, j)*getDeterminante(M2);
            i++;
        }
        return sum;
    }



    @Override
    public void onClick(View v) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        if (v.getId()==R.id.btnCargarM){
            //Toast.makeText(getActivity().getApplicationContext(),"adasd",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("DETERMINANTE");
            builder.setMessage("\nLa Matriz sera cargada randomicamente con numeros entre 1 y 50");
            builder.setIcon(android.R.drawable.ic_menu_send);
            //LayoutInflater inflater = getActivity().getLayoutInflater();
            /*final View view = inflater.inflate(R.layout.alert_dialog_inputdato, null);

            final EditText txtk=(EditText)view.findViewById(R.id.txtDato);
            //builder.setView(R.layout.alert_dialog_inputdato);
            builder.setView(R.layout.alert_dialog_inputdato);*/
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (M1.getCantFil()>0){
                        M1.cargarRandom(50);
                        mostrarMatriz();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),"DIMENSION DE LA MATRIZ NxN INCORRECTA.....",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            builder.show();
            builder.create();
            btnDeterminante.setVisibility(View.VISIBLE);
            // realizar la peticion de los datos de la matriz

            //Toast.makeText(getActivity(),"adas",Toast.LENGTH_SHORT).show();
        } else if (v.getId()==R.id.btnDeterminante){

            //Toast.makeText(getActivity().getApplicationContext(),"adasd",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("\nEl Determinante de La matriz es = ".toUpperCase()+getDeterminante(M1));
            builder.setTitle("ALGORITMO DETERMINANTE");

            //LayoutInflater inflater = getActivity().getLayoutInflater();
            /*final View view = inflater.inflate(R.layout.alert_dialog_inputdato, null);

            final EditText txtk=(EditText)view.findViewById(R.id.txtDato);
            //builder.setView(R.layout.alert_dialog_inputdato);
            builder.setView(R.layout.alert_dialog_inputdato);*/
            builder.setCancelable(false);
            builder.setIcon(android.R.drawable.ic_menu_send);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                }
            });
            builder.show();
            builder.create();
            btnDeterminante.setVisibility(View.INVISIBLE);
        }
    }

    private void mostrarMatriz() {
        gvMatriz.setAdapter(null);
        int pos=0;
        gvMatriz.setNumColumns(M1.getCantCol());
        for (int i=0;i<M1.getCantFil();i++){
            for (int j=0;j<M1.getCantCol();j++){
                datos[pos]=String.valueOf(M1.get(i,j));
                pos++;
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datos);
        gvMatriz.setAdapter(adaptador);
    }
}
