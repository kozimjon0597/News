package uz.kozimjon.news.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import io.paperdb.Paper
import uz.kozimjon.news.adapter.CategoryAdapter
import uz.kozimjon.news.adapter.CategoryOnHomeAdapter
import uz.kozimjon.news.adapter.NewAdapter
import uz.kozimjon.news.adapter.RecommendedNewAdapter
import uz.kozimjon.news.databinding.FragmentHomeBinding
import uz.kozimjon.news.model.CategoryOnHome
import uz.kozimjon.news.model.NewsResponse
import uz.kozimjon.news.utils.App
import uz.kozimjon.news.viewmodels.NewsViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HomeFragment : Fragment(),
    NewAdapter.OnNewClickListener,
    RecommendedNewAdapter.OnRecommendedNewListener, CategoryOnHomeAdapter.OnCategoryClickListener {

    @Inject
    lateinit var newsViewModel: NewsViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val categories = ArrayList<CategoryOnHome>()
    private lateinit var categoryOnHomeAdapter: CategoryOnHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        Paper.init(requireContext())
        setLocale()
        val savedTopic = Paper.book().read<String>("topicId")

        if (savedTopic.isNullOrEmpty()) {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToFirstFragment())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.GONE
            binding.nsv.visibility = View.VISIBLE
        })

        newsViewModel.newList.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            binding.nsv.visibility = View.VISIBLE
            val newAdapter = NewAdapter(this@HomeFragment)
            binding.rvNew.adapter = newAdapter
            newAdapter.submitList(it)

            val recommendedNewAdapter = RecommendedNewAdapter(this@HomeFragment)
            binding.rvRecommended.adapter = recommendedNewAdapter
            recommendedNewAdapter.submitList(it)

            for (element in it) {
                categories.add(CategoryOnHome(element.source?.name))
            }
            categoryOnHomeAdapter = CategoryOnHomeAdapter(this@HomeFragment)
            binding.rvCategory.adapter = categoryOnHomeAdapter
            categoryOnHomeAdapter.submitList(categories)
        })

        binding.rlSearch.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToSearchFragment())
        }
    }

    // Functions

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNewClick(new: NewsResponse.New) {
        val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(
            new.source?.id,
            new.source?.name,
            new.author,
            new.urlToImage,
            new.description,
            new.content
        )
        findNavController().navigate(action)
    }

    override fun onRecommendedNewClick(new: NewsResponse.New) {
        val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(
            new.source?.id,
            new.source?.name,
            new.author,
            new.urlToImage,
            new.description,
            new.content
        )
        findNavController().navigate(action)
    }

    private fun setLocale() {
        val lang = getLang()

        val locale = Locale(lang)
        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.setLocale(locale)

        context?.resources?.updateConfiguration(configuration, context?.resources!!.displayMetrics)
    }

    private fun getLang(): String {
        return Paper.book().read("my_lang") ?: "en"
    }

    override fun onCategoryClick(categoryOnHome: CategoryOnHome) {
        categories.forEach {
            it.checked = false
        }

        categoryOnHome.checked = true
        categoryOnHomeAdapter.submitList(categories)
        categoryOnHomeAdapter.notifyDataSetChanged()
    }
}