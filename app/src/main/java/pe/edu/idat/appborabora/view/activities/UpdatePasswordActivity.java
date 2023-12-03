package pe.edu.idat.appborabora.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.ActivityNuevacontraBinding;
import pe.edu.idat.appborabora.utils.ToastUtil;
import pe.edu.idat.appborabora.retrofit.request.UpdatePasswordRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class UpdatePasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityNuevacontraBinding binding;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNuevacontraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnActualizar.setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        viewModel.updatePasswordResponseMutableLiveData.observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                manejarRptaUpdatePass(apiResponse);
            }
        });
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

            UpdatePasswordRequest request = new UpdatePasswordRequest(email, oldPass, newPass);
            viewModel.updatePassword(request);
        }
    }

    private void manejarRptaUpdatePass(ApiResponse apiResponse) {
        if ((apiResponse != null && apiResponse.getStatus().equals("OK"))) {
            ToastUtil.customMensaje(UpdatePasswordActivity.this, "Contraseña actualizada con éxito.");

            setearControles();
            Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();  // Cierra la actividad
        } else {
            String mensaje = apiResponse != null ? apiResponse.getMessage() : "Error al hacer la llamada a la API.";
            ToastUtil.customMensaje(UpdatePasswordActivity.this, mensaje);
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