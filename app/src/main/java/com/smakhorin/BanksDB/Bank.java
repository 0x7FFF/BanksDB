package com.smakhorin.BanksDB;

public class Bank {

    private String address;

    private String phone;

    private String name;

    private int id;

    private int employeeCount;

    private String workTime;

    private String email;

    public Bank(int id, String name, String address, String phone, int employeeCount, String workTime, String email) {
        setId(id);
        setName(name);
        setAddress(address);
        setPhone(phone);
        setEmployeeCount(employeeCount);
        setWorkTime(workTime);
        setEmail(email);
    }

    public Bank(String name, String address, String phone, int employeeCount, String workTime, String email) {
        setName(name);
        setAddress(address);
        setPhone(phone);
        setEmployeeCount(employeeCount);
        setWorkTime(workTime);
        setEmail(email);
    }


    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getEmployeeCount ()
    {
        return employeeCount;
    }

    public void setEmployeeCount (int employeeCount)
    {
        this.employeeCount = employeeCount;
    }

    public String getWorkTime ()
    {
        return workTime;
    }

    public void setWorkTime (String workTime)
    {
        this.workTime = workTime;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }


    @Override
    public String toString()
    {
        return "Bank [address = "+address+", phone = "+phone+", name = "+name+", id = "+ id +", employeeCount = "+employeeCount+", workTime = "+workTime+", email = "+email+"]";
    }
}
