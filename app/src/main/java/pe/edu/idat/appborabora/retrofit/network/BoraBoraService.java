package pe.edu.idat.appborabora.retrofit.network;


import java.util.List;

import pe.edu.idat.appborabora.retrofit.request.LoginRequest;
import pe.edu.idat.appborabora.retrofit.request.PerfilRequest;
import pe.edu.idat.appborabora.retrofit.request.RegisterUserRequest;
import pe.edu.idat.appborabora.retrofit.request.UpdatePasswordRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import pe.edu.idat.appborabora.retrofit.response.CategoriaResponse;
import pe.edu.idat.appborabora.retrofit.response.CompraResponse;
import pe.edu.idat.appborabora.retrofit.response.HistorialComprasResponse;
import pe.edu.idat.appborabora.retrofit.response.PerfilResponse;
import pe.edu.idat.appborabora.retrofit.response.ProductoCompraResponse;
import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;
import pe.edu.idat.appborabora.retrofit.response.TopProductosResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BoraBoraService {

    //--USUARIO
    @POST("user/login")
    Call<PerfilResponse> login(@Body LoginRequest loginRequest);

    @POST("user/register")
    Call<ApiResponse> register(@Body RegisterUserRequest registerUserRequest);

    @POST("user/update-password")
    Call<ApiResponse> updatePassword(@Body UpdatePasswordRequest resetPasswordRequest);

    @PUT("user/update-user/{id}")
    Call<ApiResponse> updateUser(@Path("id") int id, @Body PerfilRequest perfilRequest);

    //-- HISTORIAL DE COMPRAS SEGUN USER ID
    @GET("compras/user/{userId}")
    Call<List<HistorialComprasResponse>> getComprasUser(@Path("userId") int userId);

    //-- LISTAR TODAS LAS CATEGORIAS
    @GET("categorias/listar")
    Call<List<CategoriaResponse>> listarCategoria();

    //-- BUSCAR PRODUCTOS POR CATEGORIA
    @GET("productos/categoria/{categoriaId}")
    Call<List<ProductoResponse>> findByCategoriaId(@Path("categoriaId") int categoriaId);

    //-- TOP 6 PRODUCTOS MAS VENDIDOS
    @GET("topProductos")
    Call<List<TopProductosResponse>> topProductos();

    //-- OBTENER LOS PRODUCTOS DE LA COMPRA DE UN USUARIO
    @GET("compras/productos/{compraId}")
    Call<List<ProductoCompraResponse>> getCompraProductos(@Path("compraId") int compraId);

    @GET("compras/info/{compraId}")
    Call<CompraResponse> getInfoCompra(@Path("compraId") int compraId);
}
