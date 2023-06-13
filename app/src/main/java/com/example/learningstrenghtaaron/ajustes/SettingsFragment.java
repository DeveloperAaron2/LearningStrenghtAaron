package com.example.learningstrenghtaaron.ajustes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.learningstrenghtaaron.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    private SwitchPreference switchModoOscuro, switchSonido;
    private ListPreference listaTemas, listaLetra, listaCalculadoras;
    private EditTextPreference etCambiarCorreo, etCambiarContrasenia;
    private Preference dialogEliminarCuenta;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
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
    }

}