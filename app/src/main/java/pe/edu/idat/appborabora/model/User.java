package pe.edu.idat.appborabora.model;

public class User {
    private String nombres;
    private String apellidos;
    private Integer docIdentidad;
    private Integer telefono;
    private String email;
    private String contrasena;

    public User(String nombres, String apellidos, Integer docIdentidad, Integer telefono, String email, String contrasena) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.docIdentidad = docIdentidad;
        this.telefono = telefono;
        this.email = email;
        this.contrasena = contrasena;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
