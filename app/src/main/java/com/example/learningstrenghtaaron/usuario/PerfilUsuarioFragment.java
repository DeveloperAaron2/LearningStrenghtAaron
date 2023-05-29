package com.example.learningstrenghtaaron.usuario;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.learningstrenghtaaron.BaseDeDatos.Firestore;
import com.example.learningstrenghtaaron.Entidades.Usuario;
import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.login.MainActivity;
import com.example.learningstrenghtaaron.rutinas.RutinasFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilUsuarioFragment extends Fragment {
    private LinearLayout layoutPrincipal;
    private ShapeableImageView fotoPerfil;
    private FloatingActionButton btnMenu;
    private MaterialTextView txtUsuario, txtDeporte, txtCorreo, txtFechaNac, txtPeso, txtAltura, txtRms;
    private Firestore firestore;

    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        firestore = Firestore.getInstance();

        inicializarComponentes(view);

        ponerDatos(user);

        btnMenu.setOnClickListener(view1 -> showMenu(view1, R.menu.fragment_perfil_usuario_menu));

        return view;
    }

/*    private void ponerDatos(FirebaseUser user) {
        if (user.getDisplayName().isBlank()) txtUsuario.setText("Pancho");
        else txtUsuario.setText(user.getDisplayName());
        txtNombre.setText("RevientaAbuelas69");
        txtCorreo.setText(user.getEmail());
        txtFechaNac.setText(user.getMetadata().toString());
        txtPeso.setText("Curvado");
        txtAltura.setText("Enano");
    }*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ponerDatos(FirebaseUser user) {
        Usuario usuario = firestore.getUsuario(user.getUid());

        if (usuario != null) {
            txtUsuario.setText(usuario.getUsuario() == null ? "" : "Usuario: " + usuario.getUsuario());
            txtDeporte.setText(usuario.getDeporte() == null ? "" : "Deporte: " + usuario.getDeporte());
            txtCorreo.setText(usuario.getCorreo() == null ? "" : "Correo: " + usuario.getCorreo());
            txtFechaNac.setText(usuario.getFechaNac() == null ? "" : "Fecha de nacimiento: " + usuario.getFechaNac());
            txtPeso.setText(String.format("%s", usuario.getPeso() == 0 ? "" : "Peso: " + usuario.getPeso()));
            txtAltura.setText(String.format("%s", usuario.getAltura() == 0 ? "" : "Altura: " + usuario.getAltura()));
            if (usuario.getMapaRms() != null && !usuario.getMapaRms().isEmpty()) {
                usuario.getMapaRms().forEach((k, v) -> txtRms.setText(String.format("%s %s: %s\n", txtRms.getText(), k, v)));
            }
        }
    }

    private void showMenu(View view, int fragment_perfil_usuario_menu) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.getMenuInflater().inflate(fragment_perfil_usuario_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.EditarUsuario:
                        startActivity(new Intent(getContext(), PerfilUsuarioActivity.class));
                        break;
                    case R.id.EditarRms:
                        Toast.makeText(getContext(), "No estas listo para eso", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.MisRutinas:
                        Fragment nuevoFragment = new RutinasFragment();// Reemplaza "NuevoFragment" con el nombre de tu clase de Fragment
                        Bundle bundle = new Bundle();
                        bundle.putString("Tipo","MisRutinas");
                        bundle.putString("IdUsuario", user.getUid());
                        System.out.println((String) txtUsuario.getText());
                        nuevoFragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutPantallaPrincipal, nuevoFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.Ajustes:
                        Toast.makeText(getContext(), "Aqui no se puede entrar", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.LogOut:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), MainActivity.class));
                }
                return false;
            }
        });
        popup.setOnDismissListener(popupMenu -> {
        });
        popup.show();
    }

    private void inicializarComponentes(View view) {
        layoutPrincipal = view.findViewById(R.id.linearLayoutFragmentPerfilUsuario);
        fotoPerfil = view.findViewById(R.id.fotoFragmentPerfilUsuario);
        btnMenu = view.findViewById(R.id.btnMenuFragmentPerfilUsuario);
        txtUsuario = view.findViewById(R.id.tilNombreUsuarioFragmentPerfilUsuario);
        txtDeporte = view.findViewById(R.id.tilDeporteFragmentPerfilUsuario);
        txtCorreo = view.findViewById(R.id.tilEmailFragmentPerfilUsuario);
        txtFechaNac = view.findViewById(R.id.tilFechaNacFragmentPerfilusuario);
        txtPeso = view.findViewById(R.id.tilPesoFragmentPerfilusuario);
        txtAltura = view.findViewById(R.id.tilAlturaFragmentPerfilusuario);
        txtRms = view.findViewById(R.id.txtRmsFragmentPerfilUsuario);
    }
}