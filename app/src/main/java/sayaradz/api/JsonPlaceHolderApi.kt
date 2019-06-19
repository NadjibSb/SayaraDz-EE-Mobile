package sayaradz.api

import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import sayaradz.dataClasses.Marque
import sayaradz.dataClasses.Modele
import sayaradz.dataClasses.Version


interface JsonPlaceHolderApi {

    // Getting all the brands
    @GET("api/marque")
    fun getMarques(@Header("Authorization") token: String): Call<List<Marque>>


    // Getting modeles by brand
    @GET("api/modele")
    fun getModelsByMarque(@Header("Authorization") token: String, @Query("marqueId") marqueId: String): Call<List<Modele>>

    // Getting versions by model
    @GET("api/version")
    fun getVersionsByModele(@Header("Authorization") token: String, @Query("modelId") modeleId: String): Call<List<Version>>

    @GET("/media/images/list/{imageName}")
    fun getImage(@Header("Authorization") token: String, @Path("imageName") img: String): Call<ResponseBody>

}

