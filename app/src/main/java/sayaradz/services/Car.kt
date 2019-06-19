package sayaradz.services

class Car {
    private var id= 0
    private var pseudoUser =""
    private var date =""
    private var image1 = ""
    private var image2 =  null
    private var image3 = null
    private var kilometrage = ""
    private var titre= ""
    private var prix = ""
    private var commentaires =""
    private var idUser = ""
    private var idVehicule = 0

    /** Getters & Setters **/
    /** carTitle**/
    public var carTitle: String
        get() {
            return titre
        }
        set(value) {
            titre = value
        }
    public var carImage: String
        get() {
            return image1
        }
        set(value) {
            image1 = value
        }





}