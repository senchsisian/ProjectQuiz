package com.neverland.projectquiz.gamepackage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.neverland.projectquiz.MENU_PAGE_FRAGMENT_TAG
import com.neverland.projectquiz.MainActivity
import com.neverland.projectquiz.R
import com.neverland.projectquiz.SCORES_OF_GAME
import org.w3c.dom.Text

class RatingFragment : Fragment() {

    private lateinit var scoresPreferences: SharedPreferences
    private var rattingScore=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scoresPreferences =
            context!!.getSharedPreferences(SCORES_OF_GAME, Context.MODE_PRIVATE)
        rattingScore=scoresPreferences.getString(SCORES_OF_GAME, "").toString()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infLater=inflater.inflate(R.layout.fragment_rating, container, false)
        val gettingScores=infLater.findViewById<TextView>(R.id.finish_scoreText)
        val ratingStars=infLater.findViewById<ImageView>(R.id.rating_stars)
        val getId=when{
            rattingScore.toInt()>=100-> R.drawable.rating_three_stars
            rattingScore.toInt()>=50-> R.drawable.rating_two_stars
            rattingScore.toInt()>0-> R.drawable.rating_star
            else-> R.drawable.sad_smile
        }
        ratingStars.setImageDrawable(getDrawable(requireContext(),getId))
        gettingScores.text=rattingScore
        return infLater
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.return_menu).setOnClickListener {
            closeFragment()
        }
        view.findViewById<TextView>(R.id.close_fragment).setOnClickListener {
            closeFragment()
        }
    }
    private fun closeFragment(){
        scoresPreferences.edit()?.putString(SCORES_OF_GAME, "0")?.apply()
        val fragmentTransaction =
            (activity as MainActivity).supportFragmentManager.beginTransaction()
        fragmentTransaction.apply {
            this.replace(R.id.main_activity, MenuPageFragment(), MENU_PAGE_FRAGMENT_TAG)
            commit()
        }
    }
}