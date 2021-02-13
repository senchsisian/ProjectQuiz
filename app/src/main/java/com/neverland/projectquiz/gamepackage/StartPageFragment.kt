package com.neverland.projectquiz.gamepackage

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
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
    private var sharedPreferences =
        activity?.getSharedPreferences(PARTS_OF_GAME, Context.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infLater = inflater.inflate(R.layout.fragment_start_page, container, false)
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
            Toast.makeText(activity, "checkedRadioId $checkedRadioId", Toast.LENGTH_SHORT).show()
            sharedPreferences?.edit()?.putString(PARTS_OF_GAME, checkedRadioId)?.apply()
            val getPart = sharedPreferences?.getString(PARTS_OF_GAME, "").toString()
            Toast.makeText(activity, "getPart $getPart", Toast.LENGTH_SHORT).show()
            fragmentTransaction =
                (activity as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.apply {
                this.add(R.id.main_activity, gamePageFragment, GAME_PAGE_FRAGMENT_TAG)
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