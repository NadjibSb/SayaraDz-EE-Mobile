package sayaradz.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import sayaradz.dataClasses.Car
import sayaradz.dataClasses.Marque
import sayaradz.dataClasses.Modele
import sayaradz.dataClasses.Version


interface ServiceProvider {

    // Getting all the brands
    @GET("api/marque")
    fun getMarques(@Header("Authorization") token: String): Call<List<Marque>>


    // Getting modeles by brand
    @GET("api/modele/mobile")
    fun getModelsByMarque(@Header("Authorization") token: String, @Query("marqueId") marqueId: String): Call<List<Modele>>

    // Getting versions by model
    @GET("api/version")
    fun getVersionsByModele(@Header("Authorization") token: String, @Query("modelId") modeleId: String): Call<List<Version>>

    // Getting all the models
    @GET("api/modele")
    fun getModels(@Header("Authorization") token: String): Call<List<Modele>>

   /* @GET("/media/images/marques/{imageName}")
    fun getImage(@Header("Authorization") token: String, @Path("imageName") img: String): Call<ResponseBody>*/


    // Getting  Query Result Car
    @GET("api/annonce/{type}")
    fun getResult(@Header("Authorization") token: String ,  @Path("type") type: String,
                                                                  @Query("date1") date1 :String?,
                                                                  @Query("date2") date2 :String?,
                                                                  @Query("km1") km1 :String?,
                                                                  @Query("km2") km2 :String?,
                                                                  @Query("marque") Marque :String?,
                                                                  @Query("modele") model:String?,
                                                                  @Query("prix1") prix1:String?,
                                                                  @Query("prix2") prix2 :String?)
                                                                   : Call<List<Car>>

    // Getting  Announcement for user
    @GET("api/annonce")
    fun getAnnounceByUserId(@Header("Authorization") token: String ): Call<List<Car>>






}

