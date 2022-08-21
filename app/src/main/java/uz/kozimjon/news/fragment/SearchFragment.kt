package uz.kozimjon.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import uz.kozimjon.news.adapter.SearchAdapter
import uz.kozimjon.news.databinding.FragmentSearchBinding
import uz.kozimjon.news.model.NewsResponse
import uz.kozimjon.news.utils.App
import uz.kozimjon.news.viewmodels.NewsViewModel
import javax.inject.Inject

class SearchFragment : Fragment(), SearchAdapter.OnSearchClickListener {
    @Inject
    lateinit var newsViewModel: NewsViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: SearchAdapter
    lateinit var list: List<NewsResponse.New>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.error.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        newsViewModel.newList.observe(viewLifecycleOwner, Observer {
            binding.searchView.isIconified = false
            binding.progressBar.visibility = View.GONE
            this.list = it

            adapter = SearchAdapter(this@SearchFragment)
            binding.rvSearch.adapter = adapter
            adapter.submitList(it)
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filteredBooks(newText)
                return false
            }
        })

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun filteredBooks(text: String) {
        val filteredList = ArrayList<NewsResponse.New>()
        for (new: NewsResponse.New in list) {
            if (new.source?.name?.lowercase()?.contains(text.lowercase()) == true) {
                filteredList.add(new)
            }
        }

        adapter.submitList(filteredList)

        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSearchClick(new: NewsResponse.New) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(
            new.source?.id,
            new.source?.name,
            new.author,
            new.urlToImage,
            new.description,
            new.content
        )
        findNavController().navigate(action)
    }
}