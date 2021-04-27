package com.example.pictureoftheday.view.epic

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pictureoftheday.databinding.FragmentEpicMainBinding
import com.example.pictureoftheday.model.epic.EPICMainData
import com.example.pictureoftheday.utils.toast
import com.example.pictureoftheday.viewmodel.epic.EPICMainViewModel


class EPICMainFragment : Fragment() {
    private var _binding: FragmentEpicMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: EPICMainViewModel by lazy {
        ViewModelProvider(this).get(EPICMainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpicMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel.getData()
            .observe(viewLifecycleOwner, { renderData(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun renderData(mainData: EPICMainData?) {
        when (mainData) {
            is EPICMainData.Success -> {
                val serverResponseData = mainData.mainServerData
                if (!serverResponseData.isNullOrEmpty()) {
                    val list = mutableListOf<EPICFragment>()
                    //Пока ограничим 10 значениями, потом будет устанавливать количество значений в Settings
                    for (i in 0..9) {
                        val date = serverResponseData[i].date!!
                        list.add(EPICFragment.newInstance(date))
                        binding.tabLayoutEpic.getTabAt(i)?.text = date
                    }
                    binding.viewPagerEpic.adapter = EPICViewPagerAdapter(childFragmentManager, list)
                    binding.tabLayoutEpic.setupWithViewPager(binding.viewPagerEpic)
                    for (i in 0..9) {
                        val date = serverResponseData[i].date!!
                        binding.tabLayoutEpic.getTabAt(i)?.text = date
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
            is EPICMainData.Loading -> {

            }
            is EPICMainData.Error -> {
                toast(mainData.error.message)
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
            EPICMainFragment()
    }
}