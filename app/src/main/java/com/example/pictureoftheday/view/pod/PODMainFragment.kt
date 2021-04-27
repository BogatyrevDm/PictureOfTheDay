package com.example.pictureoftheday.view.pod

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentPodMainBinding

class PODMainFragment : Fragment() {

    private var _binding: FragmentPodMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = PODMainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPodMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = PODViewPagerAdapter(childFragmentManager)
        binding.viewPager.currentItem = 2
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(0)?.text = context?.getString(R.string.day_before_yesterday_text)
        binding.tabLayout.getTabAt(1)?.text = context?.getString(R.string.yesterday_text)
        binding.tabLayout.getTabAt(2)?.text = context?.getString(R.string.today_text)

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}