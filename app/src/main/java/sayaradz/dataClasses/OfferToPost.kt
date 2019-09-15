package sayaradz.dataClasses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OfferToPost(

        @SerializedName("annonce")
        @Expose
        val annonceId : String ,
        @SerializedName("prix")
        @Expose
        val prix: Int

)