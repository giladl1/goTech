package com.levins.junky.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColorStateList
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.levins.junky.repository.PileRepository
//import com.levins.junky.room.PileDatabase
//import com.levins.junky.room.Piles
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*
import ManagePermissions
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintProperties
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import androidx.recyclerview.widget.GridLayoutManager
import com.levins.junky.*

class MainFragment : Fragment() {
    public lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var repository : PileRepository
    lateinit var viewModel: MainViewModel
    lateinit var permissions :List<String>
    lateinit var lastView: View
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
        viewModel.getQuestions()
        Log.v("operate adapter","before")
        viewModel.questions.observe(viewLifecycleOwner, Observer {
            operateQuestionaireWithList(it)
        })
    }
    private fun engageRepositoryToGetQuestions() {
        viewModel.getQuestions()
        Log.v("operate adapter","before")
        viewModel.questions.observe(viewLifecycleOwner, Observer {
            operateQuestionaireWithList(it)
        })
    }
//    private fun engageRepositoryToSendAnswers() {
//        viewModel.sendAnswers()
//        Log.v("operate adapter","before")
//        viewModel.answers.observe(viewLifecycleOwner, Observer {
//            operateQuestionaireWithList(it)
//        })
//    }


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
//        button.layoutParams = verticalConstraint()
        var params = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        params.topToBottom = lastView.id
        params.startToStart = main.id
        params.setMargins(60)
        button.layoutParams = params
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
        button.setText("Submit")
        main.addView(button)
    }

    private fun createTextQuestionInForm(questionItem: questionsItem?){
        val cardview = CardView(requireContext())
        val textview = TextView(requireContext())
        textview.id = View.generateViewId()
        cardview.id = View.generateViewId()
        val constraintParams = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
//        constraintParams.topMargin = 60
//        constraintParams.bottomMargin = 60
        constraintParams.setMargins(60)
        textview.layoutParams = constraintParams
        cardview.layoutParams = verticalConstraint()
        cardview.radius = 20F
        textview.setText(questionItem?.question.toString())
        cardview.addView(textview)
        ////////////////////////
        val editText = EditText(requireContext())
        val editTextConstraintParams = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        editTextConstraintParams.topToBottom = textview.id
        editTextConstraintParams.topMargin = 120
        editTextConstraintParams.bottomMargin = 60
        editTextConstraintParams.leftMargin = 60
        editText.layoutParams = editTextConstraintParams
        cardview.addView(editText)
        main.addView(cardview)
        lastView = cardview
    }
    private fun createMultiQuestionInForm(questionItem: questionsItem?) {
        val cardview = CardView(requireContext())
        val textview = TextView(requireContext())
//        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        val constraintParams = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
//        constraintParams.topMargin = 60
//        constraintParams.bottomMargin = 60
        constraintParams.setMargins(60)
        textview.layoutParams = constraintParams
        cardview.layoutParams = verticalConstraint()
        cardview.radius = 20F
        textview.setText(questionItem?.question.toString())
        textview.id = View.generateViewId()
//        textview.layoutParams = verticalConstraint()
        cardview.addView(textview)
//        main.addView(textview)
//        lastView = textview
        //////////////////
        val radioGroup = RadioGroup(requireContext())
//        radioGroup.id = View.generateViewId()
        for(answer in questionItem?.answers!!){
            var radioButton = RadioButton(requireContext())
            radioButton.setText(answer.toString())
            radioGroup.addView(radioButton)
        }
//        constraintParams.topToBottom = verticalConstraint()
//        radioGroup.id = View.generateViewId()
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
        engageRepository()
//        engageRepositoryToGetQuestions()

    }
    override fun onResume() {
        super.onResume()
        //check if location permission was approved before:
        val isLocationApproved: Boolean = ContextCompat.checkSelfPermission(
            activity as MainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val isExternalWriteApproved: Boolean = ContextCompat.checkSelfPermission(
            activity as MainActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        Log.v("islocation ",isLocationApproved.toString())
        if(isLocationApproved && isExternalWriteApproved)
            floatingActionButton.backgroundTintList  = getColorStateList(activity as MainActivity,android.R.color.holo_green_light) //resources.getColor(R.color.red)  // setBackgroundTintList(resources.getColor(R.color.red))  //background.setTint(resources.getColor( R.color.red))
        floatingActionButton.setOnClickListener() {//change to AddPileFragment
            ManagePermissions.checkPermissions()

        }
        floatingActionButton.requestFocus()
    }
    public fun refreshRecyclerview(){
        viewAdapter.notifyDataSetChanged()
    }
}


