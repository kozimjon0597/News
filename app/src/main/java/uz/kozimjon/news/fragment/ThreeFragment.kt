package uz.kozimjon.news.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import io.paperdb.Paper
import uz.kozimjon.news.adapter.TopicAdapter
import uz.kozimjon.news.databinding.FragmentThreeBinding
import uz.kozimjon.news.model.Topic
import uz.kozimjon.news.utils.App
import uz.kozimjon.news.viewmodels.NewsViewModel
import javax.inject.Inject

class ThreeFragment : Fragment() {
    @Inject
    lateinit var newsViewModel: NewsViewModel
    private var _binding: FragmentThreeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(requireContext())
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.error.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            binding.nsv.visibility = View.VISIBLE
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        newsViewModel.newList.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            binding.nsv.visibility = View.VISIBLE
            val categories = ArrayList<Topic>()

            for (element in it) {
                categories.add(Topic(element.author, element.urlToImage))
            }

            val adapter = TopicAdapter()
            binding.rvTopics.adapter = adapter
            adapter.submitList(categories)
        })

        binding.cvNext.setOnClickListener {
            val action = ThreeFragmentDirections.actionThreeFragmentToNavigationHome()
            findNavController().navigate(action)

            Paper.book().write("topicId", "123")
        }
    }
}