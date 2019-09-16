package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Commande(
        @SerializedName("version")
        val version: String,
        @SerializedName("couleur")
        val couleur: String,
        @SerializedName("options")
        val options: List<String>
)