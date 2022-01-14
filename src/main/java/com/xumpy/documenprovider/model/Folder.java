package com.xumpy.documenprovider.model;

public enum Folder {
    Overig(0),
    Aankoop(1),
	Verkoop(2),
	Bank(3),
	HRM(4),
	Belasting(5),
	Verzekering(6),
	UitzoekenYuki(7),
	Overigfinancieel(8),
	ZelfTeOrdenen(100),
	Correspondentie(101),
	Meetings(102);

    private Integer ordner;

    private Folder(Integer ordner){
        this.ordner = ordner;
    }

    public Integer getOrdner(){
    	return ordner;
	}

	public void setOrdner(Integer ordner){
		this.ordner = ordner;
	}
}
