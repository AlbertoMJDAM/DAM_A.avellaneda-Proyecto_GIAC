package proyectoDAM.giac_app_v01.menuPrincipal_U.registraAccidentes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.login.Login;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.Accidente;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.LoadingDialogBar;

public class RegistraLocalizaAcc extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    RequestQueue requestQueue;
    private Accidente accidente;
    private ImageButton btnlocaliza;
    LoadingDialogBar loadingDialogBar;
    Bundle bundle = new Bundle();
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_localiza_acc);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        PermisoLLamadas();
        PermisoLocalizacion();
        accidente = new Accidente();
        btnlocaliza = findViewById(R.id.btnlob);
        loadingDialogBar =new LoadingDialogBar(this);
        //loadingDialogBar.MuestraDialog("Buscando datos de usuario");

        //CARGAMOS EL SIGUIENTE NUMERO DE INCIDENCIA
        maxIDAccidente("https://appgiac.000webhostapp.com/mostrar_max_accidente.php");

        // TRAEMOS EL ID DEL USUARIO DESDE EL MENU PRINCIPAL
        bundle = getIntent().getExtras();
        idUsuario =bundle.getString("idusuario");

        //DAMOS VALOR A LOS ELEMENTOS DE LA TABLA QUE RELLENAREMOS POSTERIORMENTE
        accidente.setIdUsuario(idUsuario);
        accidente.setEmpleado("0");
        accidente.setVehiculoUsuario("0000AAA");
        accidente.setVehiculoImplicadoUno("Pte");
        accidente.setVehiculoImplicadoDos("Pte");
        accidente.setDescripcion("Pendiente");
        accidente.setfSuceso("0000-00-00");
        loadingDialogBar.MuestraDialog("Calculando ubicación");
        getLocation();

        // Insertamos la localizacion en la BBDD
        // BOTON ENCARGADO DE LA GEOLOCALIZACION DEL USUARIO
        btnlocaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(RegistraLocalizaAcc.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(RegistraLocalizaAcc.this,new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },100);
                }
                if(accidente.getUbicacion().length()>0){
                    InsertaAccidente("https://appgiac.000webhostapp.com/insertar_accidentes.php");
                    InsertaParte("https://appgiac.000webhostapp.com/insertar_parte.php");
                    // Creamos AlertDialog para informar de la localizacion de usuario y definir las acciones que hacer despues
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistraLocalizaAcc.this);
                    builder.setTitle("Informacion");
                    builder.setMessage("¡Tu localizacion ha sido enviada!");
                    builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "VOLVIENDO AL MENU PRINCIPAL", Toast.LENGTH_SHORT).show();
                            Intent menuUsuario = new Intent(getApplicationContext(), Login.class);
                            startActivity(menuUsuario);
                        }
                    });
                    builder.setNegativeButton("Llamar a Emergencias", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Intent.ACTION_CALL);
                            i.setData(Uri.parse("tel:+34657174544"));
                            startActivity(i);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }}
        });

    }




    // ### METODOS USADOS ###

    // BOTON ENCARGADO DE LA GEOLOCALIZACION DEL USUARIO Y CONFIRMACION DE DIRECCION LATITUD Y LONGITUD
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10, RegistraLocalizaAcc.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {

        try {
            Geocoder geocoder = new Geocoder(RegistraLocalizaAcc.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            String address = addresses.get(0).getAddressLine(0);
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            accidente.setUbicacion(address);
            accidente.setLatSuceso(String.valueOf(latitude));
            accidente.setLonSuceso(String.valueOf(longitude));
            loadingDialogBar.OcultaDialog();
            //Toast.makeText(getApplicationContext(), "Ubicacion localizada", Toast.LENGTH_LONG).show();

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

    // METODO ENCARGADO DE INSERTAR ACCIDENTE EN BBDD.

    private void InsertaAccidente(String url){
        //Toast.makeText(getApplicationContext(), accidente.toString(), Toast.LENGTH_SHORT).show();
        //ProgressDialog progressDialog =new ProgressDialog(this);
       //progressDialog.setMessage("Insertando datos de localizacion del accidente");
       // progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de insercion", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("Id_Accidente",accidente.getIdAccidente());
                parametros.put("Id_Usuario", accidente.getIdUsuario());
                parametros.put("Empleado",accidente.getEmpleado());
                parametros.put("Vehiculo_usuario", accidente.getVehiculoUsuario());
                parametros.put("V_Implicado_Uno",accidente.getVehiculoImplicadoUno());
                parametros.put("V_Implicado_Dos",accidente.getVehiculoImplicadoDos());
                parametros.put("Ubicacion",accidente.getUbicacion());
                parametros.put("Descripcion",accidente.getDescripcion());
                parametros.put("CoordenadaX",accidente.getLatSuceso());
                parametros.put("CoordenadaY",accidente.getLonSuceso());
                parametros.put("Fecha_Accidente",accidente.getfSuceso());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                                    idAccidente ++;
                                    accidente.setIdAccidente(String.valueOf(idAccidente ));
                                    //Toast.makeText(getApplicationContext(), accidente.getIdAccidente(), Toast.LENGTH_SHORT).show();
                                    //loadingDialogBar.OcultaDialog();
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

    // METODO ENCARGADO DE INSERTAR PARTE EN BBDD.
    private void InsertaParte(String url) {
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Creando un parte de la Incidencia");
        progressDialog.show();
        int idParte = Integer.parseInt(accidente.getIdAccidente()) + 10000;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //Toast.makeText(getApplicationContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Id_Parte", String.valueOf(idParte));
                parametros.put("Cod_Incidencia", "0");
                parametros.put("Cod_Accidente", accidente.getIdAccidente());
                parametros.put("Usuario", accidente.getIdUsuario());
                parametros.put("Empleado", accidente.getEmpleado());
                parametros.put("Fecha_Alta", accidente.getfSuceso());
                parametros.put("Estado_Parte", "En proceso");
                parametros.put("Fecha_Finalizacion", "0000-00-00");
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void PermisoLLamadas(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(RegistraLocalizaAcc.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                // Se tiene permiso
            } else {
                ActivityCompat.requestPermissions(RegistraLocalizaAcc.this, new String[]{Manifest.permission.CALL_PHONE}, 200);
            }
        } else {
            // No se necesita requerir permiso OS menos a 6.0.
        }
    }

    public void PermisoLocalizacion(){
        if (ContextCompat.checkSelfPermission(RegistraLocalizaAcc.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RegistraLocalizaAcc.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
    }

    /*METODO QUE MUESTRA EL USUARIO INDICADO EN SENTENCIA
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
                            Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
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
    }*/

}