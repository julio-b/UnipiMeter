package unipi.sem7.unipimeter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import unipi.sem7.unipimeter.EventsFragment.OnOsListFragmentInteractionListener;
import unipi.sem7.unipimeter.EventApproachingPOIDoa.EventApproachingPOIjoined;

import java.text.SimpleDateFormat;
import java.util.List;

public class EventApproachingPOIViewAdapter extends RecyclerView.Adapter<EventApproachingPOIViewAdapter.ViewHolder> {

    private final List<EventApproachingPOIjoined> mValues;
    private final OnOsListFragmentInteractionListener mListener;
    public static SimpleDateFormat df = new SimpleDateFormat("EEE d MMM yyyy HH:mm");

    public EventApproachingPOIViewAdapter(List<EventApproachingPOIjoined> items, OnOsListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_approaching_poi, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mValues != null) {
            EventApproachingPOIjoined eapjpoi = mValues.get(position);
            holder.eapjpoi = eapjpoi;
            EventApproachingPOI eap = eapjpoi.eap;
            POI poi = eapjpoi.poi;
            holder.eapPOI.setText(String.format("%s (%.3f km)", poi.title, poi.location.distanceTo(eap.location)/1000.0));
            holder.eapTime.setText(EventOverSpeedViewAdapter.df.format(eap.date));
            holder.eapLon.setText((""+eap.location.getLongitude()));
            holder.eapLat.setText((""+eap.location.getLatitude()));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onEapListFragmentIntercation(holder.eapjpoi);
                    }
                }
            });
        } else {
            holder.eapPOI.setText("NO POI APPROACHING EVENTS");
        }
    }

    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        else return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView eapPOI;
        public final TextView eapTime;
        public final TextView eapLon;
        public final TextView eapLat;
        public EventApproachingPOIjoined eapjpoi;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            eapPOI = (TextView) view.findViewById(R.id.eapPOI);
            eapTime = (TextView) view.findViewById(R.id.eapTime);
            eapLon = (TextView) view.findViewById(R.id.eapLon);
            eapLat = (TextView) view.findViewById(R.id.eapLat);
        }
    }
}
