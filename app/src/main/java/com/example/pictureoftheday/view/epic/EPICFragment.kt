package com.example.pictureoftheday.view.epic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentEpicBinding
import com.example.pictureoftheday.model.epic.EPICPictureData
import com.example.pictureoftheday.utils.BeginDelayedTransition
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
    private var isExpanded = false

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


                if (!serverResponseData.isNullOrEmpty() && serverResponseData.size > 0 && serverResponseData.get(
                        0
                    ).date != null
                ) {
                    binding.imageViewEpic.load(getImagePath(serverResponseData.get(0).date!!)) {
                        lifecycle(this@EPICFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }

                }

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

        binding.imageViewEpic.setOnClickListener {
            BeginDelayedTransition(binding.epicContainer)
            isExpanded = !isExpanded
            val params: ViewGroup.LayoutParams = binding.imageViewEpic.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageViewEpic.layoutParams = params
            binding.imageViewEpic.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
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