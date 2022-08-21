package uz.kozimjon.news.utils

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.kozimjon.news.model.NewsResponse

sealed class NewResource {

    object Loading : NewResource()

    data class Success(val newsResponse: Flow<Response<NewsResponse>>) : NewResource()

    data class Error(val message: String) : NewResource()
}