package org.svalero.reactive.service;

import io.reactivex.Observable;
import org.svalero.reactive.model.Coche;
import org.svalero.reactive.model.Usuario;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface CarsAPI {

    @GET("coches")
    Observable<List<Coche>> getCars();

    @GET("usuario/dni/{dni}")
    Observable<Usuario> getUsers(@Path("dni") String dni);

}
