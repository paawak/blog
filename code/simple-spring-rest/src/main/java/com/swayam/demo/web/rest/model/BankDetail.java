package com.swayam.demo.web.rest.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BankDetail {

    private int id;
    private int age;
    private String job;
    private String marital;
    private String education;
    private String defaulted;
    private BigDecimal balance;
    private String housing;
    private String loan;
    private String contact;
    private int day;
    private String month;
    private int duration;
    private int campaign;
    private int pdays;
    private int previous;
    private String poutcome;
    private String y;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getAge() {
	return age;
    }

    public void setAge(int age) {
	this.age = age;
    }

    public String getJob() {
	return job;
    }

    public void setJob(String job) {
	this.job = job;
    }

    public String getMarital() {
	return marital;
    }

    public void setMarital(String marital) {
	this.marital = marital;
    }

    public String getEducation() {
	return education;
    }

    public void setEducation(String education) {
	this.education = education;
    }

    public String getDefaulted() {
	return defaulted;
    }

    public void setDefaulted(String defaulted) {
	this.defaulted = defaulted;
    }

    public BigDecimal getBalance() {
	return balance;
    }

    public void setBalance(BigDecimal balance) {
	this.balance = balance;
    }

    public String getHousing() {
	return housing;
    }

    public void setHousing(String housing) {
	this.housing = housing;
    }

    public String getLoan() {
	return loan;
    }

    public void setLoan(String loan) {
	this.loan = loan;
    }

    public String getContact() {
	return contact;
    }

    public void setContact(String contact) {
	this.contact = contact;
    }

    public int getDay() {
	return day;
    }

    public void setDay(int day) {
	this.day = day;
    }

    public String getMonth() {
	return month;
    }

    public void setMonth(String month) {
	this.month = month;
    }

    public int getDuration() {
	return duration;
    }

    public void setDuration(int duration) {
	this.duration = duration;
    }

    public int getCampaign() {
	return campaign;
    }

    public void setCampaign(int campaign) {
	this.campaign = campaign;
    }

    public int getPdays() {
	return pdays;
    }

    public void setPdays(int pdays) {
	this.pdays = pdays;
    }

    public int getPrevious() {
	return previous;
    }

    public void setPrevious(int previous) {
	this.previous = previous;
    }

    public String getPoutcome() {
	return poutcome;
    }

    public void setPoutcome(String poutcome) {
	this.poutcome = poutcome;
    }

    public String getY() {
	return y;
    }

    public void setY(String y) {
	this.y = y;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + age;
	result = prime * result + ((balance == null) ? 0 : balance.hashCode());
	result = prime * result + campaign;
	result = prime * result + ((contact == null) ? 0 : contact.hashCode());
	result = prime * result + day;
	result = prime * result + ((defaulted == null) ? 0 : defaulted.hashCode());
	result = prime * result + duration;
	result = prime * result + ((education == null) ? 0 : education.hashCode());
	result = prime * result + ((housing == null) ? 0 : housing.hashCode());
	result = prime * result + id;
	result = prime * result + ((job == null) ? 0 : job.hashCode());
	result = prime * result + ((loan == null) ? 0 : loan.hashCode());
	result = prime * result + ((marital == null) ? 0 : marital.hashCode());
	result = prime * result + ((month == null) ? 0 : month.hashCode());
	result = prime * result + pdays;
	result = prime * result + ((poutcome == null) ? 0 : poutcome.hashCode());
	result = prime * result + previous;
	result = prime * result + ((y == null) ? 0 : y.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	BankDetail other = (BankDetail) obj;
	if (age != other.age)
	    return false;
	if (balance == null) {
	    if (other.balance != null)
		return false;
	} else if (!balance.equals(other.balance))
	    return false;
	if (campaign != other.campaign)
	    return false;
	if (contact == null) {
	    if (other.contact != null)
		return false;
	} else if (!contact.equals(other.contact))
	    return false;
	if (day != other.day)
	    return false;
	if (defaulted == null) {
	    if (other.defaulted != null)
		return false;
	} else if (!defaulted.equals(other.defaulted))
	    return false;
	if (duration != other.duration)
	    return false;
	if (education == null) {
	    if (other.education != null)
		return false;
	} else if (!education.equals(other.education))
	    return false;
	if (housing == null) {
	    if (other.housing != null)
		return false;
	} else if (!housing.equals(other.housing))
	    return false;
	if (id != other.id)
	    return false;
	if (job == null) {
	    if (other.job != null)
		return false;
	} else if (!job.equals(other.job))
	    return false;
	if (loan == null) {
	    if (other.loan != null)
		return false;
	} else if (!loan.equals(other.loan))
	    return false;
	if (marital == null) {
	    if (other.marital != null)
		return false;
	} else if (!marital.equals(other.marital))
	    return false;
	if (month == null) {
	    if (other.month != null)
		return false;
	} else if (!month.equals(other.month))
	    return false;
	if (pdays != other.pdays)
	    return false;
	if (poutcome == null) {
	    if (other.poutcome != null)
		return false;
	} else if (!poutcome.equals(other.poutcome))
	    return false;
	if (previous != other.previous)
	    return false;
	if (y == null) {
	    if (other.y != null)
		return false;
	} else if (!y.equals(other.y))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "BankDetail [id=" + id + ", age=" + age + ", job=" + job + ", marital=" + marital + ", education=" + education + ", defaulted=" + defaulted + ", balance=" + balance + ", housing="
		+ housing + ", loan=" + loan + ", contact=" + contact + ", day=" + day + ", month=" + month + ", duration=" + duration + ", campaign=" + campaign + ", pdays=" + pdays + ", previous="
		+ previous + ", poutcome=" + poutcome + ", y=" + y + "]";
    }

}
