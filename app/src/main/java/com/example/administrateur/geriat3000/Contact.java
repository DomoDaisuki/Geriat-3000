package com.example.administrateur.geriat3000;

/**
 * Created by quentin on 23/04/16.
 */
public class Contact {
    private long id;
    private String name;
    private String phone_number;

    public long getId()
    {
        return this.id;
    }

    public  void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone_number() { return this.phone_number;}

    public void setPhone_number(String phone_number) {this.phone_number = phone_number;}
    @Override
    public String toString()
    {
        return this.name;
    }
}
