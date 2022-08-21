package uz.kozimjon.news.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import uz.kozimjon.news.R
import uz.kozimjon.news.adapter.SliderAdapter
import uz.kozimjon.news.databinding.FragmentFirstBinding
import kotlin.math.abs

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var list = ArrayList<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewPager()

        binding.cvNext.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setViewPager() {
        list = ArrayList()
        list.add(R.drawable.image4)
        list.add(R.drawable.image4)
        list.add(R.drawable.image4)
        list.add(R.drawable.ic_launcher_background)
        list.add(R.drawable.ic_launcher_background)

        val sliderAdapter = SliderAdapter(list)
        binding.viewpager.adapter = sliderAdapter
        binding.viewpager.isUserInputEnabled = false

        setIndicator()
        setCompositePage()
        setAutoSlider()
    }

    private fun setAutoSlider() {
        handler = Handler(Looper.myLooper()!!)
        runnable = Runnable {
            binding.viewpager.currentItem = binding.viewpager.currentItem + 1
        }

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)

                if (position == list.size - 1) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.viewpager.currentItem = 0
                    }, 3000)
                }
            }
        })
    }

    private fun setIndicator() {
        binding.indicatorView.apply {
            setSliderWidth(resources.getDimension(R.dimen.dp_10))
            setSliderHeight(resources.getDimension(R.dimen.dp_10))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setupWithViewPager(binding.viewpager)
        }
    }

    private fun setCompositePage() {
        binding.viewpager.clipToPadding = false
        binding.viewpager.clipChildren = false

        binding.viewpager.offscreenPageLimit = 3
        binding.viewpager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer(ViewPager2.PageTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        })

        binding.viewpager.setPageTransformer(compositePageTransformer)
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        handler.removeCallbacks(runnable)
//    }

    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacks(runnable)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)

    }
}