package piftik.github.com.weatherproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import piftik.github.com.weatherproject.utils.Constants;

public class CityChoseScreenFragment extends BaseFragment {

    private static final String TAG = CityChoseScreenFragment.class.getSimpleName();
    private OnNewPageCityChosseAddListnener mOnNewLocationSelectedListnener;
    private String mCity;
    private double mLatitude;
    private double mLongitude;
    private TextView mCityChooseEditText;
    private View mGetWeatherButton;

    public static CityChoseScreenFragment newInstance() {
        return new CityChoseScreenFragment();
    }

    public void setOnNewPageCityChooseAdd(final OnNewPageCityChosseAddListnener pOnNewLocationSelectedListnener) {
        mOnNewLocationSelectedListnener = pOnNewLocationSelectedListnener;
    }

    @Override
    public void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);


    }

    @Nullable

    @Override
    public View onCreateView(final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, @Nullable final Bundle pSavedInstanceState) {
        final View view = pInflater.inflate(R.layout.city_chose_screen, pContainer, false);
        mCityChooseEditText = (TextView) view.findViewById(R.id.city_autocomplet_choose_edit_text);
        mGetWeatherButton = view.findViewById(R.id.get_weather_button);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCityChooseEditText.setCursorVisible(false);

        mCityChooseEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View pView) {
                try {
                    final Intent intentToAutocomplete =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
                    startActivityForResult(intentToAutocomplete, Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (final GooglePlayServicesRepairableException pE) {
                    Log.e(TAG, "onCreateView: Place Autocomplete exe" + pE);
                } catch (final GooglePlayServicesNotAvailableException pE) {
                    Log.e(TAG, "onCreateView: Place Autocomplete exe" + pE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(final int pRequestCode, final int pResultCode, final Intent pData) {

        if (pRequestCode == Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (pResultCode == Activity.RESULT_OK) {
                final Place place = PlaceAutocomplete.getPlace(getActivity(), pData);
                mCity = place.getName().toString();
                final LatLng latlong = place.getLatLng();
                mLatitude = latlong.latitude;
                mLongitude = latlong.longitude;

                mCityChooseEditText.setText(mCity);
                mGetWeatherButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View pView) {
                        if (mOnNewLocationSelectedListnener != null) {
                            mOnNewLocationSelectedListnener.onNewPageCityChosseAdd(mCity, mLatitude, mLongitude);
                        }
                    }
                });

            } else if (pResultCode == PlaceAutocomplete.RESULT_ERROR) {
                final Status status = PlaceAutocomplete.getStatus(getActivity(), pData);
                Log.i(TAG, status.getStatusMessage());
            } else if (pResultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "onActivityResult: " + pRequestCode);
            }
        }
    }

    @Override
    public String getTitle() {
        return null;
    }

    public interface OnNewPageCityChosseAddListnener {
        void onNewPageCityChosseAdd(String pCityId, double pLatitude, double pLongitude);
    }
}
