package proyectoDAM.giac_app_v01.menuPrincipal_T.listadoIncidencias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Incidencias;

public class adaptadorListadoIncidencias extends BaseAdapter {

    // Atributos:
    private Context context;
    private ArrayList<Incidencias> arrayList;
    private ArrayList<String> itemSelection;

    public ArrayList<String> getItemSelection() {
        return itemSelection;
    }

    public adaptadorListadoIncidencias (Context context, ArrayList<Incidencias> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        this.itemSelection = new ArrayList<>();
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
            view = layoutInflater.inflate(R.layout.lista_incidencias_adapter, null);
        }

        TextView tvIdIncidencia = (TextView) view.findViewById(R.id.tvIdIncidencia);
        TextView tvFechaIncidencia = (TextView) view.findViewById(R.id.tvFechaIncidencia);
        TextView tvUbicacionIncidencia = (TextView) view.findViewById(R.id.tvUbicacionIncidencia);
        TextView tvDescripcionIncidencia = (TextView) view.findViewById(R.id.tvDescripcionIncidencia);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);


        tvIdIncidencia.setText(arrayList.get(i).getId_Incidencia());
        tvFechaIncidencia.setText(arrayList.get(i).getFecha_Incidencia());
        tvUbicacionIncidencia.setText(arrayList.get(i).getDireccion());
        tvDescripcionIncidencia.setText(arrayList.get(i).getDescripcion());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    linearLayout.setBackground(context.getDrawable(R.drawable.btn_contorno_blanco_l));
                    checkBox.setChecked(false);
                    if(itemSelection.contains(tvIdIncidencia.getText().toString())){
                        itemSelection.remove(tvIdIncidencia.getText().toString());
                    }
                }
                else{
                    if(!checkBox.isChecked()){
                        linearLayout.setBackground(context.getDrawable(R.drawable.btn_contorno_blanco_l_select));
                        checkBox.setChecked(true);
                        itemSelection.add(tvIdIncidencia.getText().toString());
                    }
                }
            }
        });




        return view;
    }

}
