package com.example.pictureoftheday.view.pod

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentPodStartBinding
import com.example.pictureoftheday.model.Days
import com.example.pictureoftheday.model.pod.PODData
import com.example.pictureoftheday.utils.BeginDelayedTransition
import com.example.pictureoftheday.utils.getStringDateFromEnum
import com.example.pictureoftheday.utils.getStringDateWithMonthFromEnum
import com.example.pictureoftheday.viewmodel.pod.PODViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior


private const val ARG_DAY = "DAY"

class PODFragment : Fragment() {
    private var day: Days? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: FragmentPodStartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PODViewModel by lazy {
        ViewModelProvider(this).get(PODViewModel::class.java)
    }
    private var isImageExpanded = false
    private var isDescriptionShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            day = it.getParcelable(ARG_DAY)!!
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setOnClickListener {
            BeginDelayedTransition(binding.constraintContainer)
            isImageExpanded = !isImageExpanded
            val params: ViewGroup.LayoutParams = binding.imageView.layoutParams
            params.height =
                if (isImageExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageView.layoutParams = params
            binding.imageView.scaleType =
                if (isImageExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
        binding.tap.setOnClickListener {
            if (isDescriptionShown) hideDescription() else showDescription()
            isDescriptionShown = !isDescriptionShown
        }
        day?.let {
            binding.date.text = getStringDateWithMonthFromEnum(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun showDescription() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(activity, R.layout.fragment_pod_end)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200
        TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
        constraintSet.applyTo(binding.constraintContainer)
        binding.tap.text = getString(R.string.hide_description)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun hideDescription() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(activity, R.layout.fragment_pod_start)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200
        TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
        constraintSet.applyTo(binding.constraintContainer)
        binding.tap.text = getString(R.string.show_description)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPodStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(getStringDateFromEnum(day!!))
            .observe(viewLifecycleOwner, { renderData(it) })
    }

    override fun onResume() {
        super.onResume()
        binding.webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.webView.onPause()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun renderData(data: PODData?) {
        when (data) {
            is PODData.Success -> {
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
                            lifecycle(this@PODFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }

                    } else {
                        binding.webView.isVisible = true
                        binding.imageView.isVisible = false
                        binding.webView.settings.javaScriptEnabled = true
                        binding.webView.loadUrl(serverResponseData.url!!)
                    }
                    val title = serverResponseData.title
                    val explanation = serverResponseData.explanation
                    if (!title.isNullOrEmpty()) {
                        binding.title.text = title
                    }
                    if (!explanation.isNullOrEmpty()) {
                        binding.description.text = explanation
                    }

                }
            }
            is PODData.Loading -> {

            }
            is PODData.Error -> {
                toast(data.error.message)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            PODFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_DAY, day)
                }
            }
    }
}