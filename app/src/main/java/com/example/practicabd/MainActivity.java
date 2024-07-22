package com.example.practicabd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.practicabd.Clases.ClaseBD;

public class MainActivity extends AppCompatActivity {
    EditText origen,destino;
    Button registrar,leer;
    TextView respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
       origen = (EditText) findViewById(R.id.origen);
       destino = (EditText) findViewById(R.id.destino);
       registrar = (Button) findViewById(R.id.registrar);
       leer = (Button) findViewById(R.id.leer);
       respuesta = (TextView)findViewById(R.id.respuesta);

       leer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                    String response = LeerRegistros();
               String[] res = response.split("&");
               if(res[0].equals("success"))
                   respuesta.setText(res[1]);
               else
                   respuesta.setText(res[1]);
           }
       });

       registrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                    String origin = origen.getText().toString();
                    String destiny = destino.getText().toString();
                    if(origin.equals("")||destiny.equals("")){
                        Toast.makeText(getApplicationContext(),"Debes llenar ambos campos",Toast.LENGTH_LONG).show();
                    }else{
                        String response = insertBDrow(origin,destiny);
                        String[] res = response.split("-");
                        if(res[0].equals("success"))
                            Toast.makeText(getApplicationContext(),res[1],Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(),res[1],Toast.LENGTH_LONG).show();
                    }

           }
       });

    }

    private String LeerRegistros() {
        ClaseBD admin = new ClaseBD(getApplicationContext(),"BoletosADO",null,1);
        SQLiteDatabase reader = admin.getReadableDatabase();
        try {
            Cursor cursor = reader.rawQuery("Select * from boletos",null);
            String response = "id      origen       destino      fecha  \n";

            while(cursor.moveToNext()) {
                response +=  cursor.getInt(0)+"        "+cursor.getString(1)+"       "+cursor.getString(2)+"     "+cursor.getString(3);
            }
            cursor.close();
            return "success&"+response;
        }catch (Exception e){
            return "error&"+e.getMessage();
        }
    }

    private String insertBDrow(String origin, String destiny) {
        ClaseBD admin = new ClaseBD(getApplicationContext(),"BoletosADO",null,1);
        ContentValues registro =new ContentValues();
        SQLiteDatabase reader = admin.getReadableDatabase();
        try {
           registro.put("origen",origin);
           registro.put("destino",destiny);
           reader.insert("boletos",null,registro);
           reader.close();
           return "success-Registro insertado con exito!!!!";

        }catch (Exception e){
            return "Error-"+e.getMessage();
        }

    }
}