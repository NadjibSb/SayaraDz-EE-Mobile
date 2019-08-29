package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Car(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("titre")
        var title: String = "",
        @SerializedName("pseudoUser")
        var userPseudo: String = "",
        @SerializedName("user")
        var userId: String = "",
        @SerializedName("date")
        var date: String = "",
        @SerializedName("image1")
        var imageVehicle1: String = "",
        @SerializedName("image2")
        var imageVehicle2: String = "",
        @SerializedName("image3")
        var imageVehicle3: String = "",
        @SerializedName("kilometrage")
        var kilometrage: Int = 0,
        @SerializedName("prix")
        var prix: Int = 0,
        @SerializedName("vehicule")
        var idVehicule: Int = 0 ,
        @SerializedName("commentaires")
        var commentaires: String ,
       @SerializedName("marque_name")
        var marqueName: String ,
        @SerializedName("model_name")
        var modelName: String
       /* @SerializedName("version_name")
        var versionName: String ,
        @SerializedName("color")
        var color: String ,
        @SerializedName("description")
        var description: String = ""*/



)