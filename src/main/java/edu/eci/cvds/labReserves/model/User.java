package edu.eci.cvds.labReserves.model;

import java.util.*;

import java.util.*;

public class User {
    private int id;
    private String name;
    private String mail;
    private String password;
    private String rol;
    private static Set<String> rolType = Set.of("teacher", "admin");

    public User() {}

    public User(int id, String name, String mail, String password, String rol) throws LabReserveException {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        if (rolType.contains(rol)){
            this.rol = rol;
        }else{
            throw new LabReserveException(LabReserveException.INVALID_ROL_TYPE);
        }
    }

    /**
     * get the id of a user
     * @return the id of a user
     */
    public int getId() {
        return id;
    }


    /**
     * set the id of a user
     * @param id
     */
    public void setId(int id) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getMail(){
        return mail;
    }
    public void setMail(String mail){
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get the rol of a user
     * @return the rol of a user
     */
    public String getRol(){return rol;}

    /**
     * set a new rol for a user
     * @param newRol
     */
    public void setRol(String newRol){this.rol = newRol;}
}
