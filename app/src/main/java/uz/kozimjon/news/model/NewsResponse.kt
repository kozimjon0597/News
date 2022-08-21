package uz.kozimjon.news.model

data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<New>?
) {
    data class New(
        val source: Source?,
        val author: String?,
        val title: String?,
        val description: String?,
        val urlToImage: String?,
        val publishedAt: String?,
        val content: String?
    ) {
        data class Source(
            val id: String?,
            val name: String?
        )
    }
}
