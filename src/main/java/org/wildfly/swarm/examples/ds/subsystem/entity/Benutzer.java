package org.wildfly.swarm.examples.ds.subsystem.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Benutzer")
@NamedQueries({
    @NamedQuery(name = "Benutzer.findAll", query = "SELECT b FROM Benutzer b")
})
public class Benutzer implements Serializable{

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 50)
	private String vorname;

	@Column(length = 50)
	private String nachname;

	@Column(name = "unummer", length = 6)
	private String uNummer;

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getuNummer() {
		return uNummer;
	}

	public void setuNummer(String uNummer) {
		this.uNummer = uNummer;
	}
	
	  @Override
	    public boolean equals(Object obj) {
	        if (null == obj)
	            return false;
	        if (!(obj instanceof Benutzer))
	            return false;
	        Benutzer that = (Benutzer) obj;
	        if (that.nachname.equals(this.nachname) && that.vorname.equals(this.vorname) && that.id == this.id)
	            return true;
	        else
	            return false;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(this.id, this.nachname, this.vorname);
	}

}
