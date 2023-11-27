package proyectoDAM.giac_app_v01.Ayuda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_U.MenuPrincipal_U;
import proyectoDAM.giac_app_v01.registraAccidentes.RegistraDatosAcc;
import proyectoDAM.giac_app_v01.registraAccidentes.RegistraLocalizaAcc;

public class PopUp_tipocon extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123; // Damos valor al permiso.
    Button btnLlamada,btnEmail;
    String email;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_tipocon);
        DisplayMetrics medidaventana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidaventana);
        int ancho = medidaventana.widthPixels;
        int alto = medidaventana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.85), (int)(alto*0.5));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // TRAEMOS EL ID DEL USUARIO DESDE EL MENU PRINCIPAL
        bundle = getIntent().getExtras();
        email =bundle.getString("email");
        ////////////////////////////////////////

        btnEmail = findViewById(R.id.btnEmail);
        btnLlamada = findViewById(R.id.btnTelefono);

        btnLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(PopUp_tipocon.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        // Se tiene permiso
                    } else {
                        ActivityCompat.requestPermissions(PopUp_tipocon.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
                    }
                } else {
                    // No se necesita requerir permiso OS menos a 6.0.
                }
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:+34657174544"));
                startActivity(i);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent correo = new Intent (v.getContext(), EnvioMail.class);
                correo.putExtra("email", email);
                startActivity(correo);

            }
        });
    }
}