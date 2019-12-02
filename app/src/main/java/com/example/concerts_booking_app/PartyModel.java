package com.example.concerts_booking_app;

public class PartyModel {
    String PartyCoverImage , PartyName , PartyTime  , PartyKey , PartyFirstClass , PartySecondClass , PartyThirdClass ;

    public PartyModel() {

    }

    public PartyModel(String partyCoverImage, String partyName, String partyTime, String partyKey, String partyFirstClass, String partySecondClass, String partyThirdClass) {
        PartyCoverImage = partyCoverImage;
        PartyName = partyName;
        PartyTime = partyTime;
        PartyKey = partyKey;
        PartyFirstClass = partyFirstClass;
        PartySecondClass = partySecondClass;
        PartyThirdClass = partyThirdClass;
    }

    public String getPartyCoverImage() {
        return PartyCoverImage;
    }

    public void setPartyCoverImage(String partyCoverImage) {
        PartyCoverImage = partyCoverImage;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getPartyTime() {
        return PartyTime;
    }

    public void setPartyTime(String partyTime) {
        PartyTime = partyTime;
    }

    public String getPartyKey() {
        return PartyKey;
    }

    public void setPartyKey(String partyKey) {
        PartyKey = partyKey;
    }

    public String getPartyFirstClass() {
        return PartyFirstClass;
    }

    public void setPartyFirstClass(String partyFirstClass) {
        PartyFirstClass = partyFirstClass;
    }

    public String getPartySecondClass() {
        return PartySecondClass;
    }

    public void setPartySecondClass(String partySecondClass) {
        PartySecondClass = partySecondClass;
    }

    public String getPartyThirdClass() {
        return PartyThirdClass;
    }

    public void setPartyThirdClass(String partyThirdClass) {
        PartyThirdClass = partyThirdClass;
    }
}
