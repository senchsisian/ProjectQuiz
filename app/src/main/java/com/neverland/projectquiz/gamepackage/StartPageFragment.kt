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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.neverland.projectquiz.*

class StartPageFragment : Fragment() {
    private var firstPart: RadioButton? = null
    private var secondPart: RadioButton? = null
    private var thirdPart: RadioButton? = null
    private var startButton: Button? = null
    private var checkedRadioId = ""
    private var gamePageFragment = GamePageFragment()
    private lateinit var fragmentTransaction: FragmentTransaction
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

        startButton!!.setOnClickListener {
            checkedRadioId = when {
                firstPart?.isChecked == true -> KINGDOM_OF_VAN
                thirdPart?.isChecked == true -> ARTASHESYANS_FAMILY
                secondPart?.isChecked == true -> ARSHAKUNIS_FAMILY
                else -> ""
            }
            sharedPreferences.edit()?.putString(PARTS_OF_GAME, checkedRadioId)?.apply()
            fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.add(R.id.main_activity, gamePageFragment, GAME_PAGE_FRAGMENT_TAG)
                addToBackStack(null)
                commit()
            }
        }
    }

    @SuppressLint("CutPasteId")
    private fun initViews(view: View) {
        firstPart = view.findViewById(R.id.kingdom_of_van)
        secondPart = view.findViewById(R.id.artashesyan_family)
        thirdPart = view.findViewById(R.id.arshakuni_family)
        startButton = view.findViewById(R.id.start)
    }


}