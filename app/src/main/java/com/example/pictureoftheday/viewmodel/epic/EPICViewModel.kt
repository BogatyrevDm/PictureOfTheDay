package com.example.pictureoftheday.viewmodel.epic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.model.RetrofitIml
import com.example.pictureoftheday.model.epic.EPICPictureData
import com.example.pictureoftheday.model.epic.EPICPictureServerResponseData
import com.example.pictureoftheday.model.pod.PODData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EPICViewModel(
    private val liveMainDataToObserve: MutableLiveData<EPICPictureData> = MutableLiveData(),
    private val retrofitIml: RetrofitIml = RetrofitIml()
) : ViewModel(

) {
    fun getData(data: String): LiveData<EPICPictureData> {
        sendServerRequest(data)
        return liveMainDataToObserve
    }

    private fun sendServerRequest(data: String) {
        liveMainDataToObserve.value = EPICPictureData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            PODData.Error(Throwable("You need API key"))
        } else {
            retrofitIml.getEPICPicture().getPicture(data, apiKey).enqueue(object :
                Callback<List<EPICPictureServerResponseData>> {
                override fun onResponse(
                    call: Call<List<EPICPictureServerResponseData>>,
                    responseMain: Response<List<EPICPictureServerResponseData>>
                ) {
                    if (responseMain.isSuccessful && responseMain.body() != null) {
                        liveMainDataToObserve.value = EPICPictureData.Success(responseMain.body()!!)
                    } else {
                        val message = responseMain.message()
                        if (message.isNullOrEmpty()) {
                            liveMainDataToObserve.value =
                                EPICPictureData.Error(Throwable("Unidentified error"))
                        } else {
                            liveMainDataToObserve.value = EPICPictureData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(
                    call: Call<List<EPICPictureServerResponseData>>,
                    t: Throwable
                ) {
                    liveMainDataToObserve.value = EPICPictureData.Error(t)
                }

            })
        }
    }
}


