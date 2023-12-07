package proyectoDAM.giac_app_v01.login;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import proyectoDAM.giac_app_v01.menuPrincipal_T.MenuPrincipal_T;
import proyectoDAM.giac_app_v01.menuPrincipal_U.MenuPrincipal_U;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.LoadingDialogBar;
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
    LoadingDialogBar loadingDialogBar;

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
        loadingDialogBar =new LoadingDialogBar(this);

        //ESCONDEMOS EL TECLADO CUANDO DEJAMOS DE HACER FOCO EN EL EDITEXT
        edtUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        //ESCONDEMOS EL TECLADO CUANDO DEJAMOS DE HACER FOCO EN EL EDITEXT
        edtPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

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
                //loadingDialogBar.MuestraDialog("Comprobando credenciales");
            }
        });
        VerificarPermisos();
    }

    //METODO QUE CONSULTA EN LAS BBDD DE USUARIOS SI EXISTE EL USUARIO CON SU CONTRASENA
    //SI EXISTE, LANZA LA PANTALLA PRINCIPAL DE USUARIOS, SI NO, COMPRUEBA EN TRABAJADOR
    public void ComprobarUsuario(){
        //INSERTAMOS UN PROGRESSDIALOG PARA INFORMAR QUE SE ESTAN COMPROBANDO LOS DATOS
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Comprobando usuario");
        progressDialog.show();
        String urlUsuarios = "https://appgiac.000webhostapp.com/validar_usuario.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlUsuarios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent=new Intent(getApplicationContext(), MenuPrincipal_U.class);
                    intent.putExtra("Usuario",response);
                    // Creamos un objeto de la clase Handler para crear un hilo y dormir 2 segundos este para hacer visible la progressDialog de carga.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            startActivity(intent);
                        }
                    },2500);
                    ///////////////////////////////////////////////////////////////////////////////
                    //startActivity(intent);
                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    },2500);
                    ComprobarEmpleado();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Creamos un objeto de la clase Handler para crear un hilo y dormir 2 segundos este para hacer visible la progressDialog de carga.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },2500);
                ///////////////////////////////////////////////////////////////////////////////
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        //INSERTAMOS UN PROGRESSDIALOG PARA INFORMAR QUE SE ESTAN COMPROBANDO LOS DATOS
        ProgressDialog progressDialogemp =new ProgressDialog(this);
        progressDialogemp.setMessage("Comprobando Empleado");
        progressDialogemp.show();
        String urlEmpleados = "https://appgiac.000webhostapp.com/validar_empleado.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlEmpleados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent= new Intent(getApplicationContext(), MenuPrincipal_T.class);
                    intent.putExtra("Trabajador", response);
                    // Creamos un objeto de la clase Handler para crear un hilo y dormir 2 segundos este para hacer visible la progressDialog de carga.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialogemp.dismiss();
                            startActivity(intent);
                        }
                    },2500);
                    ///////////////////////////////////////////////////////////////////////////////
                    //startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Usuario o contrasena incorrectos", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialogemp.dismiss();
                        }
                    },1500);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Creamos un objeto de la clase Handler para crear un hilo y dormir 2 segundos este para hacer visible la progressDialog de carga.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialogemp.dismiss();
                    }
                },2500);
                ///////////////////////////////////////////////////////////////////////////////
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

    //METODO ENCARGADO DE COMPROBAR LOS PERMISOS NECESARIOS EN LA APLICACION Y SOLICITARLOS AL USUARIO
    private boolean checkPermission(){
        int permisoEscritura = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisoLectura = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permisoCamara = ContextCompat.checkSelfPermission(this, CAMERA);
        int permisoLocalizacion = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
        int permisoLocalizacionFina = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        int permisoTelefono = ContextCompat.checkSelfPermission(this, CALL_PHONE);
        int permisoStorage = ContextCompat.checkSelfPermission(this, STORAGE);
        return permisoEscritura == PackageManager.PERMISSION_GRANTED && permisoLectura == PackageManager.PERMISSION_GRANTED &&
                permisoCamara == PackageManager.PERMISSION_GRANTED && permisoLocalizacion == PackageManager.PERMISSION_GRANTED &&
                permisoLocalizacionFina == PackageManager.PERMISSION_GRANTED && permisoTelefono == PackageManager.PERMISSION_GRANTED &&
                permisoStorage == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE,
                CAMERA,ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION,CALL_PHONE}, 200);
    }

    private void VerificarPermisos(){
        //DEFINIMOS LA SOLICITUD DE PERMISOS AL USUARIO
        if(!checkPermission()){
            requestPermissions();
            if(checkPermission()){
                Toast.makeText(getApplicationContext(), "Permisos Aceptados", Toast.LENGTH_SHORT).show();
            }
        }
    }
}