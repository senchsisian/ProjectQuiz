package com.neverland.projectquiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.neverland.projectquiz.models.AnswersAndQuestion
import com.neverland.projectquiz.models.TimerModel

const val KEY = "key"

class GamePageFragment : Fragment() {
    private lateinit var answerOne: Button
    private lateinit var answerTwo: Button
    private lateinit var answerThree: Button
    private lateinit var answerFour: Button
    private lateinit var questionText: TextView
    private lateinit var timerText: TextView

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var quiz: DatabaseReference

    private var getData = mutableListOf<AnswersAndQuestion>()
    var quizData = AnswersAndQuestion()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //գրանցում է շտեմարանը
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        quiz = db.getReference(getString(R.string.quiz))
        //իրականցվում է շտեմարանից տվյալների ստացում

        val quizKingdomVan =
            quiz.child(getString(R.string.history)).child(getString(R.string.kingdom_of_van))
        quizKingdomVan.addValueEventListener(valueEventListenerQuiz())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infVar = inflater.inflate(R.layout.fragment_game_page, container, false)
        answerOne = infVar.findViewById(R.id.answer_1)
        answerTwo = infVar.findViewById(R.id.answer_2)
        answerThree = infVar.findViewById(R.id.answer_3)
        answerFour = infVar.findViewById(R.id.answer_4)
        questionText = infVar.findViewById(R.id.questionText)
        timerText = infVar.findViewById(R.id.timer_text)
        val timerModel = ViewModelProvider(this).get(TimerModel::class.java)
        timerModel.start()
        timerModel.timerValue.observe(viewLifecycleOwner, { timerText.text = it.toString()})
        return infVar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun valueEventListenerQuiz() = object : ValueEventListener {
        //շտեմարանից ստանում է վիկտորինայի հարցն ու պատասխանները
        override fun onDataChange(snapshot: DataSnapshot) {
            for (dSnapshot in snapshot.children) {
                val key: Int = dSnapshot.key!!.toInt()
                for (dS in dSnapshot.children) {
                    val key1: String = dS.key.toString()
                    val value: String = dS.value.toString()
                    when (key1) {
                        getString(R.string.answer_1) -> quizData.answer1 = value
                        getString(R.string.answer_2) -> quizData.answer2 = value
                        getString(R.string.answer_3) -> quizData.answer3 = value
                        getString(R.string.answer_4) -> quizData.answer4 = value
                        getString(R.string.right_answer) -> quizData.answerRight = value
                        getString(R.string.ask) -> quizData.question = value
                    }
                    getData.add(key, quizData)
                    Log.v(key1, value)
                }
                Log.v(KEY, key.toString())

            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.v("error", error.message)
        }

    }
}