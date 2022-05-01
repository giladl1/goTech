package model

import android.util.Log
import com.levins.junky.ui.main.questionsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class PilesDataSource {
        private val TAG = "FirebasePileService"

    suspend fun sendAnswersDynamically(channel: Channel<ArrayList<questionsItem>?>) {// : ArrayList<Pile>?{// {//getting the pile list from the firestore

        CoroutineScope(Dispatchers.IO).launch {
//            var questionList = ArrayList<questionsItem>()
            val controller = Controller()
            controller.sendAnswers()
//                controller.sendAnswers()
//            questionList = controller.getQuestions()
//            if(questionList.size>0) {
//                Log.v("the questionList is: ", questionList.toString())
//                channel.send(questionList)
//            }
//            else
//                Log.v("the questionList is: ", "has 0 lines")
//                    val forLoop = async {
//                        val controller = Controller()
//                        numList = controller.start()
//                        //channel.send(numList)
//
//                    }
//                    forLoop.await()
//            Log.v("the questionList is: ", questionList.toString())
//            val check = null
//                    channel.send(numList)
        }


    }

    suspend fun getQuestionsDynamically(channel: Channel<ArrayList<questionsItem>?>) {// : ArrayList<Pile>?{// {//getting the pile list from the firestore

            CoroutineScope(Dispatchers.IO).launch {
                var questionList = ArrayList<questionsItem>()
                val controller = Controller()
//                controller.rawJSON()
//                controller.sendAnswers()
                questionList = controller.getQuestions()
                if(questionList.size>0) {
                    Log.v("the questionList is: ", questionList.toString())
                    channel.send(questionList)
                }
                else
                    Log.v("the questionList is: ", "has 0 lines")
//                    val forLoop = async {
//                        val controller = Controller()
//                        numList = controller.start()
//                        //channel.send(numList)
//
//                    }
//                    forLoop.await()
                    Log.v("the questionList is: ", questionList.toString())
                    val check = null
//                    channel.send(numList)
            }


        }
}




