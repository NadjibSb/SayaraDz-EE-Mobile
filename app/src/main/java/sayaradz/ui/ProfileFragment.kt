package sayaradz.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import sayaradz.authentification.R
import sayaradz.authentification.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding

    var firebaseAuth: FirebaseAuth? = null
    lateinit var ivProfilePicture: ImageView
    lateinit var tvName: TextView
    lateinit var tvEmail: TextView


    companion object {
        fun getInstance() = ProfileFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        ivProfilePicture = binding.ivProfile
        tvName = binding.tvName
        tvEmail = binding.tvEmail
        val user = firebaseAuth?.currentUser

        tvName.text = user?.displayName
        tvEmail.text = user?.email
        Picasso.with(context)
                .load(user?.photoUrl)
                .into(ivProfilePicture)


        binding.notificationLayout.setOnClickListener {

            Toast.makeText(context, "Notifications", Toast.LENGTH_SHORT).show()
        }
        binding.annonceLayout.setOnClickListener {
            Toast.makeText(context, "Mes annonces", Toast.LENGTH_SHORT).show()

        }
        binding.commandeLayout.setOnClickListener {
            Toast.makeText(context, "Mes commandes", Toast.LENGTH_SHORT).show()

        }
        binding.offreLayout.setOnClickListener {
            Toast.makeText(context, "Mes offres", Toast.LENGTH_SHORT).show()

        }

        return binding.root
    }

}