package com.example.appthibanglaixe.Activity.FragmentMeoThi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.appthibanglaixe.Adapter.MeoThiAdapter;
import com.example.appthibanglaixe.Adapter.ThucHanhAdapter;
import com.example.appthibanglaixe.R;
import com.example.appthibanglaixe.data.sqDuLieu;
import com.example.appthibanglaixe.model.itemMeo;
import com.example.appthibanglaixe.model.itemTH;
import com.example.appthibanglaixe.model.meothiGroup;
import com.example.appthibanglaixe.model.meothiIterm;
import com.example.appthibanglaixe.model.thuchanhGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThucHanhFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThucHanhFragment extends Fragment {
    private ExpandableListView expandableListView;
    List<thuchanhGroup> mListGroup;
    //    ArrayList<meothiIterm> Itermlist;
    Map<thuchanhGroup, List<itemTH>> mListIterm;
    ThucHanhAdapter thucHanhAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThucHanhFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThucHanhFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThucHanhFragment newInstance(String param1, String param2) {
        ThucHanhFragment fragment = new ThucHanhFragment();
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
        View view = inflater.inflate(R.layout.fragment_thuc_hanh, container, false);
        // Tìm và lấy tham chiếu đến expandableListView từ giao diện người dùng
        expandableListView = view.findViewById(R.id.expandablelistview);

        // Tạo đối tượng sqDuLieu với tham số là đối tượng getActivity()
        sqDuLieu duLieu = new sqDuLieu(getActivity());

        // Lấy danh sách các mục từ đối tượng duLieu
        mListIterm = getListIterm(duLieu);

        // Tạo danh sách mục gốc từ danh sách các mục lấy được
        mListGroup = new ArrayList<>(mListIterm.keySet());

        // Tạo đối tượng ThucHanhAdapter để tùy chỉnh hiển thị cho expandableListView
        thucHanhAdapter = new ThucHanhAdapter(getActivity(), mListGroup, mListIterm);

        // Gán thucHanhAdapter cho expandableListView để hiển thị dữ liệu
        expandableListView.setAdapter(thucHanhAdapter);

        return view;
    }
    private Map<thuchanhGroup, List<itemTH>> getListIterm(sqDuLieu duLieu) {
        // Khởi tạo một đối tượng Map để lưu trữ danh sách các mục
        Map<thuchanhGroup, List<itemTH>> listMap = new HashMap<>();

        // Lấy danh sách meothiIterm từ đối tượng duLieu
        ArrayList<meothiIterm> values = duLieu.getMeoTHi("2","1");

        // Tạo các đối tượng thuchanhGroup để đại diện cho các nhóm
        thuchanhGroup meothiGroup = new thuchanhGroup(1, "Giới Thiệu");
        thuchanhGroup meothiGroup1 = new thuchanhGroup(2, "Bài 1: Chạy theo vòng số 8");
        thuchanhGroup meothiGroup2 = new thuchanhGroup(3, "Bài 2: Đi theo đường thẳng");
        thuchanhGroup meothiGroup3 = new thuchanhGroup(4, "Bài 3: Chạy đường quanh co");
        thuchanhGroup meothiGroup4 = new thuchanhGroup(5, "Bài 4: Chạy đường gồ ghề");

        // Tạo danh sách các mục (itemTH) cho từng nhóm
        List<itemTH> Iterm1 = new ArrayList<>();
        Iterm1.add(new itemTH(1, values.get(0).getNoidung()));

        List<itemTH> Iterm2 = new ArrayList<>();
        ArrayList<meothiIterm> values2 = duLieu.getMeoTHi("2","2");
        Iterm2.add(new itemTH(2, values2.get(0).getNoidung()));

        List<itemTH> Iterm3 = new ArrayList<>();
        ArrayList<meothiIterm> values3 = duLieu.getMeoTHi("2","3");
        Iterm3.add(new itemTH(3, values3.get(0).getNoidung()));

        List<itemTH> Iterm4 = new ArrayList<>();
        ArrayList<meothiIterm> values4 = duLieu.getMeoTHi("2","4");
        Iterm4.add(new itemTH(4, values4.get(0).getNoidung()));

        List<itemTH> Iterm5 = new ArrayList<>();
        ArrayList<meothiIterm> values5 = duLieu.getMeoTHi("2","5");
        Iterm5.add(new itemTH(5, values4.get(0).getNoidung()));

        // Đặt các nhóm và danh sách mục tương ứng vào Map
        listMap.put(meothiGroup, Iterm1);
        listMap.put(meothiGroup1, Iterm2);
        listMap.put(meothiGroup2, Iterm3);
        listMap.put(meothiGroup3, Iterm4);
        listMap.put(meothiGroup4, Iterm5);

        // Trả về Map chứa danh sách các mục
        return listMap;
    }
}