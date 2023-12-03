package pe.edu.idat.appborabora.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;

import pe.edu.idat.appborabora.retrofit.network.BoraBoraClient;
import pe.edu.idat.appborabora.retrofit.request.LoginRequest;
import pe.edu.idat.appborabora.retrofit.request.UpdatePasswordRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import pe.edu.idat.appborabora.retrofit.request.RegisterUserRequest;
import pe.edu.idat.appborabora.retrofit.response.PerfilResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends AndroidViewModel {
    public MutableLiveData<ApiResponse> registerResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResponse> updatePasswordResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<PerfilResponse> perfilResponseMutableLiveData = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
    }
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
}
