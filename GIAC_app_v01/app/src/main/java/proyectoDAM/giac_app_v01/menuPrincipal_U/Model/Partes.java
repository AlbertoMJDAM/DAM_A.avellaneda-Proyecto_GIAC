package proyectoDAM.giac_app_v01.menuPrincipal_U.Model;

import java.io.Serializable;

public class Partes implements Serializable {

    //Atributos
    public int id_Parte;
    public int cod_Incidencia;
    public int cod_Accidente;
    public int  usuario;
    public int  empleado;
    public String nombreEmpleado;
    public String apellidoEmpleado;
    public String emailEmpleado;
    public String telefonoEmpleado;
    public String fechaAlta;
    public String estado;
    public String fechaFin;


    public Partes() {
    }

    public Partes(int id_Parte, int cod_Incidencia, int cod_Accidente, int usuario, int empleado, String fechaAlta, String estado, String fechaFin) {
        this.id_Parte = id_Parte;
        this.cod_Incidencia = cod_Incidencia;
        this.cod_Accidente = cod_Accidente;
        this.usuario = usuario;
        this.empleado = empleado;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
        this.fechaFin = fechaFin;
    }

// Metodos getter y setter


    public int getId_Parte() {
        return id_Parte;
    }

    public void setId_Parte(int id_Parte) {
        this.id_Parte = id_Parte;
    }

    public int getCod_Incidencia() {
        return cod_Incidencia;
    }

    public void setCod_Incidencia(int cod_Incidencia) {
        this.cod_Incidencia = cod_Incidencia;
    }

    public int getCod_Accidente() {
        return cod_Accidente;
    }

    public void setCod_Accidente(int cod_Accidente) {
        this.cod_Accidente = cod_Accidente;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEmailEmpleado() {
        return emailEmpleado;
    }

    public void setEmailEmpleado(String emailEmpleado) {
        this.emailEmpleado = emailEmpleado;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    @Override
    public String toString() {
        return "Partes{" +
                "id_Parte=" + id_Parte +
                ", cod_Incidencia=" + cod_Incidencia +
                ", cod_Accidente=" + cod_Accidente +
                ", usuario=" + usuario +
                ", empleado=" + empleado +
                ", nombreEmpleado='" + nombreEmpleado + '\'' +
                ", apellidoEmpelado='" + apellidoEmpleado + '\'' +
                ", fechaAlta='" + fechaAlta + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                '}';
    }
}
