package sayaradz.dataClasses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Vehicule(
        @SerializedName("kilometrage")
        @Expose
        var kilometrage: Int = 0,
        @SerializedName("date")
        @Expose
        var date: String = "",
       /* @SerializedName("image1")
        @Expose
        var imageVehicle1: String = "", */
        @SerializedName("version")
        @Expose
        var versionPk: String ,
        @SerializedName("modele")
        @Expose
        var modelPk: Int,
        @SerializedName("couleur")
        @Expose
        var color: String

      /*
        @SerializedName("image2")
        @Expose
        var imageVehicle2: String ?,
        @SerializedName("image3")
        @Expose
        var imageVehicle3: String?,
       @SerializedName("options")
        @Expose
        var option: List<String> ?*/



)