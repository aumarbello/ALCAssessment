package com.example.ahmed.alcassessment.data.remote;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ahmed on 10/11/17.
 */

public interface CurrencyService {
    @GET("latest.json")
    Single<Response> getExchangeRate(@Query("app_id") String appId,
                                     @Query("base") String baseCurrency,
                                     @Query("symbols")String targetCurrency);
}
