package sayaradz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import sayaradz.authentification.R

class ProfileFragment : Fragment() {


    var firebaseAuth: FirebaseAuth? = null
    lateinit var ivProfilePicture: ImageView
    lateinit var tvName: TextView
    lateinit var tvEmail: TextView


    companion object {
        fun getInstance() = ProfileFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.profile_fragment, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        ivProfilePicture = rootView.findViewById<ImageView>(R.id.iv_profile)
        tvName = rootView.findViewById<TextView>(R.id.tv_name)
        tvEmail = rootView.findViewById<TextView>(R.id.tv_email)
        val user = firebaseAuth?.currentUser

        tvName.text = user?.displayName
        tvEmail.text = user?.email
        Picasso.with(context)
                .load(user?.photoUrl)
                .into(ivProfilePicture)

        return rootView
    }

}