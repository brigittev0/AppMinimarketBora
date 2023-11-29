package pe.edu.idat.appborabora.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import pe.edu.idat.appborabora.retrofit.network.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.Gson;

import java.io.IOException;
import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.ActivityLoginBinding;
import pe.edu.idat.appborabora.retrofit.network.ApiService;
import pe.edu.idat.appborabora.retrofit.network.UserClient;
import pe.edu.idat.appborabora.retrofit.request.LoginRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvolvidarcontrasena.setOnClickListener(this);
        binding.btniniciarsesion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvolvidarcontrasena) {
            setearControles();
            startActivity(new Intent(LoginActivity.this, NuevacontraActivity.class));
            finish();  // Cierra la actividad
        } else if (v.getId() == R.id.btniniciarsesion) {
            iniciarSesion();
        }
    }

    private void iniciarSesion(){
        if(validarFormulario()){

            String email = binding.tEmail.getText().toString();
            String contrasena = binding.tPassword.getText().toString();

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(email);
            loginRequest.setContrasena(contrasena);

            ApiService apiService = UserClient.getINSTANCE().getApiService();

            Call<ApiResponse> call = apiService.login(loginRequest);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        ToastUtil.customMensaje(LoginActivity.this, "Inicio de sesión exitoso.");
                        setearControles();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();  // Cierra la actividad

                    } else {
                        if (response.errorBody() != null) {
                            try {
                                // Convierte el cuerpo del error a ApiResponse
                                ApiResponse apiResponse = new Gson().fromJson(response.errorBody().string(), ApiResponse.class);
                                // Muestra el mensaje del error
                                ToastUtil.customMensaje(LoginActivity.this, apiResponse.getMessage());
                                Log.d("LoginActivity", "Mensaje de error: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    ToastUtil.customMensaje(LoginActivity.this, "Error al hacer la llamada a la API.");
                }
            });
        }
    }

    private boolean validarFormulario() {
        boolean respuesta = false;
        if (!ingresoEmail()) {
            binding.tEmail.setError("Ingrese su correo electrónico");
        } else if (!ingresoPassword()) {
            binding.tPassword.setError("Ingrese su contraseña");
        } else {
            respuesta = true;
        }
        return respuesta;
    }

    //--VALIDACIONES
    private boolean ingresoEmail() {
        boolean respuesta = true;
        String email = binding.tEmail.getText().toString().trim();
        if (email.isEmpty()) {
            binding.tEmail.setFocusableInTouchMode(true);
            binding.tEmail.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean ingresoPassword() {
        boolean respuesta = true;
        String passw = binding.tPassword.getText().toString().trim();
        if (passw.isEmpty()) {
            binding.tPassword.setFocusableInTouchMode(true);
            binding.tPassword.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    //--SETEAR CONTROLES
    private void setearControles(){
        binding.tEmail.setText("");
        binding.tPassword.setText("");
        binding.tEmail.setFocusableInTouchMode(true);
        binding.tEmail.requestFocus();
    }
}
