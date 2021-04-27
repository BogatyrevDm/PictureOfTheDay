package com.example.pictureoftheday.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentMainBinding
import com.example.pictureoftheday.view.epic.EPICMainFragment
import com.example.pictureoftheday.view.pod.PODMainFragment
import com.example.pictureoftheday.view.settings.SettingsFragment

class MainFragment : Fragment() {
    var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment_container, PODMainFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.bottom_view_picture_of_the_day -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_container, PODMainFragment.newInstance())
                        ?.addToBackStack(null)
                        ?.commit()
                    true
                }
                R.id.bottom_view_earth -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_container, EPICMainFragment.newInstance())
                        ?.addToBackStack(null)
                        ?.commit()
                    true
                }
                R.id.bottom_view_mars -> {
                    true
                }
                R.id.bottom_view_weather -> {
                    true
                }
                R.id.bottom_view_settings -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_container, SettingsFragment.newInstance())
                        ?.addToBackStack(null)
                        ?.commit()
                    true
                }
                else -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_container, PODMainFragment.newInstance())
                        ?.addToBackStack(null)
                        ?.commit()
                    true
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}
