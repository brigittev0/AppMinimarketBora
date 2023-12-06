package pe.edu.idat.appborabora.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;

import pe.edu.idat.appborabora.retrofit.network.BoraBoraClient;
import pe.edu.idat.appborabora.retrofit.network.BoraBoraService;
import pe.edu.idat.appborabora.retrofit.request.LoginRequest;
import pe.edu.idat.appborabora.retrofit.request.PerfilRequest;
import pe.edu.idat.appborabora.retrofit.request.UpdatePasswordRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import pe.edu.idat.appborabora.retrofit.request.RegisterUserRequest;
import pe.edu.idat.appborabora.retrofit.response.CategoriaResponse;
import pe.edu.idat.appborabora.retrofit.response.HistorialComprasResponse;
import pe.edu.idat.appborabora.retrofit.response.PerfilResponse;
import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthViewModel extends AndroidViewModel {
    public MutableLiveData<ApiResponse> registerResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResponse> updatePasswordResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<PerfilResponse> perfilResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<HistorialComprasResponse>> compraResponseMutableLiveData = new MutableLiveData<>();
<<<<<<< HEAD
    public MutableLiveData<List<CategoriaResponse>> categoriaResponsemMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductoResponse>> productosLiveData = new MutableLiveData<>();

=======
    public MutableLiveData<ApiResponse> updatePerfilResponseLiveData = new MutableLiveData<>();
>>>>>>> 9f96cc11109642936ae670bd67e2dbbbbd78d798
    public AuthViewModel(@NonNull Application application) {
        super(application);
    }

    //--USUARIO
    public void login(LoginRequest loginRequest) {
        new BoraBoraClient().getInstance().login(loginRequest).enqueue(new Callback<PerfilResponse>() {
            @Override
            public void onResponse(Call<PerfilResponse> call, Response<PerfilResponse> response) {
                if (response.isSuccessful()) {
                    perfilResponseMutableLiveData.setValue(response.body());
                } else {
                    try {
                        PerfilResponse errorResponse = new Gson().fromJson(response.errorBody().string(), PerfilResponse.class);
                        perfilResponseMutableLiveData.setValue(errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PerfilResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void registerUser(RegisterUserRequest registerUserRequest) {
        new BoraBoraClient().getInstance().register(registerUserRequest).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    registerResponseMutableLiveData.setValue(response.body());
                } else {
                    try {
                        ApiResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ApiResponse.class);
                        registerResponseMutableLiveData.setValue(errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        new BoraBoraClient().getInstance().updatePassword(updatePasswordRequest).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    updatePasswordResponseMutableLiveData.setValue(response.body());
                } else {
                    try {
                        ApiResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ApiResponse.class);
                        updatePasswordResponseMutableLiveData.setValue(errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updatePerfil(int userId, PerfilRequest perfilRequest) {
        new BoraBoraClient().getInstance().updateUser(userId, perfilRequest).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    updatePerfilResponseLiveData.setValue(response.body());
                } else {
                    try {
                        ApiResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ApiResponse.class);
                        updatePerfilResponseLiveData.setValue(errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<ApiResponse> observeUpdatePerfilResponse() {
        return updatePerfilResponseLiveData;
    }

    //--COMPRA
    public LiveData<List<HistorialComprasResponse>> getComprasUser(int userId) {
        new BoraBoraClient().getInstance().getComprasUser(userId).enqueue(new Callback<List<HistorialComprasResponse>>() {
            @Override
            public void onResponse(Call<List<HistorialComprasResponse>> call, Response<List<HistorialComprasResponse>> response) {
                if (response.isSuccessful()) {
                    compraResponseMutableLiveData.setValue(response.body());
                } else {
                    try {
                        List<HistorialComprasResponse> errorResponse = new Gson().fromJson(response.errorBody().string(), new TypeToken<List<HistorialComprasResponse>>(){}.getType());
                        if (!errorResponse.isEmpty() && "No se encontraron compras para el usuario".equals(errorResponse.get(0).getMessage())) {
                            // maneja el caso en que no se encontraron compras
                            compraResponseMutableLiveData.setValue(null);
                        } else {
                            // maneja otros errores
                            System.out.println("Error: " + errorResponse.get(0).getMessage());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HistorialComprasResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return compraResponseMutableLiveData;
    }

    //categoria
    public LiveData<List<CategoriaResponse>> listarCategoria() {
        new BoraBoraClient().getInstance().listarCategoria().enqueue(new Callback<List<CategoriaResponse>>() {
            @Override
            public void onResponse(Call<List<CategoriaResponse>> call, Response<List<CategoriaResponse>> response) {
                if (response.isSuccessful()) {
                    categoriaResponsemMutableLiveData.setValue(response.body());
                } else {
                    try {
                        String errorResponse = response.errorBody().string();
                        if ("No se encontraron categorias para el usuario".equals(errorResponse)) {
                            categoriaResponsemMutableLiveData.setValue(null);
                        } else {
                            System.out.println("Error: " + errorResponse);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CategoriaResponse>> call, Throwable t) {
                t.printStackTrace();
            }
    });
        return categoriaResponsemMutableLiveData;
    }
    //PRODUCTO POR CATEGORIA
    public LiveData<List<ProductoResponse>> getProductosLiveData() {
        return productosLiveData;
    }
/*
    public void loadProductosByCategoriaId(int categoriaId) {
        // Aquí haces la solicitud HTTP a tu endpoint
        // Esto es solo un ejemplo y puede que necesites ajustarlo para que funcione con tu código actual
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://tu-servidor.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BoraBoraService service = retrofit.create(BoraBoraService.class);
        Call<List<ProductoResponse>> call = service.findByCategoriaId(categoriaId);
        call.enqueue(new Callback<List<ProductoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductoResponse>> call, Response<List<ProductoResponse>> response) {
                if (response.isSuccessful()) {
                    productosLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProductoResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }*/

}
