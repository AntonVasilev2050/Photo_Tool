package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentSplashBinding
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_splash, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val mainActivity = requireActivity()
//        mainActivity.findViewById<Toolbar>(R.id.toolbar).isGone = true
//        val bottomNavigationView = mainActivity.findViewById<BottomNavigationView>(R.id.bottomView)
//        bottomNavigationView.isGone = true
        hideAppbarAndBottomView(requireActivity())
        binding.imageViewSplashImage.alpha = 0f
        binding.imageViewSplashImage.animate().setDuration(1500).alpha(1f).withEndAction {
            findNavController().navigate(R.id.viewPagerFragment)
        }
    }
}