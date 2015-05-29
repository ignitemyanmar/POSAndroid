
package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seller {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String email;
    @SerializedName("code_no")
    @Expose
    private String codeNo;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The codeNo
     */
    public String getCodeNo() {
        return codeNo;
    }

    /**
     * 
     * @param codeNo
     *     The code_no
     */
    public void setCodeNo(String codeNo) {
        this.codeNo = codeNo;
    }

	@Override
	public String toString() {
		return "Seller [id=" + id + ", name=" + name + ", email=" + email
				+ ", codeNo=" + codeNo + "]";
	}
    
    

}
