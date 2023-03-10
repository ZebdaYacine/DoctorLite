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
public class Offer extends Template {


    private long id,idType,idModule,idLevel;
    private String name, type,module,level;
    private int price;

    public Offer() {
    }
    
    @Override
    public String toString() {
        return this.getName();
    }

    public Offer(long id) {
        this.id = id;
    }

    public Offer(String name, String type, String module, String level, int price) {
        this.name = name;
        this.type = type;
        this.module = module;
        this.level = level;
        this.price = price;
    }

    public Offer(long id, String name, String type, String module, String level, int price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.module = module;
        this.level = level;
        this.price = price;
    }

    public Offer(long id, String name, String type, String module, String level, int price,long idLevel,
                 long idModule,long idType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.module = module;
        this.level = level;
        this.price = price;
        this.idLevel=idLevel;
        this.idModule=idModule;
        this.idType=idType;
    }

    public long getIdType() {
        return idType;
    }

    public void setIdType(long idType) {
        this.idType = idType;
    }

    public long getIdModule() {
        return idModule;
    }

    public void setIdModule(long idModule) {
        this.idModule = idModule;
    }

    public long getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(long idLevel) {
        this.idLevel = idLevel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
