package com.example.pictureoftheday.viewmodel.pod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.model.RetrofitIml
import com.example.pictureoftheday.model.pod.PODData
import com.example.pictureoftheday.model.pod.PODServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PODViewModel(
    private val liveDataToObserve: MutableLiveData<PODData> = MutableLiveData(),
    private val retrofitIml: RetrofitIml = RetrofitIml()
) : ViewModel(

) {
    fun getData(dayString: String): LiveData<PODData> {
        sendServerRequest(dayString)
        return liveDataToObserve
    }

    private fun sendServerRequest(dayString: String) {
        liveDataToObserve.value = PODData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            PODData.Error(Throwable("You need API key"))
        } else {
            retrofitIml.getPOD().getPictureOfTheDay(apiKey, dayString).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataToObserve.value = PODData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserve.value =
                                PODData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataToObserve.value = PODData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataToObserve.value = PODData.Error(t)
                }

            })
        }
    }
}


