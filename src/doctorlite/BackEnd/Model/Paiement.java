package doctorlite.BackEnd.Model;

public class Paiement  extends Person{
    private long id ;
    private Student std;
    private Group grp;
    private Seance seance;
    private String date,groupName,OfferName,typeOfOffer;
    private float amount,amountC;
    private int nbrSeance;
    private String  around;



    public int getNbrSeance() {
        return nbrSeance;
    }

    public void setNbrSeance(int nbrSeance) {
        this.nbrSeance = nbrSeance;
    }

    public String getAround() {
        return around;
    }

    public void setAround(String around) {
        this.around = around;
    }

    public Paiement() {}

    public Paiement(long id) {
        this.id = id;
    }

    public Paiement(Student std, Group grp) {
        this.std = std;
        this.grp = grp;
    }

    public Paiement(Student std, Group grp, String date, float amount, float amountC) {
        this.std = std;
        this.grp = grp;
        this.date = date;
        this.amount = amount;
        this.amountC = amountC;
    }

    public Paiement( Group grp,String date, float amount, float amountC) {
        this.grp = grp;
        this.date = date;
        this.amount = amount;
        this.amountC = amountC;
    }

    public Paiement( Group grp, float amount, float amountC,String around) {
        this.grp = grp;
        this.amount = amount;
        this.amountC = amountC;
        this.around=around;
    }

    public Paiement(long id, Student std, Group grp,String type, String date, float amount, float amountC) {
        this.id = id;
        this.std = std;
        this.grp = grp;
        this.date = date;
        this.typeOfOffer =type;
        this.amount = amount;
        this.amountC = amountC;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Student getStd() {
        return std;
    }

    public void setStd(Student std) {
        this.std = std;
    }

    public Group getGrp() {
        return grp;
    }

    public void setGrp(Group grp) {
        this.grp = grp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getTypeOfOffer() {
        return typeOfOffer;
    }

    public void setTypeOfOffer(String typeOfOffer) {
        this.typeOfOffer = typeOfOffer;
    }

    public float getAmountC() {
        return amountC;
    }

    public void setAmountC(float amountC) {
        this.amountC = amountC;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOfferName() {
        return OfferName;
    }

    public void setOfferName(String offerName) {
        OfferName = offerName;
    }

    public Seance getSeance() {return seance;}

    public void setSeance(Seance seance) {this.seance = seance;}
}
