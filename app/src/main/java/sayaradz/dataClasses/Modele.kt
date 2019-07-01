package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Modele (
        @SerializedName("code")
        var id: String,
        @SerializedName("nom")
        var name: String,
        @SerializedName("pk")
        var pk: String,
        @SerializedName("couleur_set")
        var colors : List<String>
)