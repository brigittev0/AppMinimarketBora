package pe.edu.idat.appborabora.retrofit.response;

public class PerfilResponse {

    private Integer userId;
    private String nombres;
    private String apellidos;
    private Integer docIdentidad;
    private Integer telefono;
    private String email;

    private String message;
    private String status;

    public PerfilResponse(Integer userId, String nombres, String apellidos, Integer docIdentidad, Integer telefono, String email, String message, String status) {
        this.userId = userId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.docIdentidad = docIdentidad;
        this.telefono = telefono;
        this.email = email;
        this.message = message;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
