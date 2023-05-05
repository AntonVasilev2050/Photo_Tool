package com.avv2050soft.unsplashtool.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.data.auth.TokenStorage
import com.avv2050soft.unsplashtool.data.repository.UnsplashRepositoryImpl
import com.avv2050soft.unsplashtool.databinding.FragmentLoginBinding
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView
import com.avv2050soft.unsplashtool.presentation.utils.launchAndCollectIn
import com.avv2050soft.unsplashtool.presentation.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val getAuthResponse =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            handleAuthResponseIntent(dataIntent)
        }

//    private val logoutResponse = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            viewModel.webLogoutComplete()
//        } else {
//
//            viewModel.webLogoutComplete()
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideAppbarAndBottomView(requireActivity())
        bindViewModel()

    }

    private fun bindViewModel() {

        binding.buttonLogin.setOnClickListener { viewModel.openLoginPage() }

        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            updateIsLoading(it)
        }
        viewModel.openAuthPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            openAuthPage(it)
        }
        viewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }
        viewModel.authSuccessFlow.launchAndCollectIn(viewLifecycleOwner) {
//            если приложение было запущено по ссылке из другого приложения,
//            то запускаем просмотр подробной информации о фото, иначе - лента с фото
            val data = activity?.intent?.data
            val photoIdBundle = Bundle()
            photoIdBundle.putString(PHOTO_ID_KEY, data.toString().substringAfterLast("/"))
            if (data != null) {
                findNavController().navigate(
                    R.id.photoDetailsFragment,
                    photoIdBundle
                )
            } else {
                findNavController().navigate(R.id.photosFragment)
            }

            UnsplashRepositoryImpl.accessToken = "Bearer ${TokenStorage.accessToken}"

            Log.d(
                "Oauth",
                "access: ${TokenStorage.accessToken} id: ${TokenStorage.idToken} refr: ${TokenStorage.refreshToken}"
            )
        }
//        viewModel.logoutPageFlow.launchAndCollectIn(viewLifecycleOwner) {
//            logoutResponse.launch(it)
//        }

//        viewModel.logoutCompletedFlow.launchAndCollectIn(viewLifecycleOwner) {
//
//            findNavController().navigate(R.id.loginFragment)
//        }
    }

    private fun updateIsLoading(isLoading: Boolean) = with(binding) {
        buttonLogin.isVisible = !isLoading
        loginProgress.isVisible = isLoading
    }

    private fun openAuthPage(intent: Intent) {
        getAuthResponse.launch(intent)
    }

    private fun handleAuthResponseIntent(intent: Intent) {
        // пытаемся получить ошибку из ответа. null - если все ок
        val exception = AuthorizationException.fromIntent(intent)
        // пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {
            // авторизация завершались ошибкой
            exception != null -> viewModel.onAuthCodeFailed(exception)
            // авторизация прошла успешно, меняем код на токен
            tokenExchangeRequest != null ->
                viewModel.onAuthCodeReceived(tokenExchangeRequest)
        }
    }
}