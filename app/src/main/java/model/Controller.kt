package model

import android.util.Log
import com.google.gson.GsonBuilder
import com.levins.junky.numberWithData
import com.levins.junky.numbers
import com.levins.junky.ui.main.questions
import com.levins.junky.ui.main.questionsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.StringBuilder
import java.util.function.Consumer


class Controller {
    lateinit var numList: ArrayList<numberWithData>//MutableList<numberWithData>
    lateinit var questionList: ArrayList<questionsItem>
    suspend fun start(/*numArray: ArrayList<numberWithData>*/): ArrayList<questionsItem> {


        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(GerritAPI::class.java)
        try {
            val retrofitData: Response<questions> = retrofitBuilder.getData().awaitResponse()
//            val retrofitData: Response<numbers> = retrofitBuilder.getData().awaitResponse()
//            numList = arrayListOf<numberWithData>()  //mutableListOf()
            questionList = arrayListOf<questionsItem>()
            Log.v("aa","bb")
//                Log.v("aa",numList.toString())
            if(retrofitData.isSuccessful){
                Log.v("aa","bb")
                Log.v("aa",retrofitData.body().toString())
                val responseBody: questions? = retrofitData.body()
                val myStringBuilder = StringBuilder()
                for(newQuestion in retrofitData.body()!!){
                    val  kk= newQuestion.question
                    val ss = newQuestion.type
                    //addNewQuestion(myData)
                    questionList.add(newQuestion)
                }
//                for (myData in responseBody?.questions!!) {
//                        myStringBuilder.append(myData.number)
//                        myStringBuilder.append("\n")
//                        addNewNum(myData.number)
//                    }
                    Log.v(
                        "responseBody is :",
                        myStringBuilder.toString()
                    )
//                }
                    /////////////////////////////
//                val responseBody: numbers? = retrofitData.body()
//                    val myStringBuilder = StringBuilder()
//                    for (myData in responseBody?.numbers!!) {
//                        myStringBuilder.append(myData.number)
//                        myStringBuilder.append("\n")
//                        addNewNum(myData.number)
//                    }
                    Log.v(
                        "responseBody is :",
                        myStringBuilder.toString()
                    )
                    numList.sortBy { it.number }
                    Log.v("response after sort ", numList.toString())
            }

        }catch (e:Exception){
            Log.v("retrofitData fail because: ", e.toString())
        }
//        val myRetrofit = CoroutineScope(Dispatchers.IO).async {

//            retrofitData.enqueue(object : Callback<numbers?> {
//                override fun onResponse(call: Call<numbers?>?, response: Response<numbers?>?) {
//                    val responseBody = response?.body()
//                    val myStringBuilder = StringBuilder()
//                    for (myData in responseBody?.numbers!!) {
//                        myStringBuilder.append(myData.number)
//                        myStringBuilder.append("\n")
//                        addNewNum(myData.number)
//                    }
//                    Log.v(
//                        "responseBody is :",
//                        myStringBuilder.toString()
//                    )
//                    numList.sortBy { it.number }
//                    Log.v("response after sort ", numList.toString())
//                }
//
//                override fun onFailure(call: Call<numbers?>?, t: Throwable?) {
//                    Log.v("responseBody is :", t.toString())
//                }
//            })
//        }
//        myRetrofit.await()
        if(questionList.isNullOrEmpty() )
            Log.v("numlist", "is empty")
        return questionList

        ////////////////
//        if(numList.isNullOrEmpty() )
//            Log.v("numlist", "is empty")
//        return numList
    }

    private fun addNewQuestion(newQuestion: questionsItem) {
         questionList.add(newQuestion)
    }

    private fun addNewNum(number: Int) {
        val newNum = numberWithData(number,false)
        val oppositeNumber = numberWithData(-1 * number,false)
        if(numList.contains(oppositeNumber)) {
            val oppositeIndex = numList.indexOf(oppositeNumber)
            //mumber added with true
            val newNumChanged = numberWithData(number, true)
            numList.add(newNumChanged)
            numList.elementAt(oppositeIndex).hasSibling = true
        }
        else numList.add(newNum)
    }
    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"//"https://pastebin.com/raw/"
    }
}