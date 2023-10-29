package proyectoDAM.giac_app_v01.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.Trabajador.Trabajador;
import proyectoDAM.giac_app_v01.Usuario.Usuario;
import proyectoDAM.giac_app_v01.registroUsuario.RegistroUsuarios;


public class Login extends AppCompatActivity {

    Button btnLogin,btnReg;
    EditText edtUser;
    TextInputEditText edtPass;
    TextView tvRem;
    CheckBox cbRecordar;
    SharedPreferences preferencias;
    SharedPreferences.Editor editorPreferencias;
    String llave = "sesion";
    String userGuardado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin =(Button) findViewById(R.id.btnLogin);
        btnReg = (Button) findViewById(R.id.btnReg);
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (TextInputEditText) findViewById(R.id.textInputEditText);
        tvRem = (TextView) findViewById(R.id.tvRem);
        cbRecordar = (CheckBox) findViewById(R.id.cbRecordar);
        preferencias = this.getPreferences(Context.MODE_PRIVATE);
        editorPreferencias = preferencias.edit();
        //AL INICIAR, REVISA SI SE RECORDO USUARIO Y LO ESTABLECE ASI COMO MARCHA EL CHECK
        if(RevisarSesion()){
            edtUser.setText(this.preferencias.getString(userGuardado, ""));
            cbRecordar.setChecked(true);
        }

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

        // METODO ONCLICK BOTON "ACCEDER". AL HACER CLICK COMPRUEBA QUE EL USUARIO Y CONTRASENA
        // EXISTE Y DERIBAN A PANTALLA DE USUARIO O PANTALLA DE EMPLEADO
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarSesion(cbRecordar.isChecked());
                ComprobarUsuario();

            }
        });
    }

    //METODO QUE CONSULTA EN LAS BBDD DE USUARIOS SI EXISTE EL USUARIO CON SU CONTRASENA
    //SI EXISTE, LANZA LA PANTALLA PRINCIPAL DE USUARIOS, SI NO, COMPRUEBA EN TRABAJADOR
    public void ComprobarUsuario(){
        String urlUsuarios = "https://appgiac.000webhostapp.com/validar_usuario.php";
        String a = "";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlUsuarios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    //Toast.makeText(getBaseContext(), "Usuario existe ",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), Usuario.class);
                    startActivity(intent);
                }else {
                    ComprobarEmpleado();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("Nombre_Usuario", edtUser.getText().toString());
                parametros.put("Password", edtPass.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //METODO QUE CONSULTA EN LAS BBDD DE EMPLEADOS SI EXISTE EL EMPLEADO CON SU CONTRASENA
    //SI EXISTE, LANZA LA PANTALLA PRINCIPAL DE EMPLEADOS
    public void ComprobarEmpleado(){
        String urlEmpleados = "https://appgiac.000webhostapp.com/validar_empleado.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlEmpleados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent= new Intent(getApplicationContext(), Trabajador.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Usuario o contrasena incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("Nombre_Usuario", edtUser.getText().toString());
                parametros.put("Password", edtPass.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //METODO QUE REALIZA LA GESTION DE GUARDAR EL USUARIO Y EL CHECK DE GUARDAR SESION
    public void GuardarSesion(boolean chequear){
        editorPreferencias.putBoolean(llave, chequear);
        editorPreferencias.putString(userGuardado, edtUser.getText().toString());
        editorPreferencias.apply();
    }

    //METODO QUE CONPRUEBA AL ARRANCAR SI EL CHECK DE GUARDAR SESION ESTABA MARCADO
    public Boolean RevisarSesion(){
        boolean sesion = this.preferencias.getBoolean(llave, false);
        return sesion;
    }
}