package sayaradz.services

import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface ServiceProvider {

    // Getting all the brands
    @GET("api/marque")
    fun getMarques(@Header("Authorization") token: String): Call<List<Marque>>


    // Getting all the models
    @GET("api/modele")
    fun getModels(@Header("Authorization") token: String): Call<List<Model>>

   /* @GET("/media/images/marques/{imageName}")
    fun getImage(@Header("Authorization") token: String, @Path("imageName") img: String): Call<ResponseBody>*/


    // Getting  Query Result Car
    @GET("api/annonce/{type}")
    fun getResult(@Header("Authorization") token: String ,  @Path("type") type: String?,
                                                                  @Query("date1") date1 :String?,
                                                                  @Query("date2") date2 :String?,
                                                                  @Query("km1") km1 :String?,
                                                                  @Query("km2") km2 :String?,
                                                                  @Query("Marque") Marque :String?,
                                                                  @Query("model") model:String?,
                                                                  @Query("prix1") prix1:String?,
                                                                  @Query("prix2") prix2 :String?)
                                                                   : Call<List<Car>>





}

