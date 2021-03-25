/**
 * Classe bean del Tipo documento
 * 
 */
class TipoDocumento {
    constructor(tipoDocumento) {
     this.idType = tipoDocumento.idType;
     this.descrizione = tipoDocumento.descrizione;
     this.children = tipoDocumento.children
    }
  }
  
  export default TipoDocumento;
  