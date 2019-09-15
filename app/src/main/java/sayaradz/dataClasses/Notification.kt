package sayaradz.dataClasses

data class Notification(
        val type: String,
        val ownerName: String,
        val ownerImg: String,
        val date: Long,
        val content: String,
        val img: String
)