package proyectoDAM.giac_app_v01.menuPrincipal_T.documentosTrabajadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.Model.Accidentes;

public class adaptadorDocumentosTrabajador extends BaseAdapter {

    private Context context;
    private ArrayList<Accidentes> arrayList;
    private ArrayList<String> itemSelection;

    public adaptadorDocumentosTrabajador(Context context, ArrayList<Accidentes> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        this.itemSelection = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
