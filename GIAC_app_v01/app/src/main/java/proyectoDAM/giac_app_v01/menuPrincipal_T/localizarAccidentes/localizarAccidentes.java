package proyectoDAM.giac_app_v01.menuPrincipal_T.localizarAccidentes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tashila.pleasewait.PleaseWaitDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Accidentes;
import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Incidencias;
import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_T.listadoAccidentes.ListadoAccidentes;
import proyectoDAM.giac_app_v01.menuPrincipal_T.listadoIncidencias.ListadoIncidencias;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.LoadingDialogBar;

public class localizarAccidentes extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    // Atributos:
    private GoogleMap mMapa;
    private Button btnSalir;
    private ArrayList<Accidentes> listaAccidentes;
    private ArrayList<Incidencias> listaIncidencias;
    private PleaseWaitDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizar_accidentes);

        // Damos valor a los elementos:
        btnSalir = (Button) findViewById(R.id.btnSalir);
        listaAccidentes = new ArrayList<Accidentes>();
        listaIncidencias = new ArrayList<Incidencias>();
        progressDialog = new PleaseWaitDialog(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        //METODO PARA EL BOTON btnSalir
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressDialog.setTitle("Espere por favor");
        progressDialog.setMessage("Buscando...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        obtenerAccidentesSinAsignar();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMapa = googleMap;
        this.mMapa.setOnMapClickListener(this);
        this.mMapa.setOnMapLongClickListener(this);
        //CENTRAMOS EL MAPA EN ESPAÑA AL ABRIRLO
        LatLng latLng = new LatLng(40.416619, -3.704373);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }

    //METODO PARA OBTENER LOS ACCIDENTES SIN ASIGNAR Y PASARLOS A UNA LISTA
    private void obtenerAccidentesSinAsignar(){
        String url = "https://appgiac.000webhostapp.com/obtener_listado_accidentes.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String Id_Accidente = jsonObject.getString("Id_Accidente");
                                String Id_Usuario = jsonObject.getString("Id_Usuario");
                                String Empleado = jsonObject.getString("Empleado");
                                String Vehiculo_usuario = jsonObject.getString("Vehiculo_usuario");
                                String V_Implicado_Uno = jsonObject.getString("V_Implicado_Uno");
                                String V_Implicado_Dos = jsonObject.getString("V_Implicado_Dos");
                                String Ubicacion = jsonObject.getString("Ubicacion");
                                String Descripcion = jsonObject.getString("Descripcion");
                                String CoordenadaX = jsonObject.getString("CoordenadaX");
                                String CoordenadaY = jsonObject.getString("CoordenadaY");
                                String Fecha_Accidente = jsonObject.getString("Fecha_Accidente");

                                Accidentes accidente = new Accidentes(Id_Accidente, Id_Usuario, Empleado, Vehiculo_usuario,
                                        V_Implicado_Uno, V_Implicado_Dos, Ubicacion, Descripcion,
                                        CoordenadaX, CoordenadaY, Fecha_Accidente);
                                listaAccidentes.add(accidente);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        obtenerIncidenciasSinAsignar();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    //METODO PARA OBTENER LAS INCIDENCIAS SIN ASIGNAR Y PASARLOS A UNA LISTA
    private void obtenerIncidenciasSinAsignar(){
        String url = "https://appgiac.000webhostapp.com/obtener_listado_incidencias.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String Id_Incidencia = jsonObject.getString("Id_Incidencia");
                                String Id_Usuario = jsonObject.getString("Id_Usuario");
                                String empleado = jsonObject.getString("empleado");
                                String Vehiculo_Usuario = jsonObject.getString("Vehiculo_Usuario");
                                String Fecha_Incidencia  = jsonObject.getString("Fecha_Incidencia");
                                String Direccion  = jsonObject.getString("Direccion");
                                String CoordenadaX = jsonObject.getString("CoordenadaX");
                                String CoordenadaY = jsonObject.getString("CoordenadaY");
                                String Descripcion = jsonObject.getString("Descripcion");

                                Incidencias incidencia = new Incidencias(Id_Incidencia, Id_Usuario, empleado, Vehiculo_Usuario,
                                        Fecha_Incidencia, Direccion, CoordenadaX, CoordenadaY, Descripcion);
                                listaIncidencias.add(incidencia);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        cargarMarcadores();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    //METODO PARA CARGAR LOS MARCADORES EN EL MAPA
    private void cargarMarcadores(){
        ArrayList<Incidencias> listaIncidenciasMapa = new ArrayList<Incidencias>();
        ArrayList<Accidentes> listaAccidentesMapa = new ArrayList<Accidentes>();

        //RECORREMOS LA LISTA DE INCIDENCIAS PARA INCLUIRLAS EN UNA NUEVA LISTA DE INCIDENCIAS
        //QUE SE VAN A MOSTRAR
        for(int x=0; x<listaIncidencias.size();x++){
            String coordX = listaIncidencias.get(x).getCoordenadaX();
            String coordY = listaIncidencias.get(x).getCoordenadaY();
            if(!coordX.equals("") && !coordY.equals(""))
            {
                listaIncidenciasMapa.add(listaIncidencias.get(x));
            }
        }

        //RECORREMOS LA LISTA DE ACCIDENTES PARA INCLUIRLAS EN UNA NUEVA LISTA DE ACCIDENTES
        //QUE SE VAN A MOSTRAR
        for(int x=0; x<listaAccidentes.size();x++){
            String coordX = listaAccidentes.get(x).getCoordenadaX();
            String coordY = listaAccidentes.get(x).getCoordenadaY();
            if(!coordX.equals("") && !coordY.equals(""))
            {
                listaAccidentesMapa.add(listaAccidentes.get(x));
            }
        }

        //RECORREMOS LA LISTA DE INCIDENCIAS A MOSTRAR Y LAS INCLUIMOS EN EL MAPA
        for (int i = 0; i < listaIncidenciasMapa.size() ; i++) {
            Marker marker1;
            marker1 = mMapa.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(listaIncidenciasMapa.get(i).getCoordenadaX()),
                    Double.parseDouble(listaIncidenciasMapa.get(i).getCoordenadaY()))).title(listaIncidenciasMapa.get(i).getId_Incidencia()));
            String[] datos = new String[4];
            datos[1] = listaIncidenciasMapa.get(i).getId_Incidencia();
            datos[2] = listaIncidenciasMapa.get(i).getId_Usuario();
            datos[3] = listaIncidenciasMapa.get(i).getVehiculo_Usuario();
            datos[0] = "I";
            marker1.setTag(datos);
        }

        //RECORREMOS LA LISTA DE ACCIDENTES A MOSTRAR Y LOS INCLUIMOS EN EL MAPA
        for (int i = 0; i < listaAccidentesMapa.size() ; i++) {
            Marker marker1;
            marker1 = mMapa.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(listaAccidentesMapa.get(i).getCoordenadaX()),
                    Double.parseDouble(listaAccidentesMapa.get(i).getCoordenadaY()))).title(listaAccidentesMapa.get(i).getId_Accidente()));
            marker1.setTag(listaAccidentesMapa.get(i));
            String[] datos = new String[4];
            datos[1] = listaAccidentesMapa.get(i).getId_Accidente();
            datos[2] = listaAccidentesMapa.get(i).getId_Usuario();
            datos[3] = listaAccidentesMapa.get(i).getVehiculo_usuario();
            datos[0] = "A";
            marker1.setTag(datos);
        }

        progressDialog.dismiss();

        //CARGAMOS EL ADAPTADOR PERSONALIZADO DE LA INFOWINDOWS
        mMapa.setInfoWindowAdapter(new adaptadorInfoMapa(LayoutInflater.from(getApplicationContext())));

        //DAMOS FUNCIONALIDAD AL PULSAR LA INFOWINDOWS MANDANDO A LA ACTIVITY DE INCIDENCIAS O ACCIDENTES
        mMapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                String[] datos = (String[]) marker.getTag();
                if(datos[0].equalsIgnoreCase("I")){
                    Intent intent = new Intent (getApplicationContext(), ListadoIncidencias.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }else{
                    Intent intent = new Intent (getApplicationContext(), ListadoAccidentes.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
            }
        });
    }
}
