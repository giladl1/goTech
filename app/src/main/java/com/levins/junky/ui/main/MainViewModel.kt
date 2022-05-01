package com.levins.junky.ui.main
import android.util.Log
import androidx.lifecycle.*
import com.levins.junky.repository.PileRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import model.AnswerInfo

class MainViewModel(private val repository: PileRepository) : ViewModel() {
    private val _questions = MutableLiveData<List<questionsItem>>()
    val questions: LiveData<List<questionsItem>> = _questions

    private val _answers = MutableLiveData<String>()
    val answers: LiveData<String> = _answers



    fun sendAnswers(answers: ArrayList<AnswerInfo>) {
            val channelRepoAnswer = Channel<String?>()
            viewModelScope.launch {
                repository.insertAnswers( answers , channelRepoAnswer)
            }

            viewModelScope.launch() {
                Log.v("piles", "repository.selectAllPiles()")
                channelRepoAnswer.consumeEach { value ->
                    println("Consumer 111: $value")
                    Log.v("channelVerific", "consumeEachViewModelVerific")
                    _answers.value = value
                }

            }
        }


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
