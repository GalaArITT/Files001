package com.ejemplo.files001;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Button leersd;
    Button escribirsd;
    TextView textView;
    EditText editText;
    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;
    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText= (EditText)findViewById(R.id.editText);
        leersd=(Button)findViewById(R.id.leersd);
        escribirsd=(Button)findViewById(R.id.escribirsd);

        textView = (TextView)findViewById(R.id.textView);

        // Código que me comprueba si existe SD y si puedo escribir o no
        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED))
        {
            sdDisponible = true;
            sdAccesoEscritura = true;
        }
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
        {
            sdDisponible = true;
            sdAccesoEscritura = false;
        }
        else
        {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }

        leersd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sdDisponible){

                    try
                    {
                        File ruta_sd = Environment.getExternalStorageDirectory();

                        File f = new File(ruta_sd.getAbsolutePath(), "ficherosd.txt");

                        BufferedReader fin =
                                new BufferedReader(
                                        new InputStreamReader(
                                                new FileInputStream(f)));

                        String texto = fin.readLine();
                        textView.setText(texto);
                        fin.close();
                    }
                    catch (Exception ex)
                    {
                        Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
                    }
                    Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();

                }

            }
        });
        escribirsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sdAccesoEscritura && sdDisponible){
                    String str = editText.getText().toString();
                    try
                    {
                        File ruta_sd = Environment.getExternalStorageDirectory();

                        File f = new File(ruta_sd.getAbsolutePath(), "ficherosd.txt");

                        OutputStreamWriter fout =
                                new OutputStreamWriter(
                                        new FileOutputStream(f));

                        fout.write(str);
                        fout.close();
                    }
                    catch (Exception ex)
                    {
                        Log.e("Ficheros", "Error al escribir fichero en la tarjeta SD");
                    }
                }
                Toast.makeText(getBaseContext(), "File loaded successfully!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}


