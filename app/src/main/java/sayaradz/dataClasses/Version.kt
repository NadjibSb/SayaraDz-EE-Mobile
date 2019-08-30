package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Version(
        @SerializedName("pk")
        var id: String,
        @SerializedName("code")
        var code: String,
        @SerializedName("nom")
        var name: String,
        @SerializedName("modele")
        var model_id: String,
        @SerializedName("modele_name")
        var model_name: String,
        @SerializedName("marque_name")
        var marque_name: String,
        @SerializedName("images")
        var imageUrl: String,

        var imgs: List<String>,

        @SerializedName("prix")
        var price: Int,
        @SerializedName("optionsToModif")
        var options: List<Option>,
        @SerializedName("ficheTechnique")
        var ficheTechnique_id: String
)