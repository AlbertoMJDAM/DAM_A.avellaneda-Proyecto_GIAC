package proyectoDAM.giac_app_v01.menuPrincipal_T.incidenciasAsignadas;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Incidencias;
import proyectoDAM.giac_app_v01.R;

public class adaptadorIncidenciasAsignadas extends BaseAdapter {

    // Atributos:
    private Context context;
    private ArrayList<Incidencias> arrayList;
    private ArrayList<String> itemSelection;

    public ArrayList<String> getItemSelection() {
        return itemSelection;
    }

    //CONSTRUCTOR
    public adaptadorIncidenciasAsignadas (Context context, ArrayList<Incidencias> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.lista_incidencias_asignadas_adapter, null);
        }

        TextView tvIdIncidencia = (TextView) view.findViewById(R.id.tvIdIncidencia);
        TextView tvFechaIncidencia = (TextView) view.findViewById(R.id.tvFechaIncidencia);
        TextView tvUbicacionIncidencia = (TextView) view.findViewById(R.id.tvUbicacionIncidencia);
        TextView tvDescripcionIncidencia = (TextView) view.findViewById(R.id.tvDescripcionIncidencia);


        tvIdIncidencia.setText(arrayList.get(i).getId_Incidencia());
        tvFechaIncidencia.setText(arrayList.get(i).getFecha_Incidencia());
        tvUbicacionIncidencia.setText(arrayList.get(i).getDireccion());
        tvDescripcionIncidencia.setText(arrayList.get(i).getDescripcion());

        //METODO ONCLICK DEL ITEM SELECCIONADO
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, gestionarIncidencia.class);
                intent.putParcelableArrayListExtra("datos", arrayList);
                intent.putExtra("incidencia", i);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //METODO LONGCLICK DEL ITEM SELECCIONADO
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getRootView().getContext())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("ELIMINAR ARCHIVO")
                        .setMessage("Se va a eliminar la incidencia:\n" +  arrayList.get(i).getId_Incidencia())
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminarIncidencia(i);
                            }
                        })
                        .setNegativeButton("Cancelar",null)
                        .show();

                return false;
            }
        });

        return view;
    }

    //METODO QUE REALIZA LA LLAMADA PARA ELIMINAR LA INCIDENCIA SELECCIONADA DE LA BASE DE DATOS
    private void eliminarIncidencia(int i){
        String url = "https://appgiac.000webhostapp.com/eliminar_incidencia.php?Id_Incidencia="+arrayList.get(i).getId_Incidencia();

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        Toast.makeText(context.getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                        arrayList.remove(i);
                        notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
