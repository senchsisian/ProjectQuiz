package com.neverland.projectquiz.gamepackage

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.content.res.AppCompatResources
import com.neverland.projectquiz.MainActivity
import com.neverland.projectquiz.R

class AboutOfGame : AppCompatDialogFragment() {
    private var getId=R.drawable.main_apricot
    private var getText=R.string.info_about_of_game

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inputWindow = LayoutInflater.from(activity as MainActivity)
                .inflate(R.layout.fragment_opening_game_dialog, null)

            inputWindow.findViewById<ImageView>(R.id.picture_of_part).setImageDrawable(
                AppCompatResources.getDrawable(requireContext(), getId)
            )
            inputWindow.findViewById<TextView>(R.id.test_of_part).setText(getText)

            builder.setView(inputWindow)
                .setNegativeButton(getText(R.string.close)) { _, _ ->
                    this.dialog?.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException(getText(R.string.activity_error).toString())
    }

}