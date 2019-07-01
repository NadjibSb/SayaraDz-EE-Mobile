package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName


data class Marque(
        @SerializedName("pk")
        var id:String,
        @SerializedName("nom")
        var name:String,
        @SerializedName("image")
        var imageUrl:String)