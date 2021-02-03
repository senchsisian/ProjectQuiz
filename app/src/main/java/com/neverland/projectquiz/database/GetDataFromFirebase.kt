package com.neverland.projectquiz.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

const val QUIZ="Quiz"
const val HISTORY="History"
const val KINGDOM_OF_VAN="KingdomOfVan"
const val ANSWER_1="Answer1"
const val ANSWER_2="Answer2"
const val ANSWER_3="Answer3"
const val ANSWER_4="Answer4"
const val ANSWER_RIGHT="AnswerRight"
const val QUESTION="Question"

class GetDataFromFirebase {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var quiz: DatabaseReference
    private lateinit var dataDB: DataDB
    private  var dataMutable= mutableListOf<DataModel>()
    private var getElement=DataModel(Random.nextInt(),0,"","","","","","")

    fun getDataFromFirebase(context: Context){
        //գրանցում է SQLLite DataBase
        dataDB= Room.databaseBuilder(context,DataDB::class.java, GET_DATA).build()
        GlobalScope.launch(Dispatchers.IO) {
            dataDB.getDataDao().deleteAll()
            Log.v("Removing elements", getElement.toString())
        }
        //գրանցում է շտեմարանը
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        quiz = db.getReference(QUIZ)

            //շտեմարանից ստանում է վիկտորինայի հարցն ու պատասխանները
            val valueEventListenerQuiz = object : ValueEventListener {
                //շտեմարանից ստանում է վիկտորինայի հարցն ու պատասխանները
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dSnapshot in snapshot.children) {
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
                        getElement.id = key
                        getElement.idKey = Random.nextInt()
                        dataMutable.add(getElement)
                        Log.v("getElement", getElement.toString())
                        getElement = DataModel(Random.nextInt(), 0, "", "", "", "", "", "")
                    }
                    val sizeData=dataMutable.size
                    if(sizeData>0) for (i in 0 until sizeData){
                        Log.v("getElement start",dataMutable.toString())
                        GlobalScope.launch(Dispatchers.IO){dataDB.getDataDao().addData(dataMutable[i])
                            Log.v("getElement in database", dataMutable[i].toString())
                        }
                        Log.v("getElement end",dataMutable.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.v("error", error.message)
                }
            }

            //իրականցվում է շտեմարանից տվյալների ստացում
            val quizKingdomVan = quiz.child(HISTORY).child(KINGDOM_OF_VAN)
            quizKingdomVan.addValueEventListener(valueEventListenerQuiz)
        }
    }
