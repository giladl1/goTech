package model

import com.levins.junky.numbers
import com.levins.junky.ui.main.questions
import com.levins.junky.ui.main.questionsItem
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GerritAPI {
//    @GET("8wJzytQX")
//    suspend fun getData() : numbers

//    @GET("8wJzytQX")
//    fun getData() : Call<numbers>

//    @GET("numbers")//"numbers"
//    fun getData() : Call<numbers>

    @GET("questions")//"numbers"
    fun getData() : Call<questions>

//    @POST("questions")
//    fun pushData(
//        @Body question: questionsItem
//    ):Response<questionsItem>

//    @POST("answers")//api/v1/create
//    suspend fun createEmployee(@Body requestBody: RequestBody): Response<ResponseBody>

//    @Headers("Content-Type: application/json")
//    @POST("users")//users
//    fun addUser(@Body userData: UserInfo): Call<UserInfo>

    @Headers("Content-Type: application/json")
    @POST("answers")//users
    fun addAnswer(@Body answerData: AnswerInfo): Call<AnswerInfo>

}