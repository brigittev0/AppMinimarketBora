package pe.edu.idat.appborabora.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.adapter.CarritoAdapter;
import pe.edu.idat.appborabora.databinding.FragmentCompraBinding;
import pe.edu.idat.appborabora.retrofit.request.CompraRequest;
import pe.edu.idat.appborabora.retrofit.response.ApiResponse;
import pe.edu.idat.appborabora.retrofit.response.ProductoCarrito;
import pe.edu.idat.appborabora.utils.Carrito;
import pe.edu.idat.appborabora.utils.ProductoCompra;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class CompraFragment extends Fragment implements CarritoAdapter.OnCarritoChangeListener {

    private FragmentCompraBinding binding;
    private RecyclerView recyclerView;
    private CarritoAdapter carritoAdapter;
    private List<ProductoCarrito> listaProductosCarrito = new ArrayList<>();

    private AuthViewModel authViewModel;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompraBinding.inflate(inflater, container, false);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        // Inicializar el RecyclerView y el adaptador aquí
        recyclerView = binding.rvcarrito; // Aquí se debe usar binding en lugar de view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        carritoAdapter = new CarritoAdapter(listaProductosCarrito,this);
        recyclerView.setAdapter(carritoAdapter);


        SharedPreferences sharedPreference = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String subtotal = sharedPreference.getString("subtotal", "0");
        String igv = sharedPreference.getString("igv", "0");
        String total = sharedPreference.getString("total", "0");
        binding.txtsubtotal.setText(subtotal);
        binding.txtigv.setText(igv);
        binding.txttotal.setText(total);

        binding.btnmetodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.metodoPagoFragment);
            }
        });


        binding.btnmasproductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.dashboardFragment);
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", 0);

        // Imprime el userId en Logcat
        Log.d("MyApp", "UserId: " + userId);
        //-------------

        binding.btnfinalizarcompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una instancia de CompraRequest
                CompraRequest compraRequest = new CompraRequest();

                // Obtener la lista de productos del carrito
                List<ProductoCarrito> productosEnCarrito = Carrito.getProductosEnCarrito(userId);
                if (productosEnCarrito == null || productosEnCarrito.isEmpty()) {
                    Toast.makeText(getContext(), "No puedes finalizar la compra con el carrito vacío", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<ProductoCompra> listaProductos = new ArrayList<>();
                for (ProductoCarrito producto : productosEnCarrito) {
                    listaProductos.add(new ProductoCompra(producto.getId(), producto.getCantidad()));
                }
                compraRequest.setProductos(listaProductos);


                // Obtener la fecha actual del sistema y establecerla en CompraRequest
                Date fechaActual = new Date();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate localDate = fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedString = localDate.format(formatter);
                    compraRequest.setFcompra(formattedString);
                }

                // Establecer el método de pago en CompraRequest como "en proceso"
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                String metodoPagoSeleccionado = sharedPreferences.getString("metodo_pago", "");
                compraRequest.setMetodopago(metodoPagoSeleccionado);

                authViewModel.postInsertCompra(compraRequest);
                authViewModel.insertCompraResponseMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ApiResponse>() {
                    @Override
                    public void onChanged(ApiResponse apiResponse) {
                        if (apiResponse != null) {
                            // Aquí puedes manejar la respuesta de la inserción de la compra
                            // Por ejemplo, puedes mostrar un mensaje al usuario
                            Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            // Imprimir el mensaje en el logcat
                            Log.d("MyApp", "Respuesta de la API: " + apiResponse.getMessage());
                            Carrito.limpiarCarrito(userId);

                            // Actualizar la lista de productos en el adaptador
                            listaProductosCarrito.clear();
                            List<ProductoCarrito> productosEnCarrito = Carrito.getProductosEnCarrito(userId);
                            if (productosEnCarrito != null) {
                                listaProductosCarrito.addAll(productosEnCarrito);
                            }
                            carritoAdapter.setListaProductosCarrito(listaProductosCarrito);
                            carritoAdapter.notifyDataSetChanged();

                            // Establecer IGV, total y subtotal a 0
                            binding.txtigv.setText("0");
                            binding.txttotal.setText("0");
                            binding.txtsubtotal.setText("0");

                            // Refrescar el fragmento para ver los cambios inmediatamente
                            getFragmentManager().beginTransaction().detach(CompraFragment.this).attach(CompraFragment.this).commit();
                        } else {
                            // Aquí puedes manejar el caso en que la respuesta es null
                            // Por ejemplo, puedes mostrar un mensaje de error al usuario
                            Toast.makeText(getContext(), "Error al insertar la compra", Toast.LENGTH_SHORT).show();
                            // Imprimir el mensaje de error en el logcat
                            Log.e("MyApp", "Error al insertar la compra");
                        }
                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);

        // Imprime el userId en Logcat
        Log.d("MyApp", "UserId: " + userId);

        // Actualizar los datos en el adaptador aquí
        listaProductosCarrito.clear();
        List<ProductoCarrito> productosEnCarrito = Carrito.getProductosEnCarrito(userId);
        if (productosEnCarrito != null) {
            listaProductosCarrito.addAll(productosEnCarrito);
        } else {
            Toast.makeText(getContext(), "Carrito Vacio", Toast.LENGTH_SHORT).show();
            Log.d("MyApp", "No hay productos en el carrito para el userId: " + userId);
            // Maneja el caso en que no hay productos en el carrito para este userId
        }
        carritoAdapter.setListaProductosCarrito(listaProductosCarrito);

    }
    public void onCarritoChange() {
        actualizarTotales();
    }

    public void actualizarTotales() {
        double subtotal = Carrito.calcularSubtotal(userId);
        double igv = Carrito.calcularIGV(userId);
        double total = Carrito.calcularTotal(userId);

        binding.txtsubtotal.setText(String.format(Locale.getDefault(), "%.2f", subtotal));
        binding.txtigv.setText(String.format(Locale.getDefault(), "%.2f", igv));
        binding.txttotal.setText(String.format(Locale.getDefault(), "%.2f", total));

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("subtotal", String.format(Locale.getDefault(), "%.2f", subtotal));
        editor.putString("igv", String.format(Locale.getDefault(), "%.2f", igv));
        editor.putString("total", String.format(Locale.getDefault(), "%.2f", total));
        editor.apply();
    }
}