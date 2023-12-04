package proyectoDAM.giac_app_v01.menuPrincipal_U.Asistencia;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import proyectoDAM.giac_app_v01.R;

public class AdaptadorPartes extends BaseAdapter {

    String tipo;
    String naEmpleado;
    String cnEmpleado = "";
    String identificador;
    String fechas;
    String estadoSuc;
    Context contexto; //contexto de la aplicacion
    RequestQueue requestQueue;
    List<Partes> listaPartes; //lista de datos a generar. Podemos usar tb un ArrayList
    String nomEmp,pApe,email,telefono;

    // Constructor del adaptador
    public AdaptadorPartes(Context contexto, List<Partes> mislistPartes) {
        this.contexto = contexto;
        listaPartes = mislistPartes;
    }



    @Override
    public int getCount() {
        return listaPartes.size(); //Devuelve la cantidad de elementos de la lista
    }


    @Override
    public Object getItem(int i) { //Devuelve el objeto de la lista en la posición indicada como parametro
        return listaPartes.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }


    // Completamos el metodo getView para que el ItemlistView haga todo aquello que queremos
    @SuppressLint("SuspiciousIndentation")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { //Es el método que se ejecuta cuando se muestra en mi ListView cada item
        View vista=view;
        LayoutInflater inflate = LayoutInflater.from(contexto); //Obtenemos el contexto del item sobre el cual estamos trabajando del ListView
        vista=inflate.inflate(R.layout.itemlistview_partes, null); //Consigo referenciar a la vista en sí

        TextView tipoParte = (TextView)vista.findViewById(R.id.tvidentificacionIA);
        TextView fSuceso = (TextView)vista.findViewById(R.id.fechaSuceso);
        TextView empleado = (TextView)vista.findViewById(R.id.idempleado);
        TextView estado = (TextView)vista.findViewById(R.id.estadoSuceso);
        TextView contacto = (TextView)vista.findViewById(R.id.contacto);
        ImageView infogiac = (ImageView)vista.findViewById(R.id.imggiac);
        ImageView mail = (ImageView)vista.findViewById(R.id.imgMail);
        ImageView llamar = (ImageView)vista.findViewById(R.id.imgTelefono);


        if(listaPartes.get(i).getId_Parte()>15000){
            identificador = String.valueOf(listaPartes.get(i).getCod_Accidente());
            tipo = "ACCIDENTE   " + identificador;
        }
        else{
            identificador =String.valueOf(listaPartes.get(i).getCod_Incidencia());
            tipo = "INCIDENCIA   " + identificador;
        }

        fechas = listaPartes.get(i).getFechaAlta();

        if (listaPartes.get(i).getFechaFin().equalsIgnoreCase("0000-00-00")){
            estadoSuc = "Pendiente de resolución";
        }
        else{
            estadoSuc = "Finalizada el dia :" + listaPartes.get(i).getFechaFin();
        }

        if((listaPartes.get(i).getEmpleado()>0)){
            muestraUsuario("https://appgiac.000webhostapp.com/mostrar_NA_empleado.php?Id_Empleado=" + String.valueOf(listaPartes.get(i).getEmpleado()));
            naEmpleado = nomEmp + " " + pApe;
            cnEmpleado = nomEmp + " " + pApe;
            infogiac.setVisibility(View.INVISIBLE);
            empleado.setText("Asistido por: " + naEmpleado);
            contacto.setText("Contacta con tu asistente " + cnEmpleado);
        }
        else{
            naEmpleado = "Sin asistencia";
            mail.setVisibility(View.INVISIBLE);
            llamar.setVisibility(View.INVISIBLE);
            empleado.setText("Asistido por: " + naEmpleado);
            contacto.setText("Contacta con informacion general GIAC");
        }

        tipoParte.setText(tipo);
        fSuceso.setText("Fecha Inicio: " + fechas);
        estado.setText("Estado: " + estadoSuc);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(contexto.getApplicationContext(), "MAIL" , Toast.LENGTH_LONG).show();
                Intent correo = new Intent (v.getContext(), EnvioMail_emp.class);
                correo.addFlags(FLAG_ACTIVITY_NEW_TASK);
                correo.putExtra("email", email);
                v.getContext().startActivity(correo);
            }
        });

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent llamar = new Intent(Intent.ACTION_CALL);
                llamar.setData(Uri.parse("tel:+34"+telefono));
                llamar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                v.getContext().startActivity(llamar);
            }
        });

        infogiac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llamar = new Intent(Intent.ACTION_CALL);
                llamar.setData(Uri.parse("tel:+34657174544"));
                llamar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                v.getContext().startActivity(llamar);
            }
        });


        return vista;
    }

    public void muestraUsuario(String urlEmpleado){
        //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
        StringRequest request =new StringRequest(Request.Method.POST, urlEmpleado,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.w("Response VOLLEY SC", response.toString());
                            //Toast.makeText(contexto.getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject =new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");
                            if (exito.equals("1")){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    nomEmp = object.getString("Nombre");
                                    pApe = object.getString("Per_Apellido");
                                    email = object.getString("Email");
                                    telefono = object.getString("Telefono");
                                }
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
        requestQueue = Volley.newRequestQueue(contexto.getApplicationContext());
        requestQueue.add(request);
    }
}
