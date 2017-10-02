package com.mk.androidtest.Models;

public class Meta
{
    private String status;

    private String msg;

    private String response_id;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getMsg ()
    {
        return msg;
    }

    public void setMsg (String msg)
    {
        this.msg = msg;
    }

    public String getResponse_id ()
    {
        return response_id;
    }

    public void setResponse_id (String response_id)
    {
        this.response_id = response_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", msg = "+msg+", response_id = "+response_id+"]";
    }
}
