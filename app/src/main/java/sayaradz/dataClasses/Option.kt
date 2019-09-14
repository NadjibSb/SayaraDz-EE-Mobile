package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Option(
        @SerializedName("ref")
        val pk: String,
        @SerializedName("code")
        val code: String,
        @SerializedName("nom")
        val name: String,
        @SerializedName("prix")
        val price: Int
) {
}