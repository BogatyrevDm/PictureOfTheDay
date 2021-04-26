package com.example.pictureoftheday.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentPictureOfTheDayBinding
import com.example.pictureoftheday.databinding.PictureOfTheDayFragmentMainBinding
import com.example.pictureoftheday.model.Days
import com.example.pictureoftheday.model.PictureOfTheDayData
import com.example.pictureoftheday.utils.getStringDateFromEnum
import com.example.pictureoftheday.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

private const val ARG_DAY = "DAY"

class PictureOfTheDayFragment : Fragment() {
    private var day: Days? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            day = it.getParcelable(ARG_DAY)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(binding.bottomLayout.root)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(getStringDateFromEnum(day!!))
            .observe(viewLifecycleOwner, { renderData(it) })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun renderData(data: PictureOfTheDayData?) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverData
                val url =
                    if (serverResponseData.mediaType == "image") serverResponseData.url else serverResponseData.thumbnailUrl
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {

                    if (serverResponseData.mediaType == "image") {
                        binding.webView.isVisible = false
                        binding.imageView.isVisible = true
                        binding.imageView.load(serverResponseData.url) {
                            lifecycle(this@PictureOfTheDayFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }

                    } else{
                        binding.webView.isVisible = true
                        binding.imageView.isVisible = false
                        binding.webView.settings.javaScriptEnabled = true
                        binding.webView.loadUrl(serverResponseData.url!!)
                    }
                    val title = serverResponseData.title
                    val explanation = serverResponseData.explanation
                    if (!title.isNullOrEmpty()) {
                        binding.bottomLayout.bottomSheetDescriptionHeader.text = title
                    }
                    if (!explanation.isNullOrEmpty()) {
                        binding.bottomLayout.bottomSheetDescription.text = explanation
                    }

                }
            }
            is PictureOfTheDayData.Loading -> {

            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(day: Days) =
            PictureOfTheDayFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_DAY, day)
                }
            }
    }
}