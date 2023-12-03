package proyectoDAM.giac_app_v01.menuPrincipal_T.accidentesAsignados;

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

import proyectoDAM.giac_app_v01.Model.Accidentes;
import proyectoDAM.giac_app_v01.R;

public class accidentesAsignados extends AppCompatActivity {

    // Atributos:
    private ListView lvAccidentesAsignados;
    private ArrayList<Accidentes> lista;
    private adaptadorAccidentesAsignados adapter;
    private Button btnRegresar;
    private String idTrabajador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accidentes_asignados);

        // Traemos un string con los datos del ID del trabajador
        Bundle extras = getIntent().getExtras();
        idTrabajador = extras.getString("idTrabajador");

        // Damos valor a los elementos:
        lvAccidentesAsignados = (ListView) findViewById(R.id.lvAccidentesAsignados);
        lista = new ArrayList<Accidentes>();
        btnRegresar = (Button) findViewById(R.id.btnRegresar);

        //Metodo para el boton btnRegresar
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        obtenerAccidentesAsignados();
    }

    //Metodo para obtener todos los accidentes asignados al trabajador y pasarlos a una lista
    private void obtenerAccidentesAsignados(){
        String url = "https://appgiac.000webhostapp.com/obtener_listado_accidentes_asignados.php?Empleado="+idTrabajador;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String Id_Usuario = jsonObject.getString("Id_Usuario");
                                String Id_Accidente = jsonObject.getString("Id_Accidente");
                                String Empleado = jsonObject.getString("Empleado");
                                String Vehiculo_usuario = jsonObject.getString("Vehiculo_usuario");
                                String V_Implicado_Uno  = jsonObject.getString("V_Implicado_Uno");
                                String V_Implicado_Dos  = jsonObject.getString("V_Implicado_Dos");
                                String Ubicacion = jsonObject.getString("Ubicacion");
                                String Descripcion = jsonObject.getString("Descripcion");
                                String CoordenadaX = jsonObject.getString("CoordenadaX");
                                String CoordenadaY = jsonObject.getString("CoordenadaY");
                                String Fecha_Accidente = jsonObject.getString("Fecha_Accidente");

                                Accidentes accidente = new Accidentes(Id_Accidente, Id_Usuario, Empleado, Vehiculo_usuario,
                                        V_Implicado_Uno, V_Implicado_Dos, Ubicacion, Descripcion, CoordenadaX,
                                        CoordenadaY, Fecha_Accidente);
                                lista.add(accidente);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter = new adaptadorAccidentesAsignados(getApplicationContext(),lista);
                        lvAccidentesAsignados.setAdapter(adapter);
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