package com.userinterface.ssuroom.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;

import com.userinterface.ssuroom.R;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchDialogFragment extends DialogFragment {
    public Geocoder geocoder;
    ArrayList<String> addressStrings;
    OnReceiveDataListener onReceiveDataListener;

    public interface OnReceiveDataListener{
        void onReceiveData(String s);
    }

    //onCreate보다 먼저 호출되는 생명주기함수
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onReceiveDataListener=(OnReceiveDataListener)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        geocoder=new Geocoder(getActivity());
        addressStrings=new ArrayList<>();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_address, null);
        SearchView searchView=view.findViewById(R.id.dialog_search);
        ListView listView=view.findViewById(R.id.dialog_list);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    addressStrings.clear();
                    List<Address> addressList=geocoder.getFromLocationName(s,10);
                    if(addressList.size()<1){
                        Toast.makeText(getActivity(),"검색 장소를 찾을 수 없습니다.",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    for(Address address:addressList){
                        String[] splitStr=address.toString().split(",");
                        String str= splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2);
                        addressStrings.add(str);
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,addressStrings);
                    listView.setAdapter(adapter);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("search_dialog",addressStrings.get(i));
                onReceiveDataListener.onReceiveData(addressStrings.get(i));
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
