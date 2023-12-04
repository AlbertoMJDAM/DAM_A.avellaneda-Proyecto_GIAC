package proyectoDAM.giac_app_v01.menuPrincipal_U.registraAccidentes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import proyectoDAM.giac_app_v01.R;

public class InfoDatosAcc extends AppCompatActivity {

    ImageView btnyoutube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infodatos_acc);

        //CONFIGURAMOS TAMAÑO DEL ACTIVITY PARA COMPORTARSE COMO UNA VENTANA EMERGENTE
        DisplayMetrics medidaventana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidaventana);
        int ancho = medidaventana.widthPixels;
        int alto = medidaventana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.85), (int)(alto*0.5));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //CARGA DE ELEMENTOS DEL LAYOUT
        btnyoutube = findViewById(R.id.btnvideo);

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