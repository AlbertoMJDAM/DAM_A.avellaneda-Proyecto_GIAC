package proyectoDAM.giac_app_v01.menuPrincipal_U.Asistencia;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import proyectoDAM.giac_app_v01.R;

public class LoadingDialogBar {
    Context context;
    Dialog dialog;

    public LoadingDialogBar(Context context) {
        this.context = context;
    }

    public void MuestraDialog(String texto){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView mensajeDialog = dialog.findViewById(R.id.tvDialogo);
        mensajeDialog.setText(texto);
        dialog.create();
        dialog.show();
    }

    public void OcultaDialog(){
        dialog.dismiss();
    }
}
