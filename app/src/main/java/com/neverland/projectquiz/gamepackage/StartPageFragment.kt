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
    private var firstPart: RadioButton? = null
    private var secondPart: RadioButton? = null
    private var thirdPart: RadioButton? = null
    private var fourthPart: RadioButton? = null
    private var startButton: Button? = null
    private var backButton: TextView? = null
    private var checkedRadioId = ""
    private lateinit var sharedPreferences :SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infLater = inflater.inflate(R.layout.fragment_start_page, container, false)
        sharedPreferences=context!!.getSharedPreferences(PARTS_OF_GAME, Context.MODE_PRIVATE)
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
                firstPart?.isChecked == true -> KINGDOM_OF_VAN
                secondPart?.isChecked == true -> ARTASHESYANS_FAMILY
                thirdPart?.isChecked == true -> ARSHAKUNIS_FAMILY
                fourthPart?.isChecked == true -> BAGRATUNIS_FAMILY
                else -> ""
            }
            sharedPreferences.edit()?.putString(PARTS_OF_GAME, checkedRadioId)?.apply()
            val fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.add(R.id.main_activity, GamePageFragment(), GAME_PAGE_FRAGMENT_TAG)
                commit()
            }
        }
    }

    @SuppressLint("CutPasteId")
    private fun initViews(view: View) {
        firstPart = view.findViewById(R.id.kingdom_of_van)
        secondPart = view.findViewById(R.id.artashesyan_family)
        thirdPart = view.findViewById(R.id.arshakuni_family)
        fourthPart = view.findViewById(R.id.bagratuni_family)
        startButton = view.findViewById(R.id.start)
        backButton = view.findViewById(R.id.backButton)
    }


}