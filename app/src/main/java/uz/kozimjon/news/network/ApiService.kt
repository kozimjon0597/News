package uz.kozimjon.news.network

import retrofit2.http.GET
import uz.kozimjon.news.model.NewsResponse

interface ApiService {

    @GET("/v2/top-headlines?country=us&apiKey=5c68ef6f850c43cfa459f09eb4700285")
    suspend fun getNews(): NewsResponse
}