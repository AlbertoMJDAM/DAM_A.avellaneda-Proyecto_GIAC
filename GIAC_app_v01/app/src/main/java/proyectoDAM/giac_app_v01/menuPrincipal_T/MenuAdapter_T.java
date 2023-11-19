package proyectoDAM.giac_app_v01.menuPrincipal_T;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import proyectoDAM.giac_app_v01.R;

public class MenuAdapter_T extends RecyclerView.Adapter<MenuAdapter_T.ViewHolder> {
    MediaPlayer incidenciaTrab,accidenteTrab,clientesTrab,documentosTrab;
    int [] imagenes;
    private MediaPlayer mp;
    Context context;
    public MenuAdapter_T(int[] imagenes) {
        this.imagenes = imagenes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.formatoimag,parent,false));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
                        //Falta pulir el tema de los botones de sonidos//
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setBackgroundResource(imagenes[position]);
        switch (position){
            case 0: holder.textotitulo.setText(holder.textotitulo.getResources().getString(R.string.TituloINCASIG));
                    holder.textoinfo.setText(holder.textoinfo.getResources().getString(R.string.incidenAsig));
                    holder.audio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(clientesTrab.isPlaying())
                                clientesTrab.pause();
                            if(accidenteTrab.isPlaying())
                                accidenteTrab.pause();
                            if(documentosTrab.isPlaying())
                                documentosTrab.pause();
                            incidenciaTrab.start();
                        }
                    });
                    break;
            case 1:
                    holder.textotitulo.setText(holder.textotitulo.getResources().getString(R.string.TituloCLIASIG));
                    holder.textoinfo.setText(holder.textoinfo.getResources().getString(R.string.accidentesAsig));
                    holder.audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(incidenciaTrab.isPlaying())
                            incidenciaTrab.pause();
                        if(accidenteTrab.isPlaying())
                            accidenteTrab.pause();
                        if(documentosTrab.isPlaying())
                            documentosTrab.pause();
                        clientesTrab.start();
                    }
                    });
                    break;
            case 2: holder.textotitulo.setText(holder.textotitulo.getResources().getString(R.string.TituloACCTRAB));
                    holder.textoinfo.setText(holder.textoinfo.getResources().getString(R.string.accidenTrab));
                    holder.audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clientesTrab.isPlaying())
                        {
                            clientesTrab.pause();
                            clientesTrab.reset();
                        }
                        if(incidenciaTrab.isPlaying()){
                            incidenciaTrab.pause();
                            incidenciaTrab.reset();
                        }
                        if(documentosTrab.isPlaying()){
                            documentosTrab.pause();
                            documentosTrab.reset();
                        }
                        accidenteTrab.start();
                    }
                    });
                    break;
            case 3: holder.textotitulo.setText(holder.textotitulo.getResources().getString(R.string.TituloDOCTRAB));
                    holder.textoinfo.setText(holder.textoinfo.getResources().getString(R.string.archivosTrab));
                    holder.audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(accidenteTrab.isPlaying())
                            accidenteTrab.pause();
                        if(incidenciaTrab.isPlaying())
                            incidenciaTrab.pause();
                        if(clientesTrab.isPlaying())
                            clientesTrab.pause();
                        documentosTrab.start();
                    }
                    });
                    break;
        }

    }

    @Override
    public int getItemCount() {
        return imagenes.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,audio;
        TextView textotitulo;
        TextView textoinfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imagen);
            textotitulo = itemView.findViewById(R.id.titulo);
            textoinfo = itemView.findViewById(R.id.descripcion);
            audio = itemView.findViewById(R.id.audio);
            incidenciaTrab = MediaPlayer.create(itemView.getContext(),R.raw.incidencias_trab);
            accidenteTrab = MediaPlayer.create(itemView.getContext(),R.raw.accidente_trab);
            clientesTrab = MediaPlayer.create(itemView.getContext(),R.raw.clientes_trab);
            documentosTrab = MediaPlayer.create(itemView.getContext(),R.raw.documentos_trab);
        }
    }

}
