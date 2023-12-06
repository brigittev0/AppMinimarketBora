package pe.edu.idat.appborabora.retrofit.request;

public class PerfilRequest {
    private String nombres;
    private String apellidos;
    private Integer docIdentidad;
    private Integer telefono;
    private String email;

    public PerfilRequest(String nombres, String apellidos, Integer docIdentidad, Integer telefono, String email) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.docIdentidad = docIdentidad;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getDocIdentidad() {
        return docIdentidad;
    }

    public void setDocIdentidad(Integer docIdentidad) {
        this.docIdentidad = docIdentidad;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
