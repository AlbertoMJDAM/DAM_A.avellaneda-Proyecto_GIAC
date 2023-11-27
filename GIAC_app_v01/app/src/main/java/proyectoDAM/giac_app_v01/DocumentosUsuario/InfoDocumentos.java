package proyectoDAM.giac_app_v01.DocumentosUsuario;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import proyectoDAM.giac_app_v01.R;

public class InfoDocumentos extends AppCompatActivity {

    VideoView longC, onClick, carpeta;
    ImageView btnyoutube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infodoc);

        //CONFIGURAMOS TAMAÑO DEL ACTIVITY PARA COMPORTARSE COMO UNA VENTANA EMERGENTE
        DisplayMetrics medidaventana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidaventana);
        int ancho = medidaventana.widthPixels;
        int alto = medidaventana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.85), (int)(alto*0.5));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //CARGA DE ELEMENTOS DEL LAYOUT
        btnyoutube = findViewById(R.id.btnvideo);
        longC = findViewById(R.id.videoLnclick);
        onClick = findViewById(R.id.videoOnclick);
        carpeta = findViewById(R.id.carpeta);
        Uri lnclick = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getResources().getResourcePackageName(R.raw.lc_) + '/' +
                getResources().getResourceTypeName(R.raw.lc_) + '/' +
                String.valueOf(R.raw.lc_));

        Uri onclick = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getResources().getResourcePackageName(R.raw.oc_) + '/' +
                getResources().getResourceTypeName(R.raw.oc_) + '/' +
                String.valueOf(R.raw.oc_));

        Uri vcarpeta = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getResources().getResourcePackageName(R.raw.carpeta) + '/' +
                getResources().getResourceTypeName(R.raw.carpeta) + '/' +
                String.valueOf(R.raw.carpeta));

        longC.setVideoURI(lnclick);
        longC.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });

        onClick.setVideoURI(onclick);
        onClick.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });

        carpeta.setVideoURI(vcarpeta);
        carpeta.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });



        // ########## DAMOS ACCION A LOS BOTONES ##########
        btnyoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://www.youtube.com/channel/UCYBjK1y_U8Ge020g4FVWiuQ");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

    }
}