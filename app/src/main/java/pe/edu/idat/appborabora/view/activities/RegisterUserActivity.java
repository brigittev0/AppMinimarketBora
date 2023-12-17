package pe.edu.idat.appborabora.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.ActivityRegistroBinding;
import pe.edu.idat.appborabora.retrofit.request.RegisterUserRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityRegistroBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btncrear.setOnClickListener(this);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.registerResponseMutableLiveData.observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                manejarRespuestaRegistro(apiResponse);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btncrear) {
            registrarUsuario();
        }
    }
    private void registrarUsuario(){
        if(validarFormulario()){
            RegisterUserRequest registerUserRequest = new RegisterUserRequest(
                    binding.tNom.getText().toString(),
                    binding.tApellido.getText().toString(),
                    Integer.parseInt(binding.tDni.getText().toString()),
                    Integer.parseInt(binding.tCelular.getText().toString()),
                    binding.tCorreo.getText().toString(),
                    binding.tPassw.getText().toString()
            );

            authViewModel.registerUser(registerUserRequest);
        }
    }

    private void manejarRespuestaRegistro(ApiResponse apiResponse) {
        if (apiResponse != null && apiResponse.getStatus().equals("CREATED")) {

            Toast.makeText(RegisterUserActivity.this,"Registro exitoso.", Toast.LENGTH_LONG).show();

            setearControles();
            Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();  // Cierra la actividad
        } else {
            String mensaje = apiResponse != null ? apiResponse.getMessage() : "Error al hacer la llamada a la API.";
            Toast.makeText(RegisterUserActivity.this, mensaje, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validarFormulario() {
        boolean respuesta = false;

        if (!ingresoNombre()) {
            binding.tNom.setError("Ingrese su nombre");
        } else if (!ingresoApellido()) {
            binding.tApellido.setError("Ingrese su apellido");
        } else if (!ingresoDni()) {
            binding.tDni.setError("Ingrese su DNI");
        } else if (!validarDni()) {
            binding.tDni.setError("El DNI debe tener 8 digitos");
        } else if (!ingresoCelular()) {
            binding.tCelular.setError("Ingrese su número de celular");
        } else if (!validarNumeroCelular()) {
            binding.tCelular.setError("Numero de celular invalido");
        } else if (!ingresoEmail()) {
            binding.tCorreo.setError("Ingrese su correo electrónico");
        } else if (!validarEmail()) {
            binding.tCorreo.setError("Correo invalido");
        } else if (!ingresoPassword()) {
            binding.tPassw.setError("Ingrese su contraseña");
        } else if (!validarPassword()) {
            binding.tPassw.setError("Debe tener 8+ caracteres, 1 mayúscula y 1 número");
        } else if (!validarCondicion1()) {
            binding.cbcondicion1.setError("");
        } else {
            respuesta = true;
        }
        return respuesta;
    }

    //--VALIDACIONES
    private boolean ingresoNombre() {
        boolean respuesta = true;
        String nombre = binding.tNom.getText().toString().trim();
        if (nombre.isEmpty()) {
            binding.tNom.setFocusableInTouchMode(true);
            binding.tNom.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean ingresoApellido() {
        boolean respuesta = true;
        String apellido = binding.tApellido.getText().toString().trim();
        if (apellido.isEmpty()) {
            binding.tApellido.setFocusableInTouchMode(true);
            binding.tApellido.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean ingresoDni() {
        boolean respuesta = true;
        String dni = binding.tDni.getText().toString().trim();
        if (dni.isEmpty()) {
            binding.tDni.setFocusableInTouchMode(true);
            binding.tDni.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean validarDni() {
        String dni = binding.tDni.getText().toString().trim();
        // Verifica que el DNI tenga exactamente 8 dígitos y sean todos números
        if (dni.length() != 8 || !esNumero(dni)) {
            return false;
        }
        return true;
    }

    private boolean esNumero(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean ingresoCelular() {
        boolean respuesta = true;
        String telefono = binding.tCelular.getText().toString().trim();
        if (telefono.isEmpty()) {
            binding.tCelular.setFocusableInTouchMode(true);
            binding.tCelular.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean validarNumeroCelular() {
        String numeroCelular = binding.tCelular.getText().toString().trim();
        // valida que el número de celular comience con '9' y tenga 9 dígitos en total
        if (!numeroCelular.matches("^9\\d{8}$")) {
            return false;
        }

        return true;
    }

    private boolean ingresoEmail() {
        boolean respuesta = true;
        String correo = binding.tCorreo.getText().toString().trim();
        if (correo.isEmpty()) {
            binding.tCorreo.setFocusableInTouchMode(true);
            binding.tCorreo.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }
    private boolean validarEmail() {
        String correo = binding.tCorreo.getText().toString().trim();
        // valida el formato de un correo electrónico
        String patronCorreo = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        // Verifica que el correo cumple con el patrón
        if (!correo.matches(patronCorreo)) {
            return false;
        }
        return true;
    }

    private boolean ingresoPassword() {
        boolean respuesta = true;
        String passw = binding.tPassw.getText().toString().trim();
        if (passw.isEmpty()) {
            binding.tPassw.setFocusableInTouchMode(true);
            binding.tPassw.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean validarPassword() {
        String contrasena = binding.tPassw.getText().toString();

        return contrasena.length() >= 8 &&
                contrasena.length() <= 15 &&
                contieneMayuscula(contrasena) &&
                contieneNumero(contrasena)&&
                contieneCaracterEspecial(contrasena);
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

    private boolean contieneCaracterEspecial(String s) {
        // Expresión regular para verificar caracteres especiales
        String patron = "[^a-zA-Z0-9]";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(s);
        return matcher.find(); }

    private boolean validarCondicion1() {
        return binding.cbcondicion1.isChecked();
    }

    //--SETEAR CONTROLES
    private void setearControles(){
        binding.tNom.setText("");
        binding.tApellido.setText("");
        binding.tDni.setText("");
        binding.tCelular.setText("");
        binding.tCorreo.setText("");
        binding.tPassw.setText("");
        binding.cbcondicion1.setChecked(false); //Limpiar check
        binding.cbcondicion1.setError(null);    //Limpiar error
        binding.cbcondicion2.setChecked(false);
        binding.tNom.setFocusableInTouchMode(true);
        binding.tNom.requestFocus();
    }
}