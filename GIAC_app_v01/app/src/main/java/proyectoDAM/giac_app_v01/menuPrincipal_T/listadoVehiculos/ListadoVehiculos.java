package proyectoDAM.giac_app_v01.menuPrincipal_T.listadoVehiculos;

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
import proyectoDAM.giac_app_v01.Model.Vehiculos;

public class ListadoVehiculos extends AppCompatActivity {

    // Atributos:
    private ListView lvListaVehiculos;
    private ArrayList<Vehiculos> lista;
    private adaptadorListaVehiculos adapter;
    private Button btnRetroceder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_vehiculos);

        // Damos valor a los elementos:
        lvListaVehiculos = (ListView) findViewById(R.id.lvListaVehiculos);
        lista = new ArrayList<Vehiculos>();
        btnRetroceder = (Button) findViewById(R.id.btnRetroceder);

        //Metodo para el boton Retroceder
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        obtenerVehiculos();
    }

    //Metodo para obtener todos los vehiculos registrados y pasarlos a una lista
    private void obtenerVehiculos(){
        String url = "https://appgiac.000webhostapp.com/obtener_listado_vehiculos.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("Id_Cliente");
                                String marca = jsonObject.getString("Marca");
                                String modelo = jsonObject.getString("Modelo");
                                String color = jsonObject.getString("Color");
                                String matricula = jsonObject.getString("Matricula");
                                String nPuertas = jsonObject.getString("Num_Puertas");
                                Vehiculos vehiculo = new Vehiculos(id, marca, modelo, color, matricula, nPuertas);
                                lista.add(vehiculo);
                                //Toast.makeText(getApplicationContext(),vehiculo.getId()+" "+vehiculo.getMarca()+" "+vehiculo.getModelo()+" "+vehiculo.getColor()+" "+vehiculo.getMatricula(),Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter = new adaptadorListaVehiculos(getApplicationContext(),lista);

                        lvListaVehiculos.setAdapter(adapter);                    }
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