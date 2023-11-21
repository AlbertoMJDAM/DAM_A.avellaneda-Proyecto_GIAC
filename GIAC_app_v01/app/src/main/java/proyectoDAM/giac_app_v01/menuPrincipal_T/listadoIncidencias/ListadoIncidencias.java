package proyectoDAM.giac_app_v01.menuPrincipal_T.listadoIncidencias;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.IncompleteAnnotationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.Model.Incidencias;

public class ListadoIncidencias extends AppCompatActivity {

    // Atributos:
    private ListView lvListaIncidencias;
    private ArrayList<Incidencias> lista;
    private adaptadorListadoIncidencias adapter;
    private Button btnGuardar, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_incidencias);

        // Damos valor a los elementos:
        lvListaIncidencias = (ListView) findViewById(R.id.lvListaIncidencias);
        lista = new ArrayList<Incidencias>();
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnSalir = (Button) findViewById(R.id.btnSalir);

        //Metodo para el boton btnGuardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> listaIncidenciasElegidas = adapter.getItemSelection();

                if(listaIncidenciasElegidas.size()>0){
                    for(int x=0; x<listaIncidenciasElegidas.size(); x++){
                        AsignarIncidenciasElegidas(listaIncidenciasElegidas);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No has elegido ninguna incidencia", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Metodo para el boton btnSalir
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        obtenerIncidencias();
    }

    //Metodo para obtener todas las incidencias registradas y pasarlos a una lista
    private void obtenerIncidencias(){
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
                                lista.add(incidencia);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter = new adaptadorListadoIncidencias(getApplicationContext(),lista);
                        lvListaIncidencias.setAdapter(adapter);

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

    private void AsignarIncidenciasElegidas(ArrayList<String> listaIncidenciasElegidas){
        // Traemos un string con los datos del ID del trabajador
        Bundle extras = getIntent().getExtras();
        String idTrabajador = extras.getString("idTrabajador").trim();
        String url = "https://appgiac.000webhostapp.com/asignar_incidencias.php?empleado="+ idTrabajador;

        //CREAMOS LA BARRA DE PROGRESO
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Actualizando");
        progressDialog.show();

        for(int x=0; x<listaIncidenciasElegidas.size(); x++){
            int finalX = x;
            StringRequest request =new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(listaIncidenciasElegidas.size()>0){
                                Toast.makeText(getApplicationContext(), "1 Incidencia asignada", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), listaIncidenciasElegidas.size()+" Incidencias asignadas", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                            finish();
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
                    parametros.put("empleado", idTrabajador);
                    parametros.put("idIncidencia",listaIncidenciasElegidas.get(finalX));
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
    }
}