package proyectoDAM.giac_app_v01.menuPrincipal_U.registraAccidentes;

import android.net.Uri;

import java.io.Serializable;

public class Accidente implements Serializable {
    String idAccidente;
    String idUsuario;
    String nombre;
    String Papellido;
    String Sapellido;
    String DNI;
    String email;
    String telefono;
    String empleado = "0";
    String vehiculoUsuario = "";
    String vehiculoImplicadoUno = "";
    String nombreImplicadoUno = "";
    String contactoImplicadoUno = "";
    String vehiculoImplicadoDos = "";
    String nombreImplicadoDos = "";
    String contactoImplicadoDos = "";
    String ubicacion = "";
    String descripcion = "";
    String latSuceso = "";
    String lonSuceso = "";
    String fSuceso = "";
    Uri img1;
    Uri img2;
    Uri img3;
    Uri img4;
    Uri img5;
    Uri img6;

    public Accidente() {

    }


    public Accidente(String idAccidente, String idUsuario, String nombre, String papellido, String sapellido, String DNI, String email, String telefono, String empleado, String vehiculoUsuario, String vehiculoImplicadoUno, String vehiculoImplicadoDos, String ubicacion, String descripcion, String latSuceso, String lonSuceso, String fSuceso) {
        this.idAccidente = idAccidente;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.Papellido = papellido;
        this.Sapellido = sapellido;
        this.DNI = DNI;
        this.email = email;
        this.telefono = telefono;
        this.empleado = empleado;
        this.vehiculoUsuario = vehiculoUsuario;
        this.vehiculoImplicadoUno = null;
        this.nombreImplicadoUno = null;
        this.contactoImplicadoUno = null;
        this.vehiculoImplicadoDos = null;
        this.nombreImplicadoDos = null;
        this.contactoImplicadoDos = null;
        this.ubicacion = ubicacion;
        this.descripcion = "";
        this.latSuceso = latSuceso;
        this.lonSuceso = lonSuceso;
        this.fSuceso = fSuceso;
        this.img1 = null;
        this.img2 = null;
        this.img3 = null;
        this.img4 = null;
        this.img5 = null;
        this.img6 = null;
    }

    public String getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(String idAccidente) {
        this.idAccidente = idAccidente;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getVehiculoUsuario() {
        return vehiculoUsuario;
    }

    public void setVehiculoUsuario(String vehiculoUsuario) {
        this.vehiculoUsuario = vehiculoUsuario;
    }

    public String getVehiculoImplicadoUno() {
        return vehiculoImplicadoUno;
    }

    public void setVehiculoImplicadoUno(String vehiculoImplicadoUno) {
        this.vehiculoImplicadoUno = vehiculoImplicadoUno;
    }

    public String getNombreImplicadoUno() {
        return nombreImplicadoUno;
    }

    public void setNombreImplicadoUno(String nombreImplicadoUno) {
        this.nombreImplicadoUno = nombreImplicadoUno;
    }

    public String getContactoImplicadoUno() {
        return contactoImplicadoUno;
    }

    public void setContactoImplicadoUno(String contactoImplicadoUno) {
        this.contactoImplicadoUno = contactoImplicadoUno;
    }

    public String getVehiculoImplicadoDos() {
        return vehiculoImplicadoDos;
    }

    public void setVehiculoImplicadoDos(String vehiculoImplicadoDos) {
        this.vehiculoImplicadoDos = vehiculoImplicadoDos;
    }

    public String getNombreImplicadoDos() {
        return nombreImplicadoDos;
    }

    public void setNombreImplicadoDos(String nombreImplicadoDos) {
        this.nombreImplicadoDos = nombreImplicadoDos;
    }

    public String getContactoImplicadoDos() {
        return contactoImplicadoDos;
    }

    public void setContactoImplicadoDos(String contactoImplicadoDos) {
        this.contactoImplicadoDos = contactoImplicadoDos;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getfSuceso() {
        return fSuceso;
    }

    public void setfSuceso(String fSuceso) {
        this.fSuceso = fSuceso;
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

    public Uri getImg5() {
        return img5;
    }

    public void setImg5(Uri img5) {
        this.img5 = img5;
    }

    public Uri getImg6() {
        return img6;
    }

    public void setImg6(Uri img6) {
        this.img6 = img6;
    }

    @Override
    public String toString() {
        return "Accidente{" +
                "idAccidente='" + idAccidente + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", Papellido='" + Papellido + '\'' +
                ", Sapellido='" + Sapellido + '\'' +
                ", DNI='" + DNI + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", empleado='" + empleado + '\'' +
                ", vehiculoUsuario='" + vehiculoUsuario + '\'' +
                ", vehiculoImplicadoUno='" + vehiculoImplicadoUno + '\'' +
                ", nombreImplicadoUno='" + nombreImplicadoUno + '\'' +
                ", contactoImplicadoUno='" + contactoImplicadoUno + '\'' +
                ", vehiculoImplicadoDos='" + vehiculoImplicadoDos + '\'' +
                ", nombreImplicadoDos='" + nombreImplicadoDos + '\'' +
                ", contactoImplicadoDos='" + contactoImplicadoDos + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", latSuceso='" + latSuceso + '\'' +
                ", lonSuceso='" + lonSuceso + '\'' +
                ", fSuceso='" + fSuceso + '\'' +
                '}';
    }
}