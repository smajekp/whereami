package pl.dzikiekoty.whereami;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {


    private View view;
    SharedPreferences sharedpreferences;
    public TextView name;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        name = (TextView) view.findViewById(R.id.etName);

        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Name)) {
            name.setText(sharedpreferences.getString(Name, ""));
        }

        Button save = (Button) view.findViewById(R.id.btnSave);
        save.setOnClickListener(this);

        Button clear = (Button) view.findViewById(R.id.btnClear);
        clear.setOnClickListener(this);

        return view;
    }

    public void Save() {
        String n = name.getText().toString();
        int nInt = Integer.parseInt(n);

        if (n != null && nInt == 0 || n == "" || nInt < 0) {

            name.setText("0");
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Name, "0");
            editor.commit();

            Toast.makeText(getActivity(), getResources().getString(R.string.settings_service_off), Toast.LENGTH_SHORT).show();
            Intent serviceIntent2 = new Intent(getActivity(), AddLocationService.class);
            getActivity().stopService(serviceIntent2);

        }

        if (n != null && nInt >= 0 && n != "") {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Name, n);
            editor.commit();

            if (n != null && nInt > 0 && n != "") {

                Toast.makeText(getActivity(), getResources().getString(R.string.settings_service_on), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), getResources().getString(R.string.location_service_saved) + nInt, Toast.LENGTH_SHORT).show();

                Intent serviceIntent = new Intent(getActivity(), AddLocationService.class);
                getActivity().stopService(serviceIntent);

                Intent serviceIntent2 = new Intent(getActivity(), AddLocationService.class);
                getActivity().startService(serviceIntent2);
            }
        }
    }

    public void clear() {
        name.setText("0");
        String n = name.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, "0");
        editor.commit();
        Toast.makeText(getActivity(), getResources().getString(R.string.settings_service_off), Toast.LENGTH_SHORT).show();
        Intent serviceIntent2 = new Intent(getActivity(), AddLocationService.class);
        getActivity().stopService(serviceIntent2);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                Save();
                break;
            case R.id.btnClear:
                clear();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
