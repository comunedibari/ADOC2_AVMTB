import { Component } from "../Component";
import { html } from "../render/html";
import { Router } from "../Router";
import { DetailCell } from "./DetailCell";
import { Th } from "./Th";

export class AttiList extends Component {
    constructor(atti, showTipo = true, id = "attiList", classes = "table") {
        super();
        this.atti = atti;
        this.showTipo = showTipo;
        this.id = id;
        this.classes = classes;
    }

    async render() {
        if(Router.isStorico()){
            this.hrefPrefix = "storico";
        } else if (Router.isStoricoAut()) {
            this.hrefPrefix = "alboriservato";
        } else {
            this.hrefPrefix = "albo";
        }
        // this.hrefPrefix = Router.isStorico() ? "storico" : "albo";
        if (Router.isStoricoAut() && !(sessionStorage.getItem("islogged")=="logged")) {
            return html`
              ${new Login(config.username, config.password)}
            `;
        } else {
        let attiListHtml = await html`<table id="${this.id}" class="${this.classes}">
            <thead class="thead-light">
                <tr>
                    ${new Th("col", "large-2 colums", "Pubbl. N°")}    
                    ${new Th("col", "large-2 colums", "Atto N°")}    
                    ${new Th("col", "", "Dettaglio")}
                </tr>
            </thead>
            <tbody>`
        if (this.atti && this.atti.length > 0) {
            for (let index = 0; index < this.atti.length; index++) {
                let atto = this.atti[index];
                attiListHtml += await html`
                <tr id="ID${atto.idUD}">
                    <th scope="row">
                        <a class="text-info" href="#/${this.hrefPrefix}/atto/${atto.idUD}">${atto.nroPubbl}</a>
                    </th>
                    <td>
                        <a class="text-info" href="#/${this.hrefPrefix}/atto/${atto.idUD}">${atto.attoNumero}</a>
                    </td>
                    ${new DetailCell(atto, this.hrefPrefix)}`;

            }

        } else {
            attiListHtml += `<tr>
            <td colspan="3">
                <strong class="text-secondary">Nessun atto presente</strong>
            </td>
            </tr>`;
        }
        attiListHtml += `</tbody></table>`;
        return html`${attiListHtml}`;
    }
    }

}