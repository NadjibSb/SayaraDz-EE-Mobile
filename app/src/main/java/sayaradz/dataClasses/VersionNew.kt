package sayaradz.dataClasses

data class VersionNew(
        var pk:String,
        var code:String,
        var nom:String,
        var imgs: List<String>,
        var couleurs: List<String>,
        var options: List<Option>,
        var tarif: Int
) {
}