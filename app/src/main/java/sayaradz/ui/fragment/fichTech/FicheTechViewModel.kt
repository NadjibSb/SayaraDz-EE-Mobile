package sayaradz.ui.fragment.fichTech

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import sayaradz.dataClasses.FichTech
import sayaradz.dataClasses.Option
import sayaradz.dataClasses.VersionNew

const val ADD = 1
const val SUB = -1

class FicheTechViewModel : ViewModel() {

    private val _fichTech = MutableLiveData<FichTech>()
    val fichTech: LiveData<FichTech>
        get() = _fichTech

    private val _versionNew = MutableLiveData<VersionNew>()
    val versionNew: LiveData<VersionNew>
        get() = _versionNew

    val _price = MutableLiveData<Int>()
    val price: LiveData<String>
        get() = Transformations.map(_price) { p->
            p.toString()
        }


    init {
        _fichTech.value = getDefaultFichTech()
        _versionNew.value = getDefaultVersion()
    }

    fun updatePrice(value: Int, action: Int) {
        when (action) {
            ADD -> _price.value = _price.value?.plus(value)
            SUB -> _price.value = _price.value?.minus(value)
            else -> _price.value = getDefaultVersion().tarif
        }
    }

    private fun getDefaultFichTech(): FichTech {
        return FichTech(
                "Peugeot",
                "208",
                "GTline",
                "Peugeot 208 Tech Vision 1.6 HDI 92ch",
                "16 Essence, 95ch",
                "6 vitesses",
                "automatique",
                "2,8 x 4 x 1,5 m",
                "1,2 L",
                "92 ch",
                "50 L",
                "200 km/h",
                "1,2 s",
                "5",
                "5")
    }


    private fun getDefaultVersion(): VersionNew {
        val version = VersionNew(
                "0",
                "#2121",
                "GTline",
                arrayListOf(
                        "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-0071373.jpg",
                        "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-2892232.jpg",
                        "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-0687668.jpg",
                        "https://www.autobip.com/storage/photos/new_car_prices/20/peugeot_208_tech_vision_1_6_hdi_92ch_2019-03-04-13-4228700.jpg"
                ),
                arrayListOf(
                        "Blanc",
                        "Rouge",
                        "Bleu",
                        "Noir",
                        "Gris",
                        "Bordaux"
                ),
                arrayListOf(
                        Option("0", "#09", "Toit", "#112233", 20000),
                        Option("0", "#09", "Vitres teintés", "#112233", 20000),
                        Option("0", "#09", "Retroviseurs éléctrique", "#112233", 20000),
                        Option("0", "#09", "Ordinateur de bord", "#112233", 20000)
                ),
                1000000
        )

        _price.value = version.tarif
        return version
    }

}


