package pe.edu.idat.appborabora.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import pe.edu.idat.appborabora.retrofit.network.BoraBoraClient;
import pe.edu.idat.appborabora.retrofit.network.BoraBoraService;
import pe.edu.idat.appborabora.retrofit.request.LoginRequest;
import pe.edu.idat.appborabora.retrofit.request.PerfilRequest;
import pe.edu.idat.appborabora.retrofit.request.ProductoCarritoRequest;
import pe.edu.idat.appborabora.retrofit.request.RegisterUserRequest;
import pe.edu.idat.appborabora.retrofit.request.UpdatePasswordRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import pe.edu.idat.appborabora.retrofit.response.CategoriaResponse;
import pe.edu.idat.appborabora.retrofit.response.HistorialComprasResponse;
import pe.edu.idat.appborabora.retrofit.response.PerfilResponse;
import pe.edu.idat.appborabora.retrofit.response.ProductoCarritoResponse;
import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;
import pe.edu.idat.appborabora.retrofit.response.TopProductosResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends AndroidViewModel {
    public MutableLiveData<ApiResponse> registerResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResponse> updatePasswordResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<PerfilResponse> perfilResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<HistorialComprasResponse>> compraResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<CategoriaResponse>> categoriaResponsemMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<TopProductosResponse>> productoResponseMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<List<ProductoCarritoResponse>> carritopResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ApiResponse> createCarritoResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResponse> agregarProductoResponseMutableLiveData = new MutableLiveData<>();

    private BoraBoraClient client = new BoraBoraClient();
    private BoraBoraService service = client.getInstance();
    public MutableLiveData<ApiResponse> updatePerfilResponseLiveData = new MutableLiveData<>();

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
                        List<HistorialComprasResponse> errorResponse = new Gson().fromJson(response.errorBody().string(), new TypeToken<List<HistorialComprasResponse>>() {
                        }.getType());
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
    public LiveData<List<ProductoResponse>> getProductosByCategoriaId(int categoriaId) {
        MutableLiveData<List<ProductoResponse>> data = new MutableLiveData<>();

        Call<List<ProductoResponse>> call = service.findByCategoriaId(categoriaId);
        call.enqueue(new Callback<List<ProductoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductoResponse>> call, Response<List<ProductoResponse>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    data.setValue(response.body());
                } else {
                    // handle the error response here
                }
            }

            @Override
            public void onFailure(Call<List<ProductoResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return data;
    }


    public LiveData<List<TopProductosResponse>> listarTopProductos() {
        new BoraBoraClient().getInstance().topProductos().enqueue(new Callback<List<TopProductosResponse>>() {
            @Override
            public void onResponse(Call<List<TopProductosResponse>> call, Response<List<TopProductosResponse>> response) {
                if (response.isSuccessful()) {
                    productoResponseMutableLiveData.setValue(response.body());
                } else {
                    try {
                        String errorResponse = response.errorBody().string();
                        if ("No se encontraron productos".equals(errorResponse)) {
                            productoResponseMutableLiveData.setValue(null);
                        } else {
                            System.out.println("Error: " + errorResponse);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TopProductosResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return productoResponseMutableLiveData;
    }

    public LiveData<List<ProductoCarritoResponse>> ListCarritoProd(int userId) {
        MutableLiveData<List<ProductoCarritoResponse>> data = new MutableLiveData<>();

        Call<List<ProductoCarritoResponse>> call = service.getCarritoProductos(userId);
        call.enqueue(new Callback<List<ProductoCarritoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductoCarritoResponse>> call, Response<List<ProductoCarritoResponse>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    data.setValue(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<ProductoCarritoResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return data;
    }

    public LiveData<List<ProductoCarritoResponse>> getCarritoProductos (int userId) {

        new BoraBoraClient().getInstance().getCarritoProductos(userId).enqueue(new Callback<List<ProductoCarritoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductoCarritoResponse>> call, Response<List<ProductoCarritoResponse>> response) {
                if (response.isSuccessful()) {
                    carritopResponseMutableLiveData.setValue(response.body());
                } else {
                    try {
                        List<ProductoCarritoResponse> errorResponse = new Gson().fromJson(response.errorBody().string(), new TypeToken<List<ProductoCarritoResponse>>() {
                        }.getType());
                        if (!errorResponse.isEmpty() && "No se encontraron compras para el usuario".equals(errorResponse.get(0).getMessage())) {
                            // maneja el caso en que no se encontraron compras
                            carritopResponseMutableLiveData.setValue(null);
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
            public void onFailure(Call<List<ProductoCarritoResponse>> call, Throwable t) {

                t.printStackTrace();
            }

        }); return carritopResponseMutableLiveData;

    }


    public void createCarrito(int userId) {
        new BoraBoraClient().getInstance().createCarrito(userId).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    createCarritoResponseMutableLiveData.setValue(response.body());
                } else {
                    try {
                        ApiResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ApiResponse.class);
                        createCarritoResponseMutableLiveData.setValue(errorResponse);
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

    public void agregarProducto(int carritoId, ProductoCarritoRequest productoCarritoRequest) {
        new BoraBoraClient().getInstance().agregarProducto(carritoId, productoCarritoRequest).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    agregarProductoResponseMutableLiveData.setValue(response.body());
                } else {
                    try {
                        ApiResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ApiResponse.class);
                        agregarProductoResponseMutableLiveData.setValue(errorResponse);
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
}








