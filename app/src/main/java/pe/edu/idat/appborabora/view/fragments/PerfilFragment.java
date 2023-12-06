package pe.edu.idat.appborabora.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.FragmentPerfilBinding;
import pe.edu.idat.appborabora.retrofit.network.BoraBoraClient;
import pe.edu.idat.appborabora.retrofit.network.BoraBoraService;
import pe.edu.idat.appborabora.retrofit.request.PerfilRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import pe.edu.idat.appborabora.retrofit.response.PerfilResponse;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;
import retrofit2.Response;

public class PerfilFragment extends Fragment {
    private FragmentPerfilBinding binding;
    private EditText nombreEditText, apellidoEditText, dniEditText, emailEditText, celularEditText;
    private Button btnActualizarPerfil;
    private AuthViewModel authViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Inicializa las variables con las referencias a los EditTexts
        nombreEditText = view.findViewById(R.id.nombre);
        apellidoEditText = view.findViewById(R.id.apellido);
        dniEditText = view.findViewById(R.id.dni);
        emailEditText = view.findViewById(R.id.email);
        celularEditText = view.findViewById(R.id.celular);
        btnActualizarPerfil = view.findViewById(R.id.btnActualizarPerfil);

        listarUsuario(view);
        btnActualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPerfil();
            }
        });

        // Observa la respuesta de la actualización del perfil
        authViewModel.observeUpdatePerfilResponse().observe(getViewLifecycleOwner(), new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                if (apiResponse != null) {
                    // Maneja el ApiResponse aquí, muestra el mensaje en la interfaz de usuario
                    Toast.makeText(requireContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void listarUsuario(View view) {
        // Obtener la referencia de la vista del fragmento para buscar las vistas
        TextView Username = view.findViewById(R.id.nombre);
        TextView Userapellido = view.findViewById(R.id.apellido);
        TextView UserDni = view.findViewById(R.id.dni);
        TextView UserEmail = view.findViewById(R.id.email);
        TextView UserCelular = view.findViewById(R.id.celular);

        //PARA OBTENER EL ID DEL USUARIO:
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String nombreUsuario = sharedPref.getString("nombres", null);
        String apellidoUsuario = sharedPref.getString("apellidos", null);
        int dniUsuario = sharedPref.getInt("docIdentidad", 0);
        String emailUsuario = sharedPref.getString("email", null);
        int cellUsuario = sharedPref.getInt("telefono", 0);

        if (nombreUsuario != null && apellidoUsuario != null && emailUsuario != null) {
            Username.setText(nombreUsuario);
            Userapellido.setText(apellidoUsuario);
            UserEmail.setText(emailUsuario);

            // Convierte los valores int a String antes de ponerlos en los TextViews
            String dniUsuarioString = String.valueOf(dniUsuario);
            String cellUsuarioString = String.valueOf(cellUsuario);

            UserDni.setText(dniUsuarioString);
            UserCelular.setText(cellUsuarioString);
        }
    }

    private void actualizarPerfil() {
        if(validarFormulario()) {
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            int idUsuario = sharedPreferences.getInt("user_id", -1);
            String nombre = nombreEditText.getText().toString();
            String apellido = apellidoEditText.getText().toString();
            int dni = Integer.parseInt(dniEditText.getText().toString());
            String email = emailEditText.getText().toString();
            int telefono = Integer.parseInt(celularEditText.getText().toString());

            PerfilRequest perfilRequest = new PerfilRequest(nombre, apellido, dni, telefono, email);
            // Llama al método en el ViewModel para actualizar el perfil
            authViewModel.updatePerfil(idUsuario, perfilRequest);
        }
    }

    private boolean validarFormulario() {
        boolean respuesta = false;

        if (!ingresoNombre()) {
            binding.nombre.setError("Ingrese su nombre");
        } else if (!ingresoApellido()) {
            binding.apellido.setError("Ingrese su apellido");
        } else if (!ingresoDni()) {
            binding.dni.setError("Ingrese su DNI");
        } else if (!validarDni()) {
            binding.dni.setError("El DNI debe tener 8 digitos");
        } else if (!ingresoCelular()) {
            binding.celular.setError("Ingrese su número de celular");
        } else if (!validarNumeroCelular()) {
            binding.celular.setError("Numero de celular invalido");
        } else if (!ingresoEmail()) {
            binding.email.setError("Ingrese su correo electrónico");
        } else if (!validarEmail()) {
            binding.email.setError("Correo invalido");
        } else {
            respuesta = true;
        }
        return respuesta;
    }

    //--VALIDACIONES
    private boolean ingresoNombre() {
        boolean respuesta = true;
        String nombre = binding.nombre.getText().toString().trim();
        if (nombre.isEmpty()) {
            binding.nombre.setFocusableInTouchMode(true);
            binding.nombre.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean ingresoApellido() {
        boolean respuesta = true;
        String apellido = binding.apellido.getText().toString().trim();
        if (apellido.isEmpty()) {
            binding.apellido.setFocusableInTouchMode(true);
            binding.apellido.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean ingresoDni() {
        boolean respuesta = true;
        String dni = binding.dni.getText().toString().trim();
        if (dni.isEmpty()) {
            binding.dni.setFocusableInTouchMode(true);
            binding.dni.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean validarDni() {
        String dni = binding.dni.getText().toString().trim();
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
        String telefono = binding.celular.getText().toString().trim();
        if (telefono.isEmpty()) {
            binding.celular.setFocusableInTouchMode(true);
            binding.celular.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }

    private boolean validarNumeroCelular() {
        String numeroCelular = binding.celular.getText().toString().trim();
        // valida que el número de celular comience con '9' y tenga 9 dígitos en total
        if (!numeroCelular.matches("^9\\d{8}$")) {
            return false;
        }

        return true;
    }

    private boolean ingresoEmail() {
        boolean respuesta = true;
        String correo = binding.email.getText().toString().trim();
        if (correo.isEmpty()) {
            binding.email.setFocusableInTouchMode(true);
            binding.email.requestFocus();
            respuesta = false;
        }
        return respuesta;
    }
    private boolean validarEmail() {
        String correo = binding.email.getText().toString().trim();
        // valida el formato de un correo electrónico
        String patronCorreo = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        // Verifica que el correo cumple con el patrón
        if (!correo.matches(patronCorreo)) {
            return false;
        }
        return true;
    }
}