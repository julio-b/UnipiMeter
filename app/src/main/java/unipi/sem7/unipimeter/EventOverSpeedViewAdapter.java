package unipi.sem7.unipimeter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import unipi.sem7.unipimeter.EventsFragment.OnOsListFragmentInteractionListener;

import java.text.SimpleDateFormat;
import java.util.List;

public class EventOverSpeedViewAdapter extends RecyclerView.Adapter<EventOverSpeedViewAdapter.ViewHolder>{

    private final List<EventOverSpeed> mValues;
    private final OnOsListFragmentInteractionListener mListener;
    public static SimpleDateFormat df = new SimpleDateFormat("EEE d MMM yyyy HH:mm");

    public EventOverSpeedViewAdapter(List<EventOverSpeed> items, OnOsListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_overspeed, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mValues != null) {
            EventOverSpeed eos = mValues.get(position);
            holder.eos = eos;
            holder.osSpeed.setText(eos.speed + " km/h");
            holder.osTime.setText(EventOverSpeedViewAdapter.df.format(eos.date));
            holder.osLon.setText((""+eos.location.getLongitude()));
            holder.osLat.setText((""+eos.location.getLatitude()));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onEosListFragmentInteraction(holder.eos);
                    }
                }
            });
        } else {
            holder.osSpeed.setText("NO OVERSPEED EVENTS");
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
        public final TextView osSpeed;
        public final TextView osTime;
        public final TextView osLon;
        public final TextView osLat;
        public EventOverSpeed eos;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            osSpeed = (TextView) view.findViewById(R.id.osSpeed);
            osTime = (TextView) view.findViewById(R.id.osTime);
            osLon = (TextView) view.findViewById(R.id.osLon);
            osLat = (TextView) view.findViewById(R.id.osLat);
        }
    }
}
