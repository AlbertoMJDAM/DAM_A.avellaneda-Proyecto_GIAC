package proyectoDAM.giac_app_v01.menuPrincipal_T.listadoVehiculos;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Vehiculos;

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

        TextView tvIdVehiculo = (TextView) view.findViewById(R.id.tvIdVehiculo);
        TextView tvPuertas = (TextView) view.findViewById(R.id.tvPuertas);
        TextView tvMarca = (TextView) view.findViewById(R.id.tvMarca);
        TextView tvModelo = (TextView) view.findViewById(R.id.tvModelo);
        TextView tvColor = (TextView) view.findViewById(R.id.tvColor);
        TextView tvMatricula = (TextView) view.findViewById(R.id.tvMatricula);
        ImageView ivFoto = (ImageView) view.findViewById(R.id.img);

        tvIdVehiculo.setText(arrayList.get(i).getId_Cliente());
        tvMarca.setText(arrayList.get(i).getMarca());
        tvModelo.setText(arrayList.get(i).getModelo());
        tvColor.setText(arrayList.get(i).getColor());
        tvMatricula.setText(arrayList.get(i).getMatricula());
        tvPuertas.setText(arrayList.get(i).getNum_Puertas());

        Uri imageMoto = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                view.getResources().getResourcePackageName(R.drawable.moto) + '/' +
                view.getResources().getResourceTypeName(R.drawable.moto) + '/' +
                String.valueOf(R.drawable.moto));
        Uri imageCamion = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                view.getResources().getResourcePackageName(R.drawable.camion) + '/' +
                view.getResources().getResourceTypeName(R.drawable.camion) + '/' +
                String.valueOf(R.drawable.camion));
        Uri imageCoche = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                view.getResources().getResourcePackageName(R.drawable.coche_no) + '/' +
                view.getResources().getResourceTypeName(R.drawable.coche_no) + '/' +
                String.valueOf(R.drawable.coche_no));
        Uri imageNodisp = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                view.getResources().getResourcePackageName(R.drawable.camera) + '/' +
                view.getResources().getResourceTypeName(R.drawable.camera) + '/' +
                String.valueOf(R.drawable.camera));

        String tipo =  arrayList.get(i).getTipo_Vehiculo();

        if(tipo.contains("MOTO")){
            ivFoto.setImageURI(imageMoto);
        }
        else if(tipo.contains("COCHE")){
            ivFoto.setImageURI(imageCoche);
        }
        else if(tipo.contains("OTROS")){
            ivFoto.setImageURI(imageCamion);
        }



        return view;
    }
}
