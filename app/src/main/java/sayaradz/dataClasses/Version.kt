package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Version(
        @SerializedName("CodeVersion")
        var id: String,
        @SerializedName("NomVersion")
        var name: String,
        @SerializedName("imageVersion")
        var imageUrl: String
)