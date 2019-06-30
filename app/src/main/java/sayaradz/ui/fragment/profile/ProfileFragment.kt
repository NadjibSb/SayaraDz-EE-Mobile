package sayaradz.ui.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import sayaradz.authentification.R
import sayaradz.authentification.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {


    var firebaseAuth: FirebaseAuth? = null
    lateinit var binding: ProfileFragmentBinding


    companion object {
        fun getInstance() = ProfileFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val user = firebaseAuth?.currentUser
        Glide.with(binding.ivProfile.context)
                .load(user?.photoUrl)
                .into(binding.ivProfile)
        binding.tvEmail.text = user?.email
        binding.tvName.text = user?.displayName

        binding.notificationLayout.setOnClickListener { v ->
            var action = ProfileFragmentDirections.actionProfileFragmentToNotificationFragment()
            v.findNavController().navigate(action)
        }
        binding.offreLayout.setOnClickListener { v ->
            var action = ProfileFragmentDirections.actionProfileFragmentToMyOffersFragment()
            v.findNavController().navigate(action)
        }
        binding.commandeLayout.setOnClickListener { v ->
            var action = ProfileFragmentDirections.actionProfileFragmentToMyCommandsFragment()
            v.findNavController().navigate(action)
        }
    }
}