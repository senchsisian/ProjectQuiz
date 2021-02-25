package com.neverland.projectquiz.gamepackage

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.neverland.projectquiz.*

class RatingDialogFragment : AppCompatDialogFragment() {

    private lateinit var scoresPreferences: SharedPreferences
    private lateinit var partsPreferences: SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences
    private var rattingScore = 0
    private var getPart = ""
    private var sharedUsername = ""
    private var mainScore=0
    private var partScore=0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Ստանում է վերջին խաղի անցանումը
        partsPreferences =
            context!!.getSharedPreferences(PARTS_OF_GAME, Context.MODE_PRIVATE)
        getPart=partsPreferences.getString(PARTS_OF_GAME, "").toString()
        //Ստանում է խաղացողի օգտանունը
        sharedPreferences =
            context!!.getSharedPreferences(GET_USERNAME, Context.MODE_PRIVATE)
        sharedUsername = sharedPreferences.getString(GET_USERNAME, "").toString()
        //ստանում է խաղացողի տվյալ խաղի մաքսիմալ միավորը և ընդհանուն միավորը
        scoresPreferences =
            context!!.getSharedPreferences(SCORES_OF_GAME, Context.MODE_PRIVATE)
        rattingScore = scoresPreferences.getInt(SCORES_OF_GAME, 0)
        partScore=scoresPreferences.getInt("$sharedUsername $getPart ", 0)
        mainScore=scoresPreferences.getInt(sharedUsername,0)
        //մաքսիմալ միավորը համեմատում է խաղի ընթացիկ միավորի հետ
        partScore=if(rattingScore>partScore) rattingScore else partScore
        scoresPreferences.edit()?.putInt("$sharedUsername $getPart ", partScore)?.apply()
        //ընդհանուր միավորը գումարում է խաղի ընթացիկ միավորին
        mainScore += rattingScore
        scoresPreferences.edit()?.putInt(sharedUsername, mainScore)?.apply()

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inputWindow = LayoutInflater.from(activity as MainActivity)
                .inflate(R.layout.fragment_rating, null)

            setRattingWindowItems(inputWindow)

            builder.setView(inputWindow)
                .setNegativeButton(getText(R.string.close)) { _, _ ->
                    scoresPreferences.edit()?.putString(SCORES_OF_GAME, "0")?.apply()
                    this.dialog?.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException(getText(R.string.activity_error).toString())
    }

    private fun setRattingWindowItems(view: View){

        val gettingScores = view.findViewById<TextView>(R.id.test_of_part)
        val ratingStars = view.findViewById<ImageView>(R.id.picture_of_part)
        val getId = when {
            rattingScore>= 100 -> R.drawable.rating_three_stars
            rattingScore >= 50 -> R.drawable.rating_two_stars
            rattingScore > 0 -> R.drawable.rating_star
            else -> R.drawable.sad_smile
        }
        ratingStars.setImageDrawable(getDrawable(requireContext(), getId))
        gettingScores.text = rattingScore.toString()
    }
}