package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Color(

        @SerializedName("pk")
        var id:String,
        @SerializedName("code")
        var code:String,
        @SerializedName("nom")
        var name:String,
        @SerializedName("modele")
        var modeleId:Int,
        @SerializedName("prix")
        var price:Int
)