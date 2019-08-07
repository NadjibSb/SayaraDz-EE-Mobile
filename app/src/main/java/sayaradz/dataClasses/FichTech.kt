package sayaradz.dataClasses

import com.google.gson.annotations.SerializedName

data class FichTech(
        @SerializedName("pk")
        var id:String,
        @SerializedName("nombrePortes")//
        var nombrePortes:String,
        @SerializedName("boiteVitesse")//
        var boiteVitesse:String,
        @SerializedName("puissanceFiscale")//
        var puissanceFiscale:String,
        @SerializedName("motorisation")//
        var motorisation:String,
        @SerializedName("consommation")//
        var consommation:String,
        @SerializedName("dimensions")
        var dimensions:String,
        @SerializedName("transmission")//
        var transmission:String,
        @SerializedName("version_fiche")
        var version_fiche:String,
        @SerializedName("capaciteReservoir")//
        var capaciteReservoir:String,
        @SerializedName("vitesseMaxi")//
        var vitesseMax:String,
        @SerializedName("acceleration")//
        var acceleration:String
)