package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Modele (
        @SerializedName("pk")
        var pk: String,
        @SerializedName("nom")
        var name: String,
        @SerializedName("code")
        var code: String,
        @SerializedName("couleur_set")
        var colors: Set<Color>,
        @SerializedName("image")
        var imageUrl: String
)