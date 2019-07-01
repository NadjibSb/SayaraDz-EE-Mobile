package sayaradz.ui.fragment.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import sayaradz.authentification.R
import sayaradz.authentification.databinding.NotificationFragmentBinding
import sayaradz.dataClasses.Notification
import sayaradz.ui.fragment.adapter.ListAdapter

class NotificationFragment : Fragment() {

    companion object {
        fun newInstance() = NotificationFragment()
    }

    private lateinit var viewModel: NotificationViewModel
    private lateinit var binding: NotificationFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.notification_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)

        viewModel.notifications.observe(this, Observer {list ->
            setUpRecycleView(binding.root,list)
        })
        // TODO: Use the ViewModel
    }


    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<Notification>) {
        var recyclerView = binding.notifList
        recyclerView.adapter = ListAdapter(
                list,
                ListAdapter.ViewHolderType.NOTIFICATION,
                this@NotificationFragment.context!!,
                "token")
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }

}
