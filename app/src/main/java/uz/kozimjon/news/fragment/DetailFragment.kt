package uz.kozimjon.news.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import uz.kozimjon.news.R
import uz.kozimjon.news.databinding.FragmentDetailBinding
import uz.kozimjon.news.model.SavedNew
import uz.kozimjon.news.paper_db.Favourites

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var isScrolled = false
    private var isChecked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id: String? = arguments?.getString("id")
        val name: String? = arguments?.getString("name")
        val categoryName: String? = arguments?.getString("categoryName")
        val image: String? = arguments?.getString("image")
        val desc: String? = arguments?.getString("desc")
        val content: String? = arguments?.getString("content")

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        Glide.with(requireContext()).load(image).into(binding.ivImage)
        binding.tvName.text = name ?: "No name"
        binding.tvCategoryName.text = categoryName ?: "No category"
        binding.tvDesc.text = desc ?: "No description"
        binding.tvDesc2.text = content ?: "No description"


        val new = SavedNew(id, name, categoryName, image, desc, content)
        if (Favourites.existFavourite(new)) {
            isChecked = true
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill)
        } else {
            isChecked = false
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_border)
        }

        binding.ivBookmark.setOnClickListener {
            if (Favourites.existFavourite(new)) {
                if (isScrolled) {
                    binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_border_black)
                } else {
                    binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_border)
                }
                Favourites.removeFavourite(new)
                isChecked = false
                Toast.makeText(requireContext(), "Saralanganlardan olindi", Toast.LENGTH_SHORT).show()
            } else {
                if (isScrolled) {
                    binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill_black)
                } else {
                    binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill)
                }

                Favourites.addFavourite(new)
                isChecked = true
                Toast.makeText(requireContext(), "Saralanganlarga qo'shildi", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        setMotionListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setMotionListener() {
        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if (p0?.currentState == p0?.startState) {
                    if (isChecked) {
                        binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill)
                    } else {
                        binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_border)
                    }

                    binding.ivBack.setImageResource(R.drawable.ic_back)
                    binding.ivShare.setImageResource(R.drawable.ic_share)
                    isScrolled = false
                } else {
                    if (isChecked) {
                        binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill_black)
                    } else {
                        binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_border_black)
                    }

                    binding.ivBack.setImageResource(R.drawable.ic_back_black)
                    binding.ivShare.setImageResource(R.drawable.ic_share_black)
                    isScrolled = true
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}