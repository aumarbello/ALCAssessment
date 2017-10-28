package com.example.ahmed.alcassessment.data.remote;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ahmed on 10/6/17.
 */

public interface ExchangeService {
    @GET("price")
    Single<ResponseBody> getExchangeRate(@Query("fsym") String cryptoCurrency,
                                         @Query("tsyms") String exchangeCurrency);
}
