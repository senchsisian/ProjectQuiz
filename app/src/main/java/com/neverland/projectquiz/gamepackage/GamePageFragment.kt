package com.neverland.projectquiz.gamepackage

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neverland.projectquiz.*
import com.neverland.projectquiz.database.DataModel
import com.neverland.projectquiz.models.GamePageViewModel
import com.neverland.projectquiz.models.TimerViewModel
import com.neverland.projectquiz.repo.DataProviderRepo

open class GamePageFragment : Fragment() {
    private lateinit var answerOne: Button
    private lateinit var answerTwo: Button
    private lateinit var answerThree: Button
    private lateinit var answerFour: Button
    private lateinit var questionText: TextView
    private lateinit var currentCount: TextView
    private lateinit var timerText: TextView
    private lateinit var dataList: List<DataModel>
    private lateinit var gamePageViewModel: GamePageViewModel
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var scoresPreferences:SharedPreferences
    private var indexOfData = 0
    private var backButton: TextView? = null
    private var answerRight = ""
    private var rightAnswerButtonNumber = 0
    private var countOfRightAnswers = 0
    private var getCurrentTimer = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Ստանում ենք Room-ից հարցերի լիստը և սկսում խաղը
        sharedPreferences =
            context!!.getSharedPreferences(PARTS_OF_GAME, Context.MODE_PRIVATE)
        scoresPreferences =
            context!!.getSharedPreferences(SCORES_OF_GAME, Context.MODE_PRIVATE)

        val getPart = sharedPreferences.getString(PARTS_OF_GAME, "").toString()
        gamePageViewModel = ViewModelProvider(this).get(GamePageViewModel::class.java)
        context?.let {
            DataProviderRepo.setContextAndInitDb(it)
        }
        gamePageViewModel.getDataFromDB(getPart)
        gamePageViewModel.liveDataGameInfo.observe(viewLifecycleOwner, { dataModels ->
            Log.d("getElement", "value posted: $dataModels")
            dataList = dataModels
            showNextQuestion()
        })
        return inflater.inflate(R.layout.fragment_game_page, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        timerView()
        answerOne.setOnClickListener {
            if (rightAnswerButtonNumber == 1) {
                countOfRightAnswers += getCurrentTimer
                currentCount.text = countOfRightAnswers.toString()
            }
            showNextQuestion()
            timerView()
        }
        answerTwo.setOnClickListener {
            if (rightAnswerButtonNumber == 2) {
                countOfRightAnswers += getCurrentTimer
                currentCount.text = countOfRightAnswers.toString()
            }
            showNextQuestion()
            timerView()
        }
        answerThree.setOnClickListener {
            if (rightAnswerButtonNumber == 3) {
                countOfRightAnswers += getCurrentTimer
                currentCount.text = countOfRightAnswers.toString()
            }
            showNextQuestion()
            timerView()
        }
        answerFour.setOnClickListener {
            if (rightAnswerButtonNumber == 4) {
                countOfRightAnswers += getCurrentTimer
                currentCount.text = countOfRightAnswers.toString()
            }
            showNextQuestion()
            timerView()
        }

        backButton?.setOnClickListener {
            val fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.replace(R.id.main_activity, StartPageFragment(), START_PAGE_FRAGMENT_TAG)
                addToBackStack(null)
                commit()
            }
        }
    }

     private fun showNextQuestion() {
        //իրականացվում է հարցերի հերթափոխում
        if (indexOfData >= dataList.size - 1) {
            scoresPreferences.edit()?.putString(SCORES_OF_GAME, currentCount.text.toString() )?.apply()
            val fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.replace(R.id.main_activity, RatingFragment(), RATING_FRAGMENT_TAG)
                addToBackStack(null)
                commit()
            }
        } else {
            updateViewsWithData(dataList[indexOfData])
            indexOfData++
            timerView()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun updateViewsWithData(singleData: DataModel) {
        // իրականացվում է ֆրագմենտի view-երի արժեքների վերագրում
        rightAnswerButtonNumber = 0
        answerRight = singleData.answerRight
        answerOne.text = singleData.answer1
        answerTwo.text = singleData.answer2
        answerThree.text = singleData.answer3
        answerFour.text = singleData.answer4
        questionText.text = "${indexOfData + 1}. ${singleData.question}"

        rightAnswerButtonNumber = when (answerRight) {
            answerOne.text -> 1
            answerTwo.text -> 2
            answerThree.text -> 3
            answerFour.text -> 4
            else -> 0
        }
    }

    private fun initViews(view: View) {
        //իրականացվում է ֆրագմենտի view-երի ներկայացում
        answerOne = view.findViewById(R.id.answer_1)
        answerTwo = view.findViewById(R.id.answer_2)
        answerThree = view.findViewById(R.id.answer_3)
        answerFour = view.findViewById(R.id.answer_4)
        questionText = view.findViewById(R.id.questionText)
        timerText = view.findViewById(R.id.timer_text)
        backButton = view.findViewById(R.id.backButton2)
        currentCount = view.findViewById(R.id.scoreText)
    }

    @SuppressLint("SetTextI18n")
    fun timerView() {
        //իրականացվում է timer-ի աշխատանքը
        val timerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
        timerViewModel.start()
        timerViewModel.liveTimerInfo.observe(viewLifecycleOwner, {
            val digits: String = if (it < 10L) "0$it" else it.toString()
            getCurrentTimer =
                it.toInt() //Մնացորդային ժամանակը ընդհանուր հաշվին է գումարվելու ճիշտ պատասխանի դեպքում
            timerText.text = "Մնաց 00:$digits"// պատկերում է ժամանակը

            if(it==0L){
               showNextQuestion()
            }
        })

    }

}