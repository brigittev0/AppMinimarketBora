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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.appborabora.adapter.DetalleHistorialCompraAdapter;
import pe.edu.idat.appborabora.databinding.FragmentDetalleHistorialCompraBinding;
import pe.edu.idat.appborabora.retrofit.response.CompraResponse;
import pe.edu.idat.appborabora.retrofit.response.ProductoCompraResponse;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class DetalleHistorialCompraFragment extends Fragment {

    private FragmentDetalleHistorialCompraBinding binding;
    private DetalleHistorialCompraAdapter detalleHistorialCompraAdapter = new DetalleHistorialCompraAdapter();
    private AuthViewModel viewModel;
    private int compraId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleHistorialCompraBinding.inflate(getLayoutInflater());

        binding.rvproductoscompra.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvproductoscompra.setAdapter(detalleHistorialCompraAdapter);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        if (getArguments() != null) {
            compraId = getArguments().getInt("compraId");
        }
        viewModel.getInfoCompra(compraId).observe(getViewLifecycleOwner(), new Observer<CompraResponse>() {
            @Override
            public void onChanged(CompraResponse compra) {
                if (compra != null) {
                    SharedPreferences sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                    String nombreUsuario = sharedPref.getString("nombres", null);
                    String apellidoUsuario = sharedPref.getString("apellidos", null);
                    int docUsuario = sharedPref.getInt("docIdentidad", 0);
                    String emailUsuario = sharedPref.getString("email", null);
                    int cellUsuario = sharedPref.getInt("telefono", 0);

                    String numeroCompra = "1223726765-0" + compra.getId();
                    binding.tvnumerocompra.setText(numeroCompra);


                    String igvSimbolo = "S/ " + compra.getIgv();
                    binding.tvigv.setText(igvSimbolo);
                    String subtotalSimbolo = "S/ " + compra.getSubtotal();
                    binding.tvsubtotal.setText(subtotalSimbolo);
                    String totalSimbolo = "S/ " + compra.getTotal();
                    binding.tvtotalcompra.setText(totalSimbolo);
                    String telefonoSimbolo = "+51 " + cellUsuario;
                    binding.tvTelefono.setText(telefonoSimbolo);
                    binding.tvfechacompra.setText(compra.getFcompra());
                    binding.tvmetodopago.setText(compra.getMetodopago());
                    binding.tvnombresapellidos.setText(nombreUsuario + " " + apellidoUsuario);
                    binding.tvcorreo.setText(String.valueOf(emailUsuario));
                    binding.tvdocumento.setText(String.valueOf(docUsuario));


                } else {
                    Toast.makeText(getContext(), "No se encontró información de la compra", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getCompraProductos(compraId).observe(getViewLifecycleOwner(), new Observer<List<ProductoCompraResponse>>() {
            @Override
            public void onChanged(List<ProductoCompraResponse> productosCompraResponses) {
                if (productosCompraResponses != null && !productosCompraResponses.isEmpty()) {
                    detalleHistorialCompraAdapter.setData(new ArrayList<>(productosCompraResponses));
                } else {
                    Toast.makeText(getContext(), "No hay productos en esta compra", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }
}