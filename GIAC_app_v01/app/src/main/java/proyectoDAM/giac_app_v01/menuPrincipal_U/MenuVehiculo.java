package proyectoDAM.giac_app_v01.menuPrincipal_U;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.Vehiculo;

public class MenuVehiculo extends AppCompatActivity {
    // Creamos url
    private static final String URL = "https://appgiac.000webhostapp.com/mostrar_vehiculos.php?Id_Cliente=";
    // Creamos objeto Listview y un arraylist de la clase creada Alojamiento. Con el que tomaremos los valores
    // para el adaptador.
    ListView listaDatos;
    String idUsuario;
    ArrayList<Vehiculo> lista;
    Bundle bundle = new Bundle();
    Button btnAddvehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuvehiculo);

        // Elementos de activity_main
        btnAddvehiculo = findViewById(R.id.btnReg);
        listaDatos=(ListView)findViewById(R.id.lstDatos);
        TextView tv1=(TextView)findViewById(R.id.tv1);


        bundle = getIntent().getExtras();
        idUsuario =bundle.getString("idusuario");

        lista = new ArrayList<Vehiculo>();

        // Instanciamos objeto RequestQueue
        RequestQueue request = Volley.newRequestQueue(this);
        //Como el elemento raiz en este caso (Viendo el fichero JSON) es un objeto (no un array)
        // instanciamos un jsonObjectRequest (si fuera un array instanciariamos un JsonArrayRequest)

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL+idUsuario, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.d("RESPONSE", response.toString(0));

                            //Creamos objeto de clase JSONObject
                            JSONObject jsonObjectPrincipal = new JSONObject(response.toString(0));
                            //Creamos objeto de clase JSONArray llamando al array de nuestro JSON

                            // Para ello vamos a https://jsonviewer.stack.hu/ estructura el archivo para visualizar bien y buscamos el array
                            JSONArray JSONList = jsonObjectPrincipal.getJSONArray("datos");

                            // Con bucle for, iteramos el array de objetos del json
                            for (int i = 0; i < JSONList.length(); i++) {

                                //Sacamos los atributos que requiere nuestra clase Alojamientos
                                int id_Cliente = JSONList.getJSONObject(i).getInt("Id_Cliente");
                                String tipo_Vehiculo = JSONList.getJSONObject(i).getString("Tipo_Vehiculo");
                                String marca = JSONList.getJSONObject(i).getString("Marca");
                                String modelo = JSONList.getJSONObject(i).getString("Modelo");
                                String color = JSONList.getJSONObject(i).getString("Color");
                                int num_Puertas = JSONList.getJSONObject(i).getInt("Num_Puertas");
                                String motor;
                                int cv;
                                String matricula = JSONList.getJSONObject(i).getString("Matricula");
                                String num_Bastidor;
                                // Sacamos ahora los atributos que pudieran ser nulos a la hora de su insercion
                                if (JSONList.getJSONObject(i).isNull("motor")){
                                    motor = "Por definir";}
                                else{
                                    motor = JSONList.getJSONObject(i).getString("motor");}
                                if (JSONList.getJSONObject(i).isNull("Cv")){
                                    cv =0;}
                                else{
                                    cv = JSONList.getJSONObject(i).getInt("Cv");}
                                if (JSONList.getJSONObject(i).isNull("Num_Bastidor")){
                                    num_Bastidor = "Por definir";}
                                else{
                                    num_Bastidor = JSONList.getJSONObject(i).getString("Num_Bastidor");}

                                // Añadimos variables al objeto de clase Alojamiento con constructor con parametros.
                                Vehiculo registro = new Vehiculo(id_Cliente, tipo_Vehiculo, marca, modelo, color, num_Puertas,motor,cv,matricula,num_Bastidor);

                                //Añadimos a listado de objetos Alojamiento
                                lista.add(registro);
                            }

                            //Creamos objeto de clase Adaptador con parametros contex y el listado de objetos Alojamiento
                            AdaptadorVehiculos miAdaptador=new AdaptadorVehiculos(getApplicationContext(), lista);
                            //Añadimos al listview el adaptador
                            listaDatos.setAdapter(miAdaptador);
                            //Preparamos el evento de clicado por si lo requiere (No util en este caso)

                            listaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Vehiculo vehiculo = lista.get(position);
                                    bundle.putSerializable("vehiculo", vehiculo);
                                    Intent vehiculosintent = new Intent(getApplicationContext(),EditaVehiculo.class);
                                    vehiculosintent.putExtra("vehiculo" , vehiculo);
                                    startActivity(vehiculosintent);
                                }

                            });

                        }
                        // Capturamos excepciones JSON
                        catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            // Implementamos metodo onErrorResponse (obligatorio)
            @Override
            public void onErrorResponse(VolleyError error) {

                // TODO Auto-generated method stub
            }
        });
        //Añadimos a request el objeto jsonObjectRequest
        request.add(jsonObjectRequest);

        btnAddvehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegVehiculo= new Intent(getApplicationContext(),RegistroVehiculo.class);
                RegVehiculo.putExtra("idusuario" , idUsuario);
                startActivity(RegVehiculo);
            }
        });
    }

}

