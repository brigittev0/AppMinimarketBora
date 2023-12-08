package pe.edu.idat.appborabora.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.FragmentContactoBinding;

public class ContactoFragment extends Fragment implements View.OnClickListener{
    private FragmentContactoBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactoBinding.inflate(inflater, container, false);
        binding.btnChatear.setOnClickListener(this);
        binding.btnLlamar.setOnClickListener(this);
        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnChatear) {
            abrirChatWhatsApp();
        } else if (v.getId() == R.id.btnLlamar) {
            realizarLlamada();
        }
    }

    private void abrirChatWhatsApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://wa.me/51904392864"));
        startActivity(intent);
    }

    private void realizarLlamada() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:51904392864"));
        startActivity(intent);
    }
}
