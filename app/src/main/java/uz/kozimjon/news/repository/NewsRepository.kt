package uz.kozimjon.news.repository

import uz.kozimjon.news.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getNews() = apiService.getNews()
}