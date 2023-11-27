package proyectoDAM.giac_app_v01.Ayuda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import proyectoDAM.giac_app_v01.R;

public class EnvioMail extends AppCompatActivity {

    Button btnBorrar,btnEnvio;
    String email;
    Bundle bundle = new Bundle();
    EditText direccion,textoMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_mail);

        // TRAEMOS EL ID DEL USUARIO DESDE EL MENU PRINCIPAL
        bundle = getIntent().getExtras();
        email =bundle.getString("email");
        ////////////////////////////////////////

        btnBorrar = findViewById(R.id.btnborrar);
        btnEnvio = findViewById(R.id.btnenvio);
        direccion = findViewById(R.id.edtDircorreo);
        textoMail = findViewById(R.id.edttxtCorreo);
        direccion.setText(email);

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textoMail.setText("");
            }
        });

        btnEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpannableString mitextoU = new SpannableString("Consulta a GIAC");
                mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
                String[] TO = {"admigiac@mail.com"};
                String[] FROM = {direccion.getText().toString()};
                String mensaje = textoMail.getText().toString();

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setData(Uri.parse("mailfrom:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, FROM);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, mensaje );

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar email."));
                    Log.i("EMAIL", "Enviando email...");
                }
                catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "NO existe ningún cliente de email instalado!.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}