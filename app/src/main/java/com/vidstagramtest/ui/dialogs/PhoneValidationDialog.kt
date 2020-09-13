package com.vidstagramtest.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.vidstagramtest.R
import com.vidstagramtest.ui.interfaces.OnFinishEnterCode
import kotlinx.android.synthetic.main.phone_code_dialog_alyout.*

class PhoneValidationDialog : DialogFragment() {

    private var finishListener: OnFinishEnterCode? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dialogLayout =
            inflater.inflate(R.layout.phone_code_dialog_alyout, container, false)
        val okButton = dialogLayout.findViewById<Button>(R.id.ok_button)
        okButton.setOnClickListener {
            finishListener?.onFinishEnterToken(editTV.text.toString())
            dismiss()
        }
        return dialogLayout
    }


    fun setListener(listener: OnFinishEnterCode) {
        finishListener = listener
    }

    companion object {
        @JvmStatic
        fun newInstance(): PhoneValidationDialog {
            return PhoneValidationDialog()
        }
    }
}
