package proyectoDAM.giac_app_v01.menuPrincipal_T.listadoAccidentes;

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
import proyectoDAM.giac_app_v01.Model.Accidentes;

public class adaptadorListaAccidentes extends BaseAdapter {

    private Context context;
    private ArrayList<Accidentes> arrayList;
    private ArrayList<String> itemSelection;

    public ArrayList<String> getItemSelection() {
        return itemSelection;
    }

    public adaptadorListaAccidentes (Context context, ArrayList<Accidentes> arrayList){
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
            view = layoutInflater.inflate(R.layout.lista_accidentes_adapter,null);
        }

        TextView tvIdAccidente = (TextView) view.findViewById(R.id.tvIdAccidente);
        TextView tvFecha = (TextView) view.findViewById(R.id.tvFecha);
        TextView tvUbicacion = (TextView) view.findViewById(R.id.tvUbicacion);
        TextView tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

        tvIdAccidente.setText(arrayList.get(i).getId_Accidente());
        tvFecha.setText(arrayList.get(i).getFecha_Accidente());
        tvUbicacion.setText(arrayList.get(i).getUbicacion());
        tvDescripcion.setText(arrayList.get(i).getDescripcion());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    //linearLayout.setBackground(context.getDrawable(R.drawable.btn_contorno_blanco_l));
                    linearLayout.setBackground(context.getDrawable((R.drawable.btn_contorno_blanco_l)));
                    checkBox.setChecked(false);
                    if(itemSelection.contains(tvIdAccidente.getText().toString())){
                        itemSelection.remove(tvIdAccidente.getText().toString());
                    }
                }
                else{
                    if(!checkBox.isChecked()){
                        //linearLayout.setBackground(context.getDrawable(R.drawable.btn_contorno_blanco_l_select));
                        linearLayout.setBackground(context.getDrawable(R.drawable.btn_contorno_blanco_l_select));
                        checkBox.setChecked(true);
                        itemSelection.add(tvIdAccidente.getText().toString());
                    }
                }
            }
        });

        return view;
    }
}
