package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Version(
        @SerializedName("codeVersion")
        var id: String,
        @SerializedName("nomVersion")
        var name: String,
        @SerializedName("modelVersion")
        var model: String,
        @SerializedName("imageVersion")
        var imageUrl: String
)