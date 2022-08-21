package uz.kozimjon.news.fragment

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.paperdb.Paper
import uz.kozimjon.news.R
import uz.kozimjon.news.databinding.FragmentBookmarkBinding
import uz.kozimjon.news.databinding.FragmentLanguageBinding
import uz.kozimjon.news.databinding.FragmentUserBinding
import java.util.*
import kotlin.coroutines.Continuation

class LanguageFragment : Fragment() {
    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()

        binding.rlEnglish.setOnClickListener {
            setLocale("en")
        }

        binding.rlRussian.setOnClickListener {
            setLocale("ru")
        }

        binding.rlUzbek.setOnClickListener {
            setLocale("uz")
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.setLocale(locale)

        context?.resources?.updateConfiguration(configuration, context?.resources!!.displayMetrics)

        Paper.book().write("my_lang", lang)

        setData()
    }

    private fun setData() {
        binding.tvTitle.text = resources.getString(R.string.settings)

        when (Paper.book().read("my_lang") ?: "en") {
            "en" -> {
                binding.ivEnglish.visibility = View.VISIBLE
                binding.ivRussian.visibility = View.GONE
                binding.ivUzbek.visibility = View.GONE

                binding.rlEnglish.setBackgroundColor(Color.parseColor("#475AD7"))
                binding.tvEnglish.setTextColor(Color.WHITE)

                binding.rlRussian.setBackgroundColor(Color.parseColor("#F8F7F7"))
                binding.tvRussian.setTextColor(Color.parseColor("#6E6D6D"))

                binding.rlUzbek.setBackgroundColor(Color.parseColor("#F8F7F7"))
                binding.tvUzbek.setTextColor(Color.parseColor("#6E6D6D"))
            }
            "ru" -> {
                binding.ivEnglish.visibility = View.GONE
                binding.ivRussian.visibility = View.VISIBLE
                binding.ivUzbek.visibility = View.GONE

                binding.rlRussian.setBackgroundColor(Color.parseColor("#475AD7"))
                binding.tvRussian.setTextColor(Color.WHITE)

                binding.rlEnglish.setBackgroundColor(Color.parseColor("#F8F7F7"))
                binding.tvEnglish.setTextColor(Color.parseColor("#6E6D6D"))

                binding.rlUzbek.setBackgroundColor(Color.parseColor("#F8F7F7"))
                binding.tvUzbek.setTextColor(Color.parseColor("#6E6D6D"))
            }
            else -> {
                binding.ivEnglish.visibility = View.GONE
                binding.ivRussian.visibility = View.GONE
                binding.ivUzbek.visibility = View.VISIBLE

                binding.rlUzbek.setBackgroundColor(Color.parseColor("#475AD7"))
                binding.tvUzbek.setTextColor(Color.WHITE)

                binding.rlRussian.setBackgroundColor(Color.parseColor("#F8F7F7"))
                binding.tvRussian.setTextColor(Color.parseColor("#6E6D6D"))

                binding.rlEnglish.setBackgroundColor(Color.parseColor("#F8F7F7"))
                binding.tvEnglish.setTextColor(Color.parseColor("#6E6D6D"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}