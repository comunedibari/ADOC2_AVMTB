package it.eng.auriga.util;


public enum PosizioneFirma {
	
	ALTO_DESTRA("ALTO_DESTRA"),
	ALTO_SINISTRA("ALTO_SINISTRA"),
	BASSO_DESTRA("BASSO_DESTRA"),
	BASSO_SINISTRA("BASSO_SINISTRA");
	
	private final String value;

	PosizioneFirma(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PosizioneFirma fromValue(String v) {
        for (PosizioneFirma c: PosizioneFirma.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}