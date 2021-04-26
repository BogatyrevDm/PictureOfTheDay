package com.example.pictureoftheday.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.*
import com.example.pictureoftheday.databinding.PictureOfTheDayFragmentBinding
import com.example.pictureoftheday.model.Days
import com.example.pictureoftheday.model.PictureOfTheDayData
import com.example.pictureoftheday.utils.getStringDateFromEnum
import com.example.pictureoftheday.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip

class PictureOfTheDayFragment : Fragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: PictureOfTheDayFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PictureOfTheDayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(binding.bottomLayout.root)
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        binding.chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                val day = Days.values()[getChipPositionById(it.id)]
                viewModel.getData(getStringDateFromEnum(day))
            }
        }
    }

    private fun getChipPositionById(id: Int): Int =
        when (id) {
            R.id.chip_1 -> 0
            R.id.chip_2 -> 1
            R.id.chip_3 -> 2
            else -> 0
        }


    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(getStringDateFromEnum(Days.TODAY))
            .observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData?) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverData
                val url =
                    if (serverResponseData.mediaType == "image") serverResponseData.url else serverResponseData.thumbnailUrl
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
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

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

}