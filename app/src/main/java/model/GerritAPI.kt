package model

import com.levins.junky.numbers
import com.levins.junky.ui.main.questions
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GerritAPI {
//    @GET("8wJzytQX")
//    suspend fun getData() : numbers

//    @GET("8wJzytQX")
//    fun getData() : Call<numbers>

//    @GET("numbers")//"numbers"
//    fun getData() : Call<numbers>

    @GET("questions")//"numbers"
    fun getData() : Call<questions>

}