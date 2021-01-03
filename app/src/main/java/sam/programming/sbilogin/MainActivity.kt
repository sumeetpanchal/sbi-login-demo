package sam.programming.sbilogin

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import sam.programming.sbilogin.model.KeyData
import sam.programming.sbilogin.utility.getKeyBoardKeys
import sam.programming.sbilogin.utility.keyCodePassword
import sam.programming.sbilogin.widget.PinEntryEditText


class MainActivity : AppCompatActivity() {

    private var isPinShown = false
    private var mMaxLength = 6

    private lateinit var mPinText: PinEntryEditText
    private lateinit var mPinTextPassword: PinEntryEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        mPinText = findViewById(R.id.pin_edit_text)
        mPinTextPassword = findViewById(R.id.pin_edit_text_password)

        mPinText.transformationMethod =
            PasswordTransformationMethod.getInstance()
        mPinText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        mPinText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length >= mMaxLength) {
                    validatePin(s.toString())
                }
            }
        })

        addBottomKeyBoard()
    }

    private fun validatePin(pinText: String) {
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Validating Pin")
        progressDialog.show()
        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                progressDialog.dismiss()
                Toast.makeText(
                    this@MainActivity,
                    "Your pin is : ${pinText}",
                    Toast.LENGTH_LONG
                ).show()
                clearText()
            }, 2000)
        }
    }

    private fun clearText() {
        mPinText.setText("")
        mPinTextPassword.setText("")
    }

    private fun addBottomKeyBoard() {
        val keyBoardRoot = findViewById<LinearLayout>(R.id.ll_keyboard_root)
        keyBoardRoot.removeAllViews()

        val keys = getKeyBoardKeys()

        val keyBoardColumn1 = keys.subList(0, 4)
        val linearColumn1 = LinearLayout(this)
        addKeysToLayout(linearColumn1, keyBoardColumn1, keyBoardRoot)

        val keyBoardColumn2 = keys.subList(4, 8)
        val linearColumn2 = LinearLayout(this)
        addKeysToLayout(linearColumn2, keyBoardColumn2, keyBoardRoot)

        val keyBoardColumn3 = keys.subList(8, 12)
        val linearColumn3 = LinearLayout(this)
        addKeysToLayout(linearColumn3, keyBoardColumn3, keyBoardRoot)
    }

    private fun addKeysToLayout(layout: LinearLayout, keys: List<KeyData>, root: LinearLayout) {
        layout.orientation = LinearLayout.VERTICAL

        val linearColumn1Params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        )
        keys.forEach { key ->
            val keyButton = Button(this)
            keyButton.textSize = 16.0f
            when (key.keyCode) {
                KeyEvent.KEYCODE_DEL -> {
                    keyButton.text = getString(R.string.backSpace)
                }
                keyCodePassword -> {
                    keyButton.text = getString(R.string.eye)
                }
                else -> {
                    keyButton.text = key.value.toString()
                }
            }
            keyButton.setOnClickListener {
                setPinText(key)
            }
            layout.addView(keyButton)
        }
        root.addView(layout, linearColumn1Params)
    }

    private fun setPinText(key: KeyData) {
        when (key.keyCode) {
            KeyEvent.KEYCODE_DEL -> {
                val length: Int = mPinText?.text?.length ?: 0
                if (length > 0) {
                    mPinText.setText(mPinText.text.toString().substring(0, length - 1))
                    mPinTextPassword.setText(
                        mPinTextPassword.text.toString().substring(0, length - 1)
                    )
                }
            }
            keyCodePassword -> {
                if (!isPinShown) {
                    mPinText.visibility = View.VISIBLE
                    mPinTextPassword.visibility = View.GONE
                    isPinShown = true
                } else {
                    mPinText.visibility = View.GONE
                    mPinTextPassword.visibility = View.VISIBLE
                    isPinShown = false
                }
            }
            else -> {
                mPinText.append(key.value.toString())
                mPinTextPassword.append("*")
            }
        }


    }
}