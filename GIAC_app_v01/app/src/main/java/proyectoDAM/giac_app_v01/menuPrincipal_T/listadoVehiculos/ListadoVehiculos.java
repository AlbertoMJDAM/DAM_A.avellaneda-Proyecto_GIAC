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
import com.tashila.pleasewait.PleaseWaitDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.LoadingDialogBar;
import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Vehiculos;

public class ListadoVehiculos extends AppCompatActivity {

    // Atributos:
    private ListView lvListaVehiculos;
    private ArrayList<Vehiculos> lista;
    private adaptadorListaVehiculos adapter;
    private Button btnRetroceder;
    private PleaseWaitDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_vehiculos);

        // DAMOS VALOR A LOS ELEMENTOS
        lvListaVehiculos = (ListView) findViewById(R.id.lvListaVehiculos);
        lista = new ArrayList<Vehiculos>();
        btnRetroceder = (Button) findViewById(R.id.btnRetroceder);
        progressDialog = new PleaseWaitDialog(this);

        //METODO PARA EL BOTON btnRetroceder
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //ACTIVAMOS LOADINGGIALOGBAR
        progressDialog.setTitle("Espere por favor");
        progressDialog.setMessage("Cargando listado de vehiculos...");
        progressDialog.setCancelable(false);
        progressDialog.show();
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
                                String Id_Cliente = jsonObject.getString("Id_Cliente");
                                String Tipo_Vehiculo = jsonObject.getString("Tipo_Vehiculo");
                                String Marca = jsonObject.getString("Marca");
                                String Modelo = jsonObject.getString("Modelo");
                                String Color = jsonObject.getString("Color");
                                String Num_Puertas = jsonObject.getString("Num_Puertas");
                                String Motor = jsonObject.getString("Motor");
                                String Cv = jsonObject.getString("Cv");
                                String Matricula = jsonObject.getString("Matricula");
                                String Num_Bastidor = jsonObject.getString("Num_Bastidor");

                                Vehiculos vehiculo = new Vehiculos(Id_Cliente, Tipo_Vehiculo, Marca, Modelo, Color,
                                        Num_Puertas, Motor, Cv, Matricula, Num_Bastidor);
                                lista.add(vehiculo);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter = new adaptadorListaVehiculos(getApplicationContext(),lista);
                        lvListaVehiculos.setAdapter(adapter);

                        //DESACTIVAMOS LOADINGGIALOGBAR
                        progressDialog.dismiss();
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