package sayaradz.services

import android.graphics.drawable.Drawable

class Marque{

    private var idMarque=""
    private var nomMarque=""
    lateinit private var image: Drawable

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

    public var Image : Drawable
        get() = image
        set(value) {
            image = value
        }

}