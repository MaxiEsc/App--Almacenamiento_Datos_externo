package com.maxescobar.almacenamientosd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre, etDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = (EditText) findViewById(R.id.etTitulo);
        etDatos = (EditText) findViewById(R.id.etmDescripcion);
    }

    //Boton Guardar
    public void BtnGuardar(View vista){
        String nombreTitulo = etNombre.getText().toString();
        String datos = etDatos.getText().toString();
        //Bloque de excepciones
        try {
            //Recupera la ruta donde se guardara el archivo
            File SDCard = Environment.getExternalStorageDirectory();
            //Muestra la ruta
            Toast.makeText(this,SDCard.getPath(),Toast.LENGTH_SHORT).show();
            //Se crea un archivo con el nombre de la ruta y se crea el archivo con el nombre que el usuario le ha pasado
            File rutaArchivo = new File(SDCard.getPath(),nombreTitulo);
            //Aqui se puede decir que el archivo se 'Abrirá' con el archivo de tipo OutputStreamWriter
            OutputStreamWriter creaArchivo = new OutputStreamWriter(openFileOutput(nombreTitulo, Activity.MODE_PRIVATE));
            //Se escribe el contenido en el archivo abierto
            creaArchivo.write(datos);
            //Limpiar el buffer
            creaArchivo.flush();
            //Cierra el archivo
            creaArchivo.close();
            //Informamos al usuario de nuestras acciones
            Toast.makeText(this,"Archivo guardado corectamente",Toast.LENGTH_SHORT).show();
            //Se limpian los campos de texto
            etNombre.setText("");
            etDatos.setText("");

        }catch (IOException e){
            Toast.makeText(this,"No se ha podido guardar, Revise de tarjeta SD" , Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo consultar
    public void BtnConsultar(View vista){
        String nombre = etNombre.getText().toString();

        try {
            //De vuelta obtenemos la ruta lo hacemos de tipo file porque no se hace en cadena
            File SDCard = Environment.getExternalStorageDirectory();
            //creamos el archivo con nombre y con la ruta pero en este caso es el que usaremos para cargar
            File rutaArchivo = new File(SDCard.getPath(),nombre);
            //Archivo de InputStreamReader que utilizaremos para crear el archivo de lectura
            InputStreamReader abrirArchivo = new InputStreamReader(openFileInput(nombre));
            //Leer el archivo
            BufferedReader leerArchivo = new BufferedReader(abrirArchivo);
            //que lea la primera linea
            String linea = leerArchivo.readLine();
            //esta variable cargara todo el contenido de lo que se lea
            String datos = "";
            //Ciclo repetitivo
            while (linea != null){
                //Concatenamos
                datos = datos + linea + "\n";
                //leemos la siguiente linea hasta que no haya nada
                linea = leerArchivo.readLine();
            }
            //Cerramos el archivo
            leerArchivo.close();
            //cerramos el lector
            abrirArchivo.close();
            //Le enviamos lo leido al multitextline...o algo asi
            etDatos.setText(datos);
        }catch (IOException e){
            Toast.makeText(this,"Error al leer el archivo. Revise su SD", Toast.LENGTH_SHORT).show();
        }
    }
}