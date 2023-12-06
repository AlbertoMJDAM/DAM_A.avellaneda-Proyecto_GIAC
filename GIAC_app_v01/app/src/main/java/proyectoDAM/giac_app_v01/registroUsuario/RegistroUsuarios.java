package proyectoDAM.giac_app_v01.registroUsuario;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import proyectoDAM.giac_app_v01.R;

public class RegistroUsuarios extends AppCompatActivity {

    private String usuario;

    RequestQueue requestQueue;

    private ImageView logoGIAC;

    private TextView tvidusu,tvNombre,tvPApe,tvSApe,tvDNI,tvFNac,tvFli,tvtipoli,tveMail,tvphone,
            tvNusu,tvPasword;
    private TextInputEditText edtNombre,edtPApe,edtSApe, edtDNI,edtFNac,edtFli, edteMail,edtphone,
            edtNusu,edtPasword;
    private Button btnSave,btnBorra;

    private Spinner sptipoli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        tvidusu = (TextView) findViewById(R.id.tvidusu);
        edtNombre = (TextInputEditText) findViewById(R.id.edtNombre);
        edtPApe = (TextInputEditText) findViewById(R.id.edtPApe);
        edtSApe = (TextInputEditText) findViewById(R.id.edtSApe);
        edtDNI = (TextInputEditText) findViewById(R.id.edTtvDNI);
        edtFNac = (TextInputEditText) findViewById(R.id.edtFNac);
        edtFli = (TextInputEditText) findViewById(R.id.edtFli);
        edteMail = (TextInputEditText) findViewById(R.id.edTeMail);
        edtphone = (TextInputEditText) findViewById(R.id.edtphone);
        edtNusu = (TextInputEditText) findViewById(R.id.edtNusu);
        edtPasword = (TextInputEditText) findViewById(R.id.edtPasword);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnBorra =  (Button) findViewById(R.id.btnBorra);
        sptipoli =(Spinner) findViewById(R.id.sptipoli);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.
                TipoPermiso, android.R.layout.simple_spinner_item);
        sptipoli.setAdapter(adapter);

        // LLAMAMOS AL METODO maxID en onCreate para que, desde el inicio de registro tome el numero de ID
        // Prueba en LocalHost
        //maxID("http://192.168.1.134/BBDD_giac/mostrar_max_usu.php");
        //Prueba en webhost
        maxID("https://appgiac.000webhostapp.com/mostrar_max_usu.php");

        // METODO ONCLICK DE BOTON "GUARDAR" PARA INSERTAR DATOS EN LA URL INDICADA EN EL METODO "insertaUsuarios"
        // TRAS LA VALIDACION DE LOS DATOS INSERTADOS MEDIANTE LOS METODOS DE VALIDACION.

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

                if(!ValidaDNI(edtDNI.getText().toString())){
                    edtDNI.setError("¡Formato DNI Incorrecto!");
                    datosOk = false;
                }

                if (!ValidaMail(edteMail.getText().toString())){
                    edteMail.setError("¡Correo electronico Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaTelefono(edtphone.getText().toString())){
                    edtphone.setError("¡Formato telefono Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaFechas(edtFNac.getText().toString())){
                    edtFNac.setError("¡Fomato de fecha Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaFechas(edtFli.getText().toString())){
                    edtFli.setError("¡Fomato de fecha Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaUsuario(edtNusu.getText().toString())){
                    edtNusu.setError("¡Nombre Usuario Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaPass(edtPasword.getText().toString())){
                    edtPasword.setError("¡Contraseña Incorrecta!");
                    datosOk = false;
                }

                if(datosOk){
                    insertaUsuarios("https://appgiac.000webhostapp.com/insertar_usuario.php");
                }
            }
        });

        // METODO ONCLICK DE BOTON "BORRAR" PARA ELIMINAR LOS DATOS DE LOS EDITTEXT
        btnBorra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrarTodo();
            }
        });
    }


    /* ################################# METODOS #######################################
        A continuacion mostramos los metodos utilizados para el registro de usuarios
     */

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

    // Metodo encargado de la validacion de Nombres de usuarios mediante regex
    public static boolean ValidaUsuario(String usuario){
        boolean usuarioCorrecto;
        String regexpass = "^[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ0-9_-]{3,15}$";
        Pattern pat= Pattern.compile(regexpass);
        Matcher mat= pat.matcher(usuario);
        if (mat.matches()) {
            usuarioCorrecto = true;
        }
        else {
            usuarioCorrecto = false;
        }
        return usuarioCorrecto;
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

    // Metodo encargado del borrado masivo de los editText
    public void BorrarTodo(){
        edtNombre.setText("");
        edtPApe.setText("");
        edtSApe.setText("");
        edtDNI.setText("");
        edtFNac.setText("");
        edtFli.setText("");
        edteMail.setText("");
        edtphone.setText("");
        edtNusu.setText("");
        edtPasword.setText("");
    }

    // Metodo que Volley que recupera de la BBDD el Nº de usuario mayor para proporcionar un nuevo numero de usuario
    public void maxID(String urlmaxid){
        StringRequest request =new StringRequest(Request.Method.POST, urlmaxid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.w("Response VOLLEY SC", response.toString());
                            JSONObject jsonObject =new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");
                            if (exito.equals("1")){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    int idUsuario = object.getInt("Id_Usuario");
                                    idUsuario++;
                                    tvidusu.setText(String.valueOf(idUsuario));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexion!", Toast.LENGTH_SHORT).show();
            }
        }
        ){};
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    //  Metodo que Volley que inserta Datos en la BBDD
    private void insertaUsuarios(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
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
                parametros.put("Id_Usuario",tvidusu.getText().toString());
                //parametros.put("Id_Usuario",usuario.toString());
                parametros.put("Nombre",edtNombre.getText().toString());
                parametros.put("Per_Apellido",edtPApe.getText().toString());
                parametros.put("Sdo_Apellido",edtSApe.getText().toString());
                parametros.put("DNI", edtDNI.getText().toString());
                parametros.put("Fecha_Nacimiento",edtFNac.getText().toString());
                parametros.put("Fecha_Licencia",edtFli.getText().toString());
                parametros.put("Tipo_Licencia",sptipoli.getItemAtPosition
                        (sptipoli.getSelectedItemPosition()).toString());
                parametros.put("Email", edteMail.getText().toString());
                parametros.put("Telefono",edtphone.getText().toString());
                parametros.put("Nombre_Usuario",edtNusu.getText().toString());
                parametros.put("Password",edtPasword.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}