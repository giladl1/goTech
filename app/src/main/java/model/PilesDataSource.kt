package model

//import kotlinx.coroutines.flow.flowViaChannel
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.levins.junky.numberWithData
import com.levins.junky.ui.main.questionsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
//import model.Pile.Companion.toPile

class PilesDataSource {
        private val TAG = "FirebasePileService"
        //    lateinit var db: FirebaseFirestore

        suspend fun getPilesDataDynamicly(channel: Channel<ArrayList<questionsItem>?>) {// : ArrayList<Pile>?{// {//getting the pile list from the firestore
//            val db = FirebasePileService.getDatabase()//   FirebaseFirestore.getInstance()
//            Log.v("hashcode: ", db.hashCode().toString())
//            var resultList: List<Pile>? = null
//            val numList = ArrayList<numberWithData>()
            ///////////////////////////////////////////////////

            CoroutineScope(Dispatchers.IO).launch {
                    var questionList = ArrayList<questionsItem>()
                val controller = Controller()
                questionList = controller.start()
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


            ///////////////////////////////////////////////////
//            val docRef = db.collection("pileProduction").orderBy("time", Query.Direction.DESCENDING)
//            val listener = docRef.addSnapshotListener { value, e ->
//                if (e != null) {
//                    Log.w("TAG", "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//                Log.v("getPilesDataDynamicly", "listener worked")
//                val changes: MutableList<DocumentChange> = value!!.documentChanges
//                CoroutineScope(Dispatchers.IO).launch {
//                    val forLoop = async {
//                        for (doc in changes) {//in value
//                            val type = doc.type
//                            val pile = doc.toPile()
//                            if (pile != null) {
//                                if(type==DocumentChange.Type.ADDED)
//                                    piles.add(pile)
//                                else if(type==DocumentChange.Type.REMOVED){
//                                    //todo remove also the image of the pile ?
//                                    piles.remove(pile)
//                                }
//                                else if(type==DocumentChange.Type.MODIFIED){
//                                    val modifiedItemKey = pile.key
//                                    val modifiedPile: Pile? = piles.find { it.key == modifiedItemKey }
////                                    val pileBeforeModify = piles.firstNotNullOf { item -> item.key.takeIf { it ==pile.key } }
//                                    Log.v("pilekey",pile.key)
//                                    Log.v("modif",modifiedPile?.description.toString() )
//                                    val index = piles.indexOf(modifiedPile)//the index of the pile intended to be removed
//                                    Log.v("myindex",index.toString())
//                                    if(index >= 0) {
////                                        val mypile = piles[index]//the pile to be reduced
//                                        Log.v("mypileReduced", modifiedPile.toString())
//                                        piles.remove(piles[piles.indexOf(modifiedPile)])
//                                        piles.add(pile)
//                                        Log.v("mypileAdded", pile.toString())
//                                    }
//                                }
//                                Log.v("getPilesDataDynamicly", "piles.add(pile)")
//                            }
//                        }
//                    }
//                    forLoop.await()
//                    Log.v("getPilesDataDynamicly", "return")
                    //channel.send(numList)//todo return it 29.3.22
//                }//listener
//            }
        }
//        suspend fun getPilesData(): List<Pile>? {//getting the pile list from the firestore
//            val db = FirebaseFirestore.getInstance()
//            var resultList: List<Pile>? = null
//            return try {
//                resultList  = db.collection("pile").orderBy("time", Query.Direction.DESCENDING)
//                    .get()
//                    .await()
//                    .documents.mapNotNull {
//                        it.toPile() }//convert documentsnapshop to pile and creating the list
//                resultList
//            } catch (e: Exception) {
//                Log.e("TAG", "Error getting user details", e)
//                null
//            }
//        }

}




