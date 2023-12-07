package proyectoDAM.giac_app_v01.menuPrincipal_U.registraAccidentes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.Accidente;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.LoadingDialogBar;

public class RegistraDatosAcc extends AppCompatActivity implements LocationListener{

    private String idUsuario;
    LocationManager locationManager;
    RequestQueue requestQueue;
    private ImageView logoGIAC;
    private TextView tvidNumUsuario,tvidNumAccidente;
    private TextInputEditText edtNombre,edtPApe,edtSApe, edtDNI, edteMail,edtphone, edtIdemp,edtMat,
            edtMatriculaImpUno,edtMatriculaImpDos,edtNombreImpUno, edtNombreImpDos,edtcontactoImpUno,
            edtcontactoImpDos,etdFsuc,edtDir,edtLat,edtLon;
    RadioButton tfnoImpUno,emailImpUno,tfnoImpDos,emailImpDos;
    private Button btnSave,btnBorra;
    private ImageButton btnlocaliza;
    Bundle bundle = new Bundle();
    LoadingDialogBar loadingDialogBar;
    Accidente accidente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_datos_acc);

        // LLAMAMOS A LA ACTIVITY DEL LA EXPLICACION
        startActivity(new Intent(RegistraDatosAcc.this, InfoDatosAcc.class));

        // INSTANCIAMOS AL OBJETO DE LA CLASE ACCIDENTE
        accidente = new Accidente();

        //CARGA DE ELEMENTOS DEL LAYOUT
        loadingDialogBar =new LoadingDialogBar(this);
        tvidNumUsuario  = findViewById(R.id.tvidNumUsuario);
        tvidNumAccidente = findViewById(R.id.tvidNumAccidente);
        edtNombre = findViewById(R.id.edtNombre);
        edtPApe = findViewById(R.id.edtPApe);
        edtSApe = findViewById(R.id.edtSApe);
        edtDNI = findViewById(R.id.edTtvDNI);
        edteMail = findViewById(R.id.edTeMail);
        edtphone = findViewById(R.id.edtphone);
        edtMat= findViewById(R.id.edtmatricula);
        edtMatriculaImpUno =findViewById(R.id.edtMatriculaImpUno);
        edtMatriculaImpDos =findViewById(R.id.edtMatriculaImpDos);
        edtNombreImpUno =findViewById(R.id.edtNombreImpUno);
        edtNombreImpDos =findViewById(R.id.edtNombreImpDos);
        edtcontactoImpUno =findViewById(R.id.edtcontactoImpUno);
        edtcontactoImpDos =findViewById(R.id.edtcontactoImpDos);
        tfnoImpUno = findViewById(R.id.radioButton11);
        emailImpUno = findViewById(R.id.radioButton12);
        tfnoImpDos = findViewById(R.id.radioButton21);
        emailImpDos = findViewById(R.id.radioButton22);
        edtIdemp = findViewById(R.id.edtidempas);
        etdFsuc = findViewById(R.id.edtFsuceso);
        edtDir= findViewById(R.id.edtDirec);
        edtLat= findViewById(R.id.edtLat);
        edtLon= findViewById(R.id.edtLon);
        btnlocaliza = findViewById(R.id.btnlob);
        btnSave = findViewById(R.id.btnSiguiente);
        btnBorra = findViewById(R.id.btnBorra);

        //CARGAMOS EL SIGUIENTE NUMERO DE INCIDENCIA
        maxIDAccidente("https://appgiac.000webhostapp.com/mostrar_max_accidente.php");


        // TRAEMOS EL ID DEL USUARIO DESDE EL MENU PRINCIPAL
        bundle = getIntent().getExtras();
        idUsuario =bundle.getString("idusuario");
        tvidNumUsuario.setText(idUsuario);
        ////////////////////////////////////////

        // AUTORELLENADO DE LOS DATOS DEL USUARIO
        muestraUsuario("https://appgiac.000webhostapp.com/mostrar_usuario.php?Id_Usuario=" + tvidNumUsuario .getText().toString());

        // ########## DAMOS ACCION A LOS BOTONES ##########
        //BOTON DE CONFIRMACION DE DATOS INTRODUCIDOS Y CREACION DE OBJETOS
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

                if(tfnoImpUno.isChecked() || tfnoImpDos.isChecked()){
                    if (!ValidaTelefono(edtphone.getText().toString())){
                        edtphone.setError("¡Formato telefono Incorrecto!");
                        datosOk = false;
                    }
                }

                if(emailImpUno.isChecked() || emailImpDos.isChecked()){
                    if (!ValidaMail(edteMail.getText().toString())){
                        edteMail.setError("¡Correo electronico Incorrecto!");
                        datosOk = false;
                    }
                }

                if (!ValidaFechas(etdFsuc.getText().toString())){
                    etdFsuc.setError("¡Fomato de fecha Incorrecto!");
                    datosOk = false;
                }
                if (!Validamatricula(edtMat.getText().toString())){
                    edtMat.setError("¡Matricula Incorrecta!");
                    datosOk = false;
                }

                if (!Validamatricula(edtMatriculaImpUno.getText().toString())){
                    edtMatriculaImpUno.setError("¡Matricula Incorrecta!");
                    datosOk = false;
                }

                if (!Validamatricula(edtMatriculaImpDos.getText().toString())){
                    edtMatriculaImpDos.setError("¡Matricula Incorrecta!");
                    datosOk = false;
                }

                if (!ValidaNombreApellContacto(edtNombreImpUno.getText().toString())){
                    edtNombreImpUno.setError("¡El nombre y apellidos no son correctos!");
                    datosOk = false;
                }

                if (!ValidaNombreApellContacto(edtNombreImpDos.getText().toString())){
                    edtNombreImpDos.setError("¡El nombre y apellidos no son correctos!");
                    datosOk = false;
                }


                if(datosOk){
                    if(edtIdemp.getText().toString().equalsIgnoreCase("")){
                        edtIdemp.setText("No requerida asistencia");
                    }
                    accidente.setIdAccidente(tvidNumAccidente.getText().toString());
                    accidente.setIdUsuario(tvidNumUsuario.getText().toString());
                    accidente.setNombre(edtNombre.getText().toString());
                    accidente.setPapellido(edtPApe.getText().toString());
                    accidente.setSapellido(edtSApe.getText().toString());
                    accidente.setDNI(edtDNI.getText().toString());
                    accidente.setEmail(edteMail.getText().toString());
                    accidente.setTelefono(edtphone.getText().toString());
                    accidente.setEmpleado(edtIdemp.getText().toString());
                    accidente.setVehiculoUsuario(edtMat.getText().toString());
                    accidente.setVehiculoImplicadoUno(edtMatriculaImpUno.getText().toString());
                    accidente.setNombreImplicadoUno(edtNombreImpUno.getText().toString());
                    accidente.setContactoImplicadoUno(edtcontactoImpUno.getText().toString());
                    accidente.setVehiculoImplicadoDos(edtMatriculaImpDos.getText().toString());
                    accidente.setNombreImplicadoDos(edtNombreImpDos.getText().toString());
                    accidente.setContactoImplicadoDos(edtcontactoImpDos.getText().toString());
                    accidente.setUbicacion(edtDir.getText().toString());
                    accidente.setLatSuceso(edtLat.getText().toString());
                    accidente.setLonSuceso(edtLon.getText().toString());
                    accidente.setfSuceso(etdFsuc.getText().toString());

                    bundle.putSerializable("Accidente", accidente);
                    Intent siguientesdatos = new Intent(getApplicationContext(), RegistraDesAcc.class);
                    siguientesdatos.putExtra("Accidente", accidente);
                    startActivity(siguientesdatos);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // BOTON DE BORRADO PARA ELIMINACIO DE LOS DATOS DE LOS EDITTEXT
        btnBorra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrarTodo();
            }
        });

        // BOTON ENCARGADO DE LA GEOLOCALIZACION DEL USUARIO
        btnlocaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(RegistraDatosAcc.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(RegistraDatosAcc.this,new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },100);
                }
                edtDir.setText("");
                edtLat.setText("");
                edtLon.setText("");
                getLocation();
                loadingDialogBar.MuestraDialog("Calculando ubicación");
            }
        });

    }

    // ### METODOS USADOS ###
    // BOTON ENCARGADO DE LA GEOLOCALIZACION DEL USUARIO Y CONFIRMACION DE DIRECCION LATITUD Y LONGITUD
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, RegistraDatosAcc.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(RegistraDatosAcc.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            String address = addresses.get(0).getAddressLine(0);
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            loadingDialogBar.OcultaDialog();

            edtDir.setText(address);
            edtLat.setText(String.valueOf(latitude));
            edtLon.setText(String.valueOf(longitude));
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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

    public static boolean ValidaNombreApellContacto(String nombre){
        boolean nomCorrecto;
        String regexnom = "^(?=.{3,50}$)([a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{2,48}[\\,\\-\\.]{0,2}[\\s]{0,2}){1,6}$";
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

    // Metodo encargado de la validacion de Matricula mediante regex
    public static boolean Validamatricula(String matricula){
        boolean matCorrecta;
        String regexmat = "[0-9]{4}[A-Z]{2,3}";
        Pattern pat= Pattern.compile(regexmat);
        Matcher mat= pat.matcher(matricula);
        if (mat.matches()) {
            matCorrecta = true;
        }
        else {
            matCorrecta = false;
        }
        return matCorrecta;
    }

    // Metodo encargado del borrado masivo de los editText
    public void BorrarTodo(){
        edtNombre.setText("");
        edtPApe.setText("");
        edtSApe.setText("");
        edtDNI.setText("");
        edteMail.setText("");
        edtphone.setText("");
    }

    // Metodo que Volley que recupera de la BBDD el Nº de INCIDENCIA mayor para proporcionar un nuevo numero de INCIDENCIA
    public void maxIdIncidencia(String urlmaxid){
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
                                    int idIncidencia = object.getInt("Id_Incidencia");
                                    idIncidencia++;
                                    tvidNumAccidente.setText(String.valueOf(idIncidencia));
                                    accidente.setIdAccidente(String.valueOf(idIncidencia));
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

    //METODO QUE MUESTRA EL USUARIO INDICADO EN SENTENCIA
    public void muestraUsuario(String urlUsuario){
        //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
        StringRequest request =new StringRequest(Request.Method.POST, urlUsuario,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.w("Response VOLLEY SC", response.toString());
                            //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject =new JSONObject(response);
                            //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");

                            if (exito.equals("1")){
                                //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    edtNombre.setText(object.getString("Nombre"));
                                    edtPApe.setText(object.getString("Per_Apellido"));
                                    edtSApe.setText(object.getString("Sdo_Apellido"));
                                    edtDNI.setText(object.getString("DNI"));
                                    edteMail.setText(object.getString("Email"));
                                    edtphone.setText(object.getString("Telefono"));
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

    // Metodo que Volley que recupera de la BBDD el Nº de INCIDENCIA mayor para proporcionar un nuevo numero de INCIDENCIA
    public void maxIDAccidente(String urlmaxid){
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
                                    int idAccidente = object.getInt("Id_Accidente");
                                    idAccidente++;
                                    tvidNumAccidente.setText(String.valueOf(idAccidente));
                                    accidente.setIdAccidente(String.valueOf(idAccidente));
                                    //Toast.makeText(getApplicationContext(), accidente.getIdAccidente(), Toast.LENGTH_SHORT).show();
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

}