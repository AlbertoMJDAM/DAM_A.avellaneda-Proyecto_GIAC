package proyectoDAM.giac_app_v01.menuPrincipal_T.incidenciasAsignadas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.menuPrincipal_U.Asistencia.LoadingDialogBar;
import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Incidencias;
import proyectoDAM.giac_app_v01.R;

public class IncidenciasAsignadas extends AppCompatActivity {

    // Atributos:
    private ListView lvIncidenciasAsignadas;
    private ArrayList<Incidencias> lista;
    private adaptadorIncidenciasAsignadas adapter;
    private Button btnRegresar;
    private String idTrabajador;
    private LoadingDialogBar loadingDialogBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencias_asignadas);

        // Traemos un string con los datos del ID del trabajador
        Bundle extras = getIntent().getExtras();
        idTrabajador = extras.getString("idTrabajador");


        // Damos valor a los elementos:
        lvIncidenciasAsignadas = (ListView) findViewById(R.id.lvIncidenciasAsignadas);
        lista = new ArrayList<Incidencias>();
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        loadingDialogBar =new LoadingDialogBar(this);

        //Metodo para el boton btnRegresar
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadingDialogBar.MuestraDialog("Cargando incidencias asignadas");
        obtenerIncidenciasAsignadas();
    }

    //Metodo para obtener todos los vehiculos registrados y pasarlos a una lista
    private void obtenerIncidenciasAsignadas(){
        String url = "https://appgiac.000webhostapp.com/obtener_listado_incidencias_asignadas.php?empleado="+idTrabajador;

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
                        adapter = new adaptadorIncidenciasAsignadas(getApplicationContext(),lista);
                        lvIncidenciasAsignadas.setAdapter(adapter);
                        loadingDialogBar.OcultaDialog();
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
}