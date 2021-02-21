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
import com.neverland.projectquiz.MainActivity
import com.neverland.projectquiz.R
import com.neverland.projectquiz.SCORES_OF_GAME

class RatingDialogFragment : AppCompatDialogFragment() {

    private lateinit var scoresPreferences: SharedPreferences
    private var rattingScore = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        scoresPreferences =
            context!!.getSharedPreferences(SCORES_OF_GAME, Context.MODE_PRIVATE)
        rattingScore = scoresPreferences.getString(SCORES_OF_GAME, "").toString()

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
            rattingScore.toInt() >= 100 -> R.drawable.rating_three_stars
            rattingScore.toInt() >= 50 -> R.drawable.rating_two_stars
            rattingScore.toInt() > 0 -> R.drawable.rating_star
            else -> R.drawable.sad_smile
        }
        ratingStars.setImageDrawable(getDrawable(requireContext(), getId))
        gettingScores.text = rattingScore
    }
}