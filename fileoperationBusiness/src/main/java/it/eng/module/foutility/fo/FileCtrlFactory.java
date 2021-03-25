package it.eng.module.foutility.fo;

public class FileCtrlFactory {

	public static CtrlBuilder getCtrlBuilder(){
		return new SpringCtrlBuilder();
	}
}
