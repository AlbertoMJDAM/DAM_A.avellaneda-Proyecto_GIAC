package proyectoDAM.giac_app_v01.menuPrincipal_T;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.general.Vehiculos;

public class adaptadorListaVehiculos extends BaseAdapter {

    private Context context;
    private ArrayList<Vehiculos> arrayList;

    public adaptadorListaVehiculos(Context context, ArrayList<Vehiculos> arrayList){
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
            view = layoutInflater.inflate(R.layout.lista_vehiculos_adapter,null);
        }

        TextView tvid = (TextView) view.findViewById(R.id.listID);
        TextView tvmarca = (TextView) view.findViewById(R.id.listMarca);
        TextView tvmodelo = (TextView) view.findViewById(R.id.listModelo);
        TextView tvcolor = (TextView) view.findViewById(R.id.listColor);
        TextView tvmatricula = (TextView) view.findViewById(R.id.listMatricula);

        tvid.setText(arrayList.get(i).getId());
        tvmarca.setText(arrayList.get(i).getMarca());
        tvmodelo.setText(arrayList.get(i).getModelo());
        tvcolor.setText(arrayList.get(i).getColor());
        tvmatricula.setText(arrayList.get(i).getMatricula());

        return view;
    }
}
