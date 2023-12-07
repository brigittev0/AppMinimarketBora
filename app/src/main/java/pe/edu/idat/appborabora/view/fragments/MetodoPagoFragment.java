package pe.edu.idat.appborabora.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.FragmentMetodoPagoBinding;

public class MetodoPagoFragment extends Fragment {

    private FragmentMetodoPagoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMetodoPagoBinding.inflate(inflater, container, false);

        binding.btntarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.metodoPagoTarjetaFragment);
            }
        });

        binding.btnrecojotienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Elección guardada", Snackbar.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}