package com.example.pictureoftheday.viewmodel.epic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.model.RetrofitIml
import com.example.pictureoftheday.model.epic.EPICMainData
import com.example.pictureoftheday.model.epic.EPICMainServerResponseData
import com.example.pictureoftheday.model.pod.PODData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EPICMainViewModel(
    private val liveMainDataToObserve: MutableLiveData<EPICMainData> = MutableLiveData(),
    private val retrofitIml: RetrofitIml = RetrofitIml()
) : ViewModel(

) {
    fun getData(): LiveData<EPICMainData> {
        sendServerRequest()
        return liveMainDataToObserve
    }

    private fun sendServerRequest() {
        liveMainDataToObserve.value = EPICMainData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            PODData.Error(Throwable("You need API key"))
        } else {
            retrofitIml.getEPICMain().getDates(apiKey).enqueue(object :
                Callback<List<EPICMainServerResponseData>> {
                override fun onResponse(
                    call: Call<List<EPICMainServerResponseData>>,
                    responseMain: Response<List<EPICMainServerResponseData>>
                ) {
                    if (responseMain.isSuccessful && responseMain.body() != null) {
                        liveMainDataToObserve.value = EPICMainData.Success(responseMain.body()!!)
                    } else {
                        val message = responseMain.message()
                        if (message.isNullOrEmpty()) {
                            liveMainDataToObserve.value =
                                EPICMainData.Error(Throwable("Unidentified error"))
                        } else {
                            liveMainDataToObserve.value = EPICMainData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<List<EPICMainServerResponseData>>, t: Throwable) {
                    liveMainDataToObserve.value = EPICMainData.Error(t)
                }

            })
        }
    }
}


