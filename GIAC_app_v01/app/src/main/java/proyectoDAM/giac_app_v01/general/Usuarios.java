package proyectoDAM.giac_app_v01.general;

public class Usuarios {

    private String id, nombre, dni, licencia, email;

    public Usuarios (String id, String nombre, String dni, String licencia, String email){
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.licencia = licencia;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
