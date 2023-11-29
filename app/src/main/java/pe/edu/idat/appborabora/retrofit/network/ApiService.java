package pe.edu.idat.appborabora.retrofit.network;


import pe.edu.idat.appborabora.retrofit.request.LoginRequest;
import pe.edu.idat.appborabora.retrofit.request.ResetPasswordRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import pe.edu.idat.appborabora.retrofit.response.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<ApiResponse> login(@Body LoginRequest loginRequest);

    @POST("register")
    Call<ApiResponse> register(@Body User user);

    @POST("update-password")
    Call<ApiResponse> updatePassword(@Body ResetPasswordRequest resetPasswordRequest);

}
