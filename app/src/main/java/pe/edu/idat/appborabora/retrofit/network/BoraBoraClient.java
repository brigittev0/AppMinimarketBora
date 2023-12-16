package pe.edu.idat.appborabora.retrofit.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoraBoraClient {

    private static final String BASE_URL = "http://192.168.18.12:8081/borabora/";
    private BoraBoraService boraboraService;

    public BoraBoraClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        boraboraService = retrofit.create(BoraBoraService.class);
    }
    public  BoraBoraService getInstance(){
        return boraboraService;
    }
}

