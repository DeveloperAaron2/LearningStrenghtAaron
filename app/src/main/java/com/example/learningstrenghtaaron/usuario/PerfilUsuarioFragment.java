package com.example.learningstrenghtaaron.usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.learningstrenghtaaron.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class PerfilUsuarioFragment extends Fragment {
    private LinearLayout layoutPrincipal;
    private ShapeableImageView fotoPerfil;
    private FloatingActionButton btnMenu;
    private MaterialTextView txtUsuario, txtNombre, txtCorreo, txtFechaNac, txtPeso, txtAltura;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        inicializarComponentes(view);

        ponerDatos(user);

        btnMenu.setOnClickListener(view1 -> showMenu(view1, R.menu.fragment_perfil_usuario_menu));

        return view;
    }

    private void ponerDatos(FirebaseUser user) {
        if (user.getDisplayName().isBlank()) txtUsuario.setText("Pancho");
        else txtUsuario.setText(user.getDisplayName());
        txtNombre.setText("RevientaAbuelas69");
        txtCorreo.setText(user.getEmail());
        txtFechaNac.setText(user.getMetadata().toString());
        txtPeso.setText("Curvado");
        txtAltura.setText("Enano");
    }

    private void showMenu(View view, int fragment_perfil_usuario_menu) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.getMenuInflater().inflate(fragment_perfil_usuario_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.EditarUsuario:
                        startActivity(new Intent(getContext(), PerfilUsuarioActivity.class));
                        break;
                    case R.id.EditarRms:
                        Toast.makeText(getContext(), "No estas listo para eso", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.MisRutinas:
                        //
                        break;
                    case R.id.Ajustes:
                        Toast.makeText(getContext(), "Aqui no se puede entrar", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        popup.setOnDismissListener(popupMenu -> {});
        popup.show();
    }

    private void inicializarComponentes(View view) {
        layoutPrincipal = view.findViewById(R.id.linearLayoutFragmentPerfilUsuario);
        fotoPerfil = view.findViewById(R.id.fotoFragmentPerfilUsuario);
        btnMenu = view.findViewById(R.id.btnMenuFragmentPerfilUsuario);
        txtUsuario = view.findViewById(R.id.tilNombreUsuarioFragmentPerfilUsuario);
        txtNombre = view.findViewById(R.id.tilNombreFragmentPerfilUsuario);
        txtCorreo = view.findViewById(R.id.tilEmailFragmentPerfilUsuario);
        txtFechaNac = view.findViewById(R.id.tilFechaNacFragmentPerfilusuario);
        txtPeso = view.findViewById(R.id.tilPesoFragmentPerfilusuario);
        txtAltura = view.findViewById(R.id.tilAlturaFragmentPerfilusuario);
    }
}