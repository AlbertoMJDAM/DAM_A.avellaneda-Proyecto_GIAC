package proyectoDAM.giac_app_v01.menuPrincipal_T.listadoUsuarios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Usuarios;

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

        TextView tvIdUsuario = (TextView) view.findViewById(R.id.tvIdUsuario);
        TextView tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        TextView tvDNI = (TextView) view.findViewById(R.id.tvDNI);
        TextView tvLicencia = (TextView) view.findViewById(R.id.tvLicencia);
        TextView tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        TextView tvApellidos = (TextView) view.findViewById(R.id.tvApellidos);

        tvIdUsuario.setText(arrayList.get(i).getId());
        tvNombre.setText(arrayList.get(i).getNombre());
        tvDNI.setText(arrayList.get(i).getDni());
        tvLicencia.setText(arrayList.get(i).getLicencia());
        tvEmail.setText(arrayList.get(i).getEmail());
        tvApellidos.setText(arrayList.get(i).getpApellido()+" "+arrayList.get(i).getsApellido());

        return view;
    }
}
