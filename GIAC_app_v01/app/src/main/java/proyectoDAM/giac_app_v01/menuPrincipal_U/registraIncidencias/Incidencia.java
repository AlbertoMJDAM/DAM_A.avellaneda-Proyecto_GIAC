package proyectoDAM.giac_app_v01.menuPrincipal_U.registraIncidencias;

import android.net.Uri;

import java.io.Serializable;

public class Incidencia implements Serializable{
    String idIncidencia;
    String idUsuario;
    String nombre;
    String Papellido;
    String Sapellido;
    String DNI;
    String email;
    String telefono;
    String Matricula;
    String idEmpleado = "0";
    String fSuceso;
    String dirSuceso;
    String latSuceso;
    String lonSuceso;
    String descripcion;
    Uri img1;
    Uri img2;
    Uri img3;
    Uri img4;

    public Incidencia(){

    }

    public Incidencia(String idUsuario, String idIncidencia, String nombre, String papellido, String sapellido, String DNI, String email, String telefono, String matricula, String idEmpleado, String fSuceso, String dirSuceso, String latSuceso, String lonSuceso) {
        this.idIncidencia = idIncidencia;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        Papellido = papellido;
        Sapellido = sapellido;
        this.DNI = DNI;
        this.email = email;
        this.telefono = telefono;
        Matricula = matricula;
        this.idEmpleado = idEmpleado;
        this.fSuceso = fSuceso;
        this.dirSuceso = dirSuceso;
        this.latSuceso = latSuceso;
        this.lonSuceso = lonSuceso;
        this.descripcion = "";
        this.img1=null;
        this.img2=null;
        this.img3=null;
        this.img4=null;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(String idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPapellido() {
        return Papellido;
    }

    public void setPapellido(String papellido) {
        Papellido = papellido;
    }

    public String getSapellido() {
        return Sapellido;
    }

    public void setSapellido(String sapellido) {
        Sapellido = sapellido;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getfSuceso() {
        return fSuceso;
    }

    public void setfSuceso(String fSuceso) {
        this.fSuceso = fSuceso;
    }

    public String getDirSuceso() {
        return dirSuceso;
    }

    public void setDirSuceso(String dirSuceso) {
        this.dirSuceso = dirSuceso;
    }

    public String getLatSuceso() {
        return latSuceso;
    }

    public void setLatSuceso(String latSuceso) {
        this.latSuceso = latSuceso;
    }

    public String getLonSuceso() {
        return lonSuceso;
    }

    public void setLonSuceso(String lonSuceso) {
        this.lonSuceso = lonSuceso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Uri getImg1() {
        return img1;
    }

    public void setImg1(Uri img1) {
        this.img1 = img1;
    }

    public Uri getImg2() {
        return img2;
    }

    public void setImg2(Uri img2) {
        this.img2 = img2;
    }

    public Uri getImg3() {
        return img3;
    }

    public void setImg3(Uri img3) {
        this.img3 = img3;
    }

    public Uri getImg4() {
        return img4;
    }

    public void setImg4(Uri img4) {
        this.img4 = img4;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "idUsuario='" + idUsuario + '\'' +
                ", idIncidencia='" + idIncidencia + '\'' +
                ", nombre='" + nombre + '\'' +
                ", Papellido='" + Papellido + '\'' +
                ", Sapellido='" + Sapellido + '\'' +
                ", DNI='" + DNI + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", Matricula='" + Matricula + '\'' +
                ", idEmpleado='" + idEmpleado + '\'' +
                ", fSuceso='" + fSuceso + '\'' +
                ", dirSuceso='" + dirSuceso + '\'' +
                ", latSuceso='" + latSuceso + '\'' +
                ", lonSuceso='" + lonSuceso + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", img1=" + img1 +
                ", img2=" + img2 +
                ", img3=" + img3 +
                ", img4=" + img4 +
                '}';
    }
}
