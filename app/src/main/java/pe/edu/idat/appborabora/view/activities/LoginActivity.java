package pe.edu.idat.appborabora.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import pe.edu.idat.appborabora.retrofit.response.PerfilResponse;
import pe.edu.idat.appborabora.utils.ToastUtil;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.ActivityLoginBinding;
import pe.edu.idat.appborabora.retrofit.request.LoginRequest;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvolvidarcontrasena.setOnClickListener(this);
        binding.btniniciarsesion.setOnClickListener(this);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.perfilResponseMutableLiveData.observe(this, new Observer<PerfilResponse>() {
            @Override
            public void onChanged(PerfilResponse perfilResponse) {
                manejarRespuestaLogin(perfilResponse);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvolvidarcontrasena) {
            setearControles();
            startActivity(new Intent(LoginActivity.this, UpdatePasswordActivity.class));
            finish();
        } else if (v.getId() == R.id.btniniciarsesion) {
            iniciarSesion();
        }
    }

    private void manejarRespuestaLogin(PerfilResponse perfilResponse) {
        if (perfilResponse != null && perfilResponse.getStatus().equals("OK")) {
            ToastUtil.customMensaje(LoginActivity.this, "Inicio de sesión exitoso.");

            //--SE GUARDA LOS DATOS DEL USUARIO INICIADO
            SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("user_id", perfilResponse.getUserId());
            editor.putString("nombres", perfilResponse.getNombres());
            editor.putString("apellidos", perfilResponse.getApellidos());
            editor.putInt("docIdentidad", perfilResponse.getDocIdentidad());
            editor.putInt("telefono", perfilResponse.getTelefono());
            editor.putString("email", perfilResponse.getEmail());
            editor.apply();
            //--DE ESTA MANERA OBTIENES CUALQUIER DATO DEL PERFIL EN CUALQUIER PARTE DEL PROYECTO
            //SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            //int userId = sharedPref.getInt("user_id", 0);  // El segundo parámetro es un valor predeterminado que se devuelve si no se encuentra "user_id"

            setearControles();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();  // Cierra la actividad
        } else {
            String mensaje = perfilResponse != null ? perfilResponse.getMessage() : "Error al hacer la llamada a la API.";
            ToastUtil.customMensaje(LoginActivity.this, mensaje);
        }
    }

    //--

    private void iniciarSesion(){
        if(validarFormulario()){
            String email = binding.tEmail.getText().toString();
            String contrasena = binding.tPassword.getText().toString();

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(email);
            loginRequest.setContrasena(contrasena);

            authViewModel.login(loginRequest);
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
