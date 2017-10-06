package com.example.ahmed.alcassessment.data.remote;

import com.example.ahmed.alcassessment.data.model.RateResponseEUR;
import com.example.ahmed.alcassessment.data.model.RateResponseUSD;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by ahmed on 10/6/17.
 */

public interface ExchangeService {
    @GET("/sub")
    Single<RateResponseUSD> getRateInUSD();

    @GET("/sub")
    Single<RateResponseEUR> getRateInEUR();
}
