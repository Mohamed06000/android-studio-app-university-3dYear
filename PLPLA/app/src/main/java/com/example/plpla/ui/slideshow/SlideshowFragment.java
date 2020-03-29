package com.example.plpla.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plpla.Client;
import com.example.plpla.R;
import com.example.plpla.RecyclerAdapter;
import com.example.plpla.SelectedMatiereActivity;

import java.util.ArrayList;

import matière.UE;

public class SlideshowFragment extends Fragment implements RecyclerAdapter.SelectedMatiere {

    private SlideshowViewModel slideshowViewModel;
    RecyclerView recyclerView;
    Client client=new Client();

    public static RecyclerAdapter getRecyclerAapter() {
        return recyclerAapter;
    }

    static RecyclerAdapter recyclerAapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_recyclerview, container, false);
        setHasOptionsMenu(true);

        //////////////
        ArrayList<UE> okokokok=listesdesmatieres();
        ////////////////

        recyclerView = root.findViewById(R.id.recyclerView);


        ///////////////////////////////////////////////
        recyclerAapter=new RecyclerAdapter(okokokok,this);
        ///////////////////////////////////////////////


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAapter);

        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);


        //recyclerView.setNestedScrollingEnabled(false);



        return root;
    }




    private ArrayList<UE> listesdesmatieres() {
        ArrayList<UE> list = new ArrayList<>();

        list.addAll(client.getListeUEBlocFondement());
        list.addAll(client.getListeUEBlocMethode());
        list.addAll(client.getListeUEBlocFondementS2());
        list.addAll(client.getListeUEBlocMethodeS2());

        return list;
    }

    @Override
    public void selectedMatiere(UE matiere) {
        Intent intent = new Intent(getActivity(), SelectedMatiereActivity.class).putExtra("data", matiere);
        startActivity(intent);




    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_navigation, menu);
        MenuItem item  = menu.findItem(R.id.action_search);
        item.setVisible(true);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_slideshow){
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

}