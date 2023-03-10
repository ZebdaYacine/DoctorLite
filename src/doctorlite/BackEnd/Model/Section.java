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
public class Section extends Template{


    public Section() {
    }

    public Section(long id) {
        super(id);
    }

    public Section(String name) {
        super(name);
    }

    public Section(long id, String name) {
        super(id, name);
    }

}
