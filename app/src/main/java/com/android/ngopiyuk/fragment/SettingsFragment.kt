package com.android.ngopiyuk.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.ngopiyuk.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class SettingsFragment : Fragment() {

    companion object {
        private const val PREF_PROFILE = "ngopiyuk_profile"
        private const val KEY_NAME = "profile_name"
        private const val KEY_EMAIL = "profile_email"
        private const val KEY_PHONE = "profile_phone"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName: TextInputEditText = view.findViewById(R.id.etName)
        val etEmail: TextInputEditText = view.findViewById(R.id.etEmail)
        val etPhone: TextInputEditText = view.findViewById(R.id.etPhone)
        val btnSave: MaterialButton = view.findViewById(R.id.btnSaveProfile)

        // Load saved profile
        val prefs = requireContext().getSharedPreferences(PREF_PROFILE, Context.MODE_PRIVATE)
        etName.setText(prefs.getString(KEY_NAME, ""))
        etEmail.setText(prefs.getString(KEY_EMAIL, ""))
        etPhone.setText(prefs.getString(KEY_PHONE, ""))

        // Save profile
        btnSave.setOnClickListener {
            prefs.edit()
                .putString(KEY_NAME, etName.text.toString())
                .putString(KEY_EMAIL, etEmail.text.toString())
                .putString(KEY_PHONE, etPhone.text.toString())
                .apply()

            Snackbar.make(view, getString(R.string.settings_saved_success), Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}
