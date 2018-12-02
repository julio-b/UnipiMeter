package unipi.sem7.unipimeter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import unipi.sem7.unipimeter.POIFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link POI} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class POIRecyclerViewAdapter extends RecyclerView.Adapter<POIRecyclerViewAdapter.ViewHolder> {

    private final List<POI> mValues;
    private final OnListFragmentInteractionListener mListener;

    public POIRecyclerViewAdapter(List<POI> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_poi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mValues != null) {
            POI poi = mValues.get(position);
            holder.poi = poi;
            holder.poiTitle.setText("" + poi.title);
            holder.poiDesc.setText(poi.description);
            holder.poiCategory.setText(poi.category);
            holder.poiLon.setText((""+poi.location.getLongitude()));
            holder.poiLat.setText((""+poi.location.getLatitude()));

            ///location long lat todo

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.poi);
                    }
                }
            });
        } else {
            holder.poiTitle.setText("NO POI");
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
        public final TextView poiTitle;
        public final TextView poiDesc;
        public final TextView poiCategory;
        public final TextView poiLon;
        public final TextView poiLat;
        public POI poi;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            poiTitle = (TextView) view.findViewById(R.id.poiTitle);
            poiDesc = (TextView) view.findViewById(R.id.poiDesc);
            poiCategory = (TextView) view.findViewById(R.id.poiCategory);
            poiLon = (TextView) view.findViewById(R.id.poiLon);
            poiLat = (TextView) view.findViewById(R.id.poiLat);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + poiTitle.getText() + "'";
        }
    }
}
