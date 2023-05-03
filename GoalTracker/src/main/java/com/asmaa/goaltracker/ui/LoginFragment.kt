package com.asmaa.goaltracker.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.asmaa.goaltracker.R
import com.asmaa.goaltracker.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginSignUpViewModel by viewModels { LoginSignUpViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            var noErrorSet: Boolean = true
            buttonLogin.setOnClickListener {
                val email = textEmail.text.toString().filter { it.isWhitespace() }
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches()
                ) {
                    textEmail.error = null
                } else {
                    textEmail.error = "Invalid Email Address"
                    noErrorSet = false
                }

                //Check for the password min 6 letters with abcd
                val password = textPassword.text.toString().filter { it.isWhitespace() }
                if(noErrorSet)
                {
                    viewModel.logInUser(email, password)
                }
            }

            buttonRegister.setOnClickListener{
                view.findNavController().navigate(R.id.registerFragment)
            }
        }
    }

}