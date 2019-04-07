package sayaradz.api

import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import sayaradz.dataClasses.Marque
import sayaradz.dataClasses.Model


interface JsonPlaceHolderApi {

    // Getting all the brands
    @GET("api/marque")
    fun getMarques(@Header("Authorization") token: String): Call<List<Marque>>


    // Getting all the models
    @GET("api/modele")
    fun getModels(@Header("Authorization") token: String): Call<List<Model>>

    @GET("/media/images/marques/{imageName}")
    fun getImage(@Header("Authorization") token: String, @Path("imageName") img: String): Call<ResponseBody>

}

