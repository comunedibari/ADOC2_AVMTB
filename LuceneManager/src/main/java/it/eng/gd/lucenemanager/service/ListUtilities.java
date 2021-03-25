package it.eng.gd.lucenemanager.service;

import java.util.List;

/**
 * restituisce l'intersezione di due liste 
 * @author jravagnan
 *
 */
public class ListUtilities {
	
	public static List<String> intersect(List<String> a,List<String> b){
			  List<String> lstIntersectAB = a;
			  lstIntersectAB.retainAll(b);
			  return lstIntersectAB;
			}
	

}
