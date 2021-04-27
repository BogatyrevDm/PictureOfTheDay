package com.example.pictureoftheday.view.epic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentEpicBinding
import com.example.pictureoftheday.model.epic.EPICPictureData
import com.example.pictureoftheday.utils.toast
import com.example.pictureoftheday.viewmodel.epic.EPICViewModel

private const val ARG_DATE = "DATE"


class EPICFragment : Fragment() {
    private var _binding: FragmentEpicBinding? = null
    private val binding get() = _binding!!
    private var date: String? = null
    private val viewModel: EPICViewModel by lazy {
        ViewModelProvider(this).get(EPICViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getString(ARG_DATE)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (date != null) {
            viewModel.getData(date!!)
                .observe(viewLifecycleOwner, { renderData(it) })
        }

    }

    private fun renderData(mainData: EPICPictureData?) {
        when (mainData) {
            is EPICPictureData.Success -> {
                val serverResponseData = mainData.mainServerData


                if (!serverResponseData.isNullOrEmpty() && mainData.mainServerData.size > 0 && mainData.mainServerData.get(
                        0
                    ).date != null
                ) {
                    binding.imageViewEpic.load(getImagePath(mainData.mainServerData.get(0).date!!)) {
                        lifecycle(this@EPICFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }

                }

//                if (url.isNullOrEmpty()) {
//                    toast("Link is empty")
//                } else {
//
//                    if (serverResponseData.mediaType == "image") {
//                        binding.webView.isVisible = false
//                        binding.imageView.isVisible = true
//                        binding.imageView.load(serverResponseData.url) {
//                            lifecycle(this@PictureOfTheDayFragment)
//                            error(R.drawable.ic_load_error_vector)
//                            placeholder(R.drawable.ic_no_photo_vector)
//                        }
//
//                    } else{
//                        binding.webView.isVisible = true
//                        binding.imageView.isVisible = false
//                        binding.webView.settings.javaScriptEnabled = true
//                        binding.webView.loadUrl(serverResponseData.url!!)
//                    }
//                    val title = serverResponseData.title
//                    val explanation = serverResponseData.explanation
//                    if (!title.isNullOrEmpty()) {
//                        binding.bottomLayout.bottomSheetDescriptionHeader.text = title
//                    }
//                    if (!explanation.isNullOrEmpty()) {
//                        binding.bottomLayout.bottomSheetDescription.text = explanation
//                    }
//
//                }
            }
            is EPICPictureData.Loading -> {

            }
            is EPICPictureData.Error -> {
                toast(mainData.error.message)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun getImagePath(path: String): String {
        val dateString = date!!.replace('-', '/')
        val apiKey: String = BuildConfig.NASA_API_KEY
        return String.format("https://api.nasa.gov/EPIC/archive/natural/$dateString/png/$path.png?api_key=$apiKey")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(dateString: String) =
            EPICFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DATE, dateString)
                }
            }
    }
}