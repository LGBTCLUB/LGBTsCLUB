package com.lgbt.LGBTsCLUB.model.usermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchModel {
    @Expose
    @SerializedName("result")
    private List<ResultEntity> result;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("response")
    private String response;

    public List<ResultEntity> getResult() {
        return result;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public static class ResultEntity {
        @Expose
        @SerializedName("fd")
        private String fd;
        @Expose
        @SerializedName("verify_otp")
        private String verifyOtp;
        @Expose
        @SerializedName("verify_status")
        private String verifyStatus;
        @Expose
        @SerializedName("reason")
        private String reason;
        @Expose
        @SerializedName("edit_status")
        private String editStatus;
        @Expose
        @SerializedName("show_gender")
        private String showGender;
        @Expose
        @SerializedName("PE_Occupation")
        private String peOccupation;
        @Expose
        @SerializedName("nationality")
        private String nationality;
        @Expose
        @SerializedName("profile_status")
        private String profileStatus;
        @Expose
        @SerializedName("devicetoken")
        private String devicetoken;
        @Expose
        @SerializedName("Expectations_app")
        private String expectationsApp;
        @Expose
        @SerializedName("modified")
        private String modified;
        @Expose
        @SerializedName("otp")
        private String otp;
        @Expose
        @SerializedName("OnlineUsers")
        private String onlineusers;
        @Expose
        @SerializedName("verify_email")
        private String verifyEmail;
        @Expose
        @SerializedName("PE_Height2")
        private String peHeight2;
        @Expose
        @SerializedName("pagecount")
        private String pagecount;
        @Expose
        @SerializedName("Lastlogin")
        private String lastlogin;
        @Expose
        @SerializedName("photo_remind_cnt")
        private String photoRemindCnt;
        @Expose
        @SerializedName("Photo7Approve")
        private String photo7approve;
        @Expose
        @SerializedName("Photo6Approve")
        private String photo6approve;
        @Expose
        @SerializedName("Photo5Approve")
        private String photo5approve;
        @Expose
        @SerializedName("Photo4Approve")
        private String photo4approve;
        @Expose
        @SerializedName("Photo3Approve")
        private String photo3approve;
        @Expose
        @SerializedName("Photo7")
        private String photo7;
        @Expose
        @SerializedName("Photo6")
        private String photo6;
        @Expose
        @SerializedName("Photo5")
        private String photo5;
        @Expose
        @SerializedName("Photo4")
        private String photo4;
        @Expose
        @SerializedName("Photo3")
        private String photo3;
        @Expose
        @SerializedName("Photo2Approve")
        private String photo2approve;
        @Expose
        @SerializedName("Photo2")
        private String photo2;
        @Expose
        @SerializedName("profile_approve")
        private String profileApprove;
        @Expose
        @SerializedName("Photo1Approve")
        private String photo1approve;
        @Expose
        @SerializedName("Photo1")
        private String photo1;
        @Expose
        @SerializedName("Orderstatus")
        private String orderstatus;
        @Expose
        @SerializedName("chatcontact")
        private String chatcontact;
        @Expose
        @SerializedName("Noofcontacts")
        private String noofcontacts;
        @Expose
        @SerializedName("expdays")
        private String expdays;
        @Expose
        @SerializedName("MemshipExpiryDate")
        private String memshipexpirydate;
        @Expose
        @SerializedName("Regdate")
        private String regdate;
        @Expose
        @SerializedName("memtype")
        private String memtype;
        @Expose
        @SerializedName("Status")
        private String status;
        @Expose
        @SerializedName("Hobbies")
        private String hobbies;
        @Expose
        @SerializedName("PE_Countrylivingin")
        private String peCountrylivingin;
        @Expose
        @SerializedName("PE_Education")
        private String peEducation;
        @Expose
        @SerializedName("PartnerExpectations")
        private String partnerexpectations;
        @Expose
        @SerializedName("PE_Height")
        private String peHeight;
        @Expose
        @SerializedName("PE_ToAge")
        private String peToage;
        @Expose
        @SerializedName("PE_FromAge")
        private String peFromage;
        @Expose
        @SerializedName("Profile")
        private String profile;
        @Expose
        @SerializedName("mobilecode")
        private String mobilecode;
        @Expose
        @SerializedName("Mobile")
        private String mobile;
        @Expose
        @SerializedName("phonehide")
        private String phonehide;
        @Expose
        @SerializedName("Country")
        private String country;
        @Expose
        @SerializedName("State")
        private String state;
        @Expose
        @SerializedName("Postal")
        private String postal;
        @Expose
        @SerializedName("city_name")
        private String cityName;
        @Expose
        @SerializedName("City")
        private String city;
        @Expose
        @SerializedName("spe_cases")
        private String speCases;
        @Expose
        @SerializedName("Height")
        private String height;
        @Expose
        @SerializedName("Language")
        private String language;
        @Expose
        @SerializedName("Religion")
        private String religion;
        @Expose
        @SerializedName("Annualincome")
        private String annualincome;
        @Expose
        @SerializedName("Occupation")
        private String occupation;
        @Expose
        @SerializedName("Education")
        private String education;
        @Expose
        @SerializedName("childrenlivingstatus")
        private String childrenlivingstatus;
        @Expose
        @SerializedName("Maritalstatus")
        private String maritalstatus;
        @Expose
        @SerializedName("Age")
        private String age;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("Gender")
        private String gender;
        @Expose
        @SerializedName("Name")
        private String name;
        @Expose
        @SerializedName("ConfirmPassword")
        private String confirmpassword;
        @Expose
        @SerializedName("ConfirmEmail")
        private String confirmemail;
        @Expose
        @SerializedName("Termsofservice")
        private String termsofservice;
        @Expose
        @SerializedName("Prefix")
        private String prefix;
        @Expose
        @SerializedName("MatriID")
        private String matriid;
        @Expose
        @SerializedName("ID")
        private String id;

        public String getFd() {
            return fd;
        }

        public void setFd(String fd) {
            this.fd = fd;
        }

        public String getVerifyOtp() {
            return verifyOtp;
        }

        public void setVerifyOtp(String verifyOtp) {
            this.verifyOtp = verifyOtp;
        }

        public String getVerifyStatus() {
            return verifyStatus;
        }

        public void setVerifyStatus(String verifyStatus) {
            this.verifyStatus = verifyStatus;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getEditStatus() {
            return editStatus;
        }

        public void setEditStatus(String editStatus) {
            this.editStatus = editStatus;
        }

        public String getShowGender() {
            return showGender;
        }

        public void setShowGender(String showGender) {
            this.showGender = showGender;
        }

        public String getPeOccupation() {
            return peOccupation;
        }

        public void setPeOccupation(String peOccupation) {
            this.peOccupation = peOccupation;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getProfileStatus() {
            return profileStatus;
        }

        public void setProfileStatus(String profileStatus) {
            this.profileStatus = profileStatus;
        }

        public String getDevicetoken() {
            return devicetoken;
        }

        public void setDevicetoken(String devicetoken) {
            this.devicetoken = devicetoken;
        }

        public String getExpectationsApp() {
            return expectationsApp;
        }

        public void setExpectationsApp(String expectationsApp) {
            this.expectationsApp = expectationsApp;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getOnlineusers() {
            return onlineusers;
        }

        public void setOnlineusers(String onlineusers) {
            this.onlineusers = onlineusers;
        }

        public String getVerifyEmail() {
            return verifyEmail;
        }

        public void setVerifyEmail(String verifyEmail) {
            this.verifyEmail = verifyEmail;
        }

        public String getPeHeight2() {
            return peHeight2;
        }

        public void setPeHeight2(String peHeight2) {
            this.peHeight2 = peHeight2;
        }

        public String getPagecount() {
            return pagecount;
        }

        public void setPagecount(String pagecount) {
            this.pagecount = pagecount;
        }

        public String getLastlogin() {
            return lastlogin;
        }

        public void setLastlogin(String lastlogin) {
            this.lastlogin = lastlogin;
        }

        public String getPhotoRemindCnt() {
            return photoRemindCnt;
        }

        public void setPhotoRemindCnt(String photoRemindCnt) {
            this.photoRemindCnt = photoRemindCnt;
        }

        public String getPhoto7approve() {
            return photo7approve;
        }

        public void setPhoto7approve(String photo7approve) {
            this.photo7approve = photo7approve;
        }

        public String getPhoto6approve() {
            return photo6approve;
        }

        public void setPhoto6approve(String photo6approve) {
            this.photo6approve = photo6approve;
        }

        public String getPhoto5approve() {
            return photo5approve;
        }

        public void setPhoto5approve(String photo5approve) {
            this.photo5approve = photo5approve;
        }

        public String getPhoto4approve() {
            return photo4approve;
        }

        public void setPhoto4approve(String photo4approve) {
            this.photo4approve = photo4approve;
        }

        public String getPhoto3approve() {
            return photo3approve;
        }

        public void setPhoto3approve(String photo3approve) {
            this.photo3approve = photo3approve;
        }

        public String getPhoto7() {
            return photo7;
        }

        public void setPhoto7(String photo7) {
            this.photo7 = photo7;
        }

        public String getPhoto6() {
            return photo6;
        }

        public void setPhoto6(String photo6) {
            this.photo6 = photo6;
        }

        public String getPhoto5() {
            return photo5;
        }

        public void setPhoto5(String photo5) {
            this.photo5 = photo5;
        }

        public String getPhoto4() {
            return photo4;
        }

        public void setPhoto4(String photo4) {
            this.photo4 = photo4;
        }

        public String getPhoto3() {
            return photo3;
        }

        public void setPhoto3(String photo3) {
            this.photo3 = photo3;
        }

        public String getPhoto2approve() {
            return photo2approve;
        }

        public void setPhoto2approve(String photo2approve) {
            this.photo2approve = photo2approve;
        }

        public String getPhoto2() {
            return photo2;
        }

        public void setPhoto2(String photo2) {
            this.photo2 = photo2;
        }

        public String getProfileApprove() {
            return profileApprove;
        }

        public void setProfileApprove(String profileApprove) {
            this.profileApprove = profileApprove;
        }

        public String getPhoto1approve() {
            return photo1approve;
        }

        public void setPhoto1approve(String photo1approve) {
            this.photo1approve = photo1approve;
        }

        public String getPhoto1() {
            return photo1;
        }

        public void setPhoto1(String photo1) {
            this.photo1 = photo1;
        }

        public String getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(String orderstatus) {
            this.orderstatus = orderstatus;
        }

        public String getChatcontact() {
            return chatcontact;
        }

        public void setChatcontact(String chatcontact) {
            this.chatcontact = chatcontact;
        }

        public String getNoofcontacts() {
            return noofcontacts;
        }

        public void setNoofcontacts(String noofcontacts) {
            this.noofcontacts = noofcontacts;
        }

        public String getExpdays() {
            return expdays;
        }

        public void setExpdays(String expdays) {
            this.expdays = expdays;
        }

        public String getMemshipexpirydate() {
            return memshipexpirydate;
        }

        public void setMemshipexpirydate(String memshipexpirydate) {
            this.memshipexpirydate = memshipexpirydate;
        }

        public String getRegdate() {
            return regdate;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }

        public String getMemtype() {
            return memtype;
        }

        public void setMemtype(String memtype) {
            this.memtype = memtype;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHobbies() {
            return hobbies;
        }

        public void setHobbies(String hobbies) {
            this.hobbies = hobbies;
        }

        public String getPeCountrylivingin() {
            return peCountrylivingin;
        }

        public void setPeCountrylivingin(String peCountrylivingin) {
            this.peCountrylivingin = peCountrylivingin;
        }

        public String getPeEducation() {
            return peEducation;
        }

        public void setPeEducation(String peEducation) {
            this.peEducation = peEducation;
        }

        public String getPartnerexpectations() {
            return partnerexpectations;
        }

        public void setPartnerexpectations(String partnerexpectations) {
            this.partnerexpectations = partnerexpectations;
        }

        public String getPeHeight() {
            return peHeight;
        }

        public void setPeHeight(String peHeight) {
            this.peHeight = peHeight;
        }

        public String getPeToage() {
            return peToage;
        }

        public void setPeToage(String peToage) {
            this.peToage = peToage;
        }

        public String getPeFromage() {
            return peFromage;
        }

        public void setPeFromage(String peFromage) {
            this.peFromage = peFromage;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getMobilecode() {
            return mobilecode;
        }

        public void setMobilecode(String mobilecode) {
            this.mobilecode = mobilecode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPhonehide() {
            return phonehide;
        }

        public void setPhonehide(String phonehide) {
            this.phonehide = phonehide;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostal() {
            return postal;
        }

        public void setPostal(String postal) {
            this.postal = postal;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSpeCases() {
            return speCases;
        }

        public void setSpeCases(String speCases) {
            this.speCases = speCases;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getAnnualincome() {
            return annualincome;
        }

        public void setAnnualincome(String annualincome) {
            this.annualincome = annualincome;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getChildrenlivingstatus() {
            return childrenlivingstatus;
        }

        public void setChildrenlivingstatus(String childrenlivingstatus) {
            this.childrenlivingstatus = childrenlivingstatus;
        }

        public String getMaritalstatus() {
            return maritalstatus;
        }

        public void setMaritalstatus(String maritalstatus) {
            this.maritalstatus = maritalstatus;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getConfirmpassword() {
            return confirmpassword;
        }

        public void setConfirmpassword(String confirmpassword) {
            this.confirmpassword = confirmpassword;
        }

        public String getConfirmemail() {
            return confirmemail;
        }

        public void setConfirmemail(String confirmemail) {
            this.confirmemail = confirmemail;
        }

        public String getTermsofservice() {
            return termsofservice;
        }

        public void setTermsofservice(String termsofservice) {
            this.termsofservice = termsofservice;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getMatriid() {
            return matriid;
        }

        public void setMatriid(String matriid) {
            this.matriid = matriid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
