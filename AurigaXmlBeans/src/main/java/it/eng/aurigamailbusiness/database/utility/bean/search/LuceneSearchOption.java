package it.eng.aurigamailbusiness.database.utility.bean.search;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum LuceneSearchOption implements Serializable{

	JUST_ONE_WORD, ALL_WORD
}
