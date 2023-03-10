/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Model;

/**
 *
 * @author Zed Yacine
 */
public class Room extends Template {

    private int nbrchair;

    public Room() {
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public Room(long id) {
        super(id);
    }

    public Room(String name) {
        super(name);
    }

    public Room(String name, int nbrchair) {
        super(name);
        this.nbrchair = nbrchair;
    }

    public Room(long id, String name, int nbrchair) {
        super(id, name);
        this.nbrchair = nbrchair;
    }

    public int getNbrchair() {
        return nbrchair;
    }

    public void setNbrchair(int nbrchair) {
        this.nbrchair = nbrchair;
    }

}
