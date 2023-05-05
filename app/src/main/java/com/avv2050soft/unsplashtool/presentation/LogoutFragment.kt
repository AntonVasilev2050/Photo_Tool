package com.avv2050soft.unsplashtool.presentation

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.data.auth.TokenStorage
import com.avv2050soft.unsplashtool.databinding.FragmentLoginBinding
import com.avv2050soft.unsplashtool.databinding.FragmentLogoutBinding
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView
import com.avv2050soft.unsplashtool.presentation.utils.launchAndCollectIn
import com.avv2050soft.unsplashtool.presentation.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

@AndroidEntryPoint
class LogoutFragment : Fragment(R.layout.fragment_logout) {

    private val viewModel: LogoutViewModel by viewModels()
    private val binding by viewBinding(FragmentLogoutBinding::bind)

//    private val getAuthResponse =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            val dataIntent = it.data ?: return@registerForActivityResult
//            handleAuthResponseIntent(dataIntent)
//        }

    private val logoutResponse = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.webLogoutComplete()
        } else {

            viewModel.webLogoutComplete()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideAppbarAndBottomView(requireActivity())
        bindViewModel()
    }

    private fun bindViewModel() {

        viewModel.logout()

        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            updateIsLoading(it)
        }
//        viewModel.openAuthPageFlow.launchAndCollectIn(viewLifecycleOwner) {
//            openAuthPage(it)
//        }
        viewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }
//        viewModel.authSuccessFlow.launchAndCollectIn(viewLifecycleOwner) {
////            если приложение было запущено по ссылке из другого приложения,
////            то запускаем просмотр подробной информации о фото, иначе - лента с фото
//            val data = activity?.intent?.data
//            val photoIdBundle = Bundle()
//            photoIdBundle.putString(PHOTO_ID_KEY, data.toString().substringAfterLast("/"))
//            if (data != null) {
//                findNavController().navigate(
//                    R.id.photoDetailsFragment,
//                    photoIdBundle
//                )
//            } else {
//                findNavController().navigate(R.id.photosFragment)
//            }
//
//            Log.d(
//                "Oauth",
//                "access: ${TokenStorage.accessToken} id: ${TokenStorage.idToken} refr: ${TokenStorage.refreshToken}"
//            )
//        }
        viewModel.logoutPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            logoutResponse.launch(it)
        }

        viewModel.logoutCompletedFlow.launchAndCollectIn(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_logoutFragment_to_splashFragment)
//            requireActivity().recreate()
        }
    }

    private fun updateIsLoading(isLoading: Boolean) = with(binding) {
        loginProgress.isVisible = isLoading
    }

//    private fun openAuthPage(intent: Intent) {
//        getAuthResponse.launch(intent)
//    }

//    private fun handleAuthResponseIntent(intent: Intent) {
//        // пытаемся получить ошибку из ответа. null - если все ок
//        val exception = AuthorizationException.fromIntent(intent)
//        // пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
//        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
//            ?.createTokenExchangeRequest()
//        when {
//            // авторизация завершались ошибкой
//            exception != null -> viewModel.onAuthCodeFailed(exception)
//            // авторизация прошла успешно, меняем код на токен
//            tokenExchangeRequest != null ->
//                viewModel.onAuthCodeReceived(tokenExchangeRequest)
//        }
//    }
}