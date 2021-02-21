package com.neverland.projectquiz.gamepackage

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.neverland.projectquiz.*

class OpeningGameDialogFragment : AppCompatDialogFragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var partsOfGame = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        sharedPreferences =
            context!!.getSharedPreferences(PARTS_OF_GAME, Context.MODE_PRIVATE)
        partsOfGame = sharedPreferences.getString(PARTS_OF_GAME, "").toString()

        val getFragmentId = when (partsOfGame) {
            KINGDOM_OF_VAN -> R.layout.kingdom_of_van
            else -> 0
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inputWindow = LayoutInflater.from(activity as MainActivity)
                .inflate(getFragmentId, null)
            builder.setView(inputWindow)
                .setNegativeButton(getText(R.string.start)) { _, _ ->
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