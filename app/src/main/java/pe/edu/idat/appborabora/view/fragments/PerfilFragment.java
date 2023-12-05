package pe.edu.idat.appborabora.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {
    private FragmentPerfilBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        listarUsuario(view);
        return view;
    }

    public void listarUsuario(View view) {
        // Obtener la referencia de la vista del fragmento para buscar las vistas
        TextView Username = view.findViewById(R.id.nombre);
        TextView Userapellido = view.findViewById(R.id.apellido);
        TextView UserDni = view.findViewById(R.id.dni);
        TextView UserEmail = view.findViewById(R.id.email);
        TextView UserCelular = view.findViewById(R.id.celular);

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
    //PARA OBTENER EL ID DEL USUARIO:
    //SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
    //int userId = sharedPref.getInt("user_id", -1);  // Devuelve -1 si "user_id" no existe
}