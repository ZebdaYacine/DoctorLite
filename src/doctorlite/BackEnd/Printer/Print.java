/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Printer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import doctorlite.BackEnd.DataBaseConnection;
import doctorlite.BackEnd.Model.Group;

/**
 *
 * @author Zed Yacine
 */
public class Print {

    private final Group grp;

    public Print(Group grp) {
        this.grp = grp;
    }

    public void PrintListStudent() throws JRException {
        JasperDesign Jds = JRXmlLoader.load(getClass().
                getResourceAsStream("/schoolmanager/JasperFiles/list.jrxml"));

        String query="SELECT S.firstName , S.lastName , G.name ,O.nameModule ,O.nameType " +
                ",O.nameLevel FROM student S ,Offer O, belongs B , Groupe G   where " +
                "S.id=B.idStudnet and G.id= B.idGroupe and G.idOffer = O.id and  B.idGroupe = "+grp.getId()+
                "  order by S.id desc";
        JRDesignQuery qr = new JRDesignQuery();
        qr.setText(query);
        Jds.setQuery(qr);
        JasperReport jreport = JasperCompileManager.compileReport(Jds);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jreport,
                null, DataBaseConnection.con);
        JasperViewer.viewReport(jasperPrint, false);
    }

}

