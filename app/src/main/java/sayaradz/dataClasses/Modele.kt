package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Modele (
        @SerializedName("code")
        var code: String,
        @SerializedName("nom")
        var name: String,
        @SerializedName("pk")
        var pk: String,
        @SerializedName("couleur_set")
        var colors : List<String> ,
        @SerializedName("ref_id")
        var refId : Int ,
        @SerializedName("marque_nom")
        var marqueName : String ,
        @SerializedName("marqueId")
        var marqueId : String ,
        @SerializedName("image")
        var imageUrl : String ,
        @SerializedName("couleur")
        var color : List<String>
//=======
       /* @SerializedName("pk")
        var pk: String,
        @SerializedName("nom")
        var name: String,
        @SerializedName("code")
        var code: String,
        @SerializedName("couleur_set")
        var colors: Set<Color>,
        @SerializedName("image")
        var imageUrl: String,
        @SerializedName("ref_id")
        var refId : Int */
)