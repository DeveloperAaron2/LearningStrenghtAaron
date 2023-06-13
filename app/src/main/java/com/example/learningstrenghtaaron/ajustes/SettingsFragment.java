package com.example.learningstrenghtaaron.ajustes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.learningstrenghtaaron.R;
import com.example.learningstrenghtaaron.baseDeDatos.Firestore;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends PreferenceFragmentCompat {
    private SwitchPreference switchModoOscuro, switchSonido;
    private ListPreference listaTemas, listaLetra, listaCalculadoras;
    private EditTextPreference etCambiarCorreo, etCambiarContrasenia;
    private Preference dialogEliminarCuenta;
    private Firestore firestore;
    private FirebaseAuth mAuth;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        firestore = Firestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        inicializarComponentes();
        listenersAplicacion();
        listenersCuenta();
    }

    private void inicializarComponentes() {
        switchModoOscuro = findPreference("switchModoOscuro");
        switchSonido = findPreference("switchSonidos");
        listaTemas = findPreference("listaTemas");
        listaLetra = findPreference("listaLetra");
        listaCalculadoras = findPreference("listaCalculadoras");
        etCambiarCorreo = findPreference("etCambiarCorreo");
        etCambiarContrasenia = findPreference("etCambiarContrasenia");
        dialogEliminarCuenta = findPreference("dialogEliminarCuenta");
    }

    private void listenersAplicacion(){
        switchModoOscuro.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                if (switchModoOscuro.isChecked()) {
                    System.out.println("Activando modo oscuro");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    //recreate();
                } else {
                    System.out.println("Desactivando modo oscuro");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //recreate();
                }
                return true;
            }
        });
        //Activa o desactiva los sonidos al entrar a rutinas, etc
        switchSonido.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                if (switchSonido.isChecked()) {
                    System.out.println("Activando sonidos");
                } else {
                    System.out.println("Desactivando sonidos");
                }
                return true;
            }
        });
        listaTemas.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                switch ((String) newValue) {
                    case "MaterialComponents":
                        System.out.println("Tema MaterialComponents");
                        //setTheme();
                        break;
                    case "Material3":
                        System.out.println("Tema Material3");
                        break;
                    case "Verde":
                        System.out.println("Tema Verde");
                        break;
                    case "LearningStrenght":
                        System.out.println("Tema LearningStrenght");
                        break;
                }
                return true;
            }
        });
        listaLetra.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                switch (listaLetra.findIndexOfValue((String) newValue)) {
                    case 0:
                        System.out.println("Tamaño de letra pequeña");
                        break;
                    case 1:
                        System.out.println("Tamaño de letra mediana");
                        break;
                    case 2:
                        System.out.println("Tamaño de letra grande");
                        break;
                }
                return true;
            }
        });
        listaCalculadoras.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                switch (listaCalculadoras.findIndexOfValue((String) newValue)) {
                    case 0:
                        System.out.println("Calculadora Macros");
                        break;
                    case 1:
                        System.out.println("Calculadora Rm");
                        break;
                }
                return true;
            }
        });
    }

    private void listenersCuenta(){
        etCambiarCorreo.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                System.out.println("Nuevo correo: " + newValue);
                return true;
            }
        });
        etCambiarContrasenia.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                System.out.println("Nueva contraseña: " + newValue);
                return true;
            }
        });
        dialogEliminarCuenta.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                // Mostrar confirmacion
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Eliminar cuenta definitivamente");
                builder.setMessage("¿Estas seguro de que deseas eliminar tu cuenta y todos tus datos definitivamente?");
                builder.setPositiveButton("Si, soy un flaco", (dialogInterface, i) -> {
                    firestore.borrarUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    mAuth.getCurrentUser().delete();
                });
                builder.setNegativeButton("No, estoy mas fuerte que el vinagre", (dialogInterface, i) ->
                        Toast.makeText(getContext(), "Asi me gusta", Toast.LENGTH_SHORT).show());
                builder.show();

                return true;
            }
        });
    }

}