package com.example.appthibanglaixe.Activity.FragmentBienBao;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appthibanglaixe.Adapter.BienBaoAdapter;
import com.example.appthibanglaixe.R;
import com.example.appthibanglaixe.model.bienbao;
import com.example.appthibanglaixe.uliti.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BienbaoCamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BienbaoCamFragment extends Fragment {
    ListView lstbienbaocam;
    BienBaoAdapter bienBaoAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BienbaoCamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BienbaoCamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BienbaoCamFragment newInstance(String param1, String param2) {
        BienbaoCamFragment fragment = new BienbaoCamFragment();
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

    //face data cho biển báo
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bienbao_cam,container, false);

        //Tạo ra 1 list view
        lstbienbaocam = view.findViewById(R.id.fb_lstbienbaocam);
        ArrayList<bienbao> arraybienbao1 = new ArrayList<>();

        //Biển báo khởi tạo với 2 tham số là hình ảnh và 1 chuỗi nội dung
        arraybienbao1.add(new bienbao(R.drawable.hinhshtruot,"Nội dung 1"));
        arraybienbao1.add(new bienbao(R.drawable.hinhshtruot,"Nội dung 2"));
        arraybienbao1.add(new bienbao(R.drawable.hinhshtruot, "Nội dung 3"));

        //Sử dụng BienBaoAdapter để customer lại listview
        bienBaoAdapter = new BienBaoAdapter(getActivity(),arraybienbao1);

        //Gán lại cho listview
        lstbienbaocam.setAdapter(bienBaoAdapter);
        return view;
    }
}