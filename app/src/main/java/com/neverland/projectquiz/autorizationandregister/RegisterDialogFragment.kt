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
import com.neverland.projectquiz.AUTHORIZATION_FRAGMENT_TAG
import com.neverland.projectquiz.EMPTY
import com.neverland.projectquiz.MainActivity
import com.neverland.projectquiz.R
import com.rengwuxian.materialedittext.MaterialEditText

const val GET_USERNAME_REGISTER_DIALOG="username register dialog"
const val GET_EMAIL_REGISTER_DIALOG="email register dialog"
const val GET_PASS_REGISTER_DIALOG="pass register dialog"
const val IS_REGISTER="is register"

 class RegisterDialogFragment : AppCompatDialogFragment(){

    var email: MaterialEditText?=null
    var pass: MaterialEditText?=null
    var username: MaterialEditText?=null
    private lateinit var root:FrameLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val registerWindow = LayoutInflater.from(activity as MainActivity).inflate(R.layout.fragment_register_dialog, null)

            email = registerWindow.findViewById(R.id.register_email)
            email?.setText(arguments?.getString(GET_EMAIL) ?: EMPTY)
            pass = registerWindow.findViewById(R.id.register_pass)
            username = registerWindow.findViewById(R.id.register_username)
            username?.setText(arguments?.getString(GET_USERNAME) ?: EMPTY)
            root = (activity as MainActivity).findViewById(R.id.main_activity)

            builder.setView(registerWindow)
                    .setTitle(getText(R.string.registerTitle))
                    .setMessage(getText(R.string.registerMessage))
                    .setNegativeButton(getText(R.string.cancel)) { _, _ ->
                        this.dialog?.dismiss()
                    }
                    .setPositiveButton(getText(R.string.register)) { _, _ ->
                        //իրականացվում է մուտքագրված տվյալների ստուգում
                        try {
                            when {

                                //գրանցվողի օգտանունի ստուգման տող
                                TextUtils.isEmpty(username?.text) -> {
                                    Snackbar.make(root, getText(R.string.username_empty), Snackbar.LENGTH_SHORT).show()
                                }
                                //գրանցվողի email-ի ստուգման տող
                                TextUtils.isEmpty(email?.text) -> {
                                    Snackbar.make(root, getText(R.string.email_empty), Snackbar.LENGTH_SHORT).show()
                                }
                                //գրանցվողի գաղտնաբառի ստուգման տող
                                pass?.text.toString().length<6 -> {
                                    pass?.setText(EMPTY)
                                    Snackbar.make(root, getText(R.string.password_wrong), Snackbar.LENGTH_SHORT).show()
                                }
                            }
                            //մուտքագրված տվյալները վերադարձնում է վավերացման պատուհան
                            val bundle=Bundle()
                            bundle.putString(GET_USERNAME_REGISTER_DIALOG, username?.text.toString())
                            bundle.putString(GET_EMAIL_REGISTER_DIALOG, email?.text.toString())
                            bundle.putString(GET_PASS_REGISTER_DIALOG, pass?.text.toString())
                            bundle.putBoolean(IS_REGISTER,true)

                            val currentFragment: Fragment? =requireFragmentManager().findFragmentByTag(AUTHORIZATION_FRAGMENT_TAG)
                            currentFragment!!.arguments=bundle
                            currentFragment.onResume()
                        } catch (e: Exception) {

                        }
                    }
            builder.create()
        } ?: throw IllegalStateException(getText(R.string.activity_error).toString())
    }
}