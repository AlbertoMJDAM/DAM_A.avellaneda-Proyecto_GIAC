package proyectoDAM.giac_app_v01.menuPrincipal_T.datosTrabajador;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.LoadingDialogBar;
import proyectoDAM.giac_app_v01.R;

public class DatosTrabajador extends AppCompatActivity {

    // Atributos:
    private TextView tvNusu2,tvidtrab,tvPerfil2;
    private EditText edtNombre,edtPApe,edtSApe,edTDNI,edtFNac,edTeMail,edtphone,edtPasword;
    private Button btnSave;
    private String datosTrabajador;
    private RequestQueue requestQueue;
    private JSONObject jsonDatosTrab;
    private LoadingDialogBar loadingDialogBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_trabajador);

        // Traemos un string con todos los datos del trabajador logueado que
        // convertimos en un objeto JSON para poder extraer los datos que
        // necesitemos en cada momento.
        Bundle extras = getIntent().getExtras();
        datosTrabajador = extras.getString("Trabajador");
        try {
            jsonDatosTrab = new JSONObject(datosTrabajador);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Damos valor a los elementos:
        tvNusu2 = (TextView) findViewById(R.id.tvNusu2);
        tvidtrab = (TextView) findViewById(R.id.tvidtrab);
        tvPerfil2 = (TextView) findViewById(R.id.tvPerfil2);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtPApe = (EditText) findViewById(R.id.edtPApe);
        edtSApe = (EditText) findViewById(R.id.edtSApe);
        edTDNI = (EditText) findViewById(R.id.edTDNI);
        edtFNac = (EditText) findViewById(R.id.edtFNac);
        edTeMail = (EditText) findViewById(R.id.edTeMail);
        edtphone = (EditText) findViewById(R.id.edtphone);
        edtPasword = (EditText) findViewById(R.id.edtPasword);
        btnSave = (Button) findViewById(R.id.btnSave);
        loadingDialogBar =new LoadingDialogBar(this);

        //ACTIVAMOS LOADINGGIALOGBAR
        loadingDialogBar.MuestraDialog("Cargando datos del empleado ");

        // Establecemos los datos en sus casillas
        try {
            tvNusu2.setText(jsonDatosTrab.getString("Nombre_Usuario"));
            tvidtrab.setText(jsonDatosTrab.getString("Id_Empleado"));
            tvPerfil2.setText(jsonDatosTrab.getString("Perfil_Empleado"));
            edtNombre.setText(jsonDatosTrab.getString("Nombre"));
            edtPApe.setText(jsonDatosTrab.getString("Per_Apellido"));
            edtSApe.setText(jsonDatosTrab.getString("Sdo_Apellido"));
            edTDNI.setText(jsonDatosTrab.getString("DNI"));
            edtFNac.setText(jsonDatosTrab.getString("Fecha_Nacimiento"));
            edTeMail.setText(jsonDatosTrab.getString("Email"));
            edtphone.setText(jsonDatosTrab.getString("Telefono"));
            edtPasword.setText(jsonDatosTrab.getString("Password"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //METODO ONCLICK DEL BOTON "GUARDAR", COMPRUEBA QUE LOS DATOS ESTEN DENTRO DE LOS
        //PARAMETROS VALIDOS Y SI SON CORRECTOS DEVUELVE BOOLEANO CON TRUE.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean datosOk = Boolean.TRUE;

                if(!ValidaNombreApell(edtNombre.getText().toString())){
                    edtNombre.setError("¡Formato Nombre Incorrecto!");
                    datosOk = false;
                }
                if(!ValidaNombreApell(edtPApe.getText().toString())){
                    edtPApe.setError("¡Formato apellido Incorrecto!");
                    datosOk = false;
                }
                if(!ValidaNombreApell(edtSApe.getText().toString())){
                    edtSApe.setError("¡Formato apellido Incorrecto!");
                    datosOk = false;
                }
                if(!ValidaDNI(edTDNI.getText().toString())){
                    edTDNI.setError("¡Formato DNI Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaFechas(edtFNac.getText().toString())){
                    edtFNac.setError("¡Fomato de fecha Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaMail(edTeMail.getText().toString())){
                    edTeMail.setError("¡Correo electronico Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaTelefono(edtphone.getText().toString())){
                    edtphone.setError("¡Formato telefono Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaPass(edtPasword.getText().toString())){
                    edtPasword.setError("¡Contraseña Incorrecta!");
                    datosOk = false;
                }
                if(datosOk) {
                    ActualizarDatosTrab();
                }
            }
        });

        //DESACTIVAMOS DIALOGBAR
        loadingDialogBar.OcultaDialog();
    }

    //METODO QUE REALIZA LA ACTUALIZACION DE DATOS EN LA BBDD TRABAJADORES
    private void ActualizarDatosTrab(){
        //Cogemos ID trabajador
        String idTrabajador;
        try {
            idTrabajador = jsonDatosTrab.getString("Id_Empleado");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = "https://appgiac.000webhostapp.com/actualizar_datos_empleados.php?Id_Empleado=" + idTrabajador;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                finish();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("Nombre",edtNombre.getText().toString().trim());
                parametros.put("Per_Apellido",edtPApe.getText().toString().trim());
                parametros.put("Sdo_Apellido",edtSApe.getText().toString().trim());
                parametros.put("DNI", edTDNI.getText().toString().trim());
                parametros.put("Fecha_Nacimiento",edtFNac.getText().toString().trim());
                parametros.put("Email", edTeMail.getText().toString().trim());
                parametros.put("Telefono",edtphone.getText().toString().trim());
                parametros.put("Password",edtPasword.getText().toString().trim());
                parametros.put("Id_Empleado",tvidtrab.getText().toString().trim());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // Metodo encargado de la validacion de Nombres y apellidos mediante regex
    public static boolean ValidaNombreApell(String nombre){
        boolean nomCorrecto;
        String regexnom = "^(?=.{3,30}$)([a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{2,26}[\\,\\-\\.]{0,1}[\\s]{0,1}){1,3}$";
        Pattern pat= Pattern.compile(regexnom);
        Matcher mat= pat.matcher(nombre);
        if (mat.matches()) {
            nomCorrecto = true;
        }
        else {
            nomCorrecto = false;
        }
        return nomCorrecto;
    }

    // Metodo encargado de la validacion de Fechas mediante regex
    public static boolean ValidaFechas(String fechas){
        boolean fecCorrecto;
        String regexfech = "^(19|20)(((([02468][048])|([13579][26]))-02-29)|(\\d{2})-((02-((0[1-9])" +
                "|1\\d|2[0-8]))|((((0[13456789])|1[012]))-((0[1-9])|((1|2)\\d)|30))|(((0[13578])|" +
                "(1[02]))-31)))$";
        Pattern pat= Pattern.compile(regexfech);
        Matcher mat= pat.matcher(fechas);
        if (mat.matches()) {
            fecCorrecto = true;
        }
        else {
            fecCorrecto = false;
        }
        return fecCorrecto;
    }

    // Metodo encargado de la validacion de DNI mediante regex
    public static boolean ValidaDNI(String DNI){
        boolean dniCorrecto;
        String regexdni = "[0-9]{8}[A-Z]";
        Pattern pat= Pattern.compile(regexdni);
        Matcher mat= pat.matcher(DNI);
        if (mat.matches()) {
            dniCorrecto = true;
        }
        else {
            dniCorrecto = false;
        }
        return dniCorrecto;
    }

    // Metodo encargado de la validacion de correos electronicos mediante regex
    public static boolean ValidaMail(String email){
        boolean mailCorrecto;
        String regexmail = "^(?=.{6,30}$)[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pat= Pattern.compile(regexmail);
        Matcher mat= pat.matcher(email);
        if (mat.matches()) {
            mailCorrecto = true;
        }
        else {
            mailCorrecto = false;
        }
        return mailCorrecto;
    }

    // Metodo encargado de la validacion de telefonos mediante regex
    public static boolean ValidaTelefono(String phone){
        boolean telCorrecto;
        String regextel = "(6|7|8|9)[ -]*([0-9][ -]*){8}";
        Pattern pat= Pattern.compile(regextel);
        Matcher mat= pat.matcher(phone);
        if (mat.matches()) {
            telCorrecto = true;
        }
        else {
            telCorrecto = false;
        }
        return telCorrecto;
    }

    // Metodo encargado de la validacion de Password mediante regex
    public static boolean ValidaPass(String password){
        boolean passCorrecto;
        String regexpass = "^[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ0-9_@.]{3,15}$";
        Pattern pat= Pattern.compile(regexpass);
        Matcher mat= pat.matcher(password);
        if (mat.matches()) {
            passCorrecto = true;
        }
        else {
            passCorrecto = false;
        }
        return passCorrecto;
    }
}