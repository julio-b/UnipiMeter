package unipi.sem7.unipimeter;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.anastr.speedviewlib.ProgressiveGauge;

public class SpeedometerFragment extends Fragment {

    private SpeedometerViewModel mViewModel;
    private ProgressiveGauge speedometerGauge;

    public static SpeedometerFragment newInstance() {
        return new SpeedometerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.speedometer_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SpeedometerViewModel.class);
        // TODO: Use the ViewModel

        speedometerGauge = (ProgressiveGauge) getView().findViewById(R.id.speedometerGauge);
        ((MainActivity) getActivity()).speedometerFragment = this;
    }

    public void setSpeed(double speed) {
        if (speedometerGauge != null)
            speedometerGauge.speedTo((float) speed);
    }

    public void setVisibility(int visid) {
        if (speedometerGauge != null)
            speedometerGauge.setVisibility(visid);
    }

}
