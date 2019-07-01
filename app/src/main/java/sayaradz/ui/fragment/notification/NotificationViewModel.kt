package sayaradz.ui.fragment.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sayaradz.dataClasses.Notification

class NotificationViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var notifications: MutableLiveData<ArrayList<Notification>>

    init {
        notifications = defaultList()
    }

    private fun defaultList(): MutableLiveData<ArrayList<Notification>> {

        var finalList = MutableLiveData<ArrayList<Notification>>()
        var list = ArrayList<Notification>()
        for (i in 0..3) {
            list.add(Notification(
                    "Offer",
                    "Nadjib",
                    "https://media.licdn.com/dms/image/C4D03AQHpYbfkM15lNw/profile-displayphoto-shrink_200_200/0?e=1564617600&v=beta&t=4SuPPRR3gJgpa3Sl9anhPr0RCcOjJvGSv1d7I1aGK3s",
                    System.currentTimeMillis(),
                    "Nadjib vous fait un offre de 320 000 da ",
                    "https://www.paycar.fr/wp-content/uploads/voiture-occasion-espagne.jpg"
            ))
        }
        finalList.value = list
        return finalList

    }
}
