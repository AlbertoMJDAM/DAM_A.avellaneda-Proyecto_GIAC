package proyectoDAM.giac_app_v01.menuPrincipal_T.accidentesAsignados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.Model.Accidentes;
import proyectoDAM.giac_app_v01.Model.Incidencias;
import proyectoDAM.giac_app_v01.R;

public class adaptadorAccidentesAsignados extends BaseAdapter {

    // Atributos:
    private Context context;
    private ArrayList<Accidentes> arrayList;
    private ArrayList<String> itemSelection;

    public ArrayList<String> getItemSelection() {
        return itemSelection;
    }

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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Incidencia numero: "+(i+1),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
