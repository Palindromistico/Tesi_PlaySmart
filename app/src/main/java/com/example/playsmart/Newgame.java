package com.example.playsmart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Newgame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Newgame extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Newgame() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Newgame.
     */
    // TODO: Rename and change types and number of parameters
    public static Newgame newInstance(String param1, String param2) {
        Newgame fragment = new Newgame();
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

    //TODO funzione per aggiungere partita al db
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_newgame, container, false);

        //TODO Seleziona dai campi disponibili nel db
        String [] campi =
                {"Scegli il campo","6-12 months","1-2 years","2-4 years","4-8 years","8-15 years","Over 15 years",};
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, campi);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        //TODO Seleziona tra orari disponibili per il campo selezionato
        String [] orari =
                {"Scegli l'orario","6-12 months","1-2 years","2-4 years","4-8 years","8-15 years","Over 15 years",};
        Spinner spinner1 = (Spinner) v.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, orari);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter2);

        String [] giocatori =
                {"Quanti giocatori ti mancano?","6-12 months","1-2 years","2-4 years","4-8 years","8-15 years","Over 15 years",};
        Spinner spinner2 = (Spinner) v.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, giocatori);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter3);

        return v;
    }


}