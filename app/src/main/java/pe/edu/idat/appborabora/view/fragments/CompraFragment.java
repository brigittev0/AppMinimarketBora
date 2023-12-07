package pe.edu.idat.appborabora.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.FragmentCompraBinding;

public class CompraFragment extends Fragment {

    private FragmentCompraBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompraBinding.inflate(inflater, container, false);

        binding.btnmetodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.metodoPagoFragment);
            }
        });

        return binding.getRoot();
    }
}