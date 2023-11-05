package proyectoDAM.giac_app_v01.menuPrincipal_T;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.general.Usuarios;
import proyectoDAM.giac_app_v01.general.Vehiculos;

public class adaptadorListaUsuarios extends BaseAdapter {

    private Context context;
    private ArrayList<Usuarios> arrayList;

    public adaptadorListaUsuarios(Context context, ArrayList<Usuarios> arrayList) {
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
            view = layoutInflater.inflate(R.layout.lista_usuarios_adapter,null);
        }

        TextView tvid = (TextView) view.findViewById(R.id.listID);
        TextView tvNombre = (TextView) view.findViewById(R.id.listNombre);
        TextView tvDNI = (TextView) view.findViewById(R.id.listDNI);
        TextView tvLicencia = (TextView) view.findViewById(R.id.listLicencia);
        TextView tvEmail = (TextView) view.findViewById(R.id.listEmail);

        tvid.setText(arrayList.get(i).getId());
        tvNombre.setText(arrayList.get(i).getNombre());
        tvDNI.setText(arrayList.get(i).getDni());
        tvLicencia.setText(arrayList.get(i).getLicencia());
        tvEmail.setText(arrayList.get(i).getEmail());

        return view;
    }
}
