package net.ivanvega.datosenandroidp77a;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    RadioGroup optAlmac;
    EditText txt;
    String filename = "MiArchivito.txt";
    FileOutputStream outputStream;
    FileInputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optAlmac = findViewById(R.id.optAlmacenamiento);
        txt = findViewById(R.id.txtTexto);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void btnAbrir_click(View view) {
        if(optAlmac.getCheckedRadioButtonId() == R.id.optInterna){
            abrirInterna();
        }else{
            abrirExterna();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void abrirExterna() {

        File dir  =
                getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        try {
            inputStream =
                    new FileInputStream(dir.getAbsolutePath() +
                            "/" +
                            "MiTexto.txt");
            Toast.makeText(this, dir.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
            Log.i("ESCRITURA", dir.getAbsolutePath() );
            InputStreamReader isr =
                    new InputStreamReader(inputStream);
            char[] buf = new char[512];
            String str = "";
            while(isr.read(buf)!= -1){
                str += String.valueOf(buf);
                buf = new char[512];
            }

            txt.setText(str);
            Toast.makeText(this,
                    "Lectura externa exitosa",
                    Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void abrirInterna() {
        try{
             inputStream = openFileInput(filename);

             int c = inputStream.read();

             char [] buf = new char[100];

            String str = "";
            InputStreamReader isr = new
                    InputStreamReader(inputStream);

                while (isr.read(buf) != -1){
                    str += String.valueOf(buf);
                    buf = new char[100];
                }

                txt.setText(str);
        }catch (Exception e){e.printStackTrace();}

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void btnGuardar_click(View view) {
        if (optAlmac.getCheckedRadioButtonId()
                == R.id.optInterna){
            salvarInterna();
        }else{
            salvarExterna();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void salvarExterna() {


        if(isExternalStorageWritable()){
            File dir  =
                    getExternalFilesDir
                            (Environment.DIRECTORY_DOCUMENTS);
            File a = new File(dir, "MiTexto.txt");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(a);
                fos.write(
                        txt.getText().toString().getBytes()
                );
                txt.setText("");
                Toast.makeText(this,
                        "Almaxenamiento externo exitoso",
                        Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        Toast.makeText(this,
                state, Toast.LENGTH_SHORT).show();
        if (Environment.isExternalStorageEmulated()){
            Toast.makeText(this,
                    "emeulada", Toast.LENGTH_SHORT).show();
        }

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void salvarInterna() {
        try {
            outputStream = openFileOutput(filename, MODE_PRIVATE);
            outputStream.write(txt.getText().toString().getBytes());
            outputStream.close();
            txt.setText("");
            Toast.makeText(this,
                    "Almacenamiento externo exitosi",
                    Toast.LENGTH_SHORT).show();



        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
