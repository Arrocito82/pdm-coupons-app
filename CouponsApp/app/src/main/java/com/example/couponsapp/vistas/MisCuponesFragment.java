package com.example.couponsapp.vistas;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.couponsapp.R;
import com.example.couponsapp.adapter.ListAdapterMisCupones;
import com.example.couponsapp.controladores.RegistrarCuponControl;
import com.example.couponsapp.modelos.RegistrarCupon;

import java.util.ArrayList;

public class MisCuponesFragment extends Fragment {

    RecyclerView recyclerView;

    int id_usuario;
    ArrayList<RegistrarCupon> misCuponesList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getArguments();
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mis_cupones, container, false);

        RegistrarCuponControl registrarCuponControl = new RegistrarCuponControl(root.getContext());

        id_usuario = data.getInt("id_user");
        try {
            misCuponesList = registrarCuponControl.traerMisCupones(id_usuario);
            llenarLista(root, misCuponesList);
        }
        catch (SQLiteException sql){
            sql.printStackTrace();
        }

        return root;
    }

    public void llenarLista(View view, ArrayList<RegistrarCupon> lis){
        try {
            ListAdapterMisCupones listAdapterCat = new ListAdapterMisCupones(lis, view.getContext(), new ListAdapterMisCupones.OnItemClickListener() {
                @Override
                public void onItemClick(RegistrarCupon registrarCupon) {

                }
            });
            recyclerView = view.findViewById(R.id.recycleMisCupones);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(listAdapterCat);
        }
        catch (SQLiteException sql){
            sql.printStackTrace();
        }
    }
}