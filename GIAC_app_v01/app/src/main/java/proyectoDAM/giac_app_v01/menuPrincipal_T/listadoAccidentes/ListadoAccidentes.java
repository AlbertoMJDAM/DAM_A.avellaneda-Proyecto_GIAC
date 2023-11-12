package proyectoDAM.giac_app_v01.menuPrincipal_T.listadoAccidentes;

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

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.Model.Accidentes;

public class ListadoAccidentes extends AppCompatActivity {

    // Atributos:
    private ListView lvListaAccidentes;
    private ArrayList<Accidentes> lista;
    private adaptadorListaAccidentes adapter;
    private Button btnRetroceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_accidentes);

        // Damos valor a los elementos:
        lvListaAccidentes = (ListView) findViewById(R.id.lvListaAccidentes);
        lista = new ArrayList<Accidentes>();
        btnRetroceder = (Button) findViewById(R.id.btnRetroceder);

        //Metodo para el boton Retroceder
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        obtenerAccidentes();
    }

    //Metodo para obtener todos los vehiculos registrados y pasarlos a una lista
    private void obtenerAccidentes(){
        String url = "https://appgiac.000webhostapp.com/obtener_listado_accidentes.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String Id_Accidente = jsonObject.getString("Id_Accidente");
                                String Empleado  = jsonObject.getString("Empleado");
                                String Vehiculo_usuario = jsonObject.getString("Vehiculo_usuario");
                                String V_Implicado_Uno  = jsonObject.getString("V_Implicado_Uno");
                                String V_Implicado_Dos  = jsonObject.getString("V_Implicado_Dos");
                                String Ubicacion = jsonObject.getString("Ubicacion");
                                String Descripcion = jsonObject.getString("Descripcion");
                                String CoordenadaX = jsonObject.getString("CoordenadaX");
                                String CoordenadaY = jsonObject.getString("CoordenadaY");
                                String Fecha_Accidente = jsonObject.getString("Fecha_Accidente");

                                Accidentes accidente = new Accidentes(Id_Accidente, Empleado, Vehiculo_usuario,
                                        V_Implicado_Uno, V_Implicado_Dos, Ubicacion, Descripcion,
                                        CoordenadaX, CoordenadaY, Fecha_Accidente);
                                lista.add(accidente);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter = new adaptadorListaAccidentes(getApplicationContext(),lista);

                        lvListaAccidentes.setAdapter(adapter);                    }
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