package pe.edu.idat.appborabora.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.ActivityNuevacontraBinding;
import pe.edu.idat.appborabora.retrofit.network.ApiService;
import pe.edu.idat.appborabora.retrofit.network.ToastUtil;
import pe.edu.idat.appborabora.retrofit.network.UserClient;
import pe.edu.idat.appborabora.retrofit.request.ResetPasswordRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevacontraActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityNuevacontraBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNuevacontraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnActualizar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnActualizar) {
            updatePassword();
        }
    }
    private void updatePassword(){
        if(validarFormulario()) {
            String email = binding.txtEmail.getText().toString().trim();
            String oldPass = binding.txtOldPassword.getText().toString().trim();
            String newPass = binding.txtNewPassword.getText().toString().trim();

            ResetPasswordRequest request = new ResetPasswordRequest(email, oldPass, newPass);

            ApiService apiService = UserClient.getINSTANCE().getApiService();

            Call<ApiResponse> call = apiService.updatePassword(request);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        ToastUtil.customMensaje(NuevacontraActivity.this, "Contraseña actualizada con éxito.");
                        setearControles();
                        Intent intent = new Intent(NuevacontraActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();  // Cierra la actividad

                    } else {
                        if (response.errorBody() != null) {
                            try {
                                // Convierte el cuerpo del error a ApiResponse
                                ApiResponse apiResponse = new Gson().fromJson(response.errorBody().string(), ApiResponse.class);
                                // Muestra el mensaje del error
                                ToastUtil.customMensaje(NuevacontraActivity.this, apiResponse.getMessage());
                                Log.d("UpdatePassActivity", "Mensaje de error: " + response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    ToastUtil.customMensaje(NuevacontraActivity.this, "Error al hacer la llamada a la API.");
                }
            });
        }
    }

    private boolean validarFormulario() {
        boolean respuesta = false;
        if (!ingresoEmail()) {
            binding.txtEmail.setError("Ingrese su correo electrónico");
        } else if (!ingresoOldPassword()) {
            binding.txtOldPassword.setError("Ingrese su contraseña actual");
        } else if (!ingresoNewPassword()) {
            binding.txtNewPassword.setError("Ingrese su nueva contraseña");
        }else if (!validarPassword()) {
            binding.txtNewPassword.setError("Debe tener 8+ caracteres, 1 mayúscula y 1 número");
        } else {
            respuesta = true;
        }
        return respuesta;
    }

    //--VALIDACIONES
    private boolean ingresoEmail() {
        boolean respuesta = true;
        String email = binding.txtEmail.getText().toString().trim();
        if (email.isEmpty()) {
            binding.txtEmail.setFocusableInTouchMode(true);
            binding.txtEmail.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean ingresoOldPassword() {
        boolean respuesta = true;
        String passw = binding.txtOldPassword.getText().toString().trim();
        if (passw.isEmpty()) {
            binding.txtOldPassword.setFocusableInTouchMode(true);
            binding.txtOldPassword.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean ingresoNewPassword() {
        boolean respuesta = true;
        String passw = binding.txtNewPassword.getText().toString().trim();
        if (passw.isEmpty()) {
            binding.txtNewPassword.setFocusableInTouchMode(true);
            binding.txtNewPassword.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean validarPassword() {
        String contrasena = binding.txtNewPassword.getText().toString();

        return contrasena.length() >= 8 &&
                contrasena.length() <= 15 &&
                contieneMayuscula(contrasena) &&
                contieneNumero(contrasena);
    }

    private boolean contieneMayuscula(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean contieneNumero(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    //--SETEAR CONTROLES
    private void setearControles(){
        binding.txtEmail.setText("");
        binding.txtOldPassword.setText("");
        binding.txtNewPassword.setText("");
        binding.txtEmail.setFocusableInTouchMode(true);
        binding.txtEmail.requestFocus();
    }
}