package proyectoDAM.giac_app_v01.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.registroUsuario.RegistroUsuarios;


public class Login extends AppCompatActivity {

    Button btnLogin,btnReg;
    EditText edtUser;
    TextView tvRem;
    CheckBox cbRecordar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin =(Button) findViewById(R.id.btnLogin);
        btnReg = (Button) findViewById(R.id.btnReg);
        edtUser = (EditText) findViewById(R.id.edtUser);
        tvRem = (TextView) findViewById(R.id.tvRem);
        cbRecordar = (CheckBox) findViewById(R.id.cbRecordar);


        // METODO ONCLICK TEXTO "NUEVO USUARIO". AL HACER CLICK ENVIA A INTENT DE REGISTRO DE USUARIO
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent (v.getContext(), RegistroUsuarios.class);
                startActivity(registro);
            }
        });

        // METODO ONCLICK TEXTO "¿Olvido su contraseña?". AL HACER CLICK ENVIA CORREO
        tvRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpannableString mitextoU = new SpannableString("¿Olvido su contraseña?");
                mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
                tvRem.setText(mitextoU);
                String[] TO = {"admigiac@mail.com"};
                String mensaje = "Buenos días, He olvidado mi usuario y/o contraseña. Por favor,\n" +
                        "agradecería que se pusieran en contacto conmigo a esta dirección para\n" +
                        "recuperar las credenciales.Agregue aquí el contenido que desees añadir";

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "@String/emailrecuperar");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar email."));
                    Log.i("EMAIL", "Enviando email...");
                }
                catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "NO existe ningún cliente de email instalado!.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /* PENDIENTE:
            - METODO ONCLICK DE ACCESO A LA APP CON NOMBRE DE USUARIO Y CONTRASEÑA
            - GUARDAR DATOS DE USUARUI CON SHARED-PREFERENCES DEL PROPIO TELEFONO
         */
    }
}