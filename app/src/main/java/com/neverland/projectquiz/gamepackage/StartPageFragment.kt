package com.neverland.projectquiz.gamepackage

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    @SuppressLint("CutPasteId")
    private fun initViews(view: View) {
        val sharedUsername = sharedPreferences.getString(GET_USERNAME, "").toString()
        val mainScore=scoresPreferences.getInt(sharedUsername,0)

        factsPart=view.findViewById(R.id.facts_of_history)
        factsPart?.isEnabled =(mainScore<250)
        factsPart?.isChecked=(mainScore<250)
        if(mainScore>=251) factsPart?.setText(R.string.finished)

        firstPart = view.findViewById(R.id.kingdom_of_van)
        firstPart?.isEnabled =(mainScore in 251..750)
        firstPart?.isChecked=(mainScore in 251..750)
        if(mainScore>=751) firstPart?.setText(R.string.finished)

        secondPart = view.findViewById(R.id.yervanduni_family)
        secondPart?.isEnabled =(mainScore in 751..1350)
        secondPart?.isChecked =(mainScore in 751..1350)
        if(mainScore>=1351) secondPart?.setText(R.string.finished)

        thirdPart = view.findViewById(R.id.artashesyan_family)
        thirdPart?.isEnabled =(mainScore in 1351..2050)
        thirdPart?.isChecked =(mainScore in 1351..2050)
        if(mainScore>=2051) firstPart?.setText(R.string.finished)

        fourthPart = view.findViewById(R.id.arshakuni_family)
        fourthPart?.isEnabled =(mainScore in 2051..2850)
        fourthPart?.isChecked =(mainScore in 2051..2850)
        if(mainScore>=2851) firstPart?.setText(R.string.finished)

//        fifthPart = view.findViewById(R.id.bagratuni_family)
//        fifthPart?.isEnabled =(mainScore>1001)
//        fifthPart?.isChecked =(mainScore>1251)

        startButton = view.findViewById(R.id.start)
        backButton = view.findViewById(R.id.backButton)
    }


}