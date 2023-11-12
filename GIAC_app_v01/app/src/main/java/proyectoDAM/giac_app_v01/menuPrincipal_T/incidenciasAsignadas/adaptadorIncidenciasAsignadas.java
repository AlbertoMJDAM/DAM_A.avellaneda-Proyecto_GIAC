package proyectoDAM.giac_app_v01.menuPrincipal_T.incidenciasAsignadas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.Model.Incidencias;
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
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);


        tvIdIncidencia.setText(arrayList.get(i).getId_Incidencia());
        tvFechaIncidencia.setText(arrayList.get(i).getFecha_Incidencia());
        tvUbicacionIncidencia.setText(arrayList.get(i).getDireccion());
        tvDescripcionIncidencia.setText(arrayList.get(i).getDescripcion());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Incidencia numero: "+(i+1),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
