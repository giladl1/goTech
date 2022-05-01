package com.levins.junky.repository

import android.content.Context
import android.util.Log
import com.levins.junky.ui.main.questionsItem
//import com.levins.junky.room.DaoAccess
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import model.AnswerInfo
import model.PilesDataSource
import kotlin.collections.ArrayList

class PileRepository(/*private val pileDao: DaoAccess,*/ val applicationContext: Context) {


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.

            public suspend fun selectAllPiles2(channelRepo:Channel<List<questionsItem>?>) {//: List<Piles>? {// suspend
                    sendQuestions(channelRepo)
            }
            //happen when db is empty. usually when the application was just installed:
            private suspend fun sendQuestions(channelRepo: Channel<List<questionsItem>?>) {
                //val channelFirestore = Channel<ArrayList<numberWithData>?>()
                val channelRetrofit = Channel<ArrayList<questionsItem>?>()
                var questionsFromRetrofit: List<questionsItem>? = null
                    val jobReceiver = CoroutineScope(currentCoroutineContext()).launch {

                        channelRetrofit.consumeEach { value ->
                            Log.v("channelVerific", "consumeEachViewModelVerific")
                            questionsFromRetrofit = value
                            /////////////////////
                            channelRepo.send(value)
                        }//consume loop
                    }
                    val jobSender = CoroutineScope(currentCoroutineContext()).launch {
                        val dataSource = PilesDataSource()
                        dataSource.getQuestionsDynamically(channelRetrofit)
                    }
                }

            suspend fun insertAnswers(
                answers: ArrayList<AnswerInfo>,
                channelRepoAnswer: Channel<String?>
            ) {
                    //val channelFirestore = Channel<ArrayList<numberWithData>?>()
                    val channelRetrofit = Channel<String?>()
                    var answerFromRetrofit: String? //List<questionsItem>? = null
                    val jobReceiver = CoroutineScope(currentCoroutineContext()).launch {

                        channelRetrofit.consumeEach { value ->
                            Log.v("channelVerific", "consumeEachViewModelVerific")
                            answerFromRetrofit = value
                            /////////////////////
                            channelRepoAnswer.send(value)
                        }//consume loop
                    }
                    val jobSender = CoroutineScope(currentCoroutineContext()).launch {
                        val dataSource = PilesDataSource()
                        dataSource.sendAnswersDynamically( answers,channelRetrofit)
                    }

            }
            // By default Room runs suspend queries off the main thread, therefore, we don't need to
            // implement anything else to ensure we're not doing long running database work
            // off the main thread.
        }



