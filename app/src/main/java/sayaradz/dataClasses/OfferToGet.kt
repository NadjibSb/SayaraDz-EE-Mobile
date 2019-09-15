package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName


data class OfferToGet(
        @SerializedName("prix")
            val prix: Int,
        @SerializedName("annonce")
            val annonceId : String,
        @SerializedName("state")
            var etat : Boolean


    )
