package com.levins.junky.ui.main
import android.util.Log
import androidx.lifecycle.*
import com.levins.junky.numberWithData
import com.levins.junky.repository.PileRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach

class MainViewModel(private val repository: PileRepository) : ViewModel() {
//    var curLong: String? = null
//    var curLat: String? = null //the location params saved when the activity is created
//    var currentPicUri: Uri? = null
//    private val SELECT_PICTURE = 101

    private val _questions = MutableLiveData<List<questionsItem>>()
    val questions: LiveData<List<questionsItem>> = _questions


//    private val _userProfile = MutableLiveData<User>()
//    val userProfile: LiveData<User> = _userProfile
//    private val _posts = MutableLiveData<List<Post>>()
//    val posts: LiveData<List<Post>> = _posts


//    val database : PileDatabase = PileDatabase.getDatabase( )
//    val repository : PileRepository = PileRepository(database.daoAccess()!!)

//    suspend fun operateFirestore() {
//        val db = FirebaseFirestore.getInstance()
//        var resultList: List<Pile>? = null
//        val piles = ArrayList<Pile>()
//
//        resultList  = db.collection("pile").orderBy("time", Query.Direction.DESCENDING)
//            .get().await()
//            .documents.mapNotNull {
//                it.toPile() }//convert documentsnapshop to pile and creating the list
//            }
    //    init {
    fun getQuestions() {
        val channelRepo = Channel<List<questionsItem>?>()
        viewModelScope.launch {
            repository.selectAllPiles2(channelRepo)
        }

        viewModelScope.launch() {
                Log.v("piles", "repository.selectAllPiles()")
                channelRepo.consumeEach { value ->
                    println("Consumer 111: $value")
                    Log.v("channelVerific", "consumeEachViewModelVerific")
                    _questions.value = value
                }

            }
        }
    }

class MainViewModelFactory(private val repository: PileRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
