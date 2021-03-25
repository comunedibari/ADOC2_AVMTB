package it.eng.module.foutility.fo;

import java.util.Comparator;

public class PriorityComparator implements Comparator<IFileController> {

	public int compare(IFileController o1, IFileController o2) {
		 
		int prio1 = o1.getPriority();        
        int prio2 = o2.getPriority();
       
        if(prio1 > prio2)
            return 1;
        else if(prio1 < prio2)
            return -1;
        else
            return 0; 
	}

	 

}
