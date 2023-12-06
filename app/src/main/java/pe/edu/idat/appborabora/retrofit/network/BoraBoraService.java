package pe.edu.idat.appborabora.retrofit.network;


import java.util.List;

import pe.edu.idat.appborabora.retrofit.request.LoginRequest;
import pe.edu.idat.appborabora.retrofit.request.RegisterUserRequest;
import pe.edu.idat.appborabora.retrofit.request.UpdatePasswordRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import pe.edu.idat.appborabora.retrofit.response.CategoriaResponse;
import pe.edu.idat.appborabora.retrofit.response.HistorialComprasResponse;
import pe.edu.idat.appborabora.retrofit.response.PerfilResponse;
import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BoraBoraService {

    //--USUARIO
    @POST("user/login")
    Call<PerfilResponse> login(@Body LoginRequest loginRequest);

    @POST("user/register")
    Call<ApiResponse> register(@Body RegisterUserRequest registerUserRequest);

    @POST("user/update-password")
    Call<ApiResponse> updatePassword(@Body UpdatePasswordRequest resetPasswordRequest);

    //--COMPRAS
    @GET("compras/user/{userId}")
    Call<List<HistorialComprasResponse>> getComprasUser(@Path("userId") int userId);

    @GET("categorias/listar")
    Call<List<CategoriaResponse>> listarCategoria();

    @GET("productos/categoria/{categoriaId}")
    Call<List<ProductoResponse>> findByCategoriaId(@Path("categoriaId") int categoriaId);
}
