package com.neverland.projectquiz.autorizationandregister

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.neverland.projectquiz.*
import com.rengwuxian.materialedittext.MaterialEditText

class InputDialogFragment : AppCompatDialogFragment() {

    private var email: MaterialEditText? = null
    private var pass: MaterialEditText? = null
    private lateinit var root: FrameLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inputWindow = LayoutInflater.from(activity as MainActivity)
                .inflate(R.layout.fragment_input_dialog, null)

            email = inputWindow.findViewById(R.id.input_email)
//            email.setText(arguments?.getString(GET_EMAIL) ?: EMPTY)
            pass = inputWindow.findViewById(R.id.input_pass)
//            pass.setText(arguments?.getString(GET_PASS) ?: EMPTY)
            root = (activity as MainActivity).findViewById(R.id.main_activity)

            builder.setView(inputWindow)
                .setTitle(getText(R.string.loginTitle))
                .setMessage(getText(R.string.loginMessage))
                .setNegativeButton(getText(R.string.cancel)) { _, _ ->
                    this.dialog?.dismiss()
                }
                .setPositiveButton(getText(R.string.login)) { _, _ ->
                    //իրականացվում է մուտքագրված տվյալների ստուգում
                    try {
                        when {
                            //գրանցվողի email-ի ստուգման տող
                            TextUtils.isEmpty(email?.text) -> {
                                Snackbar.make(
                                    root,
                                    getText(R.string.email_empty),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            //գրանցվողի գաղտնաբառի ստուգման տող
                            pass?.text.toString().length < 6 -> {
                                pass?.setText(EMPTY)
                                Snackbar.make(
                                    root,
                                    getText(R.string.password_wrong),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                        //մուտքագրված տվյալները վերադարձնում է վավերացման պատուհան
                        val bundle = Bundle()
                        bundle.putString(GET_EMAIL_INPUT_DIALOG, email?.text.toString())
                        bundle.putString(GET_PASS_INPUT_DIALOG, pass?.text.toString())
                        bundle.putBoolean(IS_LOGIN, true)

                        val currentFragment: Fragment? =
                            requireFragmentManager().findFragmentByTag(AUTHORIZATION_FRAGMENT_TAG)
                        currentFragment!!.arguments = bundle
                        currentFragment.onResume()
                        this.dialog?.dismiss()
                    } catch (e: Exception) {

                    }
                }
            builder.create()
        } ?: throw IllegalStateException(getText(R.string.activity_error).toString())
    }
}