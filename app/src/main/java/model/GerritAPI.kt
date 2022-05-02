package model

import com.levins.junky.ui.main.questions
import retrofit2.Call
import retrofit2.http.*

interface GerritAPI {

    @GET("questions")//"numbers"
    fun getData() : Call<questions>

    @Headers("Content-Type: application/json")
    @POST("answers")//users
    fun addAnswer(@Body answerData: AnswerInfo): Call<AnswerInfo>

}