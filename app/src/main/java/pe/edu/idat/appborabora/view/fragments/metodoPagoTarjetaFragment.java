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
import pe.edu.idat.appborabora.databinding.FragmentMetodoPagoTarjetaBinding;

public class metodoPagoTarjetaFragment extends Fragment implements View.OnClickListener{
    private FragmentMetodoPagoTarjetaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMetodoPagoTarjetaBinding.inflate(inflater, container, false);
        binding.btnaceptar.setOnClickListener(this);
        return binding.getRoot();
    }
        private boolean isValidCard() {
            String cardNumber = binding.cardNumber.getText().toString();
            String expiryDate = binding.expiryDate.getText().toString();
            String cvv = binding.cvv.getText().toString();

            // Validar el número de la tarjeta
            if (cardNumber.isEmpty() || cardNumber.length() < 16) {
                binding.cardNumber.setError("Introduce un número de tarjeta válido");
                return false;
            }

            // Validar la fecha de vencimiento
            if (expiryDate.isEmpty() || !expiryDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
                binding.expiryDate.setError("Introduce una fecha de vencimiento válida (MM/AA)");
                return false;
            }

            // Validar el CVV
            if (cvv.isEmpty() || cvv.length() < 3) {
                binding.cvv.setError("Introduce un CVV válido");
                return false;
            }

            return true;
        }

    @Override
    public void onClick(View v) {
        if (isValidCard()) {
            Snackbar.make(v, "Elección guardada", Snackbar.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.compraFragment);
        }
    }
}