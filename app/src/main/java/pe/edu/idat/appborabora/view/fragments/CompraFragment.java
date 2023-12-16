package pe.edu.idat.appborabora.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.adapter.CarritoAdapter;
import pe.edu.idat.appborabora.databinding.FragmentCompraBinding;
import pe.edu.idat.appborabora.retrofit.request.CompraRequest;
import pe.edu.idat.appborabora.retrofit.response.ProductoCarrito;
import pe.edu.idat.appborabora.utils.Carrito;
import pe.edu.idat.appborabora.utils.DateSerializer;
import pe.edu.idat.appborabora.utils.TimeSerializer;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class CompraFragment extends Fragment implements CarritoAdapter.OnCarritoChangeListener {

    private FragmentCompraBinding binding;
    private RecyclerView recyclerView;
    private CarritoAdapter carritoAdapter;
    private List<ProductoCarrito> listaProductosCarrito = new ArrayList<>();

    private AuthViewModel authViewModel;

    final Gson g = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .registerTypeAdapter(Time.class, new TimeSerializer())
            .create();

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

        /*
        binding.btnmasproductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.catalogoFragment, null, new NavOptions.Builder()

                        .setLaunchSingleTop(true)
                        .setPopUpTo(R.id.compraFragment, false)
                        .build());
            }
        }); */

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);
        binding.btnfinalizarcompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una instancia de CompraRequest
                CompraRequest compraRequest = new CompraRequest();

                // Obtener la lista de productos del carrito
                List<ProductoCarrito> productosEnCarrito = Carrito.getProductosEnCarrito(userId);
                List<ProductoCarrito> listaProductos = new ArrayList<>();
                for (ProductoCarrito producto : productosEnCarrito) {
                    listaProductos.add(producto);
                }


                // Establecer la lista de productos en CompraRequest
                compraRequest.setProductos(listaProductos);



                // Obtener la fecha actual del sistema y establecerla en CompraRequest
                Date fechaActual = new Date();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    compraRequest.setFcompra(fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }

                // Establecer el método de pago en CompraRequest como "en proceso"
                compraRequest.setMetodopago("en proceso");

                authViewModel.postInsertCompra(compraRequest);
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

        float subtotal = sharedPreferences.getFloat("subtotal", 0);
        float igv = sharedPreferences.getFloat("igv", 0);
        float total = sharedPreferences.getFloat("total", 0);

        // Actualiza los TextViews con los valores recuperados
        binding.txtsubtotal.setText(String.format(Locale.getDefault(), "%.2f", subtotal));
        binding.txtigv.setText(String.format(Locale.getDefault(), "%.2f", igv));
        binding.txttotal.setText(String.format(Locale.getDefault(), "%.2f", total));


        // Actualizar los datos en el adaptador aquí
        listaProductosCarrito.clear();
        List<ProductoCarrito> productosEnCarrito = Carrito.getProductosEnCarrito(userId);
        if (productosEnCarrito != null) {
            listaProductosCarrito.addAll(productosEnCarrito);
        } else {
            Log.d("MyApp", "No hay productos en el carrito para el userId: " + userId);
            // Maneja el caso en que no hay productos en el carrito para este userId
        }
        carritoAdapter.setListaProductosCarrito(listaProductosCarrito);
        carritoAdapter.notifyDataSetChanged();
    }
    public void onCarritoChange() {
        actualizarTotales();
    }

    public void actualizarTotales() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);
        double subtotal = Carrito.calcularSubtotal(userId);
        double igv = Carrito.calcularIGV(userId);
        double total = Carrito.calcularTotal(userId);

        // Almacena los valores en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("subtotal", (float) subtotal);
        editor.putFloat("igv", (float) igv);
        editor.putFloat("total", (float) total);
        editor.apply();

        binding.txtsubtotal.setText(String.format(Locale.getDefault(), "%.2f", subtotal));
        binding.txtigv.setText(String.format(Locale.getDefault(), "%.2f", igv));
        binding.txttotal.setText(String.format(Locale.getDefault(), "%.2f", total));
    }


}