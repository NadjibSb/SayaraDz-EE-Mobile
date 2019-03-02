package sayaradz.services

import android.graphics.drawable.Drawable

class Marque{

    private var pk=""
    private var idMarque=""
    private var nomMarque=""
    private var imageMarque: String =""

    /** Getters & Setters **/
    /** idMarque**/
    public var IdMarque : String
        get() {
            return idMarque
        }
        set(value) {
            idMarque = value
        }

    /** nomMarque**/
    public var NomMarque : String
        get() {
            return nomMarque
        }
        set(value) {
            nomMarque = value
        }

    public var Image: String
        get() = imageMarque
        set(value) {
            imageMarque = value
        }

}