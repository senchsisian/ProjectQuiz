package com.neverland.projectquiz.gamepackage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.neverland.projectquiz.R
import com.neverland.projectquiz.database.DataDB
import com.neverland.projectquiz.database.DataModel
import com.neverland.projectquiz.database.GET_DATA
import com.neverland.projectquiz.models.TimerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GamePageFragment : Fragment() {
    private lateinit var answerOne: Button
    private lateinit var answerTwo: Button
    private lateinit var answerThree: Button
    private lateinit var answerFour: Button
    private lateinit var questionText: TextView
    private lateinit var timerText: TextView
    private lateinit var dataDB: DataDB
    private lateinit var getDataList:List<DataModel>
   // private val indexOfData=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataDB= Room.databaseBuilder(this.requireContext(), DataDB::class.java, GET_DATA).build()
        GlobalScope.launch(Dispatchers.IO) {
            getDataList=dataDB.getDataDao().getAllData()
            Log.v("Getting elements in game page", getDataList.toString())
        }
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
}