package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName


data class Marque(
        @SerializedName("pk")
        var id:String,
        @SerializedName("nomMarque")
        var name:String,
        @SerializedName("imageMarque")
        var imageUrl:String)