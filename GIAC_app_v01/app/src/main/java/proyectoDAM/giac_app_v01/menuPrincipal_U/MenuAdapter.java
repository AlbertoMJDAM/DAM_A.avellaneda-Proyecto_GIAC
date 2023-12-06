package proyectoDAM.giac_app_v01.menuPrincipal_U;

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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    MediaPlayer incidencia,accidente,asistencia,documentos,ayuda;
    int [] imagenes;
    private MediaPlayer mp;
    Context context;
    public MenuAdapter(int[] imagenes) {
        this.imagenes = imagenes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.formatoimag,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setBackgroundResource(imagenes[position]);
        switch (position){
            case 0: holder.textotitulo.setText(holder.textotitulo.getResources().getString(R.string.TituloINC));
                    holder.textoinfo.setText(holder.textoinfo.getResources().getString(R.string.incidencia));
                    holder.audio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(accidente.isPlaying()){
                                accidente.pause();
                                accidente.seekTo(0);
                            }
                            if(asistencia.isPlaying()){
                                asistencia.pause();
                                asistencia.seekTo(0);
                            }
                            if(documentos.isPlaying()){
                                documentos.pause();
                                documentos.seekTo(0);
                            }
                            if(ayuda.isPlaying()){
                                ayuda.pause();
                                ayuda.seekTo(0);
                            }
                            incidencia.start();
                        }
                    });
                    break;
            case 1:
                    holder.textotitulo.setText(holder.textotitulo.getResources().getString(R.string.TituloACC));
                    holder.textoinfo.setText(holder.textoinfo.getResources().getString(R.string.accidente));
                    holder.audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(asistencia.isPlaying()){
                            asistencia.pause();
                            asistencia.seekTo(0);
                        }
                        if(documentos.isPlaying()){
                            documentos.pause();
                            documentos.seekTo(0);
                        }
                        if(ayuda.isPlaying()){
                            ayuda.pause();
                            ayuda.seekTo(0);
                        }
                        if(incidencia.isPlaying()){
                            incidencia.pause();
                            incidencia.seekTo(0);
                        }
                        accidente.start();
                    }
                    });
                    break;
            case 2: holder.textotitulo.setText(holder.textotitulo.getResources().getString(R.string.TituloASI));
                    holder.textoinfo.setText(holder.textoinfo.getResources().getString(R.string.asitencia));
                    holder.audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(incidencia.isPlaying()){
                            incidencia.pause();
                            incidencia.seekTo(0);
                        }
                        if(accidente.isPlaying()){
                            accidente.pause();
                            accidente.seekTo(0);
                        }
                        if(documentos.isPlaying()){
                            documentos.pause();
                            documentos.seekTo(0);
                        }
                        if(ayuda.isPlaying()){
                            ayuda.pause();
                            ayuda.seekTo(0);
                        }
                        asistencia.start();
                    }
                });
                    break;
            case 3: holder.textotitulo.setText(holder.textotitulo.getResources().getString(R.string.TituloDOC));
                    holder.textoinfo.setText(holder.textoinfo.getResources().getString(R.string.archivos));
                    holder.audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(incidencia.isPlaying()){
                            incidencia.pause();
                            incidencia.seekTo(0);
                        }
                        if(accidente.isPlaying()){
                            accidente.pause();
                            accidente.seekTo(0);
                        }
                        if(asistencia.isPlaying()){
                            asistencia.pause();
                            asistencia.seekTo(0);
                        }
                        if(ayuda.isPlaying()){
                            ayuda.pause();
                            ayuda.seekTo(0);
                        }
                        documentos.start();
                    }
                });
                    break;
            case 4: holder.textotitulo.setText(holder.textotitulo.getResources().getString(R.string.TituloAYU));
                    holder.textoinfo.setText(holder.textoinfo.getResources().getString(R.string.ayuda));
                    holder.audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(incidencia.isPlaying()){
                            incidencia.pause();
                            incidencia.seekTo(0);
                        }
                        if(accidente.isPlaying()){
                            accidente.pause();
                            accidente.seekTo(0);
                        }
                        if(asistencia.isPlaying()){
                            asistencia.pause();
                            asistencia.seekTo(0);
                        }
                        if(documentos.isPlaying()){
                            documentos.pause();
                            documentos.seekTo(0);
                        }
                        ayuda.start();
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
            incidencia = MediaPlayer.create(itemView.getContext(),R.raw.incidencia);
            accidente = MediaPlayer.create(itemView.getContext(),R.raw.accidente);
            documentos = MediaPlayer.create(itemView.getContext(),R.raw.documentos);
            asistencia = MediaPlayer.create(itemView.getContext(),R.raw.asistencia);
            ayuda = MediaPlayer.create(itemView.getContext(),R.raw.ayuda);
        }
    }
}