package pe.edu.idat.appborabora.retrofit.response;

public class LoginResponse {
    private String message;
    private String status;
    private Integer userId; 

    public LoginResponse(String message, String status, Integer userId) {
        this.message = message;
        this.status = status;
        this.userId = userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
