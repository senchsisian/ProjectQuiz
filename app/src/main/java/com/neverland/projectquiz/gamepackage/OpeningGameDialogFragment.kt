package com.neverland.projectquiz.gamepackage

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.content.res.AppCompatResources
import com.neverland.projectquiz.*

class OpeningGameDialogFragment : AppCompatDialogFragment() {
    private var getId=0
    private var getText=0
    private lateinit var sharedPreferences: SharedPreferences
    private var partsOfGame = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        sharedPreferences =
            context!!.getSharedPreferences(PARTS_OF_GAME, Context.MODE_PRIVATE)
        partsOfGame = sharedPreferences.getString(PARTS_OF_GAME, "").toString()

        when(partsOfGame) {
            FACTS_OF_HISTORY -> {
                getId=R.drawable.facts_of_history
                getText=R.string.text_facts_of_history}
            KINGDOM_OF_VAN -> {
                getId=R.drawable.urartu
                getText=R.string.text_kingdom_Of_van}
            YERVANDUNIS_FAMILY -> {
                getId=R.drawable.yervandunis_kingdom
                getText=R.string.text_yervanduni_kingdom}
            ARTASHESYANS_FAMILY -> {getId=R.drawable.artaxiad_kingdom
                getText=R.string.text_artaxiad_kingdom}
            ARSHAKUNIS_FAMILY -> {getId=R.drawable.arsacid_dynasty
                getText=R.string.text_arsacid_dynasty}
            BAGRATUNIS_FAMILY->{getId=R.drawable.bagratuni_flag
                getText=R.string.text_bagratuni_kingdom}
            else -> {getId=0
                getText=0}
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inputWindow = LayoutInflater.from(activity as MainActivity)
                .inflate(R.layout.fragment_opening_game_dialog, null)

            inputWindow.findViewById<ImageView>(R.id.picture_of_part).setImageDrawable(
                AppCompatResources.getDrawable(requireContext(), getId)
            )
            inputWindow.findViewById<TextView>(R.id.test_of_part).setText(getText)

            builder.setView(inputWindow)
                .setNegativeButton(getText(R.string.cancel)) { _, _ ->
                    this.dialog?.dismiss()
                }
                .setPositiveButton(getText(R.string.start)) { _, _ ->
                    (activity as MainActivity).supportFragmentManager.beginTransaction().apply {
                        this.add(R.id.main_activity, GamePageFragment(), GAME_PAGE_FRAGMENT_TAG)
                        commit()
                    }
                    this.dialog?.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException(getText(R.string.activity_error).toString())
    }

}