package proyectoDAM.giac_app_v01.menuPrincipal_U;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class EditaPerfil extends AppCompatActivity {

    private String idUsuario;

    RequestQueue requestQueue;

    private ImageView logoGIAC;

    private TextView tvidusu,tvNombre,tvPApe,tvSApe,tvDNI,tvFNac,tvFli,tvtipoli,tveMail,tvphone,
            tvNusu,tvPasword;
    private TextInputEditText edtNombre,edtPApe,edtSApe, edtDNI,edtFNac,edtFli, edteMail,edtphone,
            edtNusu,edtPasword;
    private Button btnSave,btnBorra,btnAutoriza;

    private Spinner sptipoli;

    Bundle bundle = new Bundle();

    String url = "https://appgiac.000webhostapp.com/mostrar_usuario.php?Id_Usuario=";
    String urlActualiza = "https://appgiac.000webhostapp.com/actualizar_usuario.php?Id_Usuario=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_perfil);

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TipoPermiso, android.R.layout.simple_spinner_item);
        sptipoli.setAdapter(adapter);
        btnAutoriza = (Button) findViewById(R.id.btnAutoriza);

        // PONGO ESTO PARA LA PRUEBA PERO HAY QUE TRAER EL IDUSUARIO DESDE EL INTEN MENU PRINCIPAL
        bundle = getIntent().getExtras();
        idUsuario =bundle.getString("idusuario");

        tvidusu.setText(idUsuario);

        muestraUsuario(url+idUsuario);

        //BLOQUEAMOS LA EDICION DE LOS CAMPOS PARA EVITARA ERRORES
        CamposBloqueados();

        // BOTON QUE DESBLOQUEA LOS CAMPOS PARA SU EDICION
        btnAutoriza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CamposDesbloqueados();
            }
        });



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
                    new AlertDialog.Builder(EditaPerfil.this)
                            .setIcon(R.drawable.infogiac)
                            .setTitle("         ACTUALIZAR DATOS")
                            .setMessage("          Se va a proceder a la actualizacion")
                            .setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EditaPerfil("https://appgiac.000webhostapp.com/actualizar_usuario.php?Id_Usuario="+ tvidusu.getText().toString());
                                }
                            })
                            .setNegativeButton("Cancelar",null)
                            .show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Algo ha ido mal", Toast.LENGTH_SHORT).show();
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

    //////////////////////////////////////////////
    //  Metodo que Volley que inserta Datos en la BBDD
    @SuppressLint("NotConstructor")
    private void EditaPerfil(String urlactualizar){
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Actualizando");
        progressDialog.show();
        StringRequest request =new StringRequest(Request.Method.POST, urlactualizar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "actualizo correctamente", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
        ){
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    AlertDialog ConfirmaActualizacion(String urlActualiza){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se actualizaran los datos de su perfil");
        builder.setPositiveButton("Si, deseo actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditaPerfil(urlActualiza);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return builder.create();
    }

    public void muestraUsuario(String urlUsuario){
        //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
        StringRequest request =new StringRequest(Request.Method.POST, urlUsuario,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.w("Response VOLLEY SC", response.toString());
                            JSONObject jsonObject =new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");

                            if (exito.equals("1")){
                                //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    edtNombre.setText(object.getString("Nombre"));
                                    edtPApe.setText(object.getString("Per_Apellido"));
                                    edtSApe.setText(object.getString("Sdo_Apellido"));
                                    edtDNI.setText(object.getString("DNI"));
                                    edtFNac.setText(object.getString("Fecha_Nacimiento"));
                                    edtFli.setText(object.getString("Fecha_Licencia"));
                                    edteMail.setText(object.getString("Email"));
                                    edtphone.setText(object.getString("Telefono"));
                                    edtNusu.setText(object.getString("Nombre_Usuario"));
                                    edtPasword.setText(object.getString("Password"));
                                    sptipoli.setSelection(ValorSpinner(object.getString("Tipo_Licencia")));
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
        ){
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public int ValorSpinner(String texto){
        int valor = 0;
        switch (texto){
            case "A1":
                valor = 0;
                break;
            case "A2":
                valor = 1;
                break;
            case "A":
                valor = 2;
                break;
            case "B":
                valor = 3;
                break;
            case "C1":
                valor = 4;
                break;
            case "C":
                valor = 5;
                break;
        }
        return valor;
    }

    // METODOS DE BLOQUEO Y DESBLOQUEO DE CAMPOS
    public void CamposBloqueados(){
        tvidusu.setEnabled(false);
        edtNombre.setEnabled(false);
        edtPApe.setEnabled(false);
        edtSApe.setEnabled(false);
        edtDNI.setEnabled(false);
        edtFNac.setEnabled(false);
        edtFli.setEnabled(false);
        edteMail.setEnabled(false);
        edtphone.setEnabled(false);
        edtNusu.setEnabled(false);
        edtPasword.setEnabled(false);
        btnSave.setEnabled(false);
        btnBorra.setEnabled(false);
        sptipoli.setEnabled(false);
    }

    public void CamposDesbloqueados(){
        tvidusu.setEnabled(true);
        edtNombre.setEnabled(true);
        edtPApe.setEnabled(true);
        edtSApe.setEnabled(true);
        edtDNI.setEnabled(true);
        edtFNac.setEnabled(true);
        edtFli.setEnabled(true);
        edteMail.setEnabled(true);
        edtphone.setEnabled(true);
        edtNusu.setEnabled(true);
        edtPasword.setEnabled(true);
        btnSave.setEnabled(true);
        btnBorra.setEnabled(true);
        sptipoli.setEnabled(true);
    }


}