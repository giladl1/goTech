package com.levins.junky.ui.main

import ManagePermissions
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColorStateList
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.levins.junky.JunkyApplication
import com.levins.junky.MainActivity
import com.levins.junky.R
import com.levins.junky.repository.PileRepository
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch
import model.AnswerInfo


class MainFragment : Fragment() {
    lateinit var repository : PileRepository
    lateinit var viewModel: MainViewModel
    lateinit var permissions :List<String>
    lateinit var lastView: View
    lateinit var results : ArrayList<resultItem>
    private lateinit var ManagePermissions: ManagePermissions
    companion object {
        fun newInstance() = MainFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val PermissionsRequestCode = 123
        permissions= listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE ,Manifest.permission.ACCESS_FINE_LOCATION)
        // Initialize a new instance of ManagePermissions class
        ManagePermissions = ManagePermissions((activity as MainActivity),permissions,PermissionsRequestCode)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        repository = ((activity?.application as JunkyApplication).repository)
        lifecycleScope.launch() {
        }
        (activity as MainActivity).setTitle("Gotech")



        return inflater.inflate(R.layout.main_fragment, container, false)// fragment_add_pile
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lastView = main
    }
    private fun engageRepository() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(repository))
            .get(MainViewModel::class.java )
//        viewModel.getQuestions()
//        Log.v("operate adapter","before")
//        viewModel.questions.observe(viewLifecycleOwner, Observer {
//            operateQuestionaireWithList(it)
//        })
    }
    private fun engageRepositoryToGetQuestions() {
        viewModel.getQuestions()
        Log.v("operate adapter","before")
        viewModel.questions.observe(viewLifecycleOwner, Observer {
            operateQuestionaireWithList(it)
        })
    }
    private fun engageRepositoryToSendAnswers(answers: ArrayList<AnswerInfo>) {
        viewModel.sendAnswers(answers)
        Log.v("operate adapter","before")
        viewModel.answers.observe(viewLifecycleOwner, Observer {
            //operateQuestionaireWithList(it)
        })
    }


    private fun operateQuestionaireWithList(questions: List<questionsItem>?) {
        for(questionItem in questions!!) {
            if(questionItem.type.equals("multiple"))
                createMultiQuestionInForm(questionItem)
            if(questionItem.type.equals("text"))
                createTextQuestionInForm(questionItem)
        }
        createSubmitButton()
    }

    private fun createSubmitButton() {
        val button = Button(requireContext())
        button.id = View.generateViewId()
        var params = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        params.topToBottom = lastView.id
        params.startToStart = main.id
        params.setMargins(60)
        button.layoutParams = params
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
        button.setText("Submit")
        button.setOnClickListener(View.OnClickListener {
            val rootView = view as ViewGroup?
            val viewcount = rootView?.childCount
            Log.v("viewcount" , viewcount.toString())
            val answersToSend = produceAnswerList(results)
            engageRepositoryToSendAnswers(answersToSend)
            cleanForm(results)
        })
        main.addView(button)
    }

    private fun cleanForm(viewsToClean: ArrayList<resultItem>) {
        Toast.makeText(requireContext(), "thank you for answering !", Toast.LENGTH_LONG).show()
        for(myview in viewsToClean){
            if(myview.type.equals("multiple"))
                main.findViewById<RadioGroup>(myview.answerViewId).check(-1)
            else if (myview.type.equals("text"))
                (main.findViewById<EditText>(myview.answerViewId)).setText("")
        }
    }

    //take the answers data from the views in order to send to the server:
    private fun produceAnswerList(results: ArrayList<resultItem>): ArrayList<AnswerInfo> {
        var answersToSend = arrayListOf<AnswerInfo>()
        //results contain the values of the views and questions data came from the server in the beginning:
        for(result in results){
            val question = result.question
            var answer: String = ""
            if(result.type.equals("multiple")) { //result.answerViewId.javaClass == EditText()
                val radioGroupId= result.answerViewId
                val myRadioGroup = main.findViewById<RadioGroup>(radioGroupId)
                val checkRadioText = main.findViewById<RadioButton?>(myRadioGroup.checkedRadioButtonId).text
//                if(checkRadioText.contains(":"))
//                    answer =  otherEditText.text
//                else
                    answer = checkRadioText.toString()

            }
            else { //the answer is in Edittext
                val editTextId = result.answerViewId
                answer = ((main.findViewById<EditText>(editTextId))?.text).toString()
            }
            val myAnswer = AnswerInfo(question,answer)
            answersToSend.add(myAnswer)
        }
        return answersToSend
    }

    private fun createTextQuestionInForm(questionItem: questionsItem?){
        val cardview = CardView(requireContext())
        val textview = TextView(requireContext())
        textview.id = View.generateViewId()
        cardview.id = View.generateViewId()
        val constraintParams = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        constraintParams.setMargins(60)
        textview.layoutParams = constraintParams
        cardview.layoutParams = verticalConstraint()
        cardview.radius = 20F
        if(questionItem?.obligatory!!.equals("yes"))
            textview.setText(questionItem?.question.toString()+"*")
        else
            textview.setText(questionItem?.question.toString())
        cardview.addView(textview)
        val editText = EditText(requireContext())
        editText.id = View.generateViewId()
        val editTextConstraintParams = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        editTextConstraintParams.topToBottom = textview.id
        editTextConstraintParams.topMargin = 120
        editTextConstraintParams.bottomMargin = 60
        editTextConstraintParams.leftMargin = 60
        editText.layoutParams = editTextConstraintParams
        cardview.addView(editText)
        main.addView(cardview)
        //for the final results when submit is pressed:
        val myResult = resultItem(questionItem?.question!!,questionItem.type ,editText.id , -1)

        results.add(myResult)
        lastView = cardview
    }
    private fun createMultiQuestionInForm(questionItem: questionsItem?) {
        val cardview = CardView(requireContext())
        val textview = TextView(requireContext())
        val constraintParams = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        constraintParams.setMargins(60)
        textview.layoutParams = constraintParams
        cardview.layoutParams = verticalConstraint()
        cardview.radius = 20F
        if(questionItem?.obligatory!!.equals("yes"))
            textview.setText(questionItem?.question.toString()+"*")
        else
            textview.setText(questionItem?.question.toString())
        textview.id = View.generateViewId()
        cardview.addView(textview)
        val radioGroup = RadioGroup(requireContext())
        var otherEditText = EditText(requireContext())
        for(answer in questionItem?.answers!!){
            var radioButton = RadioButton(requireContext())
            radioButton.setText(answer.toString())
            radioGroup.addView(radioButton)
            if(answer.contains(":")) {
                otherEditText.id=View.generateViewId()
                radioGroup.addView(otherEditText)
            }
        }
        radioGroup.id = View.generateViewId()
        val radioGroupConstraintParams = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        radioGroupConstraintParams.topToBottom = textview.id
        radioGroupConstraintParams.topMargin = 120
        radioGroupConstraintParams.bottomMargin = 60
        radioGroupConstraintParams.leftMargin = 60
        radioGroup.layoutParams = radioGroupConstraintParams
        cardview.addView(radioGroup)
        main.addView(cardview)
        cardview.id = View.generateViewId()
        lastView = cardview
        //todo finish it:
        val myResult = resultItem(questionItem.question,questionItem.type,radioGroup.id , otherEditText.id)

        results.add(myResult)

    }
    private fun verticalConstraint(): ConstraintLayout.LayoutParams {
        if(lastView.equals(main)) {
            var params = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            params.topToTop = lastView.id
            params.setMargins(40)
            return params
        }
        else{
            var params = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            params.topToBottom = lastView.id
            params.setMargins(40)
            return params
        }

    }
    override fun onStart() {
        super.onStart()
        results = arrayListOf<resultItem>()
        engageRepository()
        engageRepositoryToGetQuestions()

    }
}
data class resultItem(
    val question: String ,
    val type: String ,
    val answerViewId: Int ,
    val other: Int //the id of the textView near the other option , if exists
)


