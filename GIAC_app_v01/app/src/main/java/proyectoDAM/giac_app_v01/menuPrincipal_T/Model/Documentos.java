package proyectoDAM.giac_app_v01.menuPrincipal_T.Model;

public class Documentos {

    private String tipo, nombre, direccion;

    public Documentos(String tipo, String nombre, String direccion){
        this.tipo = tipo;
        this.nombre = nombre;
        this.direccion = direccion;
    }
    public Documentos(){}

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
