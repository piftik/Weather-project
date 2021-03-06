package piftik.github.com.weatherproject;

import java.util.ArrayList;

import piftik.github.com.weatherproject.request.RequestFromOpenWeather;

public interface IForecastLoader {
    void addListener(final IForecastLOaderListener pListener);

    void removeListener(final IForecastLOaderListener pListener);

    void getForecast(final String pCityId);

//    void getForecastForDay(final long pCityId, final int pDays);

    class Impl {
        static IForecastLoader getInstance() {
            return new RequestFromOpenWeather();
        }

    }

    interface IForecastLOaderListener {
        void onSuccess(ArrayList<Weather> pForecast);

        void onError(final int errorCode);
    }
}
