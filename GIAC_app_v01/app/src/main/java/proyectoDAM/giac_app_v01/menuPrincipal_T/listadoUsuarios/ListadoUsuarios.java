package proyectoDAM.giac_app_v01.menuPrincipal_T.listadoUsuarios;

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
import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Usuarios;

public class ListadoUsuarios extends AppCompatActivity {

    // Atributos:
    private ListView lvListaUsuarios;
    private ArrayList<Usuarios> lista;
    private adaptadorListaUsuarios adapter;
    private Button btnRetroceder;
    private PleaseWaitDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuarios);

        // Damos valor a los elementos:
        lvListaUsuarios = (ListView) findViewById(R.id.lvListaUsuarios);
        lista = new ArrayList<Usuarios>();
        btnRetroceder = (Button) findViewById(R.id.btnRetroceder);
        progressDialog = new PleaseWaitDialog(this);

        //Metodo para el boton Retroceder
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //ACTIVAMOS LOADINGGIALOGBAR
        progressDialog.setTitle("Espere por favor");
        progressDialog.setMessage("Cargando listado de usuarios...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        obtenerUsuarios();
    }

    //Metodo para obtener todos los usuarios registrados y pasarlos a una lista
    private void obtenerUsuarios(){
        String url = "https://appgiac.000webhostapp.com/obtener_listado_usuarios.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("Id_Usuario");
                                String nombre = jsonObject.getString("Nombre");
                                String dni = jsonObject.getString("DNI");
                                String licencia = jsonObject.getString("Tipo_Licencia");
                                String email = jsonObject.getString("Email");
                                String pApellido = jsonObject.getString("Per_Apellido");
                                String sApellidos = jsonObject.getString("Sdo_Apellido");
                                String Fecha_Nacimiento = jsonObject.getString("Fecha_Nacimiento");
                                String Telefono = jsonObject.getString("Telefono");
                                Usuarios usuario = new Usuarios(id, nombre, dni, licencia, email, pApellido,
                                        sApellidos,Fecha_Nacimiento, Telefono);
                                lista.add(usuario);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter = new adaptadorListaUsuarios(getApplicationContext(),lista);
                        lvListaUsuarios.setAdapter(adapter);

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