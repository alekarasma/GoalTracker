package com.asmaa.goaltracker.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.asmaa.goaltracker.R
import com.asmaa.goaltracker.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: LoginSignUpViewModel by viewModels { LoginSignUpViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loading.observe(viewLifecycleOwner) { load ->
            if (load) {
                binding.loadingSpinner.visibility = View.VISIBLE
            } else {
                binding.loadingSpinner.visibility = View.INVISIBLE
            }
        }

        viewModel.registrationSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Snackbar.make(
                    this.requireView(),
                    viewModel.firebaseResponseMsg,
                    Snackbar.LENGTH_LONG
                ).show()
                view.findNavController().navigate(R.id.addGoalFragment)
            } else {
                Snackbar.make(
                    this.requireView(),
                    viewModel.firebaseResponseMsg,
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }
        with(binding) {
            var noErrorSet: Boolean = true
            buttonRegister.setOnClickListener {
                val email = textEmail.text.toString().filterNot { it.isWhitespace() }
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches()
                ) {
                    textEmail.error = null
                } else {
                    textEmail.error = "Invalid Email Address"
                    noErrorSet = false
                }

                //Check for the password min 6 letters
                val password = textPassword.text.toString().filterNot { it.isWhitespace() }
                val name = textName.toString()
                if (textName.toString().length < 6) {
                    textName.error = "Password minimum length is 6"
                    noErrorSet = false
                } else {
                    binding.textName.error = null
                }

                if (noErrorSet) {
                    viewModel.registerUser(name, email, password)
                }
            }
        }
    }
}