package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Modele (
        @SerializedName("codeModele")
        var id: String,
        @SerializedName("nom")
        var name: String,
        @SerializedName("marque")
        var marque: String,
        @SerializedName("imageModel")
        var imageUrl: List<String>
)