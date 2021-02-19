package com.kluivert.otuuna.Appfragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.kluivert.otuuna.databinding.FragmentOtpBinding
import com.kluivert.otuuna.ui.activities.MainActivity
import com.kluivert.otuuna.utils.AppUtils
import java.util.concurrent.TimeUnit


class OtpFragment : Fragment() {

    var phonenumber: String? = null
    private var verificationId: String? = null
    val args : OtpFragmentArgs by navArgs()
    private var _binding : FragmentOtpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private var mVerificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOtpBinding.inflate(layoutInflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        init()

    }

    private fun init(){

        val number = args.phone
        phonenumber = args.phone
        binding.tvDummyNum.text = "Please enter the code sent to "+" $number"

        binding.verifyBtn.setOnClickListener{
            val a = binding.et1.text.toString().trim { it <= ' ' }
            val b = binding.et2.text.toString().trim { it <= ' ' }
            val c = binding.et3.text.toString().trim { it <= ' ' }
            val d = binding.et4.text.toString().trim { it <= ' ' }
            val e = binding.et5.text.toString().trim { it <= ' ' }
            val f = binding.et6.text.toString().trim { it <= ' ' }
            val otp = a + b + c + d + e + f
            verifyCode(otp)

        }
        binding.resendBtn.setOnClickListener{

            binding.resendTimeView.visibility = View.VISIBLE
            sendVerificationCode(phonenumber.toString())
            countdownTime()
            binding.resendBtn.visibility = View.GONE
        }

        Textwatcher()

    }

private fun verifyCode(code: String) {
    val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
    signInWithCredential(credential)
}

private fun signInWithCredential(credential: PhoneAuthCredential) {
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                // Toast.makeText(OtpVerifyActivity.this, task.getException().message(), Toast.LENGTH_LONG).show();
            }
        }
}

private fun sendVerificationCode(number: String) {

    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber(number)       // Phone number to verify
        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(requireActivity())                 // Activity (for callback binding)
        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}

private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
        super.onCodeSent(s, forceResendingToken)
        verificationId = s
    }

    override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
        val code = phoneAuthCredential.smsCode
        if (code != null) {
            val a = code.substring(0, 1)
            val b = code.substring(1, 2)
            val c = code.substring(2, 3)
            val d = code.substring(3, 4)
            val e = code.substring(4, 5)
            val f = code.substring(5, 6)
           binding.et1.setText(a)
           binding.et2.setText(b)
            binding.et3.setText(c)
            binding.et4.setText(d)
           binding.et5.setText(e)
           binding.et6.setText(f)

            // verifyCode(code);
        }
    }

    override fun onVerificationFailed(e: FirebaseException) {
        Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
    }
}


fun countdownTime() {
    object : CountDownTimer(30000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.resendTimeView.text = "Resend code in " + millisUntilFinished / 1000 + " sec"
            //here you can have your logic to set text to edittext
        }

        override fun onFinish() {
            binding.resendBtn.visibility = View.VISIBLE
           binding.resendTimeView.visibility = View.GONE
        }
    }.start()
}

private fun Textwatcher() {
   binding.et1.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.length == 1) {
                binding.et2.requestFocus()
            } else if (s.isEmpty()) {
                binding.et1.clearFocus()
            }
        }
    })
    binding.et2.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.length == 1) {
                binding.et3!!.requestFocus()
            } else if (s.length == 0) {
               binding.et1.requestFocus()
            }
        }
    })
   binding.et3.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.length == 1) {
                binding.et4.requestFocus()
            } else if (s.isEmpty()) {
               binding.et2.requestFocus()
            }
        }
    })
    binding.et4.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.length == 1) {
                binding.et5.requestFocus()
            } else if (s.length == 0) {
                binding.et3.requestFocus()
            }
        }
    })
    binding.et5.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.length == 1) {
               binding.et6.requestFocus()
            } else if (s.length == 0) {
                binding.et4.requestFocus()
            }
        }
    })
    binding.et6.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.length == 1) {
                // et6.clearFocus();
            } else if (s.length == 0) {
               binding.et5.requestFocus()
            }
        }
    })

}


    fun goHome(){
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        AppUtils.animateEnterRight(requireActivity())
        activity?.finish()
    }

}