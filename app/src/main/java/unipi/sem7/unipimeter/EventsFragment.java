package unipi.sem7.unipimeter;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class EventsFragment extends Fragment {

    private OnOsListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    private static final String ARG_EVENT_TYPE = "event";
    private String eventType = "OverSpeed";

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance(String eventType) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_TYPE, eventType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        if (getArguments() != null) {
            eventType = getArguments().getString(ARG_EVENT_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if (eventType.equals("OverSpeed")) {
                EventOverSpeedDao eosdao = (EventOverSpeedDao) AppDatabase.getDatabase(getActivity().getApplicationContext()).overspeedDao();
                eosdao.getAllOverSpeed().observe(this, new Observer<List<EventOverSpeed>>() {
                    @Override
                    public void onChanged(@Nullable final List<EventOverSpeed> eos) {
                        recyclerView.setAdapter( new EventOverSpeedViewAdapter(eos, mListener));
                    }
                });
            } else {
                EventApproachingPOIDoa eapdao = (EventApproachingPOIDoa) AppDatabase.getDatabase(getActivity().getApplicationContext()).approachingPOIDoa();
                eapdao.getAllApproachingPOIJoined().observe(this, new Observer<List<EventApproachingPOIDoa.EventApproachingPOIjoined>>() {
                    @Override
                    public void onChanged(@Nullable final List<EventApproachingPOIDoa.EventApproachingPOIjoined> eapjpoi) {
                        recyclerView.setAdapter( new EventApproachingPOIViewAdapter(eapjpoi, mListener));
                    }
                });
            }

        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOsListFragmentInteractionListener) {
            mListener = (OnOsListFragmentInteractionListener) context;
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

    public interface OnOsListFragmentInteractionListener {
        void onEosListFragmentInteraction(EventOverSpeed eos);
        void onEapListFragmentIntercation(EventApproachingPOI eap);
    }
}
