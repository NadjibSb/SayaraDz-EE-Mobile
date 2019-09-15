package sayaradz.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import sayaradz.dataClasses.*


interface ServiceProvider {

    // Getting all the brands
    @GET("api/marque")
    fun getAllMarques(@Header("Authorization") token: String): Call<List<Marque>>

    // Getting modeles by brand
    @GET("api/modele/mobile")
    fun getModelsByMarque(@Header("Authorization") token: String, @Query("marqueId") marqueId: String): Call<List<Modele>>

    // Getting versions by model
    @GET("api/version")
    fun getVersionsByModele(@Header("Authorization") token: String, @Query("modeleId") modeleId: String): Call<List<Version>>


    @GET("api/modele")
    fun getAllModels(@Header("Authorization") token: String): Call<List<Modele>>

    // Getting "fich tech" of a specific version
        @GET("api/fichetechnique/detail/{versionId}")
    fun getFichTechByVersion(@Header("Authorization") token: String, @Path("versionId") versionId: String): Call<FichTech>

    // Getting details of a specific version
    @GET("api/version/detail/{versionId}")
    fun getVersion(@Header("Authorization") token: String, @Path("versionId") versionId: String): Call<Version>

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

    // Getting  selected Announcement detail
    @GET("api/annonce/occasion/detail/{annonceId}")
    fun getAnnounceDetails(@Header("Authorization") token: String ,@Path("annonceId") annonceId: String): Call<Car>


    //Delete annonce using ID
    @DELETE("api/annonce/delete/{annonceId}/")
    fun deleteAnnounce(@Header("Authorization") token: String ,@Path("annonceId") annonceId: String) : Call<ResponseBody>

   /* //Create annonce
    @POST ("api/annonce/create/")
    fun createAnnounce (@Header("Authorization") token: String,@Body annonce :AnnoncePost) : Call<AnnoncePost>*/

    //Update annonce
    @PUT ("api/annonce/update/{annonceId}/")
    fun updateAnnounce (@Header("Authorization") token: String,@Path("annonceId") annonceId: String,@Body annonce :AnnonceUpdate) : Call<AnnonceUpdate>

    //Create annonce
    @Multipart
    @POST ("api/annonce/create/")
    fun createAnnounce (@Header("Authorization") token: String, @Part ("titre")  titre : RequestBody ,
                                                                      @Part ("vehicule") vehicule : RequestBody ,
                                                                      @Part("prix") prix : RequestBody ,
                                                                      @Part("commentaires") commentaires : RequestBody) : Call<ResponseBody>
//Create Vehicule occasion
@Multipart
@POST ("api/vehiculeoccasion/create/")
fun createVehicule (@Header("Authorization") token: String,
                    @Part file : MultipartBody.Part,
                    @Part("kilometrage")  kilometrage : RequestBody ,
                    @Part ("date")  date : RequestBody ,
                    @Part("version") version :RequestBody,
                    @Part("couleur") couleur: RequestBody ,
                    @Part("modele") modele : RequestBody
                    ) : Call<Car>


    // Getting modeles by brand
    @GET("api/refmodele/")
    fun getModelsRefByMarque(@Header("Authorization") token: String, @Query("marqueId") marqueId: String): Call<List<Modele>>

    // Getting versions by model
    @GET("api/refversion")
    fun getVersionsRefByModele(@Header("Authorization") token: String, @Query("modeleId") modeleId: String): Call<List<Version>>




}