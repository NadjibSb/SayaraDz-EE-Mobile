package sayaradz.dataClasses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VehiculeUpdate (
        @SerializedName("kilometrage")
        @Expose
        var kilometrage: Int = 0,
        @SerializedName("date")
        @Expose
        var date: String = "",
        @SerializedName("couleur")
        @Expose
        var color: String,
        @SerializedName("image1")
        @Expose
        var image1: String,
        @SerializedName("image2")
        @Expose
        var image2: String,
        @SerializedName("image3")
        @Expose
        var image3: String
)