package proyectoDAM.giac_app_v01.menuPrincipal_T.accidentesAsignados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Accidentes;
import proyectoDAM.giac_app_v01.R;

public class adaptadorAccidentesAsignados extends BaseAdapter {

    // Atributos:
    private Context context;
    private ArrayList<Accidentes> arrayList;

    //CONSTRUCTOR
    public adaptadorAccidentesAsignados (Context context, ArrayList<Accidentes> arrayList){
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
            view = layoutInflater.inflate(R.layout.lista_accidentes_asignados_adapter, null);
        }

        TextView tvIdAccidete = (TextView) view.findViewById(R.id.tvIdAccidete);
        TextView tvFechaAccidente = (TextView) view.findViewById(R.id.tvFechaAccidente);
        TextView tvUbicacionAccidente = (TextView) view.findViewById(R.id.tvUbicacionAccidente);
        TextView tvDescripcionAccidente = (TextView) view.findViewById(R.id.tvDescripcionAccidente);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);


        tvIdAccidete.setText(arrayList.get(i).getId_Accidente());
        tvFechaAccidente.setText(arrayList.get(i).getFecha_Accidente());
        tvUbicacionAccidente.setText(arrayList.get(i).getUbicacion());
        tvDescripcionAccidente.setText(arrayList.get(i).getDescripcion());

        //METODO ONCLICK DEL ITEM SELECCIONADO
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, gestionarAccidentes.class);
                intent.putParcelableArrayListExtra("datos", arrayList);
                intent.putExtra("accidente", i);
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
                        .setMessage("Se va a eliminar el accidente:\n" +  arrayList.get(i).getId_Accidente())
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminarAccidente(i);
                            }
                        })
                        .setNegativeButton("Cancelar",null)
                        .show();

                return false;
            }
        });

        return view;
    }

    //METODO QUE REALIZA LA LLAMADA PARA ELIMINAR EL ACCIDENTE SELECCIONADO DE LA BASE DE DATOS
    private void eliminarAccidente(int i){
        String url = "https://appgiac.000webhostapp.com/eliminar_accidente.php?Id_Accidente="+arrayList.get(i).getId_Accidente();

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
