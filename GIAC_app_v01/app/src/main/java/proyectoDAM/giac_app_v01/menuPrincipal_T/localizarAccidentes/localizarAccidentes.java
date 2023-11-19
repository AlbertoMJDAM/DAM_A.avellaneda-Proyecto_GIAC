package proyectoDAM.giac_app_v01.menuPrincipal_T.localizarAccidentes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.Model.Accidentes;
import proyectoDAM.giac_app_v01.Model.Incidencias;
import proyectoDAM.giac_app_v01.R;

public class localizarAccidentes extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    // Atributos:
    private GoogleMap mMapa;
    private Button btnSalir;
    private ArrayList<Accidentes> listaAccidentes;
    private ArrayList<Incidencias> listaIncidencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizar_accidentes);

        // Damos valor a los elementos:
        btnSalir = (Button) findViewById(R.id.btnSalir);
        listaAccidentes = new ArrayList<Accidentes>();
        listaIncidencias = new ArrayList<Incidencias>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        //METODO PARA EL BOTON btnSalir
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        obtenerAccidentesSinAsignar();
        obtenerIncidenciasSinAsignar();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMapa = googleMap;
        this.mMapa.setOnMapClickListener(this);
        this.mMapa.setOnMapLongClickListener(this);
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
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Cargando datos");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String Id_Accidente = jsonObject.getString("Id_Accidente");
                                String Empleado = jsonObject.getString("Empleado");
                                String Vehiculo_usuario = jsonObject.getString("Vehiculo_usuario");
                                String V_Implicado_Uno = jsonObject.getString("V_Implicado_Uno");
                                String V_Implicado_Dos = jsonObject.getString("V_Implicado_Dos");
                                String Ubicacion = jsonObject.getString("Ubicacion");
                                String Descripcion = jsonObject.getString("Descripcion");
                                String CoordenadaX = jsonObject.getString("CoordenadaX");
                                String CoordenadaY = jsonObject.getString("CoordenadaY");
                                String Fecha_Accidente = jsonObject.getString("Fecha_Accidente");

                                Accidentes accidente = new Accidentes(Id_Accidente, Empleado, Vehiculo_usuario,
                                        V_Implicado_Uno, V_Implicado_Dos, Ubicacion, Descripcion,
                                        CoordenadaX, CoordenadaY, Fecha_Accidente);
                                listaAccidentes.add(accidente);
                                progressDialog.dismiss();

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                        //Toast.makeText(getApplicationContext(), "hay "+listaAccidentes.size()+" accidentes", Toast.LENGTH_SHORT).show();
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
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Cargando datos");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String Id_Incidencia = jsonObject.getString("Id_Incidencia");
                                String empleado = jsonObject.getString("empleado");
                                String Vehiculo_Usuario = jsonObject.getString("Vehiculo_Usuario");
                                String Fecha_Incidencia  = jsonObject.getString("Fecha_Incidencia");
                                String Direccion  = jsonObject.getString("Direccion");
                                String CoordenadaX = jsonObject.getString("CoordenadaX");
                                String CoordenadaY = jsonObject.getString("CoordenadaY");
                                String Descripcion = jsonObject.getString("Descripcion");

                                Incidencias incidencia = new Incidencias(Id_Incidencia, empleado, Vehiculo_Usuario,
                                        Fecha_Incidencia, Direccion, CoordenadaX, CoordenadaY, Descripcion);
                                listaIncidencias.add(incidencia);
                                progressDialog.dismiss();
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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
        ArrayList<LatLng> listaMarcadores = new ArrayList<LatLng>();

        //RECORREMOS LA LISTA DE INCIDENCIAS PARA INCLUIRLAS EN LA LISTA DE MARCADORES
        for(int x=0; x<listaIncidencias.size();x++){
            String coordX = listaIncidencias.get(x).getCoordenadaX();
            String coordY = listaIncidencias.get(x).getCoordenadaY();
            if(!coordX.equals("") || !coordY.equals(""))
            {
                LatLng marcador = new LatLng(Double.parseDouble(listaIncidencias.get(x).getCoordenadaX()),
                        Double.parseDouble(listaIncidencias.get(x).getCoordenadaY()));
                listaMarcadores.add(marcador);
            }
        }
        //RECORREMOS LA LISTA DE ACCIDENTES PARA INCLUIRLAS EN LA LISTA DE MARCADORES
        for(int x=0; x<listaAccidentes.size();x++){
            String coordX = listaAccidentes.get(x).getCoordenadaX();
            String coordY = listaAccidentes.get(x).getCoordenadaY();
            if(!coordX.equals("") || !coordY.equals(""))
            {
                LatLng marcador = new LatLng(Double.parseDouble(listaAccidentes.get(x).getCoordenadaX()),
                        Double.parseDouble(listaAccidentes.get(x).getCoordenadaY()));
                listaMarcadores.add(marcador);
            }
        }

        for (int i = 0; i < listaMarcadores.size() ; i++) {
            mMapa.addMarker(new MarkerOptions()
                    .position(listaMarcadores.get(i)));
        }
    }

}
