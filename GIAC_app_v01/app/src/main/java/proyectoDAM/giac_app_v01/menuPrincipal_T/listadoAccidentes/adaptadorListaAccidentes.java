package proyectoDAM.giac_app_v01.menuPrincipal_T.listadoAccidentes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.Model.Accidentes;

public class adaptadorListaAccidentes extends BaseAdapter {

    private Context context;
    private ArrayList<Accidentes> arrayList;

    public adaptadorListaAccidentes (Context context, ArrayList<Accidentes> arrayList){
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
            view = layoutInflater.inflate(R.layout.lista_accidentes_adapter,null);
        }

        TextView tvIdAccidente = (TextView) view.findViewById(R.id.tvIdAccidente);
        TextView tvFecha = (TextView) view.findViewById(R.id.tvFecha);
        TextView tvUbicacion = (TextView) view.findViewById(R.id.tvUbicacion);
        TextView tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);

        tvIdAccidente.setText(arrayList.get(i).getId_Accidente());
        tvFecha.setText(arrayList.get(i).getFecha_Accidente());
        tvUbicacion.setText(arrayList.get(i).getUbicacion());
        tvDescripcion.setText(arrayList.get(i).getDescripcion());

        return view;
    }
}
