package sayaradz.dataClasses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AnnoncePost  (
    @SerializedName("vehicule")
    @Expose
    var vehicule : Vehicule ,
    @SerializedName("titre")
    @Expose
    var titre : String ,
    @SerializedName("prix")
    @Expose
     var prix : Int ,
    @SerializedName("commentaires")
    @Expose
    var commentaires : String
)