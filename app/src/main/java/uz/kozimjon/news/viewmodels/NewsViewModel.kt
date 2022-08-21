package uz.kozimjon.news.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.kozimjon.news.model.NewsResponse
import uz.kozimjon.news.repository.NewsRepository
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    val newList = MutableLiveData<List<NewsResponse.New>>()
    val error = MutableLiveData<String>()

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            try {
                val response = newsRepository.getNews()

                if (response.status == "ok") {
                    newList.value = response.articles
                } else {
                    error.value = "No data"
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }
}