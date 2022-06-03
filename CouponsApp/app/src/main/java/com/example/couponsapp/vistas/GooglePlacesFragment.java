package com.example.couponsapp.vistas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.couponsapp.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GooglePlacesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GooglePlacesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Elementos
    private Button btn_buscar;
    private EditText edit_nombre;
    private EditText edit_direccion;
    private EditText edit_longitud;
    private EditText edit_latitud;
    private EditText edit_id_maps;
    private ImageView img_negocio;


    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    public GooglePlacesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GooglePlacesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GooglePlacesFragment newInstance(String param1, String param2) {
        GooglePlacesFragment fragment = new GooglePlacesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_google_places, container, false);

        btn_buscar=view.findViewById(R.id.btn_buscar_direccion);
        Places.initialize(getContext(), getString(R.string.api_maps_key));
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(getContext());

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAutocomplete(getContext(), AUTOCOMPLETE_REQUEST_CODE);
            }
        });
        return view;
    }
    public void startAutocomplete(Context context, int requestCode){
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS, Place.Field.ADDRESS);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(context);
        startActivityForResult(intent, requestCode);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Si recibe datos del autocomplete
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                //Inicializando elementos
                edit_direccion=getActivity().findViewById(R.id.edit_direccion_negocio);
                edit_nombre=getActivity().findViewById(R.id.edit_nombre_negocio);
                edit_latitud=getActivity().findViewById(R.id.edit_latitud_negocio);
                edit_longitud=getActivity().findViewById(R.id.edit_longitud_negocio);
                edit_id_maps=getActivity().findViewById(R.id.edit_id_map_negocio);
                img_negocio=getActivity().findViewById(R.id.imageView_negocio);

                //Recuperando datos de la Api
                edit_nombre.setText(place.getName());
                edit_direccion.setText(place.getAddress());
                edit_longitud.setText(String.valueOf(place.getLatLng().longitude));
                edit_latitud.setText(String.valueOf(place.getLatLng().longitude));
                edit_id_maps.setText(place.getId());

                //URL de la imagen
                String url="https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photo_reference=";
                String photo_reference=place.getPhotoMetadatas().get(0).zza();
                url=url+photo_reference+"&key="+getString(R.string.api_maps_key);

                //Imagen
                Picasso.with(getContext()).load(url).into(img_negocio);


                Log.w("Resultado", url);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("Resultado", status.getStatusMessage());
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}