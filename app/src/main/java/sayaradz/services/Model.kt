package sayaradz.services

class Model {

    private var nom = ""
    private var pk = ""
    private var marque=""
    private var codeModele=""
    private var couleur_set = arrayListOf<String>()


    /** Getters & Setters **/

    /** pk**/
    public var Pk: String
        get() {
            return pk
        }
        set(value) {
            pk = value
        }
    /** codeModel**/
    public var CodeModele: String
        get() {
            return codeModele
        }
        set(value) {
            codeModele = value
        }

    /** nomModel**/
    public var NomModel: String
        get() {
            return nom
        }
        set(value) {
            nom= value
        }


}