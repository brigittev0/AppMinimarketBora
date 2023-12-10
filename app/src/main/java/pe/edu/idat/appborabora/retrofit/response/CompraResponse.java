package pe.edu.idat.appborabora.retrofit.response;

public class CompraResponse {
    private String message;
    private String status;
    private int id;
    private double total;
    private double igv;
    private double subtotal;
    private String metodopago;
    private int userId;
    private String fcompra;

    public CompraResponse(String message, String status, int id, double total, double igv, double subtotal, String metodopago, int userId, String fcompra) {
        this.message = message;
        this.status = status;
        this.id = id;
        this.total = total;
        this.igv = igv;
        this.subtotal = subtotal;
        this.metodopago = metodopago;
        this.userId = userId;
        this.fcompra = fcompra;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getMetodopago() {
        return metodopago;
    }

    public void setMetodopago(String metodopago) {
        this.metodopago = metodopago;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFcompra() {
        return fcompra;
    }

    public void setFcompra(String fcompra) {
        this.fcompra = fcompra;
    }
}
