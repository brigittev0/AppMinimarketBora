package pe.edu.idat.appborabora.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.appborabora.adapter.HistorialComprasAdapter;
import pe.edu.idat.appborabora.databinding.FragmentHistorialCompraBinding;
import pe.edu.idat.appborabora.retrofit.response.HistorialComprasResponse;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class HistorialCompraFragment extends Fragment {

    private FragmentHistorialCompraBinding binding;
    private HistorialComprasAdapter compraAdapter = new HistorialComprasAdapter();
    private AuthViewModel viewModel; // Añade una instancia de tu ViewModel

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistorialCompraBinding.inflate(inflater,container,false);

        binding.rvcompras.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvcompras.setAdapter(compraAdapter);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);

        viewModel.getComprasUser(userId).observe(getViewLifecycleOwner(), new Observer<List<HistorialComprasResponse>>() {
            @Override
            public void onChanged(List<HistorialComprasResponse> historialComprasResponse) {
                compraAdapter.setData(new ArrayList<>(historialComprasResponse)); // Actualiza los datos de tu adaptador
            }
        });

        return binding.getRoot();
    }
}