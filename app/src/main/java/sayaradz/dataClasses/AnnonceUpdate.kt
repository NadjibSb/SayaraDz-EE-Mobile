package sayaradz.dataClasses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AnnonceUpdate  (
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