package proyectoDAM.giac_app_v01.menuPrincipal_T.localizarAccidentes;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import proyectoDAM.giac_app_v01.R;

public class adaptadorInfoMapa implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater;

    public adaptadorInfoMapa(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        View view = inflater.inflate(R.layout.info_mapa_adapter, null);
        String[] datos = (String[]) marker.getTag();
        if(datos[0].equalsIgnoreCase("I")){
            ((TextView)view.findViewById(R.id.tvIDIncAcc)).setText(datos[1]);
            ((TextView)view.findViewById(R.id.tvIDUsuario)).setText(datos[2]);
            ((TextView)view.findViewById(R.id.tvVehiculo)).setText(datos[3]);
            ((ImageView)view.findViewById(R.id.ivIncAcc)).setImageResource(R.drawable.ico_incidencia_mapa);
        }else{
            ((TextView)view.findViewById(R.id.tvIDIncAcc)).setText(datos[1]);
            ((TextView)view.findViewById(R.id.tvIDUsuario)).setText(datos[2]);
            ((TextView)view.findViewById(R.id.tvVehiculo)).setText(datos[3]);
            ((ImageView)view.findViewById(R.id.ivIncAcc)).setImageResource(R.drawable.ico_accidente_mapa);
        }

        return view;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}
