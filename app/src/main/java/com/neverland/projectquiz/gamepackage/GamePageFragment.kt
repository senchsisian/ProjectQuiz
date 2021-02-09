package com.neverland.projectquiz.gamepackage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neverland.projectquiz.R
import com.neverland.projectquiz.database.DataModel
import com.neverland.projectquiz.models.GamePageViewModel
import com.neverland.projectquiz.models.TimerViewModel
import com.neverland.projectquiz.repo.DataProviderRepo

class GamePageFragment : Fragment() {
    private lateinit var answerOne: Button
    private lateinit var answerTwo: Button
    private lateinit var answerThree: Button
    private lateinit var answerFour: Button
    private lateinit var questionText: TextView
    private lateinit var currentCount: TextView
    private lateinit var timerText: TextView
    private lateinit var dataList: List<DataModel>
    private lateinit var gamePageViewModel: GamePageViewModel

    private var indexOfData = 0
    private var backButton: TextView? = null
    private var answerRight = ""
    private var rightAnswerButtonNumber = 0
    private var countOfRightAnswers = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gamePageViewModel = ViewModelProvider(this).get(GamePageViewModel::class.java)
        context?.let {
            DataProviderRepo.setContextAndInitDb(it)
        }
        gamePageViewModel.getDataFromDB()
        gamePageViewModel.liveDataGameInfo.observe(viewLifecycleOwner, { dataModels ->
            Log.d("getElement", "value posted: $dataModels")
            dataList = dataModels
            showNextQuestion()
        })
        return inflater.inflate(R.layout.fragment_game_page, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        timerView()
        answerOne.setOnClickListener {
            if (rightAnswerButtonNumber == 1) {
                countOfRightAnswers += 1
                currentCount.text = countOfRightAnswers.toString()
            }
            showNextQuestion()
            timerView()
        }
        answerTwo.setOnClickListener {
            if (rightAnswerButtonNumber == 2) {
                countOfRightAnswers += 1
                currentCount.text = countOfRightAnswers.toString()
            }
            showNextQuestion()
            timerView()
        }
        answerThree.setOnClickListener {
            if (rightAnswerButtonNumber == 3) {
                countOfRightAnswers += 1
                currentCount.text = countOfRightAnswers.toString()
            }
            showNextQuestion()
            timerView()
        }
        answerFour.setOnClickListener {
            if (rightAnswerButtonNumber == 4) {
                countOfRightAnswers += 1
                currentCount.text = countOfRightAnswers.toString()
            }
            showNextQuestion()
            timerView()
        }

        backButton?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun showNextQuestion() {
        if (indexOfData >= dataList.size) {
            activity?.supportFragmentManager?.popBackStack()
        } else {
            updateViewsWithData(dataList[indexOfData])
            indexOfData++
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updateViewsWithData(singleData: DataModel) {
        rightAnswerButtonNumber = 0
        answerRight = singleData.answerRight
        answerOne.text=singleData.answer1
        answerTwo.text=singleData.answer2
        answerThree.text=singleData.answer3
        answerFour.text=singleData.answer4
        questionText.text = singleData.question

        rightAnswerButtonNumber=when(answerRight){
            answerOne.text->1
            answerTwo.text->2
            answerThree.text->3
            answerFour.text->4
            else->0
        }
    }

    private fun initViews(view: View) {
        answerOne = view.findViewById(R.id.answer_1)
        answerTwo = view.findViewById(R.id.answer_2)
        answerThree = view.findViewById(R.id.answer_3)
        answerFour = view.findViewById(R.id.answer_4)
        questionText = view.findViewById(R.id.questionText)
        timerText = view.findViewById(R.id.timer_text)
        backButton = view.findViewById(R.id.backButton)
        currentCount = view.findViewById(R.id.scoreText)
    }

    @SuppressLint("SetTextI18n")
    private fun timerView() {
        val timerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
        timerViewModel.start()
        timerViewModel.liveTimerInfo.observe(viewLifecycleOwner, {
            val digits: String = if (it < 10L) "0$it" else it.toString()
            timerText.text = "Մնաց 00:$digits"
        })
    }
}