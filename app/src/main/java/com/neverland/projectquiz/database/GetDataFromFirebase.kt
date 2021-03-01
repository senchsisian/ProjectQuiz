package com.neverland.projectquiz.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.neverland.projectquiz.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class GetDataFromFirebase {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var quiz: DatabaseReference
    private lateinit var dataDB: DataDB
    private var dataMutable = mutableListOf<DataModel>()
    private var getElement = DataModel(Random.nextInt(), EMPTY, 0, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)

    fun getDataFromFirebase(context: Context) {
        //մաքրում է SQLLite DataBase
        dataDB = Room.databaseBuilder(context, DataDB::class.java, GET_DATA).build()
        GlobalScope.launch(Dispatchers.IO) {
            dataDB.getDataDao().deleteAll()
        }

        //գրանցում է շտեմարանը
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        quiz = db.getReference(QUIZ)

        //շտեմարանից ստանում է վիկտորինայի հարցն ու պատասխանները
        val valueEventListenerQuiz = object : ValueEventListener {
            //շտեմարանից ստանում է վիկտորինայի հարցն ու պատասխանները
            override fun onDataChange(snapshot: DataSnapshot) {
                for (bSnapshot in snapshot.children) {
                    val column:String = bSnapshot.key.toString()
                    for (dSnapshot in bSnapshot.children) {
                        val key: Int = dSnapshot.key!!.toInt()
                        for (dS in dSnapshot.children) {
                            val key1: String = dS.key.toString()
                            val value: String = dS.value.toString()
                            when (key1) {
                                ANSWER_1 -> getElement.answer1 = value
                                ANSWER_2 -> getElement.answer2 = value
                                ANSWER_3 -> getElement.answer3 = value
                                ANSWER_4 -> getElement.answer4 = value
                                ANSWER_RIGHT -> getElement.answerRight = value
                                QUESTION -> getElement.question = value
                            }
                        }
                        getElement.column = column
                        getElement.id = key
                        getElement.idKey = Random.nextInt()
                        dataMutable.add(getElement)
                        getElement = DataModel(Random.nextInt(), EMPTY, 0, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)
                    }
                }
                val sizeData = dataMutable.size
                if (sizeData > 0) for (i in 0 until sizeData) {
                    GlobalScope.launch(Dispatchers.IO) {
                        dataDB.getDataDao().addData(dataMutable[i])
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.v("error", error.message)
            }
        }

        //իրականցվում է շտեմարանից տվյալների ստացում
        val quizKingdomVan = quiz.child(HISTORY)
        quizKingdomVan.addValueEventListener(valueEventListenerQuiz)
    }
}