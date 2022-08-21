package uz.kozimjon.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import uz.kozimjon.news.adapter.CategoryAdapter
import uz.kozimjon.news.databinding.FragmentCategoryBinding
import uz.kozimjon.news.model.Category
import uz.kozimjon.news.utils.App
import uz.kozimjon.news.viewmodels.NewsViewModel
import javax.inject.Inject

class CategoryFragment : Fragment(), CategoryAdapter.OnCategoryClickListener {
    @Inject
    lateinit var newsViewModel: NewsViewModel
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.GONE
            binding.llContent.visibility = View.VISIBLE
        })

        newsViewModel.newList.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            binding.llContent.visibility = View.VISIBLE

            val categories = ArrayList<Category>()

            for (element in it) {
                categories.add(Category(element.author, element.urlToImage))
            }

            val categoryAdapter = CategoryAdapter(this@CategoryFragment)
            binding.rvCategory.adapter = categoryAdapter
            categoryAdapter.submitList(categories)

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCategoryClick(item: Category) {
        Toast.makeText(requireContext(), item.name, Toast.LENGTH_SHORT).show()
    }
}