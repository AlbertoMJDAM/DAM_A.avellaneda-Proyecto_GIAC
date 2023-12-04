package proyectoDAM.giac_app_v01.menuPrincipal_U.registraAccidentes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import proyectoDAM.giac_app_v01.R;

public class PopUp_Acciones extends AppCompatActivity {

    Button btnAsistencia,btnFormulario;
    String idUsuario;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_acciones);
        DisplayMetrics medidaventana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidaventana);
        int ancho = medidaventana.widthPixels;
        int alto = medidaventana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.85), (int)(alto*0.5));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // TRAEMOS EL ID DEL USUARIO DESDE EL MENU PRINCIPAL
        bundle = getIntent().getExtras();
        idUsuario =bundle.getString("idusuario");
        ////////////////////////////////////////

        btnAsistencia = findViewById(R.id.btnAviso);
        btnFormulario = findViewById(R.id.btnFormulario);

        btnAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent asistencia = new Intent (v.getContext(), RegistraLocalizaAcc.class);
                asistencia.putExtra("idusuario", idUsuario);
                startActivity(asistencia);
                //startActivity(new Intent(PopUp_Acciones.this, RegistraLocalizaAcc.class));
                //accidente.putExtra("idusuario", idusuario);
            }
        });

        btnFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent formulario = new Intent (v.getContext(), RegistraDatosAcc.class);
                formulario.putExtra("idusuario", idUsuario);
                startActivity(formulario);
                //startActivity(new Intent(PopUp_Acciones.this, RegistraDatosAcc.class));
                //accidente.putExtra("idusuario", idusuario);
            }
        });
    }
}
