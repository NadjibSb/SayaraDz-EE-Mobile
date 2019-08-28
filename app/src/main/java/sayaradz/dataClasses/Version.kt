package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Version(
        @SerializedName("code")
        var id: String,
        @SerializedName("nom")
        var name: String,
        @SerializedName("modele_name")
        var modelName: String,
        @SerializedName("images")
        var image: Int,
        @SerializedName("prix")
        var price : Int,
        @SerializedName("model")
        var modelId: Int,
        @SerializedName("pk")
        var pk: String ,
        @SerializedName("marque_name")
        var marqueName: String,
        @SerializedName("ficheTechnique")
        var fichTech: Int,
        @SerializedName("options")
        var options: List<String>


)