package proyectoDAM.giac_app_v01.menuPrincipal_U.Asistencia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tashila.pleasewait.PleaseWaitDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.LoadingDialogBar;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.Partes;

public class MenuPartes extends AppCompatActivity {
    // Creamos url
    private static final String URL = "https://appgiac.000webhostapp.com/mostrar_partes.php?Usuario=";
    // Creamos objeto Listview y un arraylist de la clase creada Alojamiento. Con el que tomaremos los valores
    // para el adaptador.
    private PleaseWaitDialog progressDialog;
    RequestQueue requestQueue;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123; // Damos valor al permiso.
    ListView listaDatos;
    String idUsuario;
    String nomEmp,pApe,email,telefono;
    ArrayList<Partes> lista;
    Bundle bundle = new Bundle();
    AdaptadorPartes miAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupartes);

        //ACTIVAMOS LOADINGGIALOGBAR
        progressDialog = new PleaseWaitDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setMessage("Cargando tus partes notificados...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        ////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MenuPartes.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                // Se tiene permiso
            } else {
                ActivityCompat.requestPermissions(MenuPartes.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {
            // No se necesita requerir permiso OS menos a 6.0.
        }
        ////////////

        // Elementos de activity_main
        listaDatos=(ListView)findViewById(R.id.lstDatos);
        TextView tv1=(TextView)findViewById(R.id.tv1);



        //PENDIENTE DE LLEVAR AL REAL
        bundle = getIntent().getExtras();
        idUsuario =bundle.getString("idusuario");
        //idUsuario = "1002";
        lista = new ArrayList<Partes>();

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
                            //loadingDialogBar.MuestraDialog("Cargando tus partes notificados");
                            // Con bucle for, iteramos el array de objetos del json
                            for (int i = 0; i < JSONList.length(); i++) {

                                //Sacamos los atributos que requiere nuestra clase Alojamientos
                                int Id_Parte = JSONList.getJSONObject(i).getInt("Id_Parte");
                                int Cod_Incidencia = JSONList.getJSONObject(i).getInt("Cod_Incidencia");
                                int Cod_Accidente = JSONList.getJSONObject(i).getInt("Cod_Accidente");
                                int Usuario = JSONList.getJSONObject(i).getInt("Usuario");
                                int Empleado;
                                String Fecha_Alta = JSONList.getJSONObject(i).getString("Fecha_Alta");
                                String Estado_Parte = JSONList.getJSONObject(i).getString("Estado_Parte");
                                String Fecha_Finalizacion = JSONList.getJSONObject(i).getString("Fecha_Finalizacion");

                                // Sacamos ahora los atributos que pudieran ser nulos a la hora de su insercion
                                if (JSONList.getJSONObject(i).isNull("Empleado")){
                                    Empleado = 0;}
                                else{Empleado = JSONList.getJSONObject(i).getInt("Empleado");}

                                //Toast.makeText(getApplicationContext(), "Empleado:" + String.valueOf(Empleado), Toast.LENGTH_LONG).show();
                                //Toast.makeText(getApplicationContext(), nomEmp, Toast.LENGTH_LONG).show();

                                // Añadimos variables al objeto de clase Alojamiento con constructor con parametros.
                                Partes registro = new Partes(Id_Parte,Cod_Incidencia,Cod_Accidente,Usuario,Empleado,Fecha_Alta,Estado_Parte,Fecha_Finalizacion);
                                muestraUsuario("https://appgiac.000webhostapp.com/mostrar_NA_empleado.php?Id_Empleado="+String.valueOf(Empleado),registro);

                                //Toast.makeText(getApplicationContext(), registro.toString(), Toast.LENGTH_LONG).show();
                                //Añadimos a listado de objetos Alojamiento
                                lista.add(registro);
                            }

                            //Creamos objeto de clase Adaptador con parametros contex y el listado de objetos Alojamiento
                            miAdaptador = new AdaptadorPartes(getApplicationContext(), lista);
                            //Añadimos al listview el adaptador
                            listaDatos.setAdapter(miAdaptador);
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
        listaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(lista.get(position).getCod_Incidencia()==0){
                    bundle.putSerializable("Parte", lista.get(position));
                    Intent accidentes = new Intent(getApplicationContext(), EditaAccidente.class);
                    accidentes.putExtra("Parte", lista.get(position));
                    startActivity(accidentes);
                }
                else{
                    bundle.putSerializable("Parte", lista.get(position));
                    Intent incidencias = new Intent(getApplicationContext(), EditaIncidencia.class);
                    incidencias.putExtra("Parte", lista.get(position));
                    startActivity(incidencias);
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //DESACTIVAMOS LOADINGGIALOGBAR
                progressDialog.dismiss();
            }
        },2500);

    }

    public void muestraUsuario(String urlEmpleado,Partes partes){
        //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
        StringRequest request =new StringRequest(Request.Method.GET, urlEmpleado,
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
                                    partes.setNombreEmpleado(object.getString("Nombre"));
                                    partes.setApellidoEmpleado(object.getString("Per_Apellido"));
                                    partes.setEmailEmpleado(object.getString("Email"));
                                    partes.setTelefonoEmpleado(object.getString("Telefono"));
                                }
                                miAdaptador.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}

