package pe.edu.idat.appborabora.retrofit.request;

import java.time.LocalDate;
import java.util.List;

import pe.edu.idat.appborabora.adapter.CarritoAdapter;
import pe.edu.idat.appborabora.retrofit.response.ProductoCarrito;
import pe.edu.idat.appborabora.utils.ProductoCompra;

public class CompraRequest {


    private Double total;

    private Double igv;

    private Double subtotal;

    private String metodopago;

    private String fcompra;

    private Integer userId;

    private List<ProductoCompra> productos;


    public CompraRequest() {

    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getMetodopago() {
        return metodopago;
    }

    public void setMetodopago(String metodopago) {
        this.metodopago = metodopago;
    }

    public String getFcompra() {
        return fcompra;
    }

    public void setFcompra(String fcompra) {
        this.fcompra = fcompra;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<ProductoCompra> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCompra> productos) {
        this.productos = productos;
    }
}
