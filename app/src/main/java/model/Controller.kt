package model

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.levins.junky.numberWithData
import com.levins.junky.numbers
import com.levins.junky.ui.main.questions
import com.levins.junky.ui.main.questionsItem
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.StringBuilder
import java.util.function.Consumer


class Controller {
//    lateinit var numList: ArrayList<numberWithData>//MutableList<numberWithData>
    lateinit var questionList: ArrayList<questionsItem>
//    suspend fun sendAnswers(){
////        val answers = arrayListOf<String>()
////        val question = questionsItem(answers,"no","wtf","Fucktext")
////        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
////            .baseUrl(BASE_URL)
////            .build()
////            .create(GerritAPI::class.java)
////        retrofitBuilder.pushData(question)
//
//    }
    suspend fun getQuestions(): ArrayList<questionsItem> {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(GerritAPI::class.java)
        try {
            val retrofitData: Response<questions> = retrofitBuilder.getData().awaitResponse()
            questionList = arrayListOf<questionsItem>()
            Log.v("aa","bb")
            if(retrofitData.isSuccessful){
                Log.v("aa","bb")
                Log.v("aa",retrofitData.body().toString())
                val responseBody: questions? = retrofitData.body()
                val myStringBuilder = StringBuilder()
                for(newQuestion in retrofitData.body()!!){
                    questionList.add(newQuestion)
                }
//                    Log.v(
//                        "responseBody is :",
//                        myStringBuilder.toString()
//                    )
//                    Log.v(
//                        "responseBody is :",
//                        myStringBuilder.toString()
//                    )
//                    numList.sortBy { it.number }
//                    Log.v("response after sort ", numList.toString())
            }

        }catch (e:Exception){
            Log.v("retrofitData fail because: ", e.toString())
        }
        if(questionList.isNullOrEmpty() )
            Log.v("numlist", "is empty")
        return questionList

    }

    fun sendAnswers(answers: ArrayList<AnswerInfo>):String {
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) //"http://api.server.com" change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GerritAPI::class.java)

//        fun<T> buildService(service: Class<T>): T{
//            return retrofit.create(service)
//        }

//        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
//        val userInfo = UserInfo(  userId = null, //id = null,
//            userName = "Alex",
//            userEmail = "alex@gmail.com",
//            userAge = "32",
//            userUid = "164E92FC-D37A-4946-81CB-29DE7EE4B124" )
        for(answerInfo in answers) {
//                val answerInfo =answers[0]
//        val answerInfo2 = AnswerInfo(
//            question = "aaa",
//            answer = "kjkjklj"
//        )
            retrofit.addAnswer(answerInfo).enqueue(
                object : Callback<AnswerInfo> {
                    override fun onFailure(call: Call<AnswerInfo>, t: Throwable) {
                        val aa = "ddd"
                        //onResult(null)
                    }

                    override fun onResponse(
                        call: Call<AnswerInfo>,
                        response: Response<AnswerInfo>
                    ) {
                        val response = response.body()
                        Log.v("response is ", response.toString())
                    }
//                override fun onResponse( call: Call<List<AnswerInfo>>, response: Response<AnswerInfo>) {
//                    val addedUser = response.body()
//                    //onResult(addedUser)
//                }

//
                }
            )
        }

//        val apiService = RestApiService()
//        val userInfo = UserInfo(  id = null,
//            userName = "Alex",
//            userEmail = "alex@gmail.com",
//            userAge = 32,
//            userUid = "164E92FC-D37A-4946-81CB-29DE7EE4B124" )

//        apiService.addUser(userInfo) {
//            if (it?.id != null) {
//                // it = newly added user parsed as response
//                // it?.id = newly added user ID
//            } else {
//                Timber.d("Error registering new user")
//            }
//        }
        return "null"
    }

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"//"https://pastebin.com/raw/"
    }
}