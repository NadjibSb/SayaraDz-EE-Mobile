package sayaradz.services

class Car {
    private var id= 0
    private var titre= ""
    private var idUser = ""
    private var date =""
    private var imageVehicle1 = ""
    private var imageVehicle2 =  null
    private var imageVehicle3 = null
    private var kilometrage = 0
    private var prix = 0
    private var commentaires =""
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
            return imageVehicle1
        }
        set(value) {
            imageVehicle1 = value
        }





}