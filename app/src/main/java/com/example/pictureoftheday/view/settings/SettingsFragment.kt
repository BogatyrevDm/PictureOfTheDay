package com.example.pictureoftheday.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.MainActivity
import com.example.pictureoftheday.databinding.FragmentSettingsBinding
import com.example.pictureoftheday.utils.BLUE_THEME
import com.example.pictureoftheday.utils.MAIN_THEME
import com.example.pictureoftheday.utils.RED_THEME
import com.example.pictureoftheday.utils.setAppTheme
import com.google.android.material.radiobutton.MaterialRadioButton


class SettingsFragment : Fragment() {
    var _binding: FragmentSettingsBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initThemeChooser()
    }

    private fun initThemeChooser() {
        initRadioButtonListener(binding.rbMainTheme, MAIN_THEME)
        initRadioButtonListener(binding.rbRedTheme, RED_THEME)
        initRadioButtonListener(binding.rbBlueTheme, BLUE_THEME)
        with(binding.rbGroup.getChildAt(getMainActivity().themeChoosen!!) as MaterialRadioButton) {
            isChecked = true
        }
    }

    private fun getMainActivity(): MainActivity {
        return activity as MainActivity
    }

    private fun initRadioButtonListener(view: View, theme: Int) {
        view.setOnClickListener {
            val activityMain = activity as MainActivity
            activityMain.apply {
                themeChoosen = theme
                setAppTheme(themeChoosen, this)
                recreate()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment()
    }
}
