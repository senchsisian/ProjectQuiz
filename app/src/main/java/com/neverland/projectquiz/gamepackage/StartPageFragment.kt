package com.neverland.projectquiz.gamepackage

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.neverland.projectquiz.*

class StartPageFragment : Fragment() {
    private var factsPart:RadioButton?=null
    private var firstPart: RadioButton? = null
    private var secondPart: RadioButton? = null
    private var thirdPart: RadioButton? = null
    private var fourthPart: RadioButton? = null
    private var fifthPart: RadioButton? = null
    private var startButton: Button? = null
    private var backButton: TextView? = null
    private var currentLevelText: TextView? = null
    private var nextLevelText: TextView? = null
    private var progressLevelText: TextView? = null
    private var progressLevel: ProgressBar? = null
    private var checkedRadioId = ""
    private lateinit var partsPreferences :SharedPreferences
    private lateinit var scoresPreferences: SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infLater = inflater.inflate(R.layout.fragment_start_page, container, false)

        partsPreferences=context!!.getSharedPreferences(PARTS_OF_GAME, Context.MODE_PRIVATE)
        scoresPreferences=context!!.getSharedPreferences(SCORES_OF_GAME, Context.MODE_PRIVATE)
        sharedPreferences=context!!.getSharedPreferences(GET_USERNAME, Context.MODE_PRIVATE)
        initViews(infLater)
        return infLater
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton!!.setOnClickListener {
            (activity as MainActivity).supportActionBar?.show()
            val fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.replace(R.id.main_activity, MenuPageFragment(), MENU_PAGE_FRAGMENT_TAG)
                commit()
            }
        }

        startButton!!.setOnClickListener {
            checkedRadioId = when {
                factsPart?.isChecked == true -> FACTS_OF_HISTORY
                firstPart?.isChecked == true -> KINGDOM_OF_VAN
                secondPart?.isChecked == true -> YERVANDUNIS_FAMILY
                thirdPart?.isChecked == true -> ARTASHESYANS_FAMILY
                fourthPart?.isChecked == true -> ARSHAKUNIS_FAMILY
                fifthPart?.isChecked == true -> BAGRATUNIS_FAMILY
                else -> ""
            }
            partsPreferences.edit()?.putString(PARTS_OF_GAME, checkedRadioId)?.apply()
            val manager = (activity as MainActivity).supportFragmentManager
            val getFragment= OpeningGameDialogFragment()
            getFragment.show(manager, OPENING_GAME_DIALOG_FRAGMENT_TAG)
        }
    }

    @SuppressLint("CutPasteId", "SetTextI18n")
    private fun initViews(view: View) {
        progressLevel=view.findViewById(R.id.progress_level)
        progressLevel!!.max=150
        progressLevel!!.progress=0
        progressLevelText=view.findViewById(R.id.progress_level_text)
        currentLevelText=view.findViewById(R.id.current_level)
        nextLevelText=view.findViewById(R.id.next_level)
        factsPart=view.findViewById(R.id.facts_of_history)
        firstPart = view.findViewById(R.id.kingdom_of_van)
        secondPart = view.findViewById(R.id.yervanduni_family)
        thirdPart = view.findViewById(R.id.artashesyan_family)
        fourthPart = view.findViewById(R.id.arshakuni_family)
        fifthPart = view.findViewById(R.id.bagratuni_family)
        startButton = view.findViewById(R.id.start)
        backButton = view.findViewById(R.id.backButton)

        val sharedUsername = sharedPreferences.getString(GET_USERNAME, "").toString()

        when(val mainScore=scoresPreferences.getInt(sharedUsername,0)){
            in 0..150->{
                factsPart?.isEnabled=true
                factsPart?.isChecked=true
                currentLevelText?.setText(R.string._1)
                nextLevelText?.setText(R.string._2)
                progressLevelText?.text= "$mainScore/150"
                progressLevel!!.progress=mainScore
            }
            in 151..300->{
                firstPart?.isEnabled=true
                firstPart?.isChecked=true
                currentLevelText?.setText(R.string._2)
                nextLevelText?.setText(R.string._3)
                progressLevelText?.text= "$mainScore/300"
                progressLevel!!.progress=mainScore-150
            }
            in 301..450->{
                secondPart?.isEnabled=true
                secondPart?.isChecked=true
                currentLevelText?.setText(R.string._3)
                nextLevelText?.setText(R.string._4)
                progressLevelText?.text= "$mainScore/450"
                progressLevel!!.progress=mainScore-300
            }
            in 451..600->{
                thirdPart?.isEnabled=true
                thirdPart?.isChecked=true
                currentLevelText?.setText(R.string._4)
                nextLevelText?.setText(R.string._5)
                progressLevelText?.text= "$mainScore/600"
                progressLevel!!.progress=mainScore-451
            }
            in 601..750->{
                fourthPart?.isEnabled=true
                fourthPart?.isChecked=true
                currentLevelText?.setText(R.string._5)
                nextLevelText?.setText(R.string._6)
                progressLevelText?.text= "$mainScore/750"
                progressLevel!!.progress=mainScore-600
            }
            else->{
                fifthPart?.isEnabled=true
                fifthPart?.isChecked=true
                currentLevelText?.setText(R.string._6)
                nextLevelText?.setText(R.string._7)
                progressLevelText?.text= "$mainScore/-"
                progressLevel!!.progress=mainScore-750
            }
        }
    }
}