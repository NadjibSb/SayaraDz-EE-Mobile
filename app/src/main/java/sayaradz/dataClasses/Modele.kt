package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class Modele (
        @SerializedName("codeModele")
        var id: String,
        @SerializedName("NomModele")
        var name: String
        //@SerializedName("imageModel")
        //var imageUrl: String
)