package uz.kozimjon.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.kozimjon.news.adapter.BookmarkAdapter
import uz.kozimjon.news.databinding.FragmentBookmarkBinding
import uz.kozimjon.news.model.SavedNew
import uz.kozimjon.news.paper_db.Favourites

class BookmarkFragment : Fragment(), BookmarkAdapter.OnBookmarkListener {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFavourites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFavourites() {
        val favourites = Favourites.getFavourites()

        if (favourites.isEmpty()) {
            binding.llCenter.visibility = View.VISIBLE
        } else {
            binding.llCenter.visibility = View.GONE
        }

        val favouriteAdapter = BookmarkAdapter(this@BookmarkFragment)
        binding.rvFavourites.adapter = favouriteAdapter
        favouriteAdapter.submitList(favourites)
    }

    override fun onBookmarkClick(savedNew: SavedNew) {
        val action = BookmarkFragmentDirections.actionNavigationBookmarkToDetailFragment(
            savedNew.id,
            savedNew.name,
            savedNew.author,
            savedNew.urlToImage,
            savedNew.description,
            savedNew.content
        )
        findNavController().navigate(action)
    }
}