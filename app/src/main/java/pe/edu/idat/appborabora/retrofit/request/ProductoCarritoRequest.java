package pe.edu.idat.appborabora.retrofit.request;

public class ProductoCarritoRequest {

    private int cantidad;
    private int productoId;

    public ProductoCarritoRequest(int cantidad, int productoId) {
        this.cantidad = cantidad;
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
}
